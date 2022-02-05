package com.indrih.transitionandcompose.feed.adapter

import androidx.recyclerview.widget.DiffUtil

sealed class Item {
    abstract val id: Int

    object Ad : Item() {
        override val id: Int = 101
    }

    data class Article(override val id: Int, val title: String, val image: String) : Item()

    companion object : DiffUtil.ItemCallback<Item>() {
        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
            when (oldItem) {
                Ad -> newItem is Ad
                is Article -> newItem is Article && newItem == oldItem
            }

        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
            when (oldItem) {
                Ad -> newItem is Ad
                is Article -> newItem is Article && newItem.id == oldItem.id
            }
    }
}
