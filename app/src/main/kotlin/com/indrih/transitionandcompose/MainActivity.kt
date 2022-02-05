package com.indrih.transitionandcompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.indrih.transitionandcompose.feed.FeedFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commit { replace(R.id.activityFragmentContainerView, FeedFragment()) }
        }
    }
}