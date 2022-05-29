package com.jessy.foodmap.detail

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupMenu
import android.widget.Toast
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

        binding.detailEdMessage.clearFocus()
        val adapter = DetailAdapter(requireContext(),viewModel)
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
            }

        }

        viewModel.checkFavoriteStatus()
        viewModel.checkLikeStatus()
        viewModel.getBlockadeUsers()
        viewModel.getFireBaseMessages()

        viewModel.getMessageLiveData.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

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

        binding.detailCollect.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                viewModel.updateCollect()
            } else {
                viewModel.updateRemoveCollect()
            }
        }

        binding.detailBtBack.setOnClickListener {
            this.findNavController().navigateUp()
        }

        binding.detailSendBtn.setOnClickListener {

                viewModel.addMessagesItem()
                viewModel.addMessage.observe(viewLifecycleOwner) {
                    Toast.makeText(activity as Activity, "已新增成功!!!", Toast.LENGTH_SHORT).show()
                    viewModel.addFireBaseMessages()
                    viewModel.getFireBaseMessages()
                    binding.detailEdMessage.text.clear()
                }
        }

       binding.detailChat.setOnClickListener {
           binding.detailEdMessage.setFocusableInTouchMode(true);
           binding.detailEdMessage.requestFocus()
       }
        return binding.root
    }


}