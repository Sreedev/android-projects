package com.sample.slothyhacker.firebaseintegrations

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_face_detection.*
import kotlinx.android.synthetic.main.fragment_face_detection.takePicture
import kotlinx.android.synthetic.main.fragment_object_tracking.*


class ObjectTrackingFragment : Fragment() {

    private val requestImageCapture = 1
    private lateinit var cameraImage: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_object_tracking, container, false)
    }

    override fun onResume() {
        super.onResume()
        takePicture.setOnClickListener {
            view?.let { takePictureForObjects() }
        }

        find_objects.setOnClickListener {
            view?.let { runObjectDetection()}
        }
        find_objects.isEnabled = false
    }
    private fun takePictureForObjects() {
        // Using Camera app take a picture
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                startActivityForResult(takePictureIntent, requestImageCapture)
            }
        }
    }


    /** Receive the result from the camera app */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == requestImageCapture && resultCode == Activity.RESULT_OK && data != null && data.extras != null) {
            val imageBitmap = data!!.extras!!.get("data") as Bitmap

            // Resizing the image
            val width = Resources.getSystem().displayMetrics.widthPixels
            val height = width / imageBitmap.width * imageBitmap.height
            cameraImage = Bitmap.createScaledBitmap(imageBitmap, width, height, false)

            // Set image and button enabled for face detection
            image_view.setImageBitmap(cameraImage)
            find_objects.isEnabled = true
        }
    }

    private fun runObjectDetection() {

    }
}
