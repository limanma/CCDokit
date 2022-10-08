package com.example.ccdokit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ccdokit.databinding.ActivitySecondBinding
import com.example.ccdokit.databinding.ActivityThirdBinding

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnJump.setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }
    }
}