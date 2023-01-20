package org.sjhstudio.lostark.ui.adatper

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.lostark.databinding.ItemBraceletSpecialEffectBinding
import org.sjhstudio.lostark.domain.model.response.Equipment

class BraceletEffectAdapter :
    ListAdapter<Equipment.Effect, BraceletEffectAdapter.BraceletEffectViewHolder>(diffCallback) {

    inner class BraceletEffectViewHolder(private val binding: ItemBraceletSpecialEffectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(effect: Equipment.Effect) {
            with(binding) {
                tvSpecialEffectName.text = Html.fromHtml(effect.name, Html.FROM_HTML_MODE_COMPACT)
                tvSpecialEffectValue.text = Html.fromHtml(effect.value, Html.FROM_HTML_MODE_COMPACT)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BraceletEffectViewHolder {
        val binding = ItemBraceletSpecialEffectBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BraceletEffectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BraceletEffectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Equipment.Effect>() {
            override fun areItemsTheSame(
                oldItem: Equipment.Effect,
                newItem: Equipment.Effect
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: Equipment.Effect,
                newItem: Equipment.Effect
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}