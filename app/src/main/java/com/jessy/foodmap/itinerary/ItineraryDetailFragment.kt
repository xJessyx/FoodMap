package com.jessy.foodmap.itinerary

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.jessy.foodmap.R
import com.jessy.foodmap.itinerary.detailpaging.ItineraryDetailPagingAdapter
import com.jessy.foodmap.databinding.FragmentItineraryDetailBinding
import com.jessy.foodmap.detail.DetailFragmentArgs
import com.jessy.foodmap.foodMapSearch.FoodMapSearchViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class ItineraryDetailFragment : BottomSheetDialogFragment() {
    private val viewModel: ItineraryDetailViewModel by lazy {
        ViewModelProvider(this).get(ItineraryDetailViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val binding = FragmentItineraryDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        val journeyArg = ItineraryDetailFragmentArgs.fromBundle(requireArguments()).journeyKey
        Log.v("journey","$journeyArg")
        binding.viewModel = viewModel

        binding.itineraryDeatailName.text = journeyArg.journeyName
        binding.itineraryDeatailStartDate.text =journeyArg.startDate
        binding.itineraryDeatailEndDate.text=journeyArg.endtDate

//        val startDateTimestamp =TimeUtil.DateToStamp(binding.itineraryDeatailStartDate.text.toString(), Locale.TAIWAN)
//        val endDateTimestamp  =TimeUtil.DateToStamp(binding.itineraryDeatailStartDate.text.toString(), Locale.TAIWAN)
//        val dateDifference = endDateTimestamp -startDateTimestamp
//        Log.v("startDate+endDate","${endDateTimestamp } ${startDateTimestamp }")
//        Log.v("dateDifference","$dateDifference")
        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val mStart = LocalDate.parse(binding.itineraryDeatailStartDate.text, format)
        val mEnd = LocalDate.parse(binding.itineraryDeatailEndDate.text, format)
        val difference = ChronoUnit.DAYS.between(mStart, mEnd)
        Log.v("difference","$difference")

        val pageAdapter = ItineraryDetailPagingAdapter(this, difference.toInt())
        binding.itineraryDeatailViewPager2.adapter = pageAdapter
        TabLayoutMediator(binding.itineraryDeatailTabs,
            binding.itineraryDeatailViewPager2) { tab, position ->

            tab.text = "第 ${position + 1} 天"
        }.attach()
        return binding.root
    }
//    object TimeUtil {
//        @JvmStatic
//        fun StampToDate(time: Long, locale: Locale): String {
//            // 進來的time以秒為單位，Date輸入為毫秒為單位，要注意
//
//            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", locale)
//
//            return simpleDateFormat.format(Date(time))
//        }
//
//        @JvmStatic
//        fun DateToStamp(date: String, locale: Locale): Long {
//            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", locale)
//
//            /// 輸出為毫秒為單位
//            return simpleDateFormat.parse(date).time
//        }
//    }
}