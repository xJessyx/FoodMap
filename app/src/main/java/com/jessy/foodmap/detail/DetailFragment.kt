package com.jessy.foodmap.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.jessy.foodmap.R
import com.jessy.foodmap.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false)

        val article = DetailFragmentArgs.fromBundle(requireArguments()).articleKey
        var viewModel = DetailViewModel(article)
        viewModel.article.value?.let { binding.detailImg.setImageResource(it.image) }



        binding.detailBtBack.setOnClickListener {
            this.findNavController().navigateUp()
        }

        binding.viewModel = viewModel

        return binding.root
    }


}