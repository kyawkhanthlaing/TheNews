package com.kkcoding.thenews

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.kkcoding.thenews.databinding.FragmentHeadlinesNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsHeadlinesFragment :Fragment(R.layout.fragment_headlines_news) {

    private lateinit var binding: FragmentHeadlinesNewsBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var pagerAdapter: PagerAdapter

    private val viewModel by viewModels<NewsHeadlinesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHeadlinesNewsBinding.bind(view)

        val headlineTitles=listOf("US","Health","Education","Business","Science","Entertainment")

        viewModel.getHeadlineArticles()
        viewPager=binding.pager
        pagerAdapter = PagerAdapter(headlineTitles)
        viewPager.adapter=pagerAdapter

        TabLayoutMediator(binding.lyTabHeadlines,viewPager){tab,position->
            tab.text=headlineTitles[position]
        }.attach()

    }
}