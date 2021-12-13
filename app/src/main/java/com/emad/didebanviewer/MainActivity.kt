package com.emad.didebanviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.emad.didebanviewer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.tvPrepare.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(R.id.root, WebFragment.newInstance(), null).addToBackStack(null).commit()
        }
        binding.tvBye.setOnClickListener {
            onBackPressed()
        }
    }
}