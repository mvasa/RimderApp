package com.vasa.android.remindme

import android.app.Activity
import android.os.Bundle
import android.support.v4.media.session.MediaButtonReceiver.handleIntent
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.details_page.*
import java.net.URL

/**
 * Created by manoj on 4/30/2018.
 */
const val LOCATIONIN = "location"
const val DURATIONIN = "duration"
const val EVENTNAMEIN = "eventname"

class DetailsActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_page)
       handleEventsIntent()

    }

    private fun handleEventsIntent()
    {
        val intent = this.intent

        returnButton.setOnClickListener {
            intent.putExtra(EVENTNAMEIN,eventText.text.toString())
            intent.putExtra(LOCATIONIN, locationText.text.toString())
            intent.putExtra(DURATIONIN, durationText.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }


    }
}