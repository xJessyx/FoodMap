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
        //viewModel.addJourneyItem()

        val storeInformationArg = AddPlaceFragmentArgs.fromBundle(requireArguments()).storeKey

        binding.addPlaceTvPlaceName.text = storeInformationArg!!.storeTitle
        viewModel.placeName =  storeInformationArg.storeTitle
        binding.addPlaceSpSelectDwellTime.setOnClickListener {

            val ca = Calendar.getInstance()
            var mHour = ca[Calendar.HOUR_OF_DAY]
            var mMinute = ca[Calendar.MINUTE]

            val timePickerDialog = TimePickerDialog(
                activity as Activity, TimePickerDialog.OnTimeSetListener { _, hourofDay, minute ->
                    mHour = hourofDay
                    mMinute = minute

                    val mHourToMillis = hourofDay*60*60*1000
                    val mMinuteToMillis = minute*60*1000
                    val totalMillis = mHourToMillis+mMinuteToMillis


                    val mHourString = String.format("%02d", mHour)
                    val mMinuteString = String.format("%02d", mMinute)
                     val mTime = "${mHourString}:${mMinuteString}"

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

//                    val mHourToMillis = hourofDay*60*60*1000
//                    val mMinuteToMillis = minute*60*1000
//                    val totalMillis = mHourToMillis+mMinuteToMillis


                    val  startTimeTimestamp = TimeUtil.DateToStamp(mTime, Locale.TAIWAN)
                    binding.addPlaceSpSelectStartTime.setText(mTime)
                    viewModel.startTime = startTimeTimestamp
                },
                mHour, mMinute, true
            )
            timePickerDialog.show()
        }
        binding.addPlaceBtSubmit.setOnClickListener {
            if((viewModel.placeName != null)&&(viewModel.daySinner != null) &&
                (viewModel.transportationSinner != null)&&(viewModel.startTime != null)&&(viewModel.dwellTime != null)){
                viewModel.addPlaceItem()
                viewModel.addFireBasePlace()
            }

        }

        viewModel.addAllJourney.observe(viewLifecycleOwner){
            setJourneySpinner()
            setDaySinner()
            setTransportationSinner()
        }




        return binding.root
    }
    object TimeUtil {
        @JvmStatic
        fun StampToDate(time: Long, locale: Locale): String {
            // 進來的time以秒為單位，Date輸入為毫秒為單位，要注意

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale)

            return simpleDateFormat.format(Date(time))
        }

        @JvmStatic
        fun DateToStamp(date: String, locale: Locale): Long {
            val simpleDateFormat = SimpleDateFormat("HH:mm", locale)

            /// 輸出為毫秒為單位
            return simpleDateFormat.parse(date).time
        }
    }




    fun setJourneySpinner() {
        var lunch = mutableListOf<String>()
        for ( item in viewModel.getAllJourney){
            lunch.add(item.name)
            Log.v("lunch","$lunch")
        }
        val adapter =
            ArrayAdapter(activity as Activity, android.R.layout.simple_spinner_dropdown_item, lunch)
        selectJourney?.adapter = adapter
        selectJourney?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.journeySinner = selectJourney?.selectedItem.toString()

                setSpinnerDayList()
                }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }


        }
    }

    fun setSpinnerDayList(){
//        val adapter = ArrayAdapter(
//            this,
//            android.R.layout.simple_spinner_dropdown_item,
//            CountyUtil.getTownsByCountyName(viewModel.journeySinner)
//        )
//        selectDay?.adapter = adapter
//        selectDay?.adapter.setSelection(CountyUtil.getTownIndexByName(viewModel.journeySinner, viewModel.daySinner))
    }
        fun setDaySinner() {
//            var totalDay: Int = 0
//            Log.v("total","$totalDay")
//
//
//                for (item in viewModel.getAllJourney) {
//
//                    if (item.journeyName == JourneyItem) {
//                        totalDay = item.totalDay
//
//                    }
//
//                }

        val lunch2 = arrayListOf("請選擇哪天","第一天", "第二天", "第三天", "第四天")
        val adapter =
            ArrayAdapter(activity as Activity, android.R.layout.simple_spinner_dropdown_item, lunch2)
        selectDay?.adapter = adapter

            selectDay?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.daySinner = selectDay?.selectedItemPosition!!.toInt()

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
                        id: Long
                    ) {
                        viewModel.transportationSinner = selectTransportation?.selectedItemPosition!!.toInt()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                }

        }
    }

