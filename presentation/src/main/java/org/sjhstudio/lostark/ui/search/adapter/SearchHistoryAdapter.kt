package org.sjhstudio.lostark.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.sjhstudio.lostark.databinding.ItemSearchHistoryBinding
import org.sjhstudio.lostark.domain.model.response.History

class SearchHistoryAdapter(
    private val onClick: (history: History) -> Unit
) : ListAdapter<History, ViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<History>() {
            override fun areItemsTheSame(oldItem: History, newItem: History) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: History, newItem: History) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return SearchHistoryViewHolder(
            ItemSearchHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is SearchHistoryViewHolder -> {
                holder.bind(currentList[position])
            }
        }
    }

    inner class SearchHistoryViewHolder(private val binding: ItemSearchHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                adapterPosition.takeIf { pos -> pos != RecyclerView.NO_POSITION }
                    ?.let { position ->
                        onClick.invoke(currentList[position])
                    }
            }
        }

        fun bind(data: History) {
            with(binding) {
                tvName.text = data.name
                tvLevel.text = data.level
                tvClassName.text = data.className
            }
        }
    }
}
