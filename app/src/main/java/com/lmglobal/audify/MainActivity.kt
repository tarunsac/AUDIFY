package com.lmglobal.audify

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    companion object {
        const val AUDIFY_PREFIX = "https://www.google.com/readit?url="
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val urlEt = findViewById<EditText>(R.id.url_et)

        if (Intent.ACTION_SEND.equals(intent.action) && intent.type != null) {
            if ("text/plain".equals(intent.type)) {
                val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
                if (sharedText != null) {
                    urlEt.setText(sharedText)
                    audify(sharedText)
                }
            }
        }

        findViewById<Button>(R.id.audifyBtn).setOnClickListener {
            audify(urlEt.text.toString())
        }
    }

    private fun audify(url: String) {
        val audifyUrl = getAudifyUrl(url)
        openUrl(audifyUrl)
    }

    private fun openUrl(audifyUrl: String) {
        val i = Intent(Intent.ACTION_VIEW)
        try {
            i.data = Uri.parse(audifyUrl)
        } catch (e: Exception) {
            Toast.makeText(this, R.string.invalid_url, Toast.LENGTH_LONG).show()
        }
        startActivity(i)
    }

    private fun getAudifyUrl(url: String): String {
        return AUDIFY_PREFIX + url
    }
}
