package com.ayinozendy.android.poc.kmmandroidapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ayinozendy.android.poc.kmmandroidapplication.ui.MainActivity

class BootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = MainActivity.createIntent(this)
        startActivity(intent)
        finish()
    }
}
