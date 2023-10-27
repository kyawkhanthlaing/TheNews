package com.kkcoding.thenews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kkcoding.thenews.databinding.FragmentPagerItemBinding

class PagerAdapter(
    val titles: List<String>
) : RecyclerView.Adapter<PagerAdapter.PagerViewHolder>() {


    inner class PagerViewHolder(val binding: FragmentPagerItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(
            FragmentPagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.binding.apply {
            pagerItemtitle.text = titles[position]
        }
    }
}
