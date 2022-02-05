package com.indrih.transitionandcompose.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.indrih.transitionandcompose.R
import com.indrih.transitionandcompose.databinding.FragmentListBinding
import com.indrih.transitionandcompose.details.DetailsFragment
import com.indrih.transitionandcompose.list.adapter.ArticlesAdapter
import com.indrih.transitionandcompose.list.adapter.Item

class ListFragment : Fragment() {
    private val articlesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ArticlesAdapter(onArticleClick = ::onArticleClick)
    }

    private var binding: FragmentListBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        postponeEnterTransition()
        return FragmentListBinding.inflate(inflater, container, false).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: throw IllegalStateException()
        with(binding.recyclerView) {
            adapter = articlesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        val articles = content.mapIndexed { index, (title, image) -> Item.Article(id = index, title = title, image = image) }
        articlesAdapter.submitList(listOf(Item.Ad) + articles) {
            startPostponedEnterTransition()
        }
    }

    private fun onArticleClick(item: Item.Article) {
        val articleDetails = DetailsFragment.Article(image = item.image, title = item.title)
        parentFragmentManager.commit {
            replace(R.id.activityRootNavigationHost, DetailsFragment.create(articleDetails))
            setReorderingAllowed(true)
            addToBackStack("null")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private val content = listOf(
        "Chongyun" to "https://playsector.ru/images/img/2021/03/18/playsectorru_20210318233520.jpg",
        "Shenhe" to "https://vgtimes.ru/uploads/posts/2022-01/thumbs/1641883205_art3.jpg",
        "Aunt and nephew" to "https://wol.su/uploads/posts/2022-01/1641420246_1-shenhe-and-chongyun-from-genshin-impact.jpg"
    )
}
