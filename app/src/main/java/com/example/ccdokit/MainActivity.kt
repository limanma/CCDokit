package com.example.ccdokit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ccdokit.databinding.ActivityMainBinding
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.startFloat?.setOnClickListener {
            EasyFloat.startFloat(WeakReference(this)) {
                Log.i(TAG, "点击了悬浮窗")
            }
        }
        binding?.startHide?.setOnClickListener {
            EasyFloat.stopFloat(WeakReference(this))
        }
        binding?.btnJump?.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
}
