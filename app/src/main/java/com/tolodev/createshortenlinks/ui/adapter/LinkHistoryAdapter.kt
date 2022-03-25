package com.tolodev.createshortenlinks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tolodev.createshortenlinks.R
import com.tolodev.createshortenlinks.databinding.ViewHeaderBinding
import com.tolodev.createshortenlinks.databinding.ViewLinkBinding
import com.tolodev.createshortenlinks.ui.models.GenericItem

const val VIEW_TYPE_HEADER = 0
const val VIEW_TYPE_LINK = 1

class LinkHistoryAdapter : RecyclerView.Adapter<LinkHistoryAdapter.ItemViewHolder>() {

    private val links: MutableList<GenericItem> = mutableListOf()

    fun setLinks(links: List<GenericItem>) {
        this.links.clear()
        addHeader()
        this.links += links
    }

    fun addLink(genericItem: GenericItem) {
        addHeader()
        links.add(genericItem)
        notifyItemInserted(links.size - 1)
    }

    private fun addHeader() {
        if (links.isEmpty()) {
            links += GenericItem.UIHeader(R.string.copy_link_history_title)
        }
    }

    fun hasLinks(): Boolean {
        return links.any { it is GenericItem.UIShortenLink }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = if (viewType == VIEW_TYPE_HEADER) {
            getViewHeaderBinding(parent)
        } else {
            getViewLinkBinding(parent)
        }
        return ItemViewHolder(binding)
    }

    private fun getViewLinkBinding(parent: ViewGroup): ViewLinkBinding {
        return ViewLinkBinding.inflate(LayoutInflater.from(parent.context)).apply {
            root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun getViewHeaderBinding(parent: ViewGroup): ViewHeaderBinding {
        return ViewHeaderBinding.inflate(LayoutInflater.from(parent.context)).apply {
            root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = links[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = links.size

    override fun getItemViewType(position: Int): Int {
        val item = links[position]
        return if (item is GenericItem.UIHeader) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_LINK
        }
    }

    inner class ItemViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genericItem: GenericItem) {

            if (genericItem is GenericItem.UIHeader) {
                with(binding as ViewHeaderBinding) {
                    textViewTitle.text =
                        root.context.getString(genericItem.title)
                }
            } else if (genericItem is GenericItem.UIShortenLink) {
                with(binding as ViewLinkBinding) {
                    textViewLinkId.text = genericItem.id
                    textViewShortLink.text = genericItem.linkMetadata.shortLink
                }
            }
        }
    }
}
