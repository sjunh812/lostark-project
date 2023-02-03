package org.sjhstudio.lostark.ui.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sjhstudio.lostark.databinding.ItemGemSummaryBinding
import org.sjhstudio.lostark.domain.model.response.Gem
import org.sjhstudio.lostark.util.setItemBackground

class GemSummaryAdapter : ListAdapter<Gem.GemInfo, GemSummaryAdapter.GemSummaryViewHolder>(
    diffCallback
) {

    inner class GemSummaryViewHolder(private val binding: ItemGemSummaryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gemInfo: Gem.GemInfo) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(gemInfo.iconUrl)
                    .into(ivGemSummary)

                ivGemSummary.setItemBackground(gemInfo.grade)
                tvGemSummaryLevel.text = gemInfo.level.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GemSummaryViewHolder {
        val binding =
            ItemGemSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GemSummaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GemSummaryViewHolder, position: Int) {
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