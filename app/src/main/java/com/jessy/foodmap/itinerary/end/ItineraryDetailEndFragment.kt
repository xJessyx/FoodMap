package com.jessy.foodmap.itinerary.end

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.databinding.FragmentLtineraryDetailEndBinding


class ItineraryDetailEndFragment : Fragment() {

    val db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

//        val journeyEndArg = ItineraryDetailEndFragmentArgs.fromBundle(requireArguments()).journeyEndKey

        val binding = FragmentLtineraryDetailEndBinding.inflate(inflater, container, false)


//        binding.itineraryEndAlltext.setText(journeyEndArg.name + journeyEndArg.totalDay + journeyEndArg.startDate + journeyEndArg.endDate)

//        val EndSwitch =  binding.itineraryEndSwitch
//        EndSwitch.setOnClickListener {
//            if (EndSwitch.isChecked){
////                EndSwitch.setBackgroundColor(Color.DKGRAY)
//                EndSwitch.setTextColor(Color.WHITE)
//                db.collection("journeys").document(journeyEndArg.id)
//                    .update("share", true)
//
//            }
//            else{
//                EndSwitch.setBackgroundColor(Color.WHITE)
//                EndSwitch.setTextColor(Color.BLACK)
//                db.collection("journeys").document(journeyEndArg.id)
//                    .update("share", false)
//            }
//        }

        return binding.root
    }
}