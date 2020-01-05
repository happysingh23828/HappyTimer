package com.androchef.happytimersample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        onCLicks()
    }

    private fun onCLicks() {
        btnCircular.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        btnNormalCountDown.setOnClickListener {
            startActivity(Intent(this, TextViewCountDownActivity::class.java))
        }
        btnDynamic.setOnClickListener {
            startActivity(Intent(this, DynamicCountDownActivity::class.java))
        }
    }
}
