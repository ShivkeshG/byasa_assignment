package com.app.byasa.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.byasa.data.models.ProductItem
import com.app.byasa.databinding.HomeProductRvItemBinding
import com.bumptech.glide.Glide

class HomeProductAdapter : RecyclerView.Adapter<HomeProductAdapter.HomeProductViewHolder>() {

    var onProductClick: ((Int) -> Unit)? = null

    inner class HomeProductViewHolder(private val binding: HomeProductRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductItem) {
            binding.apply {
                tvProductTitleHome.text = product.name
                tvProductPriceHome.text = product.price
                tvProductBrandNameHome.text = product.brand
                if (product.rating != null) {
                    ratingBarRvHomeProduct.rating = product.rating.toFloat()
                    tvRatingHomeRvProducts.text = product.rating.toString()
                }

                Glide.with(itemView).load(product.image_link).into(ivProductImgHome)
            }
            itemView.setOnClickListener {
                onProductClick!!.invoke(product.id)
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<ProductItem>() {
        override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeProductViewHolder {
        return HomeProductViewHolder(
            HomeProductRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeProductViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}