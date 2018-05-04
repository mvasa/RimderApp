package com.vasa.android.remindme

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.media.session.MediaButtonReceiver.handleIntent
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import com.vasa.android.remindme.R.id.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.events_page.*
import kotlinx.android.synthetic.main.events_page.view.*
import org.w3c.dom.Text
import java.io.File
import java.io.FileNotFoundException
import java.util.*


/**
 * Created by manoj on 4/20/2018.
 */


const val TEXTX:Float = 15F
const val SWITCHX:Float = 15F
const val EDITX:Float = 400F
const val PRIORITYX:Float = 750F

const val TEXT1Y:Float = 20F
const val TEXT2Y:Float = 270F
const val TEXT3Y:Float = 520F
const val TEXT4Y:Float = 770F
const val TEXT5Y:Float = 1020F
const val TEXT6Y:Float = 1270F

const val POSITION1Y:Float = 60F
const val POSITION2Y:Float = 330F
const val POSITION3Y:Float = 580F
const val POSITION4Y:Float = 830F
const val POSITION5Y:Float = 1080F
const val POSITION6Y:Float = 1330F


const val TEXTDISPLAYX:Float = TEXTX
const val TEXTDISPLAYY:Float = TEXT1Y
const val EDITDISPLAYX:Float = EDITX
const val EDITDISPLAYY:Float = POSITION1Y
const val PRIORITYDISPLAYX:Float = PRIORITYX
const val PRIORITYDISPAYY:Float = POSITION1Y

const val LOCATION = "location"
const val DURATION = "duration"
const val EVENTNAME = "eventname"


class EventsActivity: AppCompatActivity(){

    private val eventsArray =  ArrayList<TextView>(6)
    private val switchesArray = ArrayList<Switch>(6)
    private val editButtonArray = ArrayList<Button>(6)
    private val priorityButtonArray = ArrayList<Button>(6)
    private var duration = " "
    private var eventName = " "
    private var location = " "
    private var editEvent1:Boolean = false
    private var editEvent2:Boolean = false
    private var editEvent3:Boolean = false
    private var editEvent4:Boolean = false
    private var editEvent5:Boolean = false
    private var editEvent6:Boolean = false
    private var event1Details:String = "Location: none\t\tDuration: none"
    private var event2Details:String = "Location: none\t\tDuration: none"
    private var event3Details:String = "Location: none\t\tDuration: none"
    private var event4Details:String = "Location: none\t\tDuration: none"
    private var event5Details:String = "Location: none\t\tDuration: none"
    private var event6Details:String = "Location: none\t\tDuration: none"
    private var event1Name:String = "None"
    private var event2Name:String = "None"
    private var event3Name:String = "None"
    private var event4Name:String = "None"
    private var event5Name:String = "None"
    private var event6Name:String = "None"
    private var selectedDay = " "


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.events_page)
        eventDetails.visibility = View.INVISIBLE
        selectedDay = checkDaySelected()
        populateWidgetsArrays()
        flipSwitch()
        changePriorityOfEvent()
        populateEvents()
        handleIntent()
        openDetailsIntent()
    }


    private fun changePriorityOfEvent(){
        priorityButton1.setOnClickListener{
            changeColorOfText(event1,priorityButton1)
        }
        priorityButton2.setOnClickListener{
            changeColorOfText(event2,priorityButton2)
        }
        priorityButton3.setOnClickListener{
            changeColorOfText(event3,priorityButton3)
        }
        priorityButton4.setOnClickListener{
            changeColorOfText(event4,priorityButton4)
        }
        priorityButton5.setOnClickListener{
            changeColorOfText(event5,priorityButton5)
        }
        priorityButton6.setOnClickListener{
            changeColorOfText(event6,priorityButton6)
        }

    }
    //Red	    0xFF0000 : High Priority
    //Blue	    0x0000FF : Low Priority
    //Fuchsia	0xFF00FF : Medium Priority
    //normal    0x000000 : Very Low
    private fun changeColorOfText(text:TextView, currButton:Button){

        if(text.currentTextColor == Color.BLACK) {
            text.setTextColor(Color.BLUE)
            currButton.text = "Low"
        }
        else if(text.currentTextColor == Color.BLUE){
            text.setTextColor(Color.MAGENTA)
            currButton.text = "Medium"
        }
        else if(text.currentTextColor == Color.MAGENTA){
            text.setTextColor(Color.RED)
            currButton.text = "High"
        }
        else if(text.currentTextColor == Color.RED){
            text.setTextColor(Color.BLACK)
            currButton.text = "Regular"
        }
        else{
            text.setTextColor(Color.BLACK)
            currButton.text = "Regular"
        }
    }

    private fun populateEvents(){
        var events = ArrayList<String>(6)
        val dayEvents = arrayOf("none%none%none","none%none%none","none%none%none","none%none%none","none%none%none","none%none%none")
        var i = 0

        events.add("event1")
        events.add("event2")
        events.add("event3")
        events.add("event4")
        events.add("event5")
        events.add("event6")

        for(event in events){
            dayEvents[i]  = readEventsFromFile(selectedDay,event)
            i++
        }

        parseEventText(dayEvents[0])
        event1Name = eventName
        event1Details = "Location: "+ location+"\t\tDuration: "+duration
        event1.text = event1Name
        //event1.setTextColor(0xFFFFFF)

        parseEventText(dayEvents[1])
        event2Name = eventName
        event2Details = "Location: "+ location+"\t\tDuration: "+duration
        event2.text = event2Name
        //event2.setTextColor(0xFFFFFF)

        parseEventText(dayEvents[2])
        event3Name = eventName
        event3Details = "Location: "+ location+"\t\tDuration: "+duration
        event3.text = event3Name
        //event3.setTextColor(0xFFFFFF)

        parseEventText(dayEvents[3])
        event4Name = eventName
        event4Details = "Location: "+ location+"\t\tDuration: "+duration
        event4.text = event4Name
        //event4.setTextColor(0xFFFFFF)

        parseEventText(dayEvents[4])
        event5Name = eventName
        event5Details = "Location: "+ location+"\t\tDuration: "+duration
        event5.text = event5Name
        //event5.setTextColor(0xFFFFFF)

        parseEventText(dayEvents[5])
        event6Name = eventName
        event6Details = "Location: "+ location+"\t\tDuration: "+duration
        event6.text = event6Name
        //event6.setTextColor(0xFFFFFF)
    }
    private fun checkDaySelected():String{
        var days = ArrayList<String>(7)
        var thisDay = "monday"
        days.add("monday")
        days.add("tuesday")
        days.add("wednesday")
        days.add("thursday")
        days.add("friday")
        days.add("saturday")
        days.add("sunday")

        for(currDay in days){
            try {
                val f2 = File(filesDir, currDay + ".txt")

                val s = Scanner(f2)
                var daySelect = "0"
                while(s.hasNext()){
                    daySelect = s.next()
                }
                if(daySelect == "1") {
                    thisDay = currDay
                    return currDay
                }
                else{
                    continue
                }
            }
            catch(e:FileNotFoundException)
            {
                return "monday"
            }
            return "monday"
        }
       return thisDay
    }

    //format for default event: eventName%location%duration
    private fun setDefaultEvents(day:String,event:String)
    {
     var defaultEvent1 = "none%none%none"

        val f1 = File(filesDir,day+event+".txt")
        val pr1 = f1.printWriter()
        pr1.println(defaultEvent1)
        pr1.close()


    }

    private fun openDetailsIntent(){
        editButton1.setOnClickListener{
            editEvent1 = true
            val intent = Intent(this,DetailsActivity::class.java )
            startActivityForResult(intent,1000)
        }
        editButton2.setOnClickListener{
            editEvent2 = true
            val intent = Intent(this,DetailsActivity::class.java )
            startActivityForResult(intent,1000)
        }
        editButton3.setOnClickListener{
            editEvent3 = true
            val intent = Intent(this,DetailsActivity::class.java )
            startActivityForResult(intent,1000)
        }
        editButton4.setOnClickListener{
            editEvent4 = true
            val intent = Intent(this,DetailsActivity::class.java )
            startActivityForResult(intent,1000)
        }
        editButton5.setOnClickListener{
            editEvent5 = true
            val intent = Intent(this,DetailsActivity::class.java )
            startActivityForResult(intent,1000)
        }
        editButton6.setOnClickListener{
            editEvent6 = true
            val intent = Intent(this,DetailsActivity::class.java )
            startActivityForResult(intent,1000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        eventName = data!!.getStringExtra(EVENTNAME)
        duration = data.getStringExtra(DURATION)
        location = data.getStringExtra(LOCATION)

        writeEventsToFile()
        populateEvents()
    }

    private fun writeEventsToFile(){
        if(editEvent1)
        {
            writeEventDetailsToFile(selectedDay+"event1",eventName,location,duration)
            event1Name = eventName
            event1Details = "Location: "+ location+"Duration: " + duration
            editEvent1 = false
        }
        if(editEvent2)
        {
            writeEventDetailsToFile(selectedDay+"event2",eventName,location,duration)
            event2Name = eventName
            event2Details = "Location: "+ location+"Duration: " + duration

            editEvent2 = false
        }
        if(editEvent3)
        {
            writeEventDetailsToFile(selectedDay+"event3",eventName,location,duration)
            event3Name = eventName
            event3Details = "Location: "+ location+"Duration: " + duration

            editEvent3 = false
        }
        if(editEvent4)
        {
            writeEventDetailsToFile(selectedDay+"event4",eventName,location,duration)
            event4Name = eventName
            event4Details = "Location: "+ location+"Duration: " + duration

            editEvent4 = false
        }
        if(editEvent5)
        {
            writeEventDetailsToFile(selectedDay+"event5",eventName,location,duration)
            event5Name = eventName
            event5Details = "Location: "+ location+"Duration: " + duration

            editEvent5 = false
        }
        if(editEvent6){
            writeEventDetailsToFile(selectedDay+"event6",eventName,location,duration)
            event6Name = eventName
            event6Details = "Location: "+ location+"Duration: " + duration
            editEvent6 = false
        }

    }

    private fun writeEventDetailsToFile(fileName:String, nameOfEvent:String, eventLocation:String,eventDuration:String){
        val f = File(filesDir,fileName+".txt")
        val pr = f.printWriter()

            pr.appendln(nameOfEvent+"%"+eventLocation+"%"+eventDuration)
            pr.close()
    }

    private fun readEventsFromFile(day:String, event:String):String{
        var getEvent = ""
        try {
            val f2 = File(filesDir, day +event+ ".txt")
            val s = Scanner(f2)
            if(s.hasNext()) {
                var currEvent = s.nextLine()
                getEvent += currEvent

            }
        }
        catch(e: FileNotFoundException)
        {
            setDefaultEvents(day,event)
            return "none%none%none"
        }
        return getEvent
    }
    private fun parseEventText(eventString:String){
        var partition1 = 0
        var partition2 = 0
        var partition3 = 0
        var partitionCount = 0
        var letterCount = 0

        for(char in eventString){
            if(char == '%'){
               partitionCount++
            }
            else{
                if(partitionCount == 0){
                    partition1++
                    partition2 = partition1
                }
                else if(partitionCount == 1){
                    partition2++
                    partition3 = partition2
                }
                else{
                    partition3++
                }
            }
        }
        eventName = eventString.substring(0,partition1)
        location = eventString.substring(partition1+1,partition2+1)
        duration = eventString.substring(partition2+2,partition3+1)
    }
    private fun setEventsWidgetsPositions()
    {
        eventDetails.x = TEXTX+100F
        eventDetails.y = POSITION1Y+300F
       //eventDetails.text = "Details"

                //event1.text = "Event 1"
                event1.x = TEXTX
                event1.y = TEXT1Y
                switch1.x = SWITCHX
                switch1.y = POSITION1Y
                priorityButton1.x = PRIORITYX
                priorityButton1.y = POSITION1Y
                editButton1.x = EDITX
                editButton1.y = POSITION1Y

                //event2.text = "Event 2"
                event2.x = TEXTX
                event2.y = TEXT2Y
                switch2.x = SWITCHX
                switch2.y = POSITION2Y
                priorityButton2.x = PRIORITYX
                priorityButton2.y = POSITION2Y
                editButton2.x = EDITX
                editButton2.y = POSITION2Y

                //event3.text = "Event 3"
                event3.x = TEXTX
                event3.y = TEXT3Y
                switch3.x = SWITCHX
                switch3.y = POSITION3Y
                priorityButton3.x = PRIORITYX
                priorityButton3.y = POSITION3Y
                editButton3.x = EDITX
                editButton3.y = POSITION3Y

                //event4.text = "Event 4"
                event4.x = TEXTX
                event4.y = TEXT4Y
                switch4.x = SWITCHX
                switch4.y = POSITION4Y
                priorityButton4.x = PRIORITYX
                priorityButton4.y = POSITION4Y
                editButton4.x = EDITX
                editButton4.y = POSITION4Y

                //event5.text = "Event 5"
                event5.x = TEXTX
                event5.y = TEXT5Y
                switch5.x = SWITCHX
                switch5.y = POSITION5Y
                priorityButton5.x = PRIORITYX
                priorityButton5.y = POSITION5Y
                editButton5.x = EDITX
                editButton5.y = POSITION5Y

                //event6.text = "Event 6"
                event6.x = TEXTX
                event6.y = TEXT6Y
                switch6.x = SWITCHX
                switch6.y = POSITION6Y
                priorityButton6.x = PRIORITYX
                priorityButton6.y = POSITION6Y
                editButton6.x = EDITX
                editButton6.y = POSITION6Y

                returnHomeButton.x = SWITCHX+100F
                returnHomeButton.y = POSITION6Y+100F
    }

    private fun populateWidgetsArrays()
    {
            eventsArray.add(event1)
            eventsArray.add(event2)
            eventsArray.add(event3)
            eventsArray.add(event4)
            eventsArray.add(event5)
            eventsArray.add(event6)

            switchesArray.add(switch1)
            switchesArray.add(switch2)
            switchesArray.add(switch3)
            switchesArray.add(switch4)
            switchesArray.add(switch5)
            switchesArray.add(switch6)

            editButtonArray.add(editButton1)
            editButtonArray.add(editButton2)
            editButtonArray.add(editButton3)
            editButtonArray.add(editButton4)
            editButtonArray.add(editButton5)
            editButtonArray.add(editButton6)

            priorityButtonArray.add(priorityButton1)
            priorityButtonArray.add(priorityButton2)
            priorityButtonArray.add(priorityButton3)
            priorityButtonArray.add(priorityButton4)
            priorityButtonArray.add(priorityButton5)
            priorityButtonArray.add(priorityButton6)
    }

    private fun flipSwitch(){
        switch1.setOnCheckedChangeListener{buttonView,isChecked ->
            if(isChecked){
                changeAppView(event1, switch1,priorityButton1,editButton1)
                eventDetails.text = event1Details
                eventDetails.visibility = View.VISIBLE
                switch1.text = "Hide Details"

            }
            else{
                eventDetails.visibility = View.INVISIBLE
                setEventsWidgetsPositions()
                showWidgets()
                switch1.text = "Show Details"
            }
        }
        switch2.setOnCheckedChangeListener{_,isChecked ->
            if(isChecked){
                changeAppView(event2, switch2,priorityButton2,editButton2)
                eventDetails.text = event2Details
                eventDetails.visibility = View.VISIBLE
                switch2.text = "Hide Details"
            }
            else{

                eventDetails.visibility = View.INVISIBLE
                setEventsWidgetsPositions()
                showWidgets()
                switch2.text = "Show Details"
            }
        }
        switch3.setOnCheckedChangeListener{_,isChecked ->
            if(isChecked){
                changeAppView(event3, switch3,priorityButton3,editButton3)
                eventDetails.text = event3Details
                eventDetails.visibility = View.VISIBLE
                switch3.text = "Hide Details"
            }
            else{

                eventDetails.visibility = View.INVISIBLE
                setEventsWidgetsPositions()
                showWidgets()
                switch3.text = "Show Details"
            }
        }
        switch4.setOnCheckedChangeListener{_,isChecked ->
            if(isChecked){
                changeAppView(event4, switch4,priorityButton4,editButton4)
                eventDetails.text = event4Details
                eventDetails.visibility = View.VISIBLE
                switch4.text = "Hide Details"
            }
            else{

                eventDetails.visibility = View.INVISIBLE
                setEventsWidgetsPositions()
                showWidgets()
                switch4.text = "Show Details"
            }
        }

        switch5.setOnCheckedChangeListener{_,isChecked ->
            if(isChecked){
                changeAppView(event5, switch5,priorityButton5, editButton5)
                eventDetails.text = event5Details
                eventDetails.visibility = View.VISIBLE
                switch5.text = "Hide Details"
            }
            else{

                eventDetails.visibility = View.INVISIBLE
                setEventsWidgetsPositions()
                showWidgets()
                switch5.text = "Show Details"
            }
        }

        switch6.setOnCheckedChangeListener{_,isChecked ->
            if(isChecked){
                changeAppView(event6, switch6,priorityButton6, editButton6)
                eventDetails.text = event6Details
                eventDetails.visibility = View.VISIBLE
                switch6.text = "Hide Details"
            }
            else{

                eventDetails.visibility = View.INVISIBLE
                setEventsWidgetsPositions()
                showWidgets()
                switch6.text = "Show Details"
            }
        }

    }

    private fun changeAppView(text:TextView, switch:Switch,priority:Button,edit:Button)
    {
        for(event in eventsArray){
            if(event != text){
                event.visibility = View.INVISIBLE
            }
            else{
                event.visibility = View.VISIBLE
                event.x = TEXTX
                event.y = TEXT1Y
            }
        }

        for(sw in switchesArray){
            if(sw != switch){
                sw.visibility = View.INVISIBLE
            }
            else{
                sw.visibility = View.VISIBLE
                sw.x = SWITCHX
                sw.y = POSITION1Y
            }
        }

        for(button in priorityButtonArray){
            if(button != priority){
                button.visibility = View.INVISIBLE
            }
            else{
                button.visibility = View.VISIBLE
                button.x = PRIORITYX
                button.y = POSITION1Y
            }
        }
        for(button in editButtonArray){
            if(button != edit){
                button.visibility = View.INVISIBLE
            }
            else{
                button.visibility = View.VISIBLE
                button.x = EDITX
                button.y = POSITION1Y
            }
        }
    }
    /*
   val eventsArray =  ArrayList<TextView>()
   val switchesArray = ArrayList<Switch>()
   val editButtonArray = ArrayList<Button>()
   val priorityButtonArray = ArrayList<Button>()
   */
    private fun showWidgets()
    {
        for(event in eventsArray){
            event.visibility = View.VISIBLE
        }
        for(switch in switchesArray){
            switch.visibility = View.VISIBLE
        }
        for(edit in editButtonArray){
            edit.visibility = View.VISIBLE
        }
        for(button in priorityButtonArray){
            button.visibility = View.VISIBLE
        }
    }
    private fun handleIntent(){

        val intent = this.intent
        val data = intent.data

        returnHomeButton.setOnClickListener{
            finish()
        }
    }
}