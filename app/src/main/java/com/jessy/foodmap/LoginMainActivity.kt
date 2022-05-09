package com.jessy.foodmap

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jessy.foodmap.data.User
import com.jessy.foodmap.databinding.ActivityLoginMainBinding
import com.jessy.foodmap.home.addArticles.AddArticleViewModel


class LoginMainActivity : AppCompatActivity() {

    private val viewModel: LoginMainActivityViewModel by lazy {
        ViewModelProvider(this).get(LoginMainActivityViewModel::class.java)
    }
   // private var mGoogleSignInClient: GoogleSignInClient? = null
    private lateinit var binding: ActivityLoginMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_main)
        binding.lifecycleOwner = this



        //播放
        binding.lottieDinosaur.playAnimation()
//暫停
        binding.lottieDinosaur.pauseAnimation()
//取消
        binding.lottieDinosaur.cancelAnimation()
//獲取動畫時長
        binding.lottieDinosaur.getDuration()
//監聽
        binding.lottieDinosaur.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {

            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
//        val animationView: LottieAnimationView = binding.loginAnimationView
//        animationView.setAnimation("enjoying_the_walk.json")
//        animationView.loop(true)
//        animationView.playAnimation()

   binding.buttonSignIn.setOnClickListener {
        signIn()
    }
    }
    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("944185652421-d424eodd2u74vpscr4u1jcniif48r5nh.apps.googleusercontent.com")
            .requestEmail()
            .build()


        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
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
                viewModel.email=account.email.toString()
                viewModel.addUser()

                binding.lifecycleOwner?.let {
                    viewModel.addUser.observe(it){

                        viewModel.addFireBaseUser()
                    }
                }

                Toast.makeText(this,"login_success", Toast.LENGTH_SHORT).show()

//                val email  = account?.email
//                val token = account?.idToken
//                Log.i("givemepass", "email:$email, token:$token")
              //  findNavController().navigate(NavigationDirections.loginMainActivityHomeFragment())
              //  findNavController(com.jessy.foodmap.R.id.myNavHostFragment).navigate(NavigationDirections.loginMainActivityHomeFragment())

                val i = Intent(this, MainActivity::class.java)
               startActivity(i) //開始跳往要去的Activity
               this.finish()//結束目前Activity
                Log.v("data","$data")


            } catch (e: ApiException) {
                Log.i("givemepass", "signInResult:failed code=" + e.statusCode)
                Toast.makeText(this,"login_fail", Toast.LENGTH_SHORT).show()

            }
        } else{
            Log.i("givemepass", "login fail")
            Toast.makeText(this, "login_fail", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val RC_SIGN_IN = 100
    }


}