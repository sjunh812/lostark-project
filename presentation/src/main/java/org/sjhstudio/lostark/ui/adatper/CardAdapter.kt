package org.sjhstudio.lostark.ui.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sjhstudio.lostark.R
import org.sjhstudio.lostark.databinding.ItemCardBinding
import org.sjhstudio.lostark.domain.model.response.CardInfo
import org.sjhstudio.lostark.ui.viewmodel.MainViewModel
import org.sjhstudio.lostark.util.setCardBackground

class CardAdapter(
    private val viewModel: MainViewModel
) : ListAdapter<CardInfo, CardAdapter.CardViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CardInfo>() {
            override fun areItemsTheSame(oldItem: CardInfo, newItem: CardInfo) =
                oldItem.slot == newItem.slot

            override fun areContentsTheSame(oldItem: CardInfo, newItem: CardInfo) =
                oldItem == newItem
        }
    }

    inner class CardViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val ivAwakeList = listOf<ImageView>(
            binding.ivAwake1,
            binding.ivAwake2,
            binding.ivAwake3,
            binding.ivAwake4,
            binding.ivAwake5
        )

        fun bind(card: CardInfo) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(card.iconUrl)
                    .into(ivCard)

                ivCard.setCardBackground(card.grade)
                tvCard.apply {
                    text = card.name
                    isVisible = !viewModel.collapseCard.value
                }

                ivAwakeList.forEachIndexed { i, iv ->
                    Glide.with(iv.context)
                        .load(
                            ContextCompat.getDrawable(
                                iv.context,
                                if (i < card.awakeCount) R.drawable.img_awake_full else R.drawable.img_awake_empty
                            )
                        )
                        .into(iv)
                }
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