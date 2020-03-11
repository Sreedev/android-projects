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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.fragment_face_detection.takePicture
import kotlinx.android.synthetic.main.fragment_landmark_recognition.*
import kotlinx.android.synthetic.main.fragment_object_tracking.*

class LandMarkRecognitionFragment : Fragment() {

    private val requestImageCapture = 1
    private lateinit var cameraImage: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_landmark_recognition, container, false)
    }

    override fun onResume() {
        super.onResume()
        takePicture.setOnClickListener {
            view?.let { takePictureOfLandmark() }
        }

        detectLandmark.setOnClickListener {
            view?.let {
                runLandmarkDetection()
            }
        }
    }

    private fun takePictureOfLandmark() {
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
            imageView.setImageBitmap(cameraImage)
            detectLandmark.isEnabled = true
        }
    }

    private fun runLandmarkDetection() {
        val image = FirebaseVisionImage.fromBitmap(cameraImage)
        val detector = FirebaseVision.getInstance()
            .visionCloudLandmarkDetector
        detector.detectInImage(image)
            .addOnSuccessListener { firebaseVisionCloudLandmarks ->
                for (landmark in firebaseVisionCloudLandmarks) {
                    val landmarkName = landmark.landmark
                    val confidence = landmark.confidence

                    tv_landmark.text = "$landmarkName - $confidence %"
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }
}
