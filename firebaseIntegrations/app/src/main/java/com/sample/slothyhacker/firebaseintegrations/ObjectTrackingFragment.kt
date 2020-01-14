package com.sample.slothyhacker.firebaseintegrations

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.objects.FirebaseVisionObject
import com.google.firebase.ml.vision.objects.FirebaseVisionObjectDetectorOptions
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
            view?.let {
                runOnDeviceObjectDetection()
            }
        }

        find_objects_name.setOnClickListener {
            view?.let {
                runOnCloudObjectDetection()
            }
        }
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
        }
    }

    private fun runOnDeviceObjectDetection() {
        val image = FirebaseVisionImage.fromBitmap(cameraImage)

        val options = FirebaseVisionObjectDetectorOptions.Builder()
            .setDetectorMode(FirebaseVisionObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableMultipleObjects()
            .enableClassification()
            .build()
        val detector = FirebaseVision.getInstance().getOnDeviceObjectDetector(options)

        detector.processImage(image)
            .addOnSuccessListener { objects ->
                // Task completed successfully
                val LOG_MOD = "MLKit-ODT"
                for ((idx, obj) in objects.withIndex()) {
                    val box = obj.boundingBox
                    Log.d(LOG_MOD, "Detected object: ${idx} ")
                    when (obj.classificationCategory) {
                        //Firebase only supports this much categories
                        0 -> Log.d(LOG_MOD, "  Classification name: CATEGORY_UNKNOWN")
                        1 -> Log.d(LOG_MOD, "  Classification name: CATEGORY_HOME_GOOD")
                        2 -> Log.d(LOG_MOD, "  Classification name: CATEGORY_FASHION_GOOD")
                        3 -> Log.d(LOG_MOD, "  Classification name: CATEGORY_FOOD")
                        4 -> Log.d(LOG_MOD, "  Classification name: CATEGORY_PLACE")
                        5 -> Log.d(LOG_MOD, "  Classification name: CATEGORY_PLANT")
                    }
                    Log.d(LOG_MOD, "  Classification code: ${obj.classificationCategory}")
                    if (obj.classificationCategory != FirebaseVisionObject.CATEGORY_UNKNOWN) {
                        val confidence: Int = obj.classificationConfidence!!.times(100).toInt()
                        Log.d(LOG_MOD, "  Confidence: ${confidence}%")
                    }
                    Log.d(
                        LOG_MOD,
                        "  boundingBox: (${box.left}, ${box.top}) - (${box.right},${box.bottom})"
                    )
                }
            }
            .addOnFailureListener {
                // Task failed with an exception
                Toast.makeText(
                    activity, "Oops, something went wrong!",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun runOnCloudObjectDetection() {
        val LOG_COD = "MLKit-OCD"
        val image = FirebaseVisionImage.fromBitmap(cameraImage)
        val detector = FirebaseVision.getInstance().cloudImageLabeler
        detector.processImage(image)
            .addOnSuccessListener { objects ->
                for ((idx, obj) in objects.withIndex()) {
                    Log.d(LOG_COD, "Detected object: ${idx} ")
                    Log.d(LOG_COD, "Name: ${obj.text}")
                    Log.d(LOG_COD, "Confidence: ${obj.confidence}")
                    Log.d(LOG_COD, "EntityId: ${obj.entityId}")
                }
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                Toast.makeText(
                    activity, "Oops, something went wrong!",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
