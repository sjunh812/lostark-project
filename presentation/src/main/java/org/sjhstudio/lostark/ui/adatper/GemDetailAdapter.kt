package org.sjhstudio.lostark.ui.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import org.sjhstudio.lostark.databinding.ItemGemDetailBinding
import org.sjhstudio.lostark.domain.model.response.Gem
import org.sjhstudio.lostark.util.setGemName
import org.sjhstudio.lostark.util.setItemBackground

class GemDetailAdapter :
    ListAdapter<Gem.GemInfo, GemDetailAdapter.GemDetailViewHolder>(diffCallback) {

    inner class GemDetailViewHolder(private val binding: ItemGemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gemInfo: Gem.GemInfo) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(gemInfo.iconUrl)
                    .into(ivGemDetail)

                ivGemDetail.setItemBackground(gemInfo.grade)
                tvGemDetailName.setGemName(gemInfo)

                gemInfo.effect?.let { effect ->
                    Glide.with(itemView.context)
                        .load(effect.iconUrl)
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                        .into(ivGemDetailEffect)

                    tvGemDetailEffectName.text = effect.name
                    tvGemDetailEffectDescription.text = effect.description
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GemDetailViewHolder {
        val binding =
            ItemGemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GemDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GemDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Gem.GemInfo>() {
            override fun areItemsTheSame(oldItem: Gem.GemInfo, newItem: Gem.GemInfo) =
                oldItem.slot == newItem.slot

            override fun areContentsTheSame(oldItem: Gem.GemInfo, newItem: Gem.GemInfo) =
                oldItem == newItem
        }
    }
}