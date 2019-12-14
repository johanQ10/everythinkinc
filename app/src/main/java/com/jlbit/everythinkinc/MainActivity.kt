package com.jlbit.everythinkinc

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.jlbit.everythinkinc.client.Request
import com.jlbit.everythinkinc.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var actionBar: ActionBar
    private lateinit var request: Request

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        actionBar = supportActionBar!!

        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setDisplayUseLogoEnabled(true)
        actionBar.setDisplayShowTitleEnabled(true)

        request = Request(applicationContext)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.frame_layout, HomeFragment())
            .commit()
    }

}
