package com.sample.slothyhacker.firebaseintegrations

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_email_auth.main_container
import kotlinx.android.synthetic.main.fragment_phone_number_validation.*
import java.util.concurrent.TimeUnit

class PhoneNumberValidationFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var verificationInProgress = false
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        initiateCallBack()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_phone_number_validation, container, false)
    }

    /**
     * Update your phone number
     */
    override fun onResume() {
        super.onResume()
        signin.setOnClickListener {
            startPhoneNumberVerification("+91-Phone number")
        }
    }

    private fun initiateCallBack() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                verificationInProgress = false
                showSnackbar("STATE_VERIFY_SUCCESS")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                verificationInProgress = false
                if (e is FirebaseAuthInvalidCredentialsException) {
                    showSnackbar("Invalid phone number.")
                } else if (e is FirebaseTooManyRequestsException) {
                    showSnackbar("Quota exceeded.")
                }
                showSnackbar("STATE_VERIFY_FAILED")
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                showSnackbar("onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token
                showSnackbar("State code sent")
            }
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        PhoneAuthProvider.getInstance()
            .verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, activity!!, callbacks)
        verificationInProgress = true
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                showSnackbar("signInWithCredential:Success")
            } else {
                // Sign in failed, display a message and update the UI
                Log.w(TAG, "signInWithCredential:Failed", it.exception)
                if (it.exception is FirebaseAuthInvalidCredentialsException) {
                    showSnackbar("Invalid code.")
                }
                showSnackbar("Sign in failed")
            }
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(main_container, message, Snackbar.LENGTH_LONG).show()
    }
}
