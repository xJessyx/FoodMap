package com.jessy.foodmap

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.jessy.foodmap.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
       binding.lifecycleOwner = this
        setupBottomNav()


    }
    private fun setupBottomNav() {
        binding.myBottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToHomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.itineraryPlanningFragment -> {

                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToItineraryPlanningFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.foodMapSearchFragment -> {

                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToFoodMapSearchFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.memberFragment -> {
                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToMemberFragment())
//                    when (viewModel.isLoggedIn) {
//                        true -> {
//                            findNavController(R.id.myNavHostFragment).navigate(
//                                NavigationDirections.navigateToProfileFragment(
//                                    viewModel.user.value
//                                )
//                            )
//                        }
//                        false -> {
//                            findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToLoginDialog())
//                            return@setOnItemSelectedListener false
//                        }
//                    }
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    fun hideToolBar() {
        binding.toolbar.visibility = View.GONE
    }

    fun showToolBar() {
        binding.toolbar.visibility = View.VISIBLE
    }

    fun hidBottomNavigation(){
        binding.myBottomNavigationView.visibility = View.GONE
    }

    fun showBottomNavigation(){
        binding.myBottomNavigationView.visibility = View.VISIBLE
    }
}