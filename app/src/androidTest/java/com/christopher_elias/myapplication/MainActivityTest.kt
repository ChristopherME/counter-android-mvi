package com.christopher_elias.myapplication

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import com.christopher_elias.myapplication.presentation.MainActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/*
 * Created by Christopher Elias on 27/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun verifyFragmentInstancesAfterConfigChanges() {
        // This test case actually let me verify the logs and see if the CounterViewModel is created twice. Is not.
        scenario.onActivity { mainActivity ->

            // After being resumed we already added the HomeFragment,
            // therefore, only 1 fragment is added to the activity backStack.
            assertEquals(
                mainActivity.supportFragmentManager.fragments.size,
                1
            )

            // Recreate a config change
            mainActivity.recreate()

            // Validate again the number of fragments added to the backStack.
            assertEquals(
                mainActivity.supportFragmentManager.fragments.size,
                1
            )

        }
    }
}