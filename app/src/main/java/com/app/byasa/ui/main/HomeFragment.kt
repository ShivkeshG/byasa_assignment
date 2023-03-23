package com.app.byasa.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.byasa.R
import com.app.byasa.data.repository.ProductsRepository
import com.app.byasa.data.utils.Resource
import com.app.byasa.databinding.FragmentHomeBinding
import com.app.byasa.ui.adapter.HomeProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<ProductsViewModel>()
    private lateinit var homeProductAdapter: HomeProductAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpProductsRv()

        lifecycleScope.launchWhenStarted {
            viewModel.getProducts.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        showProductProgressBar()
                    }
                    is Resource.Success -> {
                        hideProductProgressBar()
                        homeProductAdapter.differ.submitList(it.data)
                        Log.e("Products", it.data.toString())
                    }
                    is Resource.Error -> {
                        hideProductProgressBar()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

        homeProductAdapter.onProductClick = {
            val bundle = Bundle().apply {
                putInt("productId", it)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_productFragment,
                bundle
            )
        }
    }

    private fun showProductProgressBar() {
        binding.progressBarHomeProductsFrag.visibility = View.VISIBLE
    }

    private fun hideProductProgressBar() {
        binding.progressBarHomeProductsFrag.visibility = View.GONE
    }

    private fun setUpProductsRv() {
        homeProductAdapter = HomeProductAdapter()
        binding.rvAllProductsHomeFrag.apply {
            adapter = homeProductAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}