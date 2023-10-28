package com.kkcoding.thenews.ui.detail_article

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.kkcoding.domain.model.ArticleItem
import com.kkcoding.thenews.R
import com.kkcoding.thenews.databinding.FragmentDetailsNewsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArticleDetailsFragment : Fragment(R.layout.fragment_details_news) {

    private lateinit var binding: FragmentDetailsNewsBinding

    private val args: ArticleDetailsFragmentArgs by navArgs()

    private val viewModel by viewModels<ArticleDetailViewModel>()

    private var article: ArticleItem? = null

    private var isAlreadySaved: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsNewsBinding.bind(view)

        article = args.article
        isAlreadySaved = article?.isSaved ?: false

        setUpWebView()
        setUpActions()
    }

    private fun setUpActions() {

        updateFab(isAlreadySaved)
        binding.fab.setOnClickListener {
            article?.let { item ->
                binding.fab.setImageResource(if (isAlreadySaved) R.drawable.ic_favorite else R.drawable.ic_fav_outline)
                if (isAlreadySaved) {
                    viewModel.unsaveArticle(item)
                    updateFab(false)
                } else {
                    viewModel.saveArticle(item)
                    updateFab(true)
                }
            }

            isAlreadySaved = !isAlreadySaved

        }
    }

    private fun updateFab(isSaved: Boolean) {
        binding.fab.setImageResource(if (isSaved) R.drawable.ic_favorite else R.drawable.ic_fav_outline)
    }

    private fun setUpWebView() {
        article?.let {
            binding.webView.apply {
                loadUrl(it.url)
                webViewClient = object : WebViewClient() {

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        binding.webProgress.isVisible = false
                    }

                }
                settings.javaScriptEnabled = true
            }
        }
    }
}