package com.indrih.transitionandcompose.details

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import coil.load
import coil.request.ImageRequest
import coil.request.ImageResult
import com.indrih.transitionandcompose.databinding.FragmentDetailsBinding
import kotlinx.parcelize.Parcelize

class DetailsFragment : Fragment() {
    private var binding: FragmentDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition() // <-- comment this to fix
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentDetailsBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: throw IllegalStateException("binding == null")
        val article = getArticle()
        binding.titleTextView.text = article.title
        binding.articleImageView.load(article.image) {
            listener(object : ImageRequest.Listener {
                override fun onSuccess(request: ImageRequest, metadata: ImageResult.Metadata) {
                    super.onSuccess(request, metadata)
                    startPostponedEnterTransition() // <-- comment this to fix
                }

                override fun onError(request: ImageRequest, throwable: Throwable) {
                    super.onError(request, throwable)
                    startPostponedEnterTransition() // <-- comment this to fix
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun getArticle(): Article =
        arguments?.getParcelable(ARTICLE_KEY) ?: error("No article")

    @Parcelize
    data class Article(val image: String, val title: String) : Parcelable

    companion object {
        private const val ARTICLE_KEY = "ARTICLE_DETAILS"

        fun create(article: Article) = DetailsFragment().apply {
            arguments = bundleOf(ARTICLE_KEY to article)
        }
    }
}