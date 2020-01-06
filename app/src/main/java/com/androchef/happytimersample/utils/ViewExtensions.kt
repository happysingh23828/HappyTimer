package com.androchef.happytimersample.utils

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun AppCompatActivity.toast(message: String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}