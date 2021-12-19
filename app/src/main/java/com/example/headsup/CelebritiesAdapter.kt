package com.example.headsup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.headsup.databinding.ItemRowBinding

class CelebritiesAdapter(private  var celebrities: ArrayList<CelebritiesItem>):
    RecyclerView.Adapter<CelebritiesAdapter.CelebritiesViewHolder>() {
    class CelebritiesViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CelebritiesViewHolder {
        return CelebritiesViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CelebritiesViewHolder, position: Int) {
        val celeb = celebrities[position]

        holder.binding.apply {
            celeNameTV.text = celeb.name
            firstTabooTV.text = celeb.taboo1
            secondTabooTV.text = celeb.taboo2
            thirdTabooTV.text = celeb.taboo3

        }
    }

    override fun getItemCount() = celebrities.size

    fun updateRecyclerView(userInput: ArrayList<CelebritiesItem>) {
        this.celebrities = userInput
        notifyDataSetChanged()
    }

}