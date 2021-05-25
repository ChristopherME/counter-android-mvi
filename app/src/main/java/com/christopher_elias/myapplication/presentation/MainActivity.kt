package com.christopher_elias.myapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.christopher_elias.myapplication.R
import com.christopher_elias.myapplication.presentation.features.home.HomeFragment
import com.christopher_elias.myapplication.utils.TransactionAnimations
import com.christopher_elias.myapplication.utils.TransactionType
import com.christopher_elias.myapplication.utils.fragmentStackManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .fragmentStackManager(
                transactionType = TransactionType.REPLACE_FRAGMENT,
                newFragment = HomeFragment(),
                addToBackStack = false,
                transactionAnimations = TransactionAnimations.NONE
            )
    }
}