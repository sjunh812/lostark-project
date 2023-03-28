package org.sjhstudio.lostark.ui.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.lostark.R
import org.sjhstudio.lostark.databinding.ItemCardEffectDetailBinding
import org.sjhstudio.lostark.domain.model.response.CardEffect
import org.sjhstudio.lostark.util.dpToPx

class CardEffectDetailAdapter :
    ListAdapter<CardEffect, CardEffectDetailAdapter.CardEffectDetailViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CardEffect>() {
            override fun areItemsTheSame(oldItem: CardEffect, newItem: CardEffect) =
                oldItem.index == newItem.index

            override fun areContentsTheSame(oldItem: CardEffect, newItem: CardEffect) =
                oldItem == newItem
        }
    }

    inner class CardEffectDetailViewHolder(private val binding: ItemCardEffectDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(effect: CardEffect) {
            with(binding) {
                tvCardEffectName.text = effect.items.last().name
                println("xxx ${effect.items.size}")
                effect.items.forEach {
                    val tvTag = TextView(itemView.context, null, 0, R.style.Widget_LostArk_Tag)
                        .apply {
                            minWidth = itemView.context.dpToPx(45).toInt()
                            text = if (it.awake != null) "${it.awake}각성" else "${it.set}세트"
                        }
                    val tvDescription = TextView(itemView.context)
                        .apply {
                            setPadding(20, 0, 0, 0)
                            setTextAppearance(R.style.TextAppearance_LostArk_Overline)
                            text = it.description
                        }
                    val containerItem = LinearLayout(itemView.context)
                        .apply {
                            orientation = LinearLayout.HORIZONTAL
                            setPadding(12)
                            addView(tvTag)
                            addView(tvDescription)
                        }

                    layoutContainer.addView(containerItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardEffectDetailViewHolder {
        return CardEffectDetailViewHolder(
            ItemCardEffectDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardEffectDetailViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}