package com.jessy.foodmap.itinerary.invite.paging

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.jessy.foodmap.databinding.FragmentJoinBinding


class JoinFragment : Fragment() {

private val viewModel: JoinViewModel by lazy {
    ViewModelProvider(this).get(JoinViewModel::class.java)
}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val journeyId = requireArguments().getString("journeyId") ?: ""
        Log.v("journeyIdArg join ","$journeyId")
        val binding = FragmentJoinBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        viewModel.getJoinInviteItem(journeyId)

       val adapter =JoinAdapter()
        binding.joinRecyclerView.adapter = adapter
        binding.viewModel = viewModel
        binding.joinRecyclerView.addItemDecoration(DividerItemDecoration(activity as Activity, DividerItemDecoration.VERTICAL))

        viewModel.getJoinInvite.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()

                Log.v("it join", "$it")
            }

        }

        return binding.root
    }

    companion object{
        fun newInstance(jId: String):Fragment{
            val fragment = JoinFragment()
            val bundle = Bundle()

            bundle.putString("journeyId",jId)
            fragment.arguments = bundle
            return fragment

        }
    }

}