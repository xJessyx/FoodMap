package com.jessy.foodmap.itinerary.end

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jessy.foodmap.databinding.FragmentItineraryDetailRecommendEndBinding

class ItineraryDetailRecommendEndFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


//        val journeyEndArg = ItineraryDetailRecommendEndFragmentArgs.fromBundle(requireArguments()).journeyRecommendEndKey

        val binding = FragmentItineraryDetailRecommendEndBinding.inflate(inflater, container, false)

        return binding.root
    }

    //  fun checkStatus(){

//        val today = LocalDate.now()
//        val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//
//        val parseStartDate = LocalDate.parse(itineraryDeatailStartDate, fmt)
//        val parseEndDate = LocalDate.parse(itineraryDeatailEndDate, fmt)
//
//        if(today.isBefore(parseStartDate)) {
//
//
//            Log.v("today< start","$today <  $parseStartDate")
//
//        }else if(today.isAfter(parseEndDate)){
//
//            Log.v("today > end","$today >  $parseEndDate")
//
//        }else{
//
//            Log.v("start <today< end"," $parseStartDate < $today <  $parseEndDate ")
//
//        }
//
//    }

//    fun checkSwitchStatus(){
//        endSwitch?.setOnClickListener {
//            if (endSwitch?.isChecked!!){
////                EndSwitch.setBackgroundColor(Color.DKGRAY)
//                endSwitch!!.setTextColor(Color.WHITE)
//                db.collection("journeys").document(journeyArg.id)
//                    .update("share", true)
//
//            }
//            else{
//                endSwitch!!.setBackgroundColor(Color.WHITE)
//                endSwitch!!.setTextColor(Color.BLACK)
//                db.collection("journeys").document(journeyArg.id)
//                    .update("share", false)
//            }
//        }
//
//    }

}