package com.jessy.foodmap

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

//  show Api value
//        val ai: ApplicationInfo = applicationContext.packageManager
//            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
//        val value = ai.metaData["com.google.android.geo.API_KEY"]
//
//        val key = value.toString()
//        Toast.makeText(applicationContext,key, Toast.LENGTH_LONG).show()


    }
    private fun setupBottomNav() {
        binding.myBottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToHomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_itineraryPlanning -> {

                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToItineraryPlanningFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_foodMapSearch -> {

                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToFoodMapSearchFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_member -> {
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
}