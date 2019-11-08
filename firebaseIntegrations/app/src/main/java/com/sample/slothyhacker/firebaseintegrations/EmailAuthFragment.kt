package com.sample.slothyhacker.firebaseintegrations

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_email_auth.*


class EmailAuthFragment : Fragment() {

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialise()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_email_auth, container, false)
    }

    private fun initialise() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onResume() {
        super.onResume()
        button_auth!!.setOnClickListener {
            checkIfRegisteredUser()
        }
    }

    private fun checkIfRegisteredUser() {
        mAuth?.fetchSignInMethodsForEmail(et_email.text.toString())
            ?.addOnCompleteListener { task ->
                if (task.result!!.signInMethods!!.size == 0) {
                    createNewAccount()
                } else {
                    checkIfEmailVerified()
                }
            }?.addOnFailureListener { e -> e.printStackTrace() }
    }

    private fun checkIfEmailVerified() {
        mAuth?.currentUser!!.reload().addOnSuccessListener {
           if(mAuth!!.currentUser!!.isEmailVerified){
               loginUser()
           }else{
               showSnackbar("Please very your email")
           }
        }
    }

    private fun createNewAccount() {
        if (!TextUtils.isEmpty(et_email.text) && !TextUtils.isEmpty(et_password.text)) {

            mAuth!!
                .createUserWithEmailAndPassword(
                    et_email.text.toString(),
                    et_password.text.toString()
                )
                .addOnCompleteListener(activity!!) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val userId = mAuth!!.currentUser!!.uid
                        //Verify Email
                        verifyEmail()
                        //update user profile information
                        val currentUserDb = mDatabaseReference!!.child(userId)
                        currentUserDb.child("firstName").setValue("TestUserName")
                        currentUserDb.child("lastName").setValue("TestLastName")
                        showSnackbar("Authentication Success.")
                    } else {
                        // If sign in fails, display a message to the user.
                        showSnackbar("Authentication failed.")
                    }
                }
        } else {
            showSnackbar("Enter all details")
        }
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    showSnackbar("Verification email sent to " + mUser.email)
                } else {
                    showSnackbar("Failed to send verification email.")
                }
            }
    }

    private fun loginUser() {
        var email = et_email?.text.toString()
        var password = et_password.text.toString()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity!!) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with signed-in user's information
                        showSnackbar("Logged-in Successfully.")
                    } else {
                        // If sign in fails, display a message to the user.
                        showSnackbar("Authentication failed.")
                    }
                }
        } else {
            showSnackbar("Enter all details")
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(main_container, message, Snackbar.LENGTH_LONG).show()
    }
}

