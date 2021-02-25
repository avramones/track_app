package com.test.traklapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.traklapp.R
import com.test.traklapp.model.Track
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView

    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setParams()
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host)
        bottomNavigationView = findViewById(R.id.bottom_navigation)


        try {
            if (intent.extras?.containsKey("track")!!) {
                Log.d("TRack", "track")
                var bundle = Bundle()
                var track: Track = intent.getSerializableExtra("track") as Track
                bundle.putSerializable("track", track)
                navController.navigate(R.id.detailFragment, bundle)
            }
        }catch (e : Exception){

        }

            NavigationUI.setupWithNavController(
                bottomNavigationView,
                navController
            )


    }

    private fun setParams() {
        supportActionBar?.hide()
    }


}

