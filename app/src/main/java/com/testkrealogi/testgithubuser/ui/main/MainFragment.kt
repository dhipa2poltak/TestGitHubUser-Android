package com.testkrealogi.testgithubuser.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.testkrealogi.testgithubuser.databinding.MainFragmentBinding
import com.testkrealogi.testgithubuser.ui.adapter.HistoryAdapter
import com.testkrealogi.testgithubuser.ui.adapter.UserAdapter
import com.testkrealogi.testgithubuser.ui.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainFragment : BaseFragment() {

  private lateinit var binding: MainFragmentBinding
  private val viewModel: MainViewModel by viewModel()

  private val historyAdapter: HistoryAdapter by inject()
  private val userAdapter: UserAdapter by inject { parametersOf(viewModel) }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = MainFragmentBinding.inflate(layoutInflater, container, false)

    return binding.root
  }

  @Suppress("DEPRECATION")
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    binding.rvUser.layoutManager =
      LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    binding.rvUser.addItemDecoration(
      DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
    )

    userAdapter.onTapUserListener = { user ->
      if (user.login != null && user.avatarUrl != null) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment()
        action.login = user.login
        action.avatarUrl = user.avatarUrl
        Navigation.findNavController(requireView()).navigate(action)
      }
    }
    binding.rvUser.adapter = userAdapter

    binding.rvHistory.layoutManager =
      LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    binding.rvHistory.addItemDecoration(
      DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
    )

    historyAdapter.onTapHistoryListener = { history ->
      binding.etSearch.setText(history.text)
      //viewModel.searchText = history.text
      viewModel.processInputUserName()
    }
    binding.rvHistory.adapter = historyAdapter

    //--

    binding.ibSearch.setOnClickListener {
      viewModel.processInputUserName()
    }

    binding.rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val xx = recyclerView.computeVerticalScrollRange()
        val xy = recyclerView.computeVerticalScrollOffset()
        val xz = recyclerView.computeVerticalScrollExtent()
        val zz = (xy.toFloat() / (xx - xz).toFloat() * 100).toInt()
        if (zz >= 75 && !viewModel.isLoadingData) {
          viewModel.doSearchUsers(viewModel.searchText, viewModel.page + 1)
        }
        super.onScrolled(recyclerView, dx, dy)
      }
    })

    binding.etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
      override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
          viewModel.processInputUserName()

          return true
        }

        return false
      }
    })

    //--

    viewModel.toastMessage.observe(viewLifecycleOwner, { value ->
      if (value != null && value.isNotEmpty()) {
        Toast.makeText(requireContext(), value, Toast.LENGTH_SHORT).show()
        viewModel.clearToastMessage()
      }
    })

    viewModel.isShowDialogLoading.observe(viewLifecycleOwner, { value ->
      if (value) {
        if (viewModel.users.isEmpty()) {
          prgDialog.show()
        }
      } else {
        prgDialog.dismiss()
      }
    })

    viewModel.doNotifyUserDataSetChanged.observe(viewLifecycleOwner, { value ->
      if (value != null && value) {
        userAdapter.notifyDataSetChanged()
        viewModel.clearNotifyUserDataSetChanged()
      }
    })

    viewModel.doShowResult.observe(viewLifecycleOwner, { value ->
      if (value != null && value) {
        binding.rvHistory.visibility = View.GONE
        binding.rvUser.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.GONE
      }
    })

    viewModel.doShowNoData.observe(viewLifecycleOwner, { value ->
      if (value != null && value) {
        binding.rvHistory.visibility = View.GONE
        binding.rvUser.visibility = View.GONE
        binding.tvNoData.visibility = View.VISIBLE
      }
    })

    viewModel.userData.observe(viewLifecycleOwner, {
      userAdapter.notifyItemInserted(viewModel.users.size - 1)
    })

    viewModel.getHistoriesLiveData().observe(viewLifecycleOwner, { histories ->
      historyAdapter.swapData(histories)
    })

    binding.tvNoData.visibility = View.GONE
    if (viewModel.searchText.isEmpty()) {
      binding.rvHistory.visibility = View.VISIBLE
      binding.rvUser.visibility = View.GONE
      binding.tvNoData.visibility = View.GONE
    } else if (viewModel.users.size > 0) {
      binding.rvHistory.visibility = View.GONE
      binding.rvUser.visibility = View.VISIBLE
      binding.tvNoData.visibility = View.GONE
    }
  }

  private val myTextWatcher = object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
      viewModel.searchText = s.toString()
      viewModel.isNewSearch = true
      viewModel.isAllDataLoaded = false

      if (viewModel.searchText.isEmpty()) {
        viewModel.users.clear()
        viewModel.page = 1

        binding.rvHistory.visibility = View.VISIBLE
        binding.rvUser.visibility = View.GONE
        binding.tvNoData.visibility = View.GONE
        viewModel.clearShowResult()
        viewModel.clearShowNoData()
      }
    }
  }
  override fun onResume() {
    super.onResume()

    binding.etSearch.addTextChangedListener(myTextWatcher)
  }

  override fun onPause() {
    super.onPause()

    binding.etSearch.removeTextChangedListener(myTextWatcher)
  }
}
