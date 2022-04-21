package com.jessy.foodmap.itinerary

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.R
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.databinding.FragmentAddItineraryBinding
import java.lang.String.format
import java.text.SimpleDateFormat
import java.util.*


class AddItineraryFragment : BottomSheetDialogFragment() {
    lateinit var startDate: Button
    lateinit var endDate: Button

    private val viewModel: AddItineraryViewModel by lazy {
        ViewModelProvider(this).get(AddItineraryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val binding = FragmentAddItineraryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        startDate = binding.addItineraryEdStartDate
        endDate = binding.addItineraryEdEndDate
        binding.viewModel = viewModel

        binding.addItineraryEdStartDate.setOnClickListener {
           setStartData()
        }

        binding.addItineraryEdEndDate.setOnClickListener {
            setEndData()
        }

        binding.addItineraryBt.setOnClickListener {

            if( (viewModel.itineraryName.value != null) &&( viewModel.itineraryStartDate.value !=null) &&( viewModel.itineraryEndDate.value !=null) ) {
                viewModel.addItineraryItem()
                viewModel.addFireBaseJourney()

                viewModel.addItinerary.observe(viewLifecycleOwner){
                    it?.let {
                        findNavController().navigate(NavigationDirections.addItineraryFragmentToItineraryDetailFragment(
                            it))
                        Log.v("it", "$it")
                    }
                }


            } else {
                Toast.makeText(activity as Activity, "有資料尚未填寫!!!", Toast.LENGTH_SHORT).show()
            }
        }

//        viewModel.addItinerary.observe(viewLifecycleOwner){
//
//            Log.v("viewModel.itineraryName1","$viewModel.itineraryName")
//            viewModel.addItineraryItem()
//            Log.v("viewModel.itineraryName2","$viewModel.itineraryName")
//
//        }

        return binding.root
    }

    private fun setStartData(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(activity as Activity, { _, year, month, day ->
            run {
                val format = "出發:${setDateFormat(year, month, day)}"
               startDate.text = format
            }
        }, year, month, day).show()
    }
    private fun setEndData(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(activity as Activity, { _, year, month, day ->
            run {
                val format = "結束:${setDateFormat(year, month, day)}"
                endDate.text = format
            }
        }, year, month, day).show()
    }
    private fun setDateFormat(year: Int, month: Int, day: Int): String {
        return "$year-${month + 1}-$day"
    }

}