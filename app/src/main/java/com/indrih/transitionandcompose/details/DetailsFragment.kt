package com.indrih.transitionandcompose.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import coil.request.ImageRequest
import coil.request.ImageResult
import com.indrih.transitionandcompose.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private var binding: FragmentDetailsBinding? = null
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentDetailsBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: throw IllegalStateException()
        binding.articleImageView.load(args.image) {
            this.listener(object : ImageRequest.Listener {
                override fun onSuccess(request: ImageRequest, metadata: ImageResult.Metadata) {
                    super.onSuccess(request, metadata)
                    startPostponedEnterTransition()
                }

                override fun onError(request: ImageRequest, throwable: Throwable) {
                    super.onError(request, throwable)
                    startPostponedEnterTransition()
                }
            })
        }
        binding.titleTextView.text = args.title
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}