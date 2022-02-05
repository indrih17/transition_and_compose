package com.indrih.transitionandcompose.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.ListAdapter
import com.indrih.transitionandcompose.databinding.ItemAdBinding

class FeedAdapter(
    private val onArticleClick: (Item.Article) -> Unit
) : ListAdapter<Item, ViewHolder>(Item) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        when (ViewType.values().first { it.value == viewType }) {
            ViewType.Ad ->
                ViewHolder.Ad(binding = ItemAdBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            ViewType.Article ->
                ViewHolder.Article(composeView = ComposeView(parent.context), onArticleClick = onArticleClick)
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        when (holder) {
            is ViewHolder.Ad -> Unit
            is ViewHolder.Article -> holder.render(getItem(position) as Item.Article)
        }

    override fun onViewRecycled(holder: ViewHolder) {
        // Dispose of the underlying Composition of the ComposeView
        // when RecyclerView has recycled this ViewHolder
        holder.let { it as? ViewHolder.Article }?.composeView?.disposeComposition()
    }

    enum class ViewType(val value: Int) {
        Ad(value = 0),
        Article(value = 1),
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            Item.Ad -> ViewType.Ad
            is Item.Article -> ViewType.Article
        }.value
}