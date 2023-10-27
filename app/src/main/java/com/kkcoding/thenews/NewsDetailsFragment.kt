package com.kkcoding.thenews

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kkcoding.thenews.databinding.FragmentDetailsNewsBinding

class NewsDetailsFragment :Fragment(R.layout.fragment_details_news) {

    private lateinit var binding: FragmentDetailsNewsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsNewsBinding.bind(view)

    }
}