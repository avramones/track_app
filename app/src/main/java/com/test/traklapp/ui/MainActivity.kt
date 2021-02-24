package com.test.traklapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.test.traklapp.R

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setParams()
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host)
    }


    @SuppressLint("ResourceAsColor")
    fun setParams() {
        supportActionBar?.hide()
    }
}