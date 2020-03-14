package com.sample.slothyhacker.firebaseintegrations

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import kotlinx.android.synthetic.main.fragment_translation.*


class TranslationFragment : Fragment() {
    private var isGermanModelDownloaded = false
    private var isHindiModelDownloaded = false
    private lateinit var englishHindiTranslator: FirebaseTranslator
    private lateinit var englishGermanTranslator: FirebaseTranslator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_translation, container, false)
    }

    override fun onResume() {
        super.onResume()
        rb_german.isChecked = true
        progressbar.visibility = View.VISIBLE

        if (!isHindiModelDownloaded) {
            createEnglishHindiTranslator()
        }

        if (!isGermanModelDownloaded) {
            createEnglishGermanTranslator()
        }

        addClicks()

    }

    private fun addClicks() {
        button_translate.setOnClickListener {
            view?.let {
                if (rb_hindi.isChecked) {
                    translateToReqLang(et_text_to_translate.text, englishHindiTranslator)
                } else {
                    translateToReqLang(et_text_to_translate.text, englishGermanTranslator)
                }
            }
        }

        rb_hindi.setOnClickListener {
            view?.let {
                translateToReqLang(et_text_to_translate.text, englishHindiTranslator)
            }
        }

        rb_german.setOnClickListener {
            view?.let {
                translateToReqLang(et_text_to_translate.text, englishGermanTranslator)
            }
        }
    }

    private fun createEnglishGermanTranslator() {
        englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(
            FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(FirebaseTranslateLanguage.EN)
                .setTargetLanguage(FirebaseTranslateLanguage.DE)
                .build()
        )

        englishGermanTranslator.downloadModelIfNeeded()
            .addOnSuccessListener {
                Toast.makeText(activity, "Model downloaded successfully", Toast.LENGTH_SHORT)
                    .show()
                isGermanModelDownloaded = true
                progressbar.visibility = View.GONE
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Model download failed!!", Toast.LENGTH_SHORT).show()
                isGermanModelDownloaded = false
                progressbar.visibility = View.GONE
            }
    }

    private fun createEnglishHindiTranslator() {
        englishHindiTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(
            FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(FirebaseTranslateLanguage.EN)
                .setTargetLanguage(FirebaseTranslateLanguage.HI)
                .build()
        )
        englishHindiTranslator.downloadModelIfNeeded()
            .addOnSuccessListener {
                Toast.makeText(activity, "Model downloaded successfully", Toast.LENGTH_SHORT)
                    .show()
                isHindiModelDownloaded = true
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Model download failed!!", Toast.LENGTH_SHORT).show()
                isHindiModelDownloaded = false
            }

    }

    // Run the created English-German on the entered text
    private fun translateToReqLang(textToTranslate: Editable, translator: FirebaseTranslator) {
        progressbar.visibility = View.VISIBLE
        translator.translate(textToTranslate.toString())
            .addOnSuccessListener { translatedText ->
                tv_translated_text.text = translatedText
                progressbar.visibility = View.GONE
            }
            .addOnFailureListener { exception ->
                Toast.makeText(activity, "Failed To translate", Toast.LENGTH_LONG)
                    .show()
                progressbar.visibility = View.GONE
            }

    }
}