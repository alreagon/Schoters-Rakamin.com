package com.example.schoters.ui.view.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.schoters.R
import com.example.schoters.data.remote.model.GetNewsResponse
import com.example.schoters.databinding.ItemAdapterBinding
import kotlinx.android.synthetic.main.item_adapter.view.*


class NewsApiAdapter : RecyclerView.Adapter<NewsApiAdapter.ViewHolder>() {
    private var list = ArrayList<GetNewsResponse>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GetNewsResponse)
    }

    class ViewHolder(var binding: ItemAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(data: List<GetNewsResponse>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsResponse = list[position]
        val image_name_url = newsResponse.articles!![0].urlToImage

        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(image_name_url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.itemView.card_imagee.setImageResource(R.drawable.default_photo)
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(holder.itemView.card_imagee)


            cardTittle.text = newsResponse.articles[0].title
            if (newsResponse.articles[0].author!!.isEmpty()) {
                cardAuthor.text = "Author : Tidak diketahui"
            } else {
                cardAuthor.text = "Author : ${newsResponse.articles[0].author}"
            }
            cardReleaseAt.text = "Release date: ${newsResponse.articles[0].publishedAt}"
            itemCard.setOnClickListener {
                onItemClickCallback.onItemClicked(newsResponse)
            }

        }
    }

    override fun getItemCount() = list.size

}
