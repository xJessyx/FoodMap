package com.jessy.foodmap.home.addArticles

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.jessy.foodmap.databinding.FragmentAddArticleBinding
import androidx.navigation.fragment.findNavController
import com.jessy.foodmap.NavigationDirections

class AddArticleFragment : Fragment() {
    private val viewModel: AddArticleViewModel by lazy {
        ViewModelProvider(this).get(AddArticleViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentAddArticleBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.addArticleBtn.setOnClickListener {


            if( (viewModel.articleTitle.value != null) &&( viewModel.articleConent.value !=null)) {
                viewModel.addArticleItem()
                viewModel.addFireBaseArticle()
                viewModel.addArticle.observe(viewLifecycleOwner){
                    it?.let {
                        findNavController().navigate(NavigationDirections.addArticleFragmentHomeFragment())
                        Log.v("it", "$it")

                    }
                }
            } else {
                Toast.makeText(activity as Activity, "有資料尚未填寫!!!", Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }


}