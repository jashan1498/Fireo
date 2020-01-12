package com.example.fireo

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern

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
                intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
            backButton -> finish()
        }
    }

    private fun validateEditTexts() {
        lottieAnim.playAnimation()
        val userEmail = username_edit_text.text.toString()
        val password = pass_edit_text.text.toString()

        val emailValidation = userEmail.length > 6 && validateEmail(userEmail)
        val passwordValidation = password.length > 7

        if (emailValidation && passwordValidation) {
            lottieAnim.visibility = View.VISIBLE
            firebaseAuth.createUserWithEmailAndPassword(userEmail, password)
                .addOnSuccessListener {
                    val user: FirebaseUser? = it.user

                    if (user != null) {
                        if (user.email!!.isNotEmpty()) {
                            firebaseUser = user
                            val redirectToMain = Intent(this, MainActivity::class.java)
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

    private fun validateEmail(userEmail: String): Boolean {
        val regex = "[a-z|0-9|_%+-]+@[a-z|0-9]+.[a-z|0-9]+"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(userEmail)
        if (matcher.matches()) {
            return true
        }
        Toast.makeText(this, "Wrong Format For Email", Toast.LENGTH_LONG).show()
        return false
    }
}