package org.sjhstudio.lostark.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.sjhstudio.lostark.databinding.ItemSearchHistoryBinding
import org.sjhstudio.lostark.databinding.ItemSearchHistorySummaryBinding
import org.sjhstudio.lostark.domain.model.response.History

class SearchHistoryAdapter(
    private val viewType: Int = DETAIL_VIEW_TYPE,
    private val onClick: (history: History) -> Unit,
    private val onDelete: (history: History) -> Unit,
) : ListAdapter<History, ViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<History>() {
            override fun areItemsTheSame(oldItem: History, newItem: History) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: History, newItem: History) =
                oldItem == newItem
        }
        const val DETAIL_VIEW_TYPE = 0
        const val SUMMARY_VIEW_TYPE = 1
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == DETAIL_VIEW_TYPE) {
            SearchHistoryViewHolder(
                ItemSearchHistoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            SearchHistorySummaryViewHolder(
                ItemSearchHistorySummaryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is SearchHistoryViewHolder -> {
                holder.bind(currentList[position])
            }
            is SearchHistorySummaryViewHolder -> {
                holder.bind(currentList[position])
            }
        }
    }

    inner class SearchHistoryViewHolder(private val binding: ItemSearchHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener { click(onClick) }
            binding.ivDelete.setOnClickListener { click(onDelete) }
        }

        fun bind(data: History) {
            with(binding) {
                tvName.text = data.name
                tvLevel.text = data.level
                tvClassName.text = data.className
            }
        }

        private fun click(invoke: (history: History) -> Unit) {
            adapterPosition.takeIf { pos -> pos != RecyclerView.NO_POSITION }
                ?.let { pos -> invoke(currentList[pos]) }
        }
    }

    inner class SearchHistorySummaryViewHolder(private val binding: ItemSearchHistorySummaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener { click(onClick) }
            binding.ivDelete.setOnClickListener { click(onDelete) }
        }

        fun bind(data: History) {
            with(binding) {
                tvName.text = data.name
            }
        }

        private fun click(invoke: (history: History) -> Unit) {
            adapterPosition.takeIf { pos -> pos != RecyclerView.NO_POSITION }
                ?.let { pos -> invoke(currentList[pos]) }
        }
    }
}
