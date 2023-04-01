package org.sjhstudio.lostark.ui.adatper

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.lostark.databinding.ItemCardEffectSummaryBinding
import org.sjhstudio.lostark.domain.model.response.CardEffect

class CardEffectSummaryAdapter :
    ListAdapter<CardEffect, CardEffectSummaryAdapter.CardEffectSummaryViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CardEffect>() {
            override fun areItemsTheSame(oldItem: CardEffect, newItem: CardEffect) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: CardEffect, newItem: CardEffect) =
                oldItem == newItem
        }
    }

    inner class CardEffectSummaryViewHolder(private val binding: ItemCardEffectSummaryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(effect: CardEffect) {
            with(binding) {
                if (effect.items.isNotEmpty()) {
                    tvCardEffectName.text = effect.items.lastOrNull()?.name.orEmpty()
                    tvCardEffectSet.apply {
                        text = "${effect.items.lastOrNull()?.set ?: ""}세트"
                        isVisible = effect.items.lastOrNull()?.set != null
                    }
                    tvCardEffectAwake.apply {
                        text = "${effect.items.lastOrNull()?.awake ?: ""}각성"
                        isVisible = effect.items.lastOrNull()?.awake != null
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardEffectSummaryViewHolder {
        return CardEffectSummaryViewHolder(
            ItemCardEffectSummaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardEffectSummaryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}