package com.indrih.transitionandcompose.list.adapter

import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.recyclerview.widget.RecyclerView
import com.indrih.transitionandcompose.ArticleCard
import com.indrih.transitionandcompose.databinding.ItemAdBinding

sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    class Ad(binding: ItemAdBinding) : ViewHolder(binding.root)

    class Article(
        val composeView: ComposeView,
        private val onArticleClick: (Item.Article) -> Unit
    ) : ViewHolder(composeView) {
        init {
            composeView.setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
        }

        fun render(item: Item.Article) {
            composeView.setContent {
                ArticleCard(
                    title = item.title,
                    image = item.image,
                    onArticleClick = { onArticleClick(item) }
                )
            }
        }
    }
}