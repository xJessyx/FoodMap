package com.jessy.foodmap.home.addArticles

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.jessy.foodmap.MainActivity
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.R
import com.jessy.foodmap.databinding.FragmentAddArticleBinding


class AddArticleFragment : Fragment() {
    private val viewModel: AddArticleViewModel by lazy {
        ViewModelProvider(this).get(AddArticleViewModel::class.java)
    }
    private lateinit var placesClient: PlacesClient
    lateinit var autocompleteSessionToken: AutocompleteSessionToken
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentAddArticleBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        (activity as MainActivity).hideToolBar()

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.addArticle_autocomplete)
                    as AutocompleteSupportFragment

        initPlaces()

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))



        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: ${place.name}, ${place.id}")
                viewModel.articlePlaceName =  place.name
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })

        binding.addArticleBtn.setOnClickListener {


            if( (viewModel.articleTitle.value != null) &&( viewModel.articleConent.value !=null) &&(viewModel.articlePlaceName != null)) {
                viewModel.addArticleItem()
                viewModel.addFireBaseArticle()
                viewModel.addArticle.observe(viewLifecycleOwner){
                    it?.let {
                        findNavController().navigate(NavigationDirections.addArticleFragmentHomeFragment())

                    }
                }
            } else {
                Toast.makeText(activity as Activity, "有資料尚未填寫!!!", Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val searchView = view.findViewById<EditText>(R.id.addArticle_autocomplete)
//
//        searchView.setHintTextColor(Color.RED)
//        searchView.setTextColor(Color.GREEN)
//    }

    private fun initPlaces() {
        val info = (activity as MainActivity).applicationContext.packageManager
            .getApplicationInfo(
                (activity as MainActivity).packageName,
                PackageManager.GET_META_DATA
            )

        val key = info.metaData[resources.getString(R.string.map_api_key_name)].toString()
        Places.initialize(requireContext(), key)

//        Places.initialize(requireActivity().getApplicationContext(), BuildConfig.MAPS_API_KEY)
        placesClient = Places.createClient(activity as Activity)
        autocompleteSessionToken = AutocompleteSessionToken.newInstance()
    }



}