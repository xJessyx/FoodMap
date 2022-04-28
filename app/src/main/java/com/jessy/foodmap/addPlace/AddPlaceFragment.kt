package com.jessy.foodmap.addPlace

import android.app.Activity
import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import com.jessy.foodmap.databinding.FragmentAddPlaceBinding
import java.util.*

class AddPlaceFragment : Fragment() {
    var selectJourney: Spinner? = null
    var selectDay: Spinner? = null
    var selectTransportation: Spinner? = null
    var selectTotalDay: Int = 0
    var lunchDay = mutableListOf<String>()

    private val viewModel: AddPlaceViewModel by lazy {
        ViewModelProvider(this).get(AddPlaceViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val binding = FragmentAddPlaceBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        selectJourney = binding.addPlaceSpSelectJourney
        selectDay = binding.addPlaceSpSelectDay
        selectTransportation = binding.addPlaceSpSelectTransportation
        viewModel.getAllJourney()
        val storeInformationArg = AddPlaceFragmentArgs.fromBundle(requireArguments()).storeKey

        binding.addPlaceTvPlaceName.text = storeInformationArg!!.storeTitle
        viewModel.placeName = storeInformationArg.storeTitle
        binding.addPlaceSpSelectDwellTime.setOnClickListener {

            val ca = Calendar.getInstance()
            var mHour = ca[Calendar.HOUR_OF_DAY]
            var mMinute = ca[Calendar.MINUTE]

            val timePickerDialog = TimePickerDialog(
                activity as Activity, TimePickerDialog.OnTimeSetListener { _, hourofDay, minute ->
                    mHour = hourofDay
                    mMinute = minute

                    val mHourString = String.format("%02d", mHour)
                    val mMinuteString = String.format("%02d", mMinute)
                    val mTime = "${mHourString}:${mMinuteString}"
                    val totalMillis = TimeUtil.DateToStamp(mTime, Locale.TAIWAN)

                    binding.addPlaceSpSelectDwellTime.setText(mTime)
                    viewModel.dwellTime = totalMillis
                },
                mHour, mMinute, true
            )
            timePickerDialog.show()
        }


        binding.addPlaceSpSelectStartTime.setOnClickListener {

            val ca = Calendar.getInstance()
            var mHour = ca[Calendar.HOUR_OF_DAY]
            var mMinute = ca[Calendar.MINUTE]

            val timePickerDialog = TimePickerDialog(
                activity as Activity, TimePickerDialog.OnTimeSetListener { _, hourofDay, minute ->
                    mHour = hourofDay
                    mMinute = minute
                    val mHourString = String.format("%02d", mHour)
                    val mMinuteString = String.format("%02d", mMinute)
                    val mTime = "${mHourString}:${mMinuteString}"
                    val totalMillis = TimeUtil.DateToStamp(mTime, Locale.TAIWAN)
                    viewModel.startTime = totalMillis

                    binding.addPlaceSpSelectStartTime.setText(mTime)
                },
                mHour, mMinute, true
            )
            timePickerDialog.show()
        }
        binding.addPlaceBtSubmit.setOnClickListener {
            if ((viewModel.placeName != null) && (viewModel.daySinner != null) &&
                (viewModel.transportationSinner != null) && (viewModel.startTime != null) && (viewModel.dwellTime != null)
            ) {
                viewModel.addPlaceItem()
                viewModel.addFireBasePlace()
            }

        }

        viewModel.addAllJourney.observe(viewLifecycleOwner) {
            setJourneySpinner()
            //setDaySinner()


            setTransportationSinner()
        }

        return binding.root
    }

    object TimeUtil {

        @JvmStatic
        fun DateToStamp(date: String, locale: Locale): Long {
            val simpleDateFormat = SimpleDateFormat("HH:mm", locale)
            /// 輸出為毫秒為單位
            return simpleDateFormat.parse(date).time
        }
    }

    fun setJourneySpinner() {
        var lunch = mutableListOf<String>()
        for (item in viewModel.getAllJourney) {
            lunch.add(item.name)
        }
        val adapter =
            ArrayAdapter(activity as Activity, android.R.layout.simple_spinner_dropdown_item, lunch)
        selectJourney?.adapter = adapter
        selectJourney?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                viewModel.journeySinner = selectJourney?.selectedItem.toString()
                lunchDay.clear()

                for (item in viewModel.getAllJourney) {
                    if (item.name == selectJourney?.selectedItem.toString()) {
                        selectTotalDay = item.totalDay
                        for (i in 1..selectTotalDay) {
                            lunchDay.add("第 $i 天")
                        }
                        Log.v("lunch3", "$lunchDay")
                        viewModel.journeyId = item.id
                        Log.v("selectTotalDay", "$selectTotalDay")
                    }
                }
                setDaySinner()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }


    }

    fun setDaySinner() {
        val adapter2 =
            ArrayAdapter(activity as Activity,
                android.R.layout.simple_spinner_dropdown_item,
                lunchDay)
        Log.v("lunchDay","$lunchDay")
        selectDay?.adapter = adapter2
        //監聽「第幾天」下拉選單選擇
        selectDay?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                viewModel.daySinner = selectDay?.selectedItemPosition!!.toInt()
                Log.v("viewModel.daySinner","${viewModel.daySinner}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    fun setTransportationSinner() {

        val lunch = arrayListOf("請選擇交通工具", "步行", "公車", "捷運", "火車")
        val adapter =
            ArrayAdapter(activity as Activity,
                android.R.layout.simple_spinner_dropdown_item,
                lunch)
        selectTransportation?.adapter = adapter

        selectTransportation?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    viewModel.transportationSinner =
                        selectTransportation?.selectedItemPosition!!.toInt()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }

    }
}

