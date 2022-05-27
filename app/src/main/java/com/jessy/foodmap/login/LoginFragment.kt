package com.jessy.foodmap.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.jessy.foodmap.MainActivity
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.R
import com.jessy.foodmap.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        (activity as MainActivity).hidBottomNavigation()
        (activity as MainActivity).hideToolBar()



        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.addUser.observe(viewLifecycleOwner) {
            Log.v("yaya","viewModel.addUser.observe, it=$it")
            Log.v("2", "111")
            viewModel.addFireBaseUser(it)
            Log.v("2", "1222")

        }

        viewModel.navigateToHome.observe(viewLifecycleOwner) {

            it?.let {
                Toast.makeText(activity as Activity, "login_success", Toast.LENGTH_SHORT).show()
                findNavController().navigate(NavigationDirections.loginFragmentHomeFragment())
                viewModel.onHomeNavigated()
            }

        }
        binding.buttonSignIn.setOnClickListener {
            signIn()
        }
        return binding.root

    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("944185652421-d424eodd2u74vpscr4u1jcniif48r5nh.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(activity as Activity, gso)
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.userName = account.displayName.toString()
                viewModel.email = account.email.toString()
                viewModel.image = account.photoUrl.toString()

                viewModel.getFireBaseUser()


            } catch (e: ApiException) {
                Log.i("givemepass", "signInResult:failed code=" + e.statusCode)
                Toast.makeText(activity as Activity, "login_fail", Toast.LENGTH_SHORT).show()

            }
        } else {
            Log.i("givemepass", "login fail")
            Toast.makeText(activity as Activity, "login_fail", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val RC_SIGN_IN = 100
    }

}