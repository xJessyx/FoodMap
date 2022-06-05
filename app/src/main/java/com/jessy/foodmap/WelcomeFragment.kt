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
        (activity as MainActivity).hideToolBar()

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_welcome, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.lottieDinosaur.getDuration()
        binding.lottieDinosaur.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {

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