package com.jessy.foodmap.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.ads.mediationtestsuite.viewmodels.HomeActivityViewModel
import com.jessy.foodmap.R
import com.jessy.foodmap.databinding.FragmentDetailBinding
import com.jessy.foodmap.itinerary.ItineraryDetailViewModel

class DetailFragment : Fragment() {
//    private val viewModel: DetailViewModel by lazy {
//        ViewModelProvider(this).get(DetailViewModel::class.java)
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false)

      val articleKey = DetailFragmentArgs.fromBundle(requireArguments()).articleKey
       var viewModel = DetailViewModel(articleKey)
//        viewModel.article.value?.let { binding.detailImg.setImageResource(it.image) }
            //viewModel.article.value = binding.detailImg(articleKey.image)
//        viewModel.author = articleKey.author
//        viewModel.authorImage= articleKey.authorImage
//        viewModel.image =articleKey.image
    binding.viewModel = viewModel


//
//        binding.detailTvAuthorName.text = articleKey.author
//       binding.detailImg.imageurl = articleKey.authorImage.toString()
//       binding.detailImgPerson.


        binding.detailBtBack.setOnClickListener {
            this.findNavController().navigateUp()
        }


        return binding.root
    }


}