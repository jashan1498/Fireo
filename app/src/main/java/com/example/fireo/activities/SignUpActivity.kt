package com.example.fireo.activities

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.example.fireo.BaseApplication
import com.example.fireo.Constants.Constants
import com.example.fireo.Constants.Constants.DEFAULT_USER_TYPE
import com.example.fireo.R
import com.example.fireo.Utils.LoginHelper
import com.example.fireo.model.User
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseApplication(), View.OnClickListener {

    private lateinit var signInButton: TextView
    private lateinit var backButton: ImageView
    private lateinit var loginButton: TextView
    private lateinit var lottieAnim: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signInButton = sign_up_button
        backButton = back_btn
        loginButton = login_btn
        lottieAnim = lottie_anim

        signInButton.setOnClickListener(this)
        backButton.setOnClickListener(this)
        loginButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            signInButton -> validateEditTexts()
            loginButton -> {
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                this.finish()
            }
            backButton -> finish()
        }
    }

    private fun validateEditTexts() {
        lottieAnim.playAnimation()
        val userEmail = username_edit_text.text.toString()
        val password = pass_edit_text.text.toString()
        val phone = phone_number.text.toString()

        val emailValidation = userEmail.length > 6 && LoginHelper.validateEmail(userEmail)
        val passwordValidation = password.length > 7
        val phoneValidation = phone.length > 8

        if (!emailValidation) {
            Toast.makeText(
                this,
                this.resources.getString(R.string.email_invalid),
                Toast.LENGTH_LONG
            ).show()
        } else if (!passwordValidation) {
            Toast.makeText(
                this,
                this.resources.getString(R.string.pass_invalid),
                Toast.LENGTH_LONG
            ).show()
        } else if (!passwordValidation) {
            Toast.makeText(
                this,
                this.resources.getString(R.string.invalid_phone),
                Toast.LENGTH_LONG
            ).show()
        }



        if (emailValidation && passwordValidation && phoneValidation) {
            lottieAnim.visibility = View.VISIBLE
            firebaseAuth.createUserWithEmailAndPassword(userEmail, password)
                .addOnSuccessListener {
                    val user: FirebaseUser? = it.user

                    if (user != null) {
                        if (user.email!!.isNotEmpty()) {
                            firebaseUser = user
                            syncUserWithFirebase()
                        }
                    }
                }.addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        resources.getString(R.string.error_string),
                        Toast.LENGTH_LONG
                    ).show()
                    lottieAnim.visibility = View.INVISIBLE
                    lottieAnim.pauseAnimation()
                }
        }
    }

    private fun redirectToMain() {
        val redirectToMain = Intent(this, SplashActivity::class.java)
        lottieAnim.setAnimation(R.raw.animation_done)
        lottieAnim.pauseAnimation()
        lottieAnim.loop(false)
        lottieAnim.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
                startActivity(redirectToMain)
            }

            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
        })
        lottieAnim.playAnimation()
    }

    private fun syncUserWithFirebase() {
        val fireStore = fireStore
        val user = User(
            firebaseUser.email,
            firebaseUser.uid,
            DEFAULT_USER_TYPE,
            full_name.text.toString(),
            phone_number.text.toString()
        )
        fireStore.collection(Constants.Collections.USER).document(firebaseUser.uid).set(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    redirectToMain()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.data_not_synced),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}