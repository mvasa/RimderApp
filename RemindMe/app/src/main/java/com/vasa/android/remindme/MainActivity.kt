package com.vasa.android.remindme

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.widget.RadioGroup
import com.vasa.android.remindme.R.id.Greeting
import com.vasa.android.remindme.R.id.openEventsButton
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    var monday:Boolean = false
    var tuesday:Boolean = false
    var wednesday:Boolean = false
    var thursday:Boolean = false
    var friday:Boolean = false
    var saturday:Boolean = false
    var sunday:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectDay()
        openEventsIntent()
      // populateGreetings()
    }

    private fun selectDay() {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            mondayRadioButton.y = 500F
            tuesdayRadioButton.y = 600F
            wednesdayRadioButton.y = 700F
            thursdayadioButton.y = 800F
            fridayRadioButton.y = 900F
            saturdayRadioButton.y = 1000F
            sundayRadioButton.y = 1100F
            if (R.id.mondayRadioButton == checkedId) {
                monday = true
                tuesday = false
                wednesday = false
                thursday = false
                friday = false
                saturday = false
                sunday = false
            }

             if (R.id.tuesdayRadioButton == checkedId) {
                monday = false
                tuesday = true
                wednesday = false
                thursday = false
                friday = false
                saturday = false
                sunday = false
            }

            if (R.id.wednesdayRadioButton == checkedId) {
                monday = false
                tuesday = false
                wednesday = true
                thursday = false
                friday = false
                saturday = false
                sunday = false
            }

            if (R.id.thursdayadioButton == checkedId) {
                monday = false
                tuesday = false
                wednesday = false
                thursday = true
                friday = false
                saturday = false
                sunday = false
            }

            if (R.id.fridayRadioButton == checkedId) {
                monday = false
                tuesday = false
                wednesday = false
                thursday = false
                friday = true
                saturday = false
                sunday = false
            }

            if (R.id.saturdayRadioButton == checkedId) {
                monday = false
                tuesday = false
                wednesday = false
                thursday = false
                friday = false
                saturday = true
                sunday = false
            }

            if (R.id.sundayRadioButton == checkedId) {
                monday = false
                tuesday = false
                wednesday = false
                thursday = false
                friday = false
                saturday = false
                sunday = true
            }
        }
    }

    private fun checkDaySelected():String {

        if (monday) {
           return "monday"
        }
        if (tuesday) {
           return "tuesday"
        }
        if (wednesday) {
            return "wednesday"
        }
        if (thursday) {
            return "thursday"
        }
        if (friday) {
            return "friday"
        }
        if (saturday) {
            return "saturday"
        }
        if (sunday) {
            return "sunday"
        }
        return "monday"
    }

    private fun writeToFileDaySelected(day:String)
    {
        var days = ArrayList<String>(7)
        days.add("monday")
        days.add("tuesday")
        days.add("wednesday")
        days.add("thursday")
        days.add("friday")
        days.add("saturday")
        days.add("sunday")

        for(currDay in days){
            if(day == currDay){
                val f = File(filesDir,day+".txt")
                val pr = f.printWriter()
                pr.print("1")
                pr.close()
            }
            else{
                val f2 = File(filesDir,currDay+".txt")
                val pr2 = f2.printWriter()

                pr2.appendln("0")
                pr2.close()
            }
        }

    }
    private fun openEventsIntent(){

        openEventsButton.setOnClickListener{

            var day = checkDaySelected()
            writeToFileDaySelected(day)
            val intent = Intent(this,EventsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        reWriteDayFiles()
    }
    private fun reWriteDayFiles(){
        var days = ArrayList<String>(7)
        days.add("monday")
        days.add("tuesday")
        days.add("wednesday")
        days.add("thursday")
        days.add("friday")
        days.add("saturday")
        days.add("sunday")

        for(currDay in days){
                val f = File(filesDir,currDay+".txt")
                val pr = f.printWriter()
                pr.print("0")
                pr.close()
        }
    }
    private fun populateGreetings(){
        val date:Date = Date();

        Greeting.text = "Hello user, the date today is: " + Calendar.DAY_OF_MONTH.toString() +"/"+ Calendar.MONTH.toString()+"/"+ Calendar.YEAR.toString()
    }
}
