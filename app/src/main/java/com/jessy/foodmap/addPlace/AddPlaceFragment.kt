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
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jessy.foodmap.MainActivity
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.R
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
        (activity as MainActivity).hideToolBar()

        val binding = FragmentAddPlaceBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        selectJourney = binding.addPlaceSpSelectJourney
        selectDay = binding.addPlaceSpSelectDay
        selectTransportation = binding.addPlaceSpSelectTransportation
        val placeSelectDataArg =
            AddPlaceFragmentArgs.fromBundle(requireArguments()).placeSelectDataKey

        if (placeSelectDataArg != null) {
            if (placeSelectDataArg.storelnformation.storeTitle.isNotEmpty()) {
                binding.addPlaceTvPlaceName.text = placeSelectDataArg.storelnformation.storeTitle
            }
        }
        viewModel.placeName = placeSelectDataArg!!.storelnformation.storeTitle
        viewModel.latitude = placeSelectDataArg.storelnformation.latitude
        viewModel.longitude = placeSelectDataArg.storelnformation.longitude

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
        binding.addPlaceBtSubmit.setOnClickListener {
            if (viewModel.dwellTime != null) {
                viewModel.addPlaceItem()
                viewModel.addFireBasePlace()

                Toast.makeText(activity as Activity, "???????????????!!!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(NavigationDirections.addPlaceFragmentMyItineraryPagingFragment())


            } else {

                Toast.makeText(activity as Activity, "?????????????????????!!!", Toast.LENGTH_SHORT).show()

            }

        }



        if (!placeSelectDataArg.journey.name.isEmpty() && !placeSelectDataArg.storelnformation.storeTitle.isEmpty()) {
            val lunch = mutableListOf<String>()

            lunch.clear()
            lunch.add(placeSelectDataArg.journey.name)
            Log.v("placeSelectDataArg.journey.name", "${placeSelectDataArg.journey.name}")
//            setJourneySpinner(lunch)
            val adapter =
                ArrayAdapter(activity as Activity, R.layout.layout_item_center, lunch)

            adapter.setDropDownViewResource(R.layout.layout_item_center)


            selectJourney?.adapter = adapter
            selectJourney?.isEnabled = false
            viewModel.journeySinner = placeSelectDataArg.journey.name


            viewModel.journeyId = placeSelectDataArg.journey.id

            val day = placeSelectDataArg.place.day

            lunchDay.add("??? $day ???")
            selectDay?.isEnabled = false
            viewModel.daySinner = day

            Log.v("lunchDay",
                "${lunchDay}")

            val adapter2 =
                ArrayAdapter(activity as Activity,

                    R.layout.layout_item_center,
                    lunchDay)
            selectDay?.adapter = adapter2

            adapter.setDropDownViewResource(R.layout.layout_item_center)

            setTransportationSinner()


        } else {
            val lunch = mutableListOf<String>()

            lunch.clear()
            viewModel.getAllJourney()
            lunch.add("???????????????")
            viewModel.addAllJourney.observe(viewLifecycleOwner) {
                for (item in viewModel.getAllJourney) {
                    lunch.add(item.name)
                }
                lunchDay.clear()

                setJourneySpinner(lunch)

                setTransportationSinner()
            }

        }


        return binding.root

    }

    object TimeUtil {

        @JvmStatic
        fun DateToStamp(date: String, locale: Locale): Long {
            val simpleDateFormat = SimpleDateFormat("HH:mm", locale)
            /// ????????????????????????
            return simpleDateFormat.parse(date).time
        }
    }

    fun setJourneySpinner(lunch: MutableList<String>) {
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
                view!!.textAlignment = View.TEXT_ALIGNMENT_CENTER
                viewModel.journeySinner = selectJourney?.selectedItem.toString()
                lunchDay.clear()
                Log.v("lunchDay2", "$lunchDay")

                if (lunch.size > 1) {

                    for (item in viewModel.getAllJourney) {
                        if (item.name == selectJourney?.selectedItem.toString()) {
                            lunchDay.add("???????????????")
                            selectTotalDay = item.totalDay
                            for (i in 1..selectTotalDay) {
                                lunchDay.add("??? $i ???")
                            }
                            viewModel.journeyId = item.id
                        }
                    }
                    setDaySinner(lunchDay, 0)

                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    fun setDaySinner(lunchDay: MutableList<String>, day: Int) {
        val adapter2 =
            ArrayAdapter(activity as Activity,
                android.R.layout.simple_spinner_dropdown_item,
                lunchDay)
        selectDay?.adapter = adapter2
        //???????????????????????????????????????
        selectDay?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                view!!.textAlignment = View.TEXT_ALIGNMENT_CENTER

                viewModel.daySinner = selectDay?.selectedItemPosition!!.toInt()

                Log.v("viewModel.daySinner", "${viewModel.daySinner}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    fun setTransportationSinner() {

        val lunch = arrayListOf("?????????????????????", "??????", "??????", "?????????", "??????")
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
                    view!!.textAlignment = View.TEXT_ALIGNMENT_CENTER

                    viewModel.transportationSinner =
                        selectTransportation?.selectedItemPosition!!.toInt()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }

    }
}

