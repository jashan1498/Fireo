package com.example.fireo.activities

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.example.fireo.BaseApplication
import com.example.fireo.R
import com.example.fireo.Utils.LoginHelper
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseApplication(), View.OnClickListener {

    private lateinit var signInButton: TextView
    private lateinit var backButton: ImageView
    private lateinit var loginButton: Button
    private lateinit var lottieAnim: LottieAnimationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInButton = sign_up_btn
        backButton = back_btn
        loginButton = login_button
        lottieAnim = lottie_anim

        signInButton.setOnClickListener(this)
        backButton.setOnClickListener(this)
        loginButton.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v) {
            sign_up_btn -> {
                intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                this.finish()
            }
            loginButton -> validateEditTexts()
            backButton -> finish()
        }
    }

    private fun validateEditTexts() {
        lottieAnim.playAnimation()
        val userEmail = username_edit_text.text.toString()
        val password = pass_edit_text.text.toString()

        val emailValidation = userEmail.length > 6 && LoginHelper.validateEmail(userEmail)
        val passwordValidation = password.length > 7

        if (!emailValidation || !passwordValidation) {
            Toast.makeText(
                this,
                this.resources.getString(R.string.invalid_info),
                Toast.LENGTH_LONG
            ).show()
        }

        if (emailValidation && passwordValidation) {
            lottieAnim.visibility = View.VISIBLE
            firebaseAuth.signInWithEmailAndPassword(userEmail, password)
                .addOnSuccessListener {
                    val user: FirebaseUser? = it.user

                    if (user != null) {
                        if (user.email!!.isNotEmpty()) {
                            firebaseUser = user
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
                    }
                }.addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "An Error Occurred! Please Try Again Later",
                        Toast.LENGTH_LONG
                    ).show()
                    lottieAnim.visibility = View.INVISIBLE
                    lottieAnim.pauseAnimation()
                }
        }
    }
}
