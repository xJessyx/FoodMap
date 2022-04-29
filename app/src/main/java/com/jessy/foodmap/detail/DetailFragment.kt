package com.jessy.foodmap.detail

import android.app.Activity
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.ads.mediationtestsuite.viewmodels.HomeActivityViewModel
import com.jessy.foodmap.MainActivity
import com.jessy.foodmap.R
import com.jessy.foodmap.databinding.FragmentDetailBinding
import com.jessy.foodmap.itinerary.ItineraryDetailViewModel
import java.util.*

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
        (activity as MainActivity).hidbottomNavigation()

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
    //    var aa =  TimeUtil.StampToDate(1560839160000, Locale.TAIWAN)




        binding.detailBtBack.setOnClickListener {
            this.findNavController().navigateUp()
        }


        return binding.root
    }

//    object TimeUtil {
//
//        @JvmStatic
//        fun StampToDate(time: Long, locale: Locale): String {
//            // 進來的time以秒為單位，Date輸入為毫秒為單位，要注意
//
//            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale)
//
//            return simpleDateFormat.format(Date(time))
//        }
//        @JvmStatic
//        fun DateToStamp(date: String, locale: Locale): Long {
//            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale)
//
//            /// 輸出為毫秒為單位
//            return simpleDateFormat.parse(date).time
//        }
//    }

}