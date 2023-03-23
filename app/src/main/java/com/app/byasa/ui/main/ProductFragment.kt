package com.app.byasa.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.app.byasa.databinding.FragmentProductBinding
import com.app.byasa.ui.adapter.ColorsAdapter
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private lateinit var colorsAdapter: ColorsAdapter
    private val viewModel by viewModels<ProductsViewModel>()
    private val args: ProductFragmentArgs by navArgs()

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpColorsAdapter()
        val id = args.productId

        val product = viewModel.getProductById(id)
        binding.apply {
            tvProductTitleHome.text = product.name
            tvProductPriceHome.text = product.price
            tvProductBrandNameHome.text = product.brand
            tvProductDescription.text = product.description
            tvProductType.text = product.product_type
            if (product.rating != null){
                tvRatingHomeRvProducts.text = product.rating.toString()
                ratingBarRvHomeProduct.rating = product.rating.toFloat()
            }else{
                tvRatingHomeRvProducts.text = 0.toString()
                ratingBarRvHomeProduct.rating = 0F
            }

            Glide.with(requireContext()).load(product.image_link).into(ivProductImgProductFrag)
        }
        colorsAdapter.differ.submitList(product.product_colors)

    }

    private fun setUpColorsAdapter() {
        colorsAdapter = ColorsAdapter()
        binding.rvColorsProductFrag.apply {
            adapter = colorsAdapter
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}