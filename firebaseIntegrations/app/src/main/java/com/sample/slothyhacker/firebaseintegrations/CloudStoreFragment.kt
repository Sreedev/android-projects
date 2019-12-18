package com.sample.slothyhacker.firebaseintegrations

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.fragment_cloud_store.*
import java.io.IOException
import java.util.*


class CloudStoreFragment : Fragment() {
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private lateinit var downloadurl: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cloud_store, container, false)
    }

    override fun onResume() {
        super.onResume()
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        tv_no_image.visibility = View.INVISIBLE

        btn_choose_image.setOnClickListener {
            view?.let { choosePicture() }
        }

        btn_upload_image.setOnClickListener {
            view?.let {
                uploadPhoto()
            }
        }

        btn_download_image.setOnClickListener {
            view?.let {
                downloadImage()
            }
        }
    }

    private fun choosePicture() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, filePath)
                image_preview.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    fun uploadPhoto() {
        if (filePath != null) {
            val ref = storageReference?.child("SampleFiles/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

            uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot,
                    Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl
            })?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    Toast.makeText(activity, "Successfully uploaded", Toast.LENGTH_SHORT).show()
                    image_preview.setImageResource(0)
                    image_preview.visibility = View.INVISIBLE
                    tv_no_image.visibility = View.VISIBLE
                    downloadurl = downloadUri.toString()
                } else {
                    Toast.makeText(activity, "Failed to upload", Toast.LENGTH_SHORT).show()
                }
            }?.addOnFailureListener {
                Toast.makeText(activity, "Exception while uploading", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, "Please upload an Image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun downloadImage() {
        Glide.with(activity).load(downloadurl).into(image_preview)
        image_preview.visibility = View.VISIBLE
        tv_no_image.visibility = View.INVISIBLE
    }
}
