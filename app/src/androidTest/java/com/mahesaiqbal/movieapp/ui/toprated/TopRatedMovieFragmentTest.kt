package com.mahesaiqbal.movieapp.ui.toprated

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.mahesaiqbal.movieapp.R
import com.mahesaiqbal.movieapp.ui.main.MainActivity
import com.mahesaiqbal.movieapp.utils.EspressoIdlingResource
import com.mahesaiqbal.movieapp.utils.RecyclerViewItemCountAssertion
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TopRatedMovieFragmentTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.rv_top_rated_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_top_rated_movie)).check(RecyclerViewItemCountAssertion(20))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }
}