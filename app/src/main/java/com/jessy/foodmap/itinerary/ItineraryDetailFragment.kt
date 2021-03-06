package com.jessy.foodmap.itinerary

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.data.Place
import com.jessy.foodmap.data.PlaceSelectData
import com.jessy.foodmap.data.StoreInformation
import com.jessy.foodmap.itinerary.detailpaging.ItineraryDetailPagingAdapter
import com.jessy.foodmap.databinding.FragmentItineraryDetailBinding
import com.jessy.foodmap.login.UserManager
import com.jessy.foodmap.login.UserManager.Companion.user
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class ItineraryDetailFragment : BottomSheetDialogFragment() {
    private val viewModel: ItineraryDetailViewModel by lazy {
        ViewModelProvider(this).get(ItineraryDetailViewModel::class.java)
    }
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val binding = FragmentItineraryDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        val journeyArg = ItineraryDetailFragmentArgs.fromBundle(requireArguments()).journeyKey
        binding.viewModel = viewModel
        binding.itineraryDeatailName.text = journeyArg.name
        binding.itineraryDeatailStartDate.text = journeyArg.startDate
        binding.itineraryDeatailEndDate.text = journeyArg.endDate
        viewModel.itineraryDeatailImg = journeyArg.image

        val pageAdapter = ItineraryDetailPagingAdapter(this, journeyArg)

        binding.itineraryDeatailViewPager2.adapter = pageAdapter
        TabLayoutMediator(binding.itineraryDeatailTabs,
            binding.itineraryDeatailViewPager2) { tab, position ->
            tab.text = "??? ${position + 1} ???"
        }.attach()

        val today = LocalDate.now()
        val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val parseStartDate = LocalDate.parse(journeyArg.startDate, fmt)

        val parseEndDate = LocalDate.parse(journeyArg.endDate, fmt)

        if (journeyArg.userId == user?.id && !journeyArg.share) {

            if (today.isBefore(parseStartDate)) {
                //binding.itineraryDetailFabBtn.visibility = View.VISIBLE
                Log.v("today< start", "$today <  $parseStartDate")

            } else if (today.isAfter(parseEndDate)) {
                // binding.itineraryDetailFabBtn.visibility = View.GONE
                binding.itineraryDetailShare.visibility = View.VISIBLE
                binding.itineraryDetailSwitch.visibility = View.VISIBLE
                db.collection("journeys").document(journeyArg.id)
                    .update("status", 2)

                binding.itineraryDetailSwitch.setOnClickListener {
                    if (binding.itineraryDetailSwitch.isChecked) {
                        binding.itineraryDetailSwitch.setTextColor(Color.WHITE)
                        db.collection("journeys").document(journeyArg.id)
                            .update("share", true)

                    } else {
                        binding.itineraryDetailSwitch.setBackgroundColor(Color.WHITE)
                        binding.itineraryDetailSwitch.setTextColor(Color.BLACK)
                        db.collection("journeys").document(journeyArg.id)
                            .update("share", false)
                    }
                }

                Log.v("today > end", "$today >  $parseEndDate")

            } else {
                //  binding.itineraryDetailFabBtn.visibility = View.VISIBLE
                Log.v("start <today< end", " $parseStartDate < $today <  $parseEndDate ")
                db.collection("journeys").document(journeyArg.id)
                    .update("status", 1)
            }
        }
        return binding.root
    }


}