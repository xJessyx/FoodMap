package com.jessy.foodmap.addPlace

import android.app.Activity
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.jessy.foodmap.databinding.FragmentAddPlaceBinding
import java.lang.String.format

class AddPlaceFragment : Fragment() {
//    val calender = Calendar.getInstance()
    var selectJourney: Spinner? = null
    var selectDay: Spinner? = null
    var selectTransportation: Spinner? = null
    var selectDwellTime: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentAddPlaceBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        selectJourney = binding.addPlaceSpSelectJourney
        selectDay = binding.addPlaceSpSelectDay
        selectTransportation = binding.addPlaceSpSelectTransportation
        selectDwellTime = binding.addPlaceSpSelectDwellTime.toString()

        binding.addPlaceSpSelectDwellTime.setOnClickListener {
            setdDwellTime()
        }

        setJourneySpinner()
        setDaySinner()
        setTransportationSinner()

        return binding.root
    }


    fun setJourneySpinner() {

        val lunch = arrayListOf("甜點之旅", "吃飯吃到飽", "評價小吃", "蘭嶼必吃")
        val adapter =
            ArrayAdapter(activity as Activity, android.R.layout.simple_spinner_dropdown_item, lunch)
        selectJourney?.adapter = adapter
    }

    fun setDaySinner() {

        val lunch = arrayListOf("第一天", "第二天", "第三天", "第四天")
        val adapter =
            ArrayAdapter(activity as Activity, android.R.layout.simple_spinner_dropdown_item, lunch)
        selectJourney?.adapter = adapter
    }

    fun setTransportationSinner() {

        val lunch = arrayListOf("步行", "公車", "捷運", "火車")
        val adapter =
            ArrayAdapter(activity as Activity, android.R.layout.simple_spinner_dropdown_item, lunch)
        selectJourney?.adapter = adapter
    }

    fun setdDwellTime() {

        val ca = Calendar.getInstance()
        var mHour = ca[Calendar.HOUR_OF_DAY]
        var mMinute = ca[Calendar.MINUTE]

        val timePickerDialog = TimePickerDialog(
            activity as Activity, TimePickerDialog.OnTimeSetListener { _, hourofDay, minute ->
                mHour = hourofDay
                mMinute = minute
                val mTime = "${hourofDay}:${minute}"
                selectDwellTime = mTime
            },
            mHour, mMinute, true
        )
        timePickerDialog.show()
    }
}



