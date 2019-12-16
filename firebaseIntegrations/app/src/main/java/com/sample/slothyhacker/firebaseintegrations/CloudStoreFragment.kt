package com.sample.slothyhacker.firebaseintegrations

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_cloud_store.*
import java.io.IOException


class CloudStoreFragment : Fragment() {
    private val PICK_IMAGE_REQUEST = 234
    private var filePath: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cloud_store, container, false)
    }

    override fun onResume() {
        super.onResume()
        buttonChoose.setOnClickListener {
            view?.let { choosePicture() }
        }

        buttonUpload.setOnClickListener {
            view?.let { uploadPicture() }
        }
    }

    private fun uploadPicture() {

    }

    private fun choosePicture() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, filePath)
                imageView_cloudstore.setImageBitmap(bitmap)

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }
}
