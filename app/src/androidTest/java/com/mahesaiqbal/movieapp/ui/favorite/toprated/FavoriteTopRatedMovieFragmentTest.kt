package com.mahesaiqbal.movieapp.ui.favorite.toprated

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
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
class FavoriteTopRatedMovieFragmentTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadFavMovies() {
        // Check the amount of data stored on the list of favorite movies
        // If the list is empty, you can comment the code below
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.rv_fav_top_rated_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_fav_top_rated_movie)).check(RecyclerViewItemCountAssertion(1))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }
}