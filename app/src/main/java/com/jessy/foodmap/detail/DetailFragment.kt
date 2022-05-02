package com.jessy.foodmap.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.MainActivity
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.R
import com.jessy.foodmap.data.StoreInformation
import com.jessy.foodmap.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    //    private val viewModel: DetailViewModel by lazy {
//        ViewModelProvider(this).get(DetailViewModel::class.java)
//    }
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false)
        val articleKey = DetailFragmentArgs.fromBundle(requireArguments()).articleKey
        var viewModel = DetailViewModel(articleKey)

        (activity as MainActivity).hidbottomNavigation()

//        viewModel.article.value?.let { binding.detailImg.setImageResource(it.image) }
        //viewModel.article.value = binding.detailImg(articleKey.image)
//        viewModel.author = articleKey.author
//        viewModel.authorImage= articleKey.authorImage
//        viewModel.image =articleKey.image
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        var likeSum :Int =0
        var collectSum:Int = 0
//        binding.detailTvAuthorName.text = articleKey.author
//       binding.detailImg.imageurl = articleKey.authorImage.toString()
//       binding.detailImgPerson.
    //    var aa =  TimeUtil.StampToDate(1560839160000, Locale.TAIWAN)

       viewModel.article.observe(viewLifecycleOwner) {



           val data = StoreInformation(null, it.placeName,"","",null)

           binding.detailAddStore.setOnClickListener {
               findNavController().navigate(NavigationDirections.detailFragmentAddPlaceFragment(data))
           }

       }

        binding.detailLike.setOnClickListener {

            likeSum =likeSum+1

            if(likeSum % 2 != 0){
                binding.detailLike.setBackgroundResource(R.drawable.heart2)
               db.collection("articles").document(articleKey.id)
                   .update("totalLike", articleKey.totalLike +1)
            }else{
                binding.detailLike.setBackgroundResource(R.drawable.like2)
                db.collection("articles").document(articleKey.id)
                    .update("totalLike",articleKey.totalLike )
            }

        }

        binding.detailCollect.setOnClickListener {
            var favoriteUsersItem= mutableListOf<String>()

            collectSum =collectSum+1
            if(collectSum % 2 != 0) {
                binding.detailCollect.setBackgroundResource(R.drawable.star3)

                favoriteUsersItem.add(0, "32fRAA8nlkV2gAojqHB1")
                db.collection("articles").document(articleKey.id)
                    .update("favoriteUsers", favoriteUsersItem)
            }else{
                binding.detailCollect.setBackgroundResource(R.drawable.star)

                favoriteUsersItem.add(0, "")
                db.collection("articles").document(articleKey.id)
                    .update("favoriteUsers", favoriteUsersItem)
            }
        }

        binding.detailBtBack.setOnClickListener {
            this.findNavController().navigateUp()
        }


        return binding.root
    }

//    object TimeUtil {
//
//        @JvmStatic
//        fun StampToDate(time: Long, locale: Locale): String {
//            // 進來的time以秒為單位，Date輸入為毫秒為單位，要注意
//
//            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale)
//
//            return simpleDateFormat.format(Date(time))
//        }
//        @JvmStatic
//        fun DateToStamp(date: String, locale: Locale): Long {
//            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale)
//
//            /// 輸出為毫秒為單位
//            return simpleDateFormat.parse(date).time
//        }
//    }

}