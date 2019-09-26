package com.mahesaiqbal.movieapp.utils

import androidx.test.espresso.idling.CountingIdlingResource

class EspressoIdlingResource {

    companion object {
        val RESOURCE = "GLOBAL"
        val espressoTestIdlingResource = CountingIdlingResource(RESOURCE)

        fun increment() {
            espressoTestIdlingResource.increment()
        }

        fun decrement() {
            espressoTestIdlingResource.decrement()
        }

        fun getEspressoIdlingResource() = espressoTestIdlingResource
    }
}
