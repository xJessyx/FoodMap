package com.jessy.foodmap

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jessy.foodmap.databinding.FragmentProgressBarBinding

class ProgressBarFragment : DialogFragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentProgressBarBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root

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