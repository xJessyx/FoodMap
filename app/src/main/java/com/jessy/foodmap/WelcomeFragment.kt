package com.jessy.foodmap

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

class WelcomeFragment : Fragment() {

    private lateinit var binding: com.jessy.foodmap.databinding.FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        (activity as MainActivity).hidBottomNavigation()
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_welcome, container, false)
//        //  binding = DataBindingUtil.setContentView(activity as Activity, R.layout.fragment_login)
//
        binding.lifecycleOwner = viewLifecycleOwner
//        val lottieDinosaur = findViewById(R.id.lottie_dinosaur)
//播放
     //   binding.lottieDinosaur.playAnimation()
        //  Log.v("playAnimation","${binding.lottieDinosaur.playAnimation()}")

//暫停
       // binding.lottieDinosaur.pauseAnimation()
        // Log.v("pauseAnimation","${binding.lottieDinosaur.pauseAnimation()}")

//取消
      //  binding.lottieDinosaur.cancelAnimation()
//        Log.v("cancelAnimation","${binding.lottieDinosaur.cancelAnimation()}")

//獲取動畫時長
        binding.lottieDinosaur.getDuration()

          Log.v("binding.lottieDinosaur.getDuration()","${binding.lottieDinosaur.getDuration()}")
//監聽
        binding.lottieDinosaur.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                //Log.v("onAnimationRepeat","onAnimationRepeat")
            }

            override fun onAnimationEnd(animation: Animator?) {
                //Log.v("onAnimationEnd","onAnimationEnd")

                findNavController().navigate(NavigationDirections.welcomeFragmentLoginFragment())

            }

            override fun onAnimationCancel(animation: Animator?) {
                Log.v("onAnimationCancel","onAnimationCancel")

            }

            override fun onAnimationStart(animation: Animator?) {
                Log.v("onAnimationStart","onAnimationStart")
            }
        })
        return binding.root

    }

}