package com.sample.slothyhacker.firebaseintegrations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_fblist.*

class HomeListFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fblist, container, false)
    }

    override fun onResume() {
        super.onResume()
        tv_email_auth.setOnClickListener(this)
        tv_phone_auth.setOnClickListener(this)
        tv_cloud_store.setOnClickListener(this)
        tv_face_detection.setOnClickListener(this)
        tv_text_recognition.setOnClickListener(this)
        tv_object_tracking.setOnClickListener(this)
        tv_landmark_recognition.setOnClickListener(this)
        tv_translation.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_email_auth -> {
                v.findNavController().navigate(R.id.action_FBListFragment_to_emailAuthFragment)
            }
            R.id.tv_phone_auth -> {
                v.findNavController().navigate(R.id.action_FBListFragment_to_phoneNumberValidationFragment)
            }
            R.id.tv_cloud_store -> {
                v.findNavController().navigate(R.id.action_FBListFragment_to_cloudStoreFragment)
            }
            R.id.tv_face_detection -> {
                v.findNavController().navigate(R.id.action_FBListFragment_to_faceDetectionFragment)
            }
            R.id.tv_text_recognition -> {
                v.findNavController().navigate(R.id.action_FBListFragment_to_textRecogFragment)
            }
            R.id.tv_object_tracking -> {
                v.findNavController().navigate(R.id.action_FBListFragment_to_objectTrackingFragment)
            }
            R.id.tv_landmark_recognition -> {
                v.findNavController().navigate(R.id.action_FBListFragment_to_textlandmarkRecogFragment)
            }
            R.id.tv_translation -> {
                v.findNavController().navigate(R.id.action_FBListFragment_to_translateFragment)
            }
        }
    }
}
