package com.sample.slothyhacker.firebaseintegrations

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_translation.*


class TranslationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_translation, container, false)
    }

    override fun onResume() {
        super.onResume()

        button_translate.setOnClickListener {
            view?.let { translateToReqLang(et_text_to_translate.text)}
        }
    }

    private fun translateToReqLang(textToTranslate: Editable) {

    }

}