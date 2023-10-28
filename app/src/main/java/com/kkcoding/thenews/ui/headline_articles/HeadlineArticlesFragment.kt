package com.kkcoding.thenews.ui.headline_articles

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.Tab
import com.google.android.material.tabs.TabLayoutMediator
import com.kkcoding.domain.model.ArticleItem
import com.kkcoding.thenews.R
import com.kkcoding.thenews.databinding.FragmentHeadlinesNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HeadlineArticlesFragment : Fragment(R.layout.fragment_headlines_news) {

    private lateinit var binding: FragmentHeadlinesNewsBinding
    private val viewModel by viewModels<HeadlineArticlesViewModel>()
    private val recyclerAdapter: ArticleAdapter = ArticleAdapter (
        onClickItem = {

            findNavController().navigate(HeadlineArticlesFragmentDirections.actionNewsHeadlinesFragmentToNewsDetailsFragment(it))
        },
        onSaveArticle = {
            viewModel.saveArticle(it)
        },
        onUnsaveArticle = {
            viewModel.unsaveArticle(it)
        }
    )
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHeadlinesNewsBinding.bind(view)

        initUi()

        collectData()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedCategory.collectLatest {selected ->
                    binding.lyTabHeadline.selectTab(binding.lyTabHeadline.getTabAt(
                        HeadlineArticleCategory.values().indexOf(selected)
                    ))
                }
            }
        }

    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    when {
                        it.loading -> renderLoading()
                        !it.errorMsg.isNullOrEmpty() -> renderError(it.errorMsg)
                        it.articles != null -> renderSuccess(it.articles)
                    }
                }
            }
        }
    }

    private fun renderLoading() {
        binding.pgHeadline.isVisible = true
    }

    private fun renderError(msg: String) {
        binding.pgHeadline.isVisible = false
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG).show()
    }

    private fun renderSuccess(list: List<ArticleItem>) {
        binding.pgHeadline.isVisible = false
        recyclerAdapter.submitList(list)
    }

    private fun initUi() {
        val titles = HeadlineArticleCategory.values().map { it.title }
        binding.rvHeadlineArticle.adapter = recyclerAdapter
        binding.lyTabHeadline.apply {

            titles.onEach { category ->
                addTab(newTab().let {
                    it.text = category
                    it
                })
            }
        }
        binding.lyTabHeadline.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.updateCategory(HeadlineArticleCategory.values()[tab?.position ?: 0])
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}