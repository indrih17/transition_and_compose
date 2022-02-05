package com.indrih.transitionandcompose.feed.adapter

import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.recyclerview.widget.RecyclerView
import com.indrih.transitionandcompose.ArticleCard
import com.indrih.transitionandcompose.databinding.ItemAdBinding

sealed class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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

        fun render(article: Item.Article) {
            composeView.setContent {
                ArticleCard(
                    title = article.title,
                    image = article.image,
                    onArticleClick = { onArticleClick(article) }
                )
            }
        }
    }
}