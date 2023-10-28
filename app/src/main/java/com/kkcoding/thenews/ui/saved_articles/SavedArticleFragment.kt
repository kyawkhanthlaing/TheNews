package com.kkcoding.thenews.ui.saved_articles

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.kkcoding.thenews.R
import com.kkcoding.thenews.databinding.FragmentSavedNewsBinding
import com.kkcoding.thenews.ui.headline_articles.ArticleAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SavedArticleFragment : Fragment(R.layout.fragment_saved_news) {

    private lateinit var binding: FragmentSavedNewsBinding

    private val viewModel: SavedArticleViewModel by viewModels()

    private val articleAdapter: ArticleAdapter = ArticleAdapter(
        onClickItem = {
            findNavController().navigate(SavedArticleFragmentDirections.actionNewsSavedFragmentToNewsDetailsFragment(it))
        },
        onSaveArticle = {

        },
        onUnsaveArticle = {
            viewModel.unsaveArticle(it)
        }
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedNewsBinding.bind(view)

        initUi()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    articleAdapter.submitList(it)
                }
            }
        }
    }

    private fun initUi() {
        binding.rvSavedArticles.adapter = articleAdapter

    }
}