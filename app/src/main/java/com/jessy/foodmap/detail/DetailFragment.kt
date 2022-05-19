package com.jessy.foodmap.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.R
import com.jessy.foodmap.data.Journey
import com.jessy.foodmap.data.Place
import com.jessy.foodmap.data.PlaceSelectData
import com.jessy.foodmap.data.StoreInformation
import com.jessy.foodmap.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
//        private val viewModel: DetailViewModel by lazy {
//        ViewModelProvider(this).get(DetailViewModel::class.java)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false)
        val articleKey = DetailFragmentArgs.fromBundle(requireArguments()).articleKey
        var viewModel = DetailViewModel(articleKey)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = DetailAdapter()
        binding.detailRecyclerView.adapter = adapter



        viewModel.article.observe(viewLifecycleOwner) {

            val data = PlaceSelectData(StoreInformation(null,
                it.placeName,
                "",
                "",
                it.latitude,
                it.longitude), Place(),
                Journey())

            binding.detailAddStore.setOnClickListener {
                findNavController().navigate(NavigationDirections.detailFragmentAddPlaceFragment(
                    data))
                Log.v("placeSelectDataArgs:detailArg", "$data")
            }

        }

        viewModel.checkFavoriteStatus()
        viewModel.checkLikeStatus()
        viewModel.getFireBaseMessages()
        viewModel.addFireBaseMessages()

        viewModel.getMessageLiveData.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        viewModel


        viewModel.favoriteStatus.observe(viewLifecycleOwner) {
            if (it) {
                binding.detailCollect.setChecked(true)
            } else {
                binding.detailCollect.setChecked(false)
            }

        }

        viewModel.likeStatus.observe(viewLifecycleOwner) {
            if (it) {
                binding.detailLike.setChecked(true)
            } else {
                binding.detailLike.setChecked(false)
            }
        }


        binding.detailLike.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.updateLike()
            } else {
                viewModel.updateRemoveLike()
            }

        }

        Log.v("isChecked", "ssssssssss")
        binding.detailCollect.setOnCheckedChangeListener { _, isChecked ->
            Log.v("aaa", "$isChecked")

            if (isChecked) {
                Log.v("bb", "$isChecked")
                viewModel.updateCollect()
            } else {
                Log.v("cc", "$isChecked")

                viewModel.updateRemoveCollect()
            }
            Log.v("dd", "$isChecked")

        }

        binding.detailBtBack.setOnClickListener {
            this.findNavController().navigateUp()
        }


        return binding.root
    }


}