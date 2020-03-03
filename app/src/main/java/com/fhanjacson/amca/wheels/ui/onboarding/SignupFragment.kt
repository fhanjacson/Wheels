package com.fhanjacson.amca.wheels.ui.onboarding

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fhanjacson.amca.wheels.Constant

import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.model.ProfileActivity
import com.fhanjacson.amca.wheels.repository.FirestoreRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.signup_fragment.*
import kotlinx.android.synthetic.main.signup_fragment.view.*
import java.util.regex.Pattern

class SignupFragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()
    lateinit var signupButton: Button
    private val repo = FirestoreRepository()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signup_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signupButton = view.signup_button

        signupButton.setOnClickListener {
            signupButton.isEnabled = false
            signUp(view.signup_fullname.text.toString(), view.signup_email.text.toString(), view.signup_password.text.toString())
        }
    }

    private fun signUp(fullName: String, emailAddress: String, password: String) {

        if (!validateForm()) {
            signupButton.isEnabled = true
            return
        }

        auth.createUserWithEmailAndPassword(emailAddress, password)
            .addOnSuccessListener {
                Log.d(Constant.LOG_TAG, "createUserWithEmail:success")
                setUserProfile(fullName)
            }
            .addOnFailureListener {
                Log.d(Constant.LOG_TAG, "createUserWIthEmail:Fail $it")
                Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
            }
    }


    fun setUserProfile(fullName: String) {
        val user = auth.currentUser

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(fullName)
//            .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
            .build()

        user?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(Constant.LOG_TAG, "User profile updated.")
                Toast.makeText(context, "Registration Success!", Toast.LENGTH_SHORT).show()
                addToAccountHistory(ProfileActivity(user.uid, Timestamp.now(), Constant.ACTIVITY_TYPE_ACCOUNT_CREATED, "Account first created"))
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, "User profile not updated.", Toast.LENGTH_SHORT).show()
                signupButton.isEnabled = true
            }
        }

    }

    private fun addToAccountHistory(activity: ProfileActivity) {
        repo.addActivity(activity)
    }

    private fun isEmailValid(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern = Pattern.compile("[a-zA-Z0-9!@#$]{8,24}")
        return passwordPattern.matcher(password).matches()
    }


    private fun validateForm(): Boolean {
        var valid = true

        val fullName = signup_fullname.text.toString()

        if (TextUtils.isEmpty(fullName)) {
            signup_fullname.error = "Full name Required"
            valid = false
        } else {
            signup_fullname.error = null
        }


        val email = signup_email.text.toString()

        if (TextUtils.isEmpty(email)) {
            signup_email.error = "Email Required"
            valid = false
        } else if (!isEmailValid(email)) {
            signup_email.error = "Email is badly formatted"
            valid = false
        } else {
            signup_email.error = null
        }


        val password = signup_password.text.toString()



        if (TextUtils.isEmpty(password)) {
            signup_password.error = "Password Required"
            valid = false
        } else if (!isPasswordValid(password)) {
            signup_password.error = "8 to 24 Character Minimum valid character: [a-z A-Z 0-9 !@#$]"
            valid = false
        } else {
            signup_password.error = null
        }
        return valid
    }
}
