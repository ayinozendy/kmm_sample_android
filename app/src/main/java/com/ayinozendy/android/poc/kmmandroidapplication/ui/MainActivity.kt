package com.ayinozendy.android.poc.kmmandroidapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ayinozendy.android.poc.kmmandroidapplication.R
import com.ayinozendy.android.poc.kmmandroidapplication.databinding.ActivityMainBinding
import com.ayinozendy.android.poc.kmmandroidapplication.ui.playlist.PlaylistFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val fragment = PlaylistFragment.createInstance()
            val tx = supportFragmentManager.beginTransaction()
            tx.add(R.id.fragmentContainerView, fragment, PlaylistFragment.TX_TAG)
            tx.commit()
        }
    }
}
