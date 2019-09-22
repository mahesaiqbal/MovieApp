package com.mahesaiqbal.movieapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mahesaiqbal.movieapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter

        tabs.setupWithViewPager(view_pager)
    }
}
