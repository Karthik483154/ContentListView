package com.telstra.contentlistview

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.telstra.contentlistview.view.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ErrorTextStateTest {

    private lateinit var errorText: String

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun invalidateString() {
        errorText = activityRule.activity.getString(R.string.app_no_internet_message)
    }

    @Test
    fun test_isErrorVisible_OnInternetIssue() {
        onView(withId(R.id.contentErrorMessage)).check(matches(withText(errorText)))
    }
}