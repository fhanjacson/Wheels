package com.fhanjacson.amca.wheels.ui.account


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.fhanjacson.amca.wheels.Constant

import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.base.GlideApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.fragment_account_detail.view.*
import java.io.File
import java.io.FileInputStream

/**
 * A simple [Fragment] subclass.
 */
class AccountDetailFragment : Fragment() {

    val auth = FirebaseAuth.getInstance()
    private lateinit var fview: View
    private lateinit var newDisplayName: String
    private lateinit var newProfileImageFilePath: Uri
    private lateinit var newProfileImageUri: Uri
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference

    private lateinit var gsNewProfileImageRef: StorageReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fview = view
        val user = auth.currentUser
        if (user != null) {
            if (user.photoUrl != null) {
                GlideApp.with(view.context).load(user.photoUrl).into(view.profileImage_detail)
            } else {
                view.profileImage_detail.setImageResource(R.drawable.placeholder_profile_image)
            }

            view.profileFullNameText.setText(user.displayName)


            view.saveProfileButton.setOnClickListener {
                updateProfile()
                view.saveProfileButton.isEnabled = false
            }

            view.changePhotoButton.setOnClickListener {
                setNewPhoto()
                view.changePhotoButton.isEnabled = false
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constant.PICK_IMAGE_REQUEST_CODE && data != null && data.data != null) {
            newProfileImageFilePath = data.data!!
            fview.profileImage_detail.setImageURI(newProfileImageFilePath)
            fview.changePhotoButton.isEnabled = true
        } else {
            Toast.makeText(context, "Fail to get profile image from image gallery", Toast.LENGTH_LONG).show()
            fview.changePhotoButton.isEnabled = true
        }
    }

    private fun setNewPhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, Constant.PICK_IMAGE_REQUEST_CODE)
    }

    private fun updateProfile() {
        if (!validateUpdateProfileForm()) {
            return
        }

        val user = auth.currentUser
        if (user != null) {
            if (::newProfileImageFilePath.isInitialized) {
                gsNewProfileImageRef = storageRef.child("profileImages/${user.uid}")
                gsNewProfileImageRef.putFile(newProfileImageFilePath)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Profile Image uploaded", Toast.LENGTH_SHORT).show()
                        gsNewProfileImageRef.downloadUrl.addOnSuccessListener {
                            newProfileImageUri = it
                            updateProfile(true)
                        }
                    }
                    .addOnFailureListener {
                        fview.saveProfileButton.isEnabled = true
                        Toast.makeText(context, "Fail to upload Profile Image: ${it.message}", Toast.LENGTH_LONG).show()
                    }
            } else {
                updateProfile(false)
            }
        }

    }

    private fun updateProfile(withPhoto: Boolean) {
        val user = auth.currentUser
        if (user != null) {
            lateinit var profileUpdates: UserProfileChangeRequest
            if (withPhoto) {
                profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(newDisplayName)
                    .setPhotoUri(newProfileImageUri)
                    .build()
            } else {
                profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(newDisplayName)
                    .build()
            }

            user.updateProfile(profileUpdates).addOnSuccessListener {
                Toast.makeText(context, "Profile saved", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }.addOnFailureListener {
                fview.saveProfileButton.isEnabled = true
                Toast.makeText(context, "Profile Failed to update: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateUpdateProfileForm(): Boolean {
        var valid = true


        val newDisplayNameLayout = fview.profileFullNameLayout
        newDisplayName = fview.profileFullNameText.text.toString()

        if (TextUtils.isEmpty(newDisplayName)) {
            newDisplayNameLayout.error = "Full Name required"
            valid = false
        } else {
            newDisplayNameLayout.error = null
        }


        return valid
    }


}
