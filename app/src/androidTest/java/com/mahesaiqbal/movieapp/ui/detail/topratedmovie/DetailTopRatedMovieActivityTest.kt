package com.mahesaiqbal.movieapp.ui.detail.topratedmovie

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.mahesaiqbal.movieapp.R
import com.mahesaiqbal.movieapp.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailTopRatedMovieActivityTest {

    @Rule
    @JvmField
    var movieActivityRule: ActivityTestRule<DetailTopRatedMovieActivity> =
        object : ActivityTestRule<DetailTopRatedMovieActivity>(DetailTopRatedMovieActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                val result = Intent(targetContext, DetailTopRatedMovieActivity::class.java)
                result.putExtra("movie_id", 496243)
                return result
            }
        }

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(withText("Parasite")))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }
}