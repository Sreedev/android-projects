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
import kotlinx.android.synthetic.main.fragment_face_detection.*


class TextRecogFragment : Fragment() {

    private val requestImageCapture = 1
    private lateinit var cameraImage: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text_recog, container, false)
    }

    override fun onResume() {
        super.onResume()
        takePicture.setOnClickListener {
            view?.let { takePicture() }
        }

        detectFace.setOnClickListener {
            view?.let { runTextRecognition()}
        }
    }
    
    private fun takePicture() {
        // Using Camera app take a picture
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                startActivityForResult(takePictureIntent, requestImageCapture)
                happiness.text = ""
                graphicOverlay.clear()
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
            detectFace.isEnabled = true
        }
    }

    private fun runTextRecognition() {
        val image = FirebaseVisionImage.fromBitmap(cameraImage)
        val detector = FirebaseVision.getInstance()
            .onDeviceTextRecognizer
        detector.processImage(image)
            .addOnSuccessListener { texts ->
                Toast.makeText(activity,texts.text,Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e -> e.printStackTrace() }
    }


}
