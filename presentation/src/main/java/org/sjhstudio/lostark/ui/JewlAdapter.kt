package org.sjhstudio.lostark.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sjhstudio.lostark.databinding.ItemJewlBinding
import org.sjhstudio.lostark.domain.model.JewlInfo
import org.sjhstudio.lostark.util.getJewlImageUrl

class JewlAdapter : ListAdapter<JewlInfo, JewlAdapter.JewlViewHolder>(diffCallback) {

    inner class JewlViewHolder(private val binding: ItemJewlBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(jeweInfo: JewlInfo) {
            with(binding) {
                getJewlImageUrl(jeweInfo.jewlName)?.let { url ->
                    println("xxx $url")
                    Glide.with(itemView.context)
                        .load(url)
                        .into(ivJewl)
                } ?: ivJewl.setImageResource(0)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JewlViewHolder {
        val binding = ItemJewlBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JewlViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JewlViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<JewlInfo>() {
            override fun areItemsTheSame(oldItem: JewlInfo, newItem: JewlInfo) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: JewlInfo, newItem: JewlInfo) =
                oldItem == newItem
        }
    }
}