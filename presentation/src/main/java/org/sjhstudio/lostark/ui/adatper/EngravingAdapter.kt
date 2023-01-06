package org.sjhstudio.lostark.ui.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.lostark.databinding.ItemEngravingBinding
import org.sjhstudio.lostark.domain.model.response.Engraving

class EngravingAdapter :
    ListAdapter<Engraving.Effect, EngravingAdapter.EngravingViewHolder>(diffCallback) {

    inner class EngravingViewHolder(private val binding: ItemEngravingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                container.setOnClickListener {
                    adapterPosition.takeIf { it != RecyclerView.NO_POSITION }?.let {  pos ->
                        val item = getItem(pos)
                        val expand = item.isExpanded

                        item.isExpanded = !expand
                        notifyItemChanged(pos)
                    }
                }
            }
        }

        fun bind(effect: Engraving.Effect) {
            with(binding) {
                data = effect
                tvEngravingDescription.isVisible = effect.isExpanded
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EngravingViewHolder {
        val binding =
            ItemEngravingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EngravingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EngravingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Engraving.Effect>() {
            override fun areItemsTheSame(oldItem: Engraving.Effect, newItem: Engraving.Effect) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Engraving.Effect, newItem: Engraving.Effect) =
                oldItem == newItem
        }
    }
}