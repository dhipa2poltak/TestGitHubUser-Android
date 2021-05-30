package com.dpfht.testgithubuser.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.dpfht.testgithubuser.R
import com.dpfht.testgithubuser.databinding.DetailFragmentBinding
import org.koin.android.ext.android.inject

class DetailFragment : Fragment() {

  private lateinit var binding: DetailFragmentBinding
  //private val viewModel: DetailViewModel by viewModel()
  private val picasso: Picasso by inject()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DetailFragmentBinding.inflate(layoutInflater, container, false)

    return binding.root
  }

  @Suppress("DEPRECATION")
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val args = DetailFragmentArgs.fromBundle(requireArguments())
    val login = args.login
    val avatarUrl = args.avatarUrl

    binding.tvLogin.text = login

    picasso.load(avatarUrl)
      .error(android.R.drawable.ic_menu_close_clear_cancel)
      .placeholder(R.drawable.loading)
      .into(binding.ivAvatar)
  }
}
