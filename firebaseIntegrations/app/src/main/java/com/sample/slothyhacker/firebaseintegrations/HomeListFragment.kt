package com.sample.slothyhacker.firebaseintegrations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_fblist.*

class HomeListFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fblist, container, false)
    }

    override fun onResume() {
        super.onResume()
        tv_email_auth.setOnClickListener(this)
        tv_phone_auth.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_email_auth -> {
                v.findNavController().navigate(R.id.action_FBListFragment_to_emailAuthFragment)
            }
            R.id.tv_phone_auth -> {
                Toast.makeText(activity,"Phone number", Toast.LENGTH_LONG).show()
            }
        }
    }
}
