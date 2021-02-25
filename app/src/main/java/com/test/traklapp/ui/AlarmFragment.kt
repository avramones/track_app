package com.test.traklapp.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.test.traklapp.R
import com.test.traklapp.utils.AlarmReceiver
import kotlinx.android.synthetic.main.fragment_alarm.*
import java.util.*

class AlarmFragment : Fragment() {

    var alarmTimePicker: TimePicker? = null
    var pendingIntent: PendingIntent? = null
    var alarmManager: AlarmManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alarm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alarmTimePicker = view.findViewById(R.id.alarmTimePicker)
        alarmManager = activity?.getSystemService(ALARM_SERVICE) as AlarmManager
        toggleButton.setOnClickListener { v -> v?.let { startAlarm(it) } }

    }


    private fun startAlarm(view: View) {
        if ((view as ToggleButton).isChecked) {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker!!.currentHour)
            calendar.set(Calendar.MINUTE, alarmTimePicker!!.currentMinute)
            val intent = Intent(activity, AlarmReceiver::class.java)
            pendingIntent = PendingIntent.getBroadcast(activity, 0, intent, 0)

            var time = calendar.timeInMillis - calendar.timeInMillis % 60000

            if (System.currentTimeMillis() > time) {
                if (Calendar.AM_PM === 0)
                    time += 1000 * 60 * 60 * 12
                else
                    time += time + 1000 * 60 * 60 * 24
            }
            /* For Repeating Alarm set time intervals as 10000 like below lines */
            // alarmManager!!.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent)

            alarmManager!!.set(AlarmManager.RTC, time, pendingIntent);
            Toast.makeText(activity, "ALARM ON", Toast.LENGTH_SHORT).show()
        } else {
            alarmManager!!.cancel(pendingIntent)
            Toast.makeText(activity, "ALARM OFF", Toast.LENGTH_SHORT).show()
        }
    }


}