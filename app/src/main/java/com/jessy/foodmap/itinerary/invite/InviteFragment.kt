package com.jessy.foodmap.itinerary.invite

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.jessy.foodmap.R
import com.jessy.foodmap.databinding.FragmentInviteBinding
import com.jessy.foodmap.itinerary.invite.paging.InvitePagingAdapter

class InviteFragment : Fragment() {

    private val viewModel: InviteViewModel by lazy {
        ViewModelProvider(this)[InviteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val binding = FragmentInviteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val journeyArg = InviteFragmentArgs.fromBundle(requireArguments()).journeyKey
        val viewModel = InviteViewModel(journeyArg)
        binding.viewModel = viewModel
        val pageAdapter =
            InvitePagingAdapter(journeyArg.id, requireActivity().supportFragmentManager, lifecycle)
        binding.inviteViewpager2.adapter = pageAdapter

        binding.inviteItineraryName.text = journeyArg.name
        binding.inviteStartDate.text = journeyArg.startDate
        binding.inviteEndDate.text = journeyArg.endDate
        binding.inviteBtn.setOnClickListener {
            val item = LayoutInflater.from(activity as Activity).inflate(R.layout.item_layout, null)

            AlertDialog.Builder(activity as Activity)
                .setTitle(R.string.edit_email)
                .setView(item)
                .setPositiveButton(R.string.yes) { _, _ ->
                    val editText = item.findViewById(R.id.edit_text) as EditText
                    val email = editText.text.toString()
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(activity as Activity,
                            R.string.edit_email,
                            Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        viewModel.checkUser(email)
                        viewModel.getSenderUser.observe(viewLifecycleOwner) {
                            it?.let {
                                viewModel.addInvitationsItem(email, it)

                                viewModel.addInvite.observe(viewLifecycleOwner) {

                                    viewModel.addFireBaseInvitations()
                                }
                            }
                        }

                        viewModel.errorUser.observe(viewLifecycleOwner) {
                            Toast.makeText(activity as Activity,
                                R.string.no_such_person,
                                Toast.LENGTH_SHORT).show()
                        }

                    }

                }
                .show()
        }

        TabLayoutMediator(binding.inviteTabs, binding.inviteViewpager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "????????????"
                }
                1 -> {
                    tab.text = "?????????"
                }
            }

        }.attach()

        return binding.root
    }

}