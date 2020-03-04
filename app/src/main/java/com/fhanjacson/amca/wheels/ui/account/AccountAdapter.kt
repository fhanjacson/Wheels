package com.fhanjacson.amca.wheels.ui.account

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.fhanjacson.amca.wheels.Constant
import com.fhanjacson.amca.wheels.R
import com.fhanjacson.amca.wheels.model.AccountSetting
import com.fhanjacson.amca.wheels.model.ProfileActivity
import com.fhanjacson.amca.wheels.repository.FirestoreRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.dialog_change_password.*
import kotlinx.android.synthetic.main.dialog_change_password.cancelButton
import kotlinx.android.synthetic.main.dialog_delete_account.*
import java.util.regex.Pattern

class AccountAdapter(
    private val myDataset: ArrayList<AccountSetting>,
    var callback: AccountAdapterInterface
) :
    RecyclerView.Adapter<AccountSettingViewHolder>() {

    private val repo = FirestoreRepository()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var mContext: Context
    private lateinit var inflater: LayoutInflater

    private lateinit var changePasswordDialogBuilder: AlertDialog.Builder
    private lateinit var changePasswordDialog: AlertDialog

    private lateinit var newPasswordText_changePassword: String
    private lateinit var oldPasswordText_changePassword: String

    private lateinit var deleteAccountDialogBuilder: AlertDialog.Builder
    private lateinit var deleteAccountDialog: AlertDialog

    private lateinit var confirmPasswordText_deleteAccount: String
    private lateinit var confirmDelete_deleteAccount: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountSettingViewHolder {
        return AccountSettingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_account_setting,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    override fun onBindViewHolder(holder: AccountSettingViewHolder, position: Int) {
        mContext = holder.itemView.context
        inflater = LayoutInflater.from(mContext)

        holder.accountSettingTextView.text = myDataset[position].name

        when (myDataset[position].id) {
            Constant.SETTING_LOGIN -> {
                holder.left_icon.setImageResource(R.drawable.login)
                holder.itemLayout.setOnClickListener {
                    holder.itemLayout.findNavController().navigate(R.id.action_accountFragment2_to_onboardingFragment2)
                }
            }

            Constant.SETTING_PROFILE -> {
                holder.left_icon.setImageResource(R.drawable.profile)
                holder.itemLayout.setOnClickListener {
                    holder.itemLayout.findNavController().navigate(R.id.action_accountFragment2_to_accountDetailFragment)
                }

            }

            Constant.SETTING_CHANGE_PASSWORD -> {
                holder.left_icon.setImageResource(R.drawable.reset_password)
                holder.itemLayout.setOnClickListener {
                    changePasswordDialogBuilder = AlertDialog.Builder(mContext)
                    changePasswordDialogBuilder.setTitle("Change Password")
                    changePasswordDialogBuilder.setView(
                        inflater.inflate(
                            R.layout.dialog_change_password,
                            null
                        )
                    )
                    changePasswordDialog = changePasswordDialogBuilder.create()
                    changePasswordDialog.show()


                    changePasswordDialog.cancelButton.setOnClickListener {
                        changePasswordDialog.dismiss()
                    }

                    changePasswordDialog.changePassword.setOnClickListener {
                        changePasswordDialog.changePassword.isEnabled = false
                        changePassword()
                    }


                }
            }

            Constant.SETTING_DELETE_PROFILE -> {
                holder.left_icon.setImageResource(R.drawable.delete_profile)
                holder.itemLayout.setOnClickListener {
                    deleteAccountDialogBuilder = AlertDialog.Builder(mContext)
                    deleteAccountDialogBuilder.setTitle("Delete Account?")
                    deleteAccountDialogBuilder.setView(inflater.inflate(R.layout.dialog_delete_account, null))
                    deleteAccountDialog = deleteAccountDialogBuilder.create()
                    deleteAccountDialog.show()

                    deleteAccountDialog.cancelButton.setOnClickListener {
                        deleteAccountDialog.dismiss()
                    }

                    deleteAccountDialog.deleteAccount.setOnClickListener {
                        deleteAccountDialog.deleteAccount.isEnabled = false
                        deleteAccount()
                    }
                }
            }

            Constant.SETTING_LOGOUT -> {
                holder.left_icon.setImageResource(R.drawable.logout)
                holder.itemLayout.setOnClickListener {
                    val logoutDialogBuilder = AlertDialog.Builder(mContext)
                    logoutDialogBuilder.setTitle("Are you sure you want to logout?")
                    logoutDialogBuilder.setPositiveButton(android.R.string.yes) { _, _ ->
                        auth.signOut()
                        callback.updateUIAfterUserSignOut()
                        Toast.makeText(mContext, "Logged Out", Toast.LENGTH_SHORT).show()
                    }
                    logoutDialogBuilder.setNegativeButton(android.R.string.no) { _, _ ->

                    }
                    val logoutDialog = logoutDialogBuilder.create()
                    logoutDialog.show()


                }
            }


        }
    }


    private fun changePassword() {
        if (!validateChangePasswordForm()) {
            changePasswordDialog.changePassword.isEnabled = true
            return
        }

        val user = auth.currentUser
        if (user != null) {
            val cred = EmailAuthProvider.getCredential(
                user.email.toString(),
                oldPasswordText_changePassword
            )
            user.reauthenticate(cred)
                .addOnSuccessListener {
                    user.updatePassword(newPasswordText_changePassword)
                        .addOnSuccessListener {
                            Toast.makeText(mContext, "Password updated.", Toast.LENGTH_LONG).show()
                            addToAccountHistory(ProfileActivity(user.uid, Timestamp.now(), Constant.ACTIVITY_TYPE_CHANGE_PASSWORD, "Password changed by user through Wheels mobile application (Android)"))
                            changePasswordDialog.dismiss()
                        }
                        .addOnFailureListener {
                            Toast.makeText(mContext, "Change password failed. ${it.message}", Toast.LENGTH_LONG).show()
                            changePasswordDialog.changePassword.isEnabled = true
                        }
                }
                .addOnFailureListener {
                    Toast.makeText(
                        mContext,
                        "Fail to re-authenticate. ${it.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    changePasswordDialog.changePassword.isEnabled = true
                }
        }
    }

    private fun validateChangePasswordForm(): Boolean {
        var valid = true

        val oldPasswordLayout = changePasswordDialog.oldPasswordLayout
        oldPasswordText_changePassword = changePasswordDialog.oldPasswordText.text.toString()

        val newPasswordLayout = changePasswordDialog.newPasswordLayout
        newPasswordText_changePassword = changePasswordDialog.newPasswordText.text.toString()

        val newPasswordConfirmLayout = changePasswordDialog.newPasswordConfirmLayout
        val newPasswordConfirmText = changePasswordDialog.newPasswordConfirmText.text.toString()

        if (TextUtils.isEmpty(oldPasswordText_changePassword)) {
            oldPasswordLayout.error = "Old Password Required"
            valid = false
        } else {
            oldPasswordLayout.error = null
        }

        if (TextUtils.isEmpty(newPasswordText_changePassword)) {
            newPasswordLayout.error = "New Password Required"
            valid = false
        } else if (!isPasswordValid(newPasswordText_changePassword)) {
            newPasswordLayout.error =
                "8 to 24 Character Minimum valid character: [a-z A-Z 0-9 !@#$]"
            valid = false
        } else {
            newPasswordLayout.error = null
        }

        if (TextUtils.isEmpty(newPasswordConfirmText)) {
            newPasswordConfirmLayout.error = "New Password Required"
            valid = false
        } else if (!isPasswordValid(newPasswordConfirmText)) {
            newPasswordConfirmLayout.error =
                "8 to 24 Character Minimum valid character: [a-z A-Z 0-9 !@#$]"
            valid = false
        } else {
            newPasswordConfirmLayout.error = null
        }

        if (newPasswordText_changePassword != newPasswordConfirmText) {
            newPasswordConfirmLayout.error = "Password does not match"
            valid = false
        } else {
            newPasswordConfirmLayout.error = null
        }

        return valid
    }

    private fun deleteAccount() {
        if (!validateDeleteAccountForm()) {
            deleteAccountDialog.deleteAccount.isEnabled = true
            return
        }

        val user = auth.currentUser
        if (user != null) {
            val cred =
                EmailAuthProvider.getCredential(user.email.toString(), confirmPasswordText_deleteAccount)
            user.reauthenticate(cred)
                .addOnSuccessListener {
                    user.delete().addOnSuccessListener {
                        Toast.makeText(mContext, "Account Deleted.", Toast.LENGTH_LONG).show()
                        addToAccountHistory(ProfileActivity(user.uid, Timestamp.now(), Constant.ACTIVITY_TYPE_ACCOUNT_DELETED, "Account deletion initiated by user through Wheels mobile application (Android)"))
                        deleteAccountDialog.dismiss()
                        callback.updateUIAfterUserSignOut()
                    }
                        .addOnFailureListener {
                            Toast.makeText(mContext, "Account deletion failed. ${it.message}", Toast.LENGTH_LONG).show()
                            deleteAccountDialog.deleteAccount.isEnabled = true
                        }
                }
                .addOnFailureListener {
                    Toast.makeText(mContext, "Fail to re-authenticate. ${it.message}", Toast.LENGTH_LONG).show()
                    deleteAccountDialog.deleteAccount.isEnabled = true
                }

        }
    }

    private fun validateDeleteAccountForm(): Boolean {
        var valid = true

        val passwordLayout = deleteAccountDialog.confirmPasswordLayout
        confirmPasswordText_deleteAccount = deleteAccountDialog.confirmPasswordText.text.toString()

        val confirmDeleteAccountLayout = deleteAccountDialog.confirmDeleteLayout
        val confirmDeleteAccountText = deleteAccountDialog.confirmDeleteText.text.toString()

        if (TextUtils.isEmpty(confirmPasswordText_deleteAccount)) {
            passwordLayout.error = "Password is required"
            valid = false
        } else {
            passwordLayout.error = null
        }

        if (TextUtils.isEmpty(confirmDeleteAccountText)) {
            confirmDeleteAccountLayout.error = "Delete Confirmation is required"
            valid = false
        } else if (confirmDeleteAccountText != mContext.getString(R.string.text_delete_my_account)) {
            confirmDeleteAccountLayout.error = "Please type: DELETE MY ACCOUNT"
            valid = false
        } else {
            confirmDeleteAccountLayout.error = null
        }
        return valid
    }

    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern = Pattern.compile("[a-zA-Z0-9!@#$]{8,24}")
        return passwordPattern.matcher(password).matches()
    }


    interface AccountAdapterInterface {
        fun updateUIAfterUserSignOut()
    }

    private fun addToAccountHistory(activity: ProfileActivity) {
        repo.addActivity(activity)
    }

}