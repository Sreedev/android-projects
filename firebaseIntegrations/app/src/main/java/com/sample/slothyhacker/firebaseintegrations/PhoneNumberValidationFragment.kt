package com.sample.slothyhacker.firebaseintegrations


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.sample.slothyhacker.firebaseintegrations.R.*
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
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout.fragment_phone_number_validation, container, false)
    }

    /**
     * Update your phone number
     */
    override fun onResume() {
        super.onResume()
        signin.setOnClickListener {
            startPhoneNumberVerification("+91 PhoneNumber")
        }
    }

    private fun initiateCallBack() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                verificationInProgress = false
                signIn(credential)
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
                storedVerificationId = verificationId
                resendToken = token
                showAlert(verificationId)
            }
        }
    }

    private fun showAlert(verificationId: String) {
        var alert = AlertDialog.Builder(activity, style.ThemeOverlay_MaterialComponents_Dialog_Alert)
        val edittext = EditText(activity)
        alert.setMessage("OTP")
        alert.setTitle("Enter the otp sent to you")
        alert.setView(edittext)
        alert.setPositiveButton("Confirm") { dialog, whichButton ->
            val credential = PhoneAuthProvider.getCredential(verificationId, "123456")
            signIn(credential)
        }
        alert.show()
    }

    private fun signIn(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(activity!!) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                showSnackbar("Sign In: Success")
            } else {
                // Sign in failed, display a message and update the UI
                showSnackbar("Sign In: Failure")
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    // The verification code entered was invalid
                    showSnackbar("Invalid code was entered")
                }
                // Sign in failed
            }
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        PhoneAuthProvider.getInstance()
            .verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, activity!!, callbacks)
        verificationInProgress = true
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(main_container, message, Snackbar.LENGTH_LONG).show()
    }
}
