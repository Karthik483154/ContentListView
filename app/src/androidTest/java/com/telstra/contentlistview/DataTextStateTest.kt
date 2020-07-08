package com.telstra.contentlistview

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.telstra.contentlistview.view.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class DataTextStateTest {

    @get:Rule
    var activityTestRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    /**
     * RecyclerView comes into view
     */
    @Test
    fun test_isListVisible_onAppLaunch() {
        onView(ViewMatchers.withId(R.id.contentRecyclerView)).check(matches(isDisplayed()))
    }
}