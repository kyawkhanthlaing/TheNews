package com.kkcoding.thenews

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kkcoding.thenews.databinding.FragmentSavedNewsBinding

class NewsSavedFragment :Fragment(R.layout.fragment_saved_news) {

    private lateinit var binding: FragmentSavedNewsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedNewsBinding.bind(view)

    }
}