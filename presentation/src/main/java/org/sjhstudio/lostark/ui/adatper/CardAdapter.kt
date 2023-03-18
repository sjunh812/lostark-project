package org.sjhstudio.lostark.ui.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sjhstudio.lostark.databinding.ItemCardBinding
import org.sjhstudio.lostark.domain.model.response.CardInfo

class CardAdapter : ListAdapter<CardInfo, CardAdapter.CardViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CardInfo>() {
            override fun areItemsTheSame(oldItem: CardInfo, newItem: CardInfo) =
                oldItem.slot == newItem.slot

            override fun areContentsTheSame(oldItem: CardInfo, newItem: CardInfo) =
                oldItem == newItem
        }
    }

    inner class CardViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(card: CardInfo) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(card.iconUrl)
                    .into(ivCard)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            ItemCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}