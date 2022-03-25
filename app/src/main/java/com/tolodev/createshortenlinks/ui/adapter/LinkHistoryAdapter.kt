package com.tolodev.createshortenlinks.ui.adapter

import android.content.Context
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tolodev.createshortenlinks.databinding.ViewLinkBinding
import com.tolodev.createshortenlinks.ui.models.UIShortenLink

class LinkHistoryAdapter(private val action: (linkId: String) -> Unit) :
    RecyclerView.Adapter<LinkHistoryAdapter.ItemViewHolder>() {

    private var links: MutableList<UIShortenLink> = mutableListOf()

    fun setLinks(links: List<UIShortenLink>) {
        this.links += links
    }

    fun addLink(uIShortenLink: UIShortenLink) {
        links.add(uIShortenLink)
        notifyItemInserted(links.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ViewLinkBinding.inflate(LayoutInflater.from(parent.context)).apply {
            root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = links[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = links.size

    inner class ItemViewHolder(private val binding: ViewLinkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val context: Context = binding.root.context

        fun bind(uiShortenLink: UIShortenLink) = with(binding) {
            binding.textViewShortLink.text = uiShortenLink.linkMetadata.shortLink
            binding.root.setOnClickListener { action.invoke(uiShortenLink.id) }
        }
    }
}
