package com.christopher_elias.myapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.christopher_elias.myapplication.R
import com.christopher_elias.myapplication.presentation.features.home.HomeFragment
import com.christopher_elias.myapplication.navigation_ext.TransactionAnimations
import com.christopher_elias.myapplication.navigation_ext.TransactionType
import com.christopher_elias.myapplication.navigation_ext.fragmentStackManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("Main", "Hello")

        /*
         * Avoid creating a new HomeFragment instance (and all the other objects that class creates) again on configuration change.
         */
        if (savedInstanceState == null) {
            Log.i("Main", "Add HomeFragment for the first time")
            addHomeFragment()
        }
    }

    private fun addHomeFragment() {
        supportFragmentManager.beginTransaction()
            .fragmentStackManager(
                transactionType = TransactionType.REPLACE_FRAGMENT,
                newFragment = HomeFragment(),
                addToBackStack = false,
                transactionAnimations = TransactionAnimations.NONE
            )
    }
}