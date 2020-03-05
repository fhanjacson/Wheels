package com.fhanjacson.amca.wheels.ui.onboarding

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.fhanjacson.amca.wheels.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.view.*
import java.util.regex.Pattern

class LoginFragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()
    lateinit var loginButton: Button
    lateinit var emailText: EditText
    lateinit var passwordText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
// directly

        loginButton = view.loginButton

        loginButton.setOnClickListener {
            loginButton.isEnabled = false
            login(view.login_email.text.toString(), view.login_password.text.toString())
        }
    }



    private fun login(email: String, password: String) {

        if (!validateForm()) {
            loginButton.isEnabled = true
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Toast.makeText(context, "Login Success!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Wrong Email or Password entered", Toast.LENGTH_SHORT).show()
                loginButton.isEnabled = true
            }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = login_email.text.toString()

        if (TextUtils.isEmpty(email)) {
            login_email.error = "Email Required"
            valid = false
        } else if (!isEmailValid(email)) {
            login_email.error = "Email is badly formatted"
            valid = false
        } else {
            login_email.error = null
        }

        val password = login_password.text.toString()

        if (TextUtils.isEmpty(password)) {
            login_password.error = "Password Required"
            valid = false
        } else if (!isPasswordValid(password)) {
            login_password.error = "8 to 24 Character Minimum valid character: [a-z A-Z 0-9 !@#$]"
            valid = false
        } else {
            login_password.error = null
        }

        return valid
    }

    private fun isEmailValid(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern = Pattern.compile("[a-zA-Z0-9!@#$]{8,24}")
        return passwordPattern.matcher(password).matches()
    }

}


