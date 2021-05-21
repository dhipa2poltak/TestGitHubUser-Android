package com.testkrealogi.testgithubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testkrealogi.testgithubuser.databinding.HistoryItemBinding
import com.testkrealogi.testgithubuser.model.History

class HistoryAdapter(private val histories: MutableList<History>): RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {

  private lateinit var binding: HistoryItemBinding

  var onTapHistoryListener: ((History) -> Unit)? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
    binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return HistoryHolder(binding)
  }

  override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
    holder.bindData(histories[position])
  }

  override fun getItemCount(): Int {
    return histories.size
  }

  fun swapData(histories: List<History>) {
    this.histories.clear()
    this.histories.addAll(histories)
    notifyDataSetChanged()
  }

  inner class HistoryHolder(private val binding: HistoryItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(history: History) {
      binding.textHistory.text = history.text

      binding.cardItem.setOnClickListener {
        onTapHistoryListener?.let { param -> param(history) }
      }
    }
  }
}
