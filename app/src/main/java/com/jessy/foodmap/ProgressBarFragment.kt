package com.jessy.foodmap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ProgressBarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment



//       val handler = Handler()
//
//        binding.homeBar.visibility = View.VISIBLE
//
//        var i = binding.homeBar.progress
//
//        Thread(Runnable {
//            // this loop will run until the value of i becomes 99
//            while (i < 100) {
//                i += 1
//                // Update the progress bar and display the current value
//                handler.post(Runnable {
//                    binding.homeBar.progress = i
//                    // setting current progress to the textview
//                    binding.homeTvBar!!.text = i.toString() + "/" + binding.homeBar.max
//                })
//                try {
//                    Thread.sleep(100)
//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
//            }
//
//            // setting the visibility of the progressbar to invisible
//            // or you can use View.GONE instead of invisible
//            // View.GONE will remove the progressbar
//            binding.homeBar.visibility = View.INVISIBLE
//            binding.homeTvBar.visibility = View.INVISIBLE
//
//        }).start()



        return inflater.inflate(R.layout.fragment_progress_bar, container, false)
    }

}