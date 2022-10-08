package com.example.ccdokit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ccdokit.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}