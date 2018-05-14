package quizdroid.kjerauld.washington.edu.quizdroid

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast



class Preferences : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        val app = QuizApp.Companion

        var myContext: Context = this
        app.setContext(myContext)

        val submit: Button = findViewById(R.id.button7)
        val back: Button = findViewById(R.id.button8)
        val url: EditText =  findViewById(R.id.editText2)
        val checker: EditText = findViewById(R.id.editText)
        val box01: TextView = findViewById(R.id.textView9)
        val box02: TextView = findViewById(R.id.textView10)
        val box03: TextView = findViewById(R.id.textView8)

        box01.setText("Quiz JSON File URL")
        box02.setText("Update Time to Check For Data")
        box03.setText("in Minutes")

        val current_url: String = prefs.check_url
        if (current_url == "") {
            url.setText("http://tednewardsandbox.site44.com/questions.json")
        } else {
            url.setText(current_url)
        }

        submit.setOnClickListener() {
            if(url.getText().toString() != "" && checker.getText().toString() != "") {
                val new_url = url.getText().toString()
                val new_timer: Int = checker.getText().toString().toDouble().toInt()
                prefs.check_url = new_url
                prefs.check_number = new_timer


                if (app.isNetworkAvailable(applicationContext) == false) {
                    if(app.isAirplaneModeOn(applicationContext)) {
                        app.alertDialogueBuilder(this)
                    } else {
                        Toast.makeText(this, "No Internet Connection Available", Toast.LENGTH_LONG).show()
                    }

                    prefs.check_status = false

                } else {
                    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

                    val intent = Intent(this, MyAlarm::class.java)

                    val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

                    prefs.check_status = true

                    var myContext: Context = this
                    app.setContext(myContext)

                    app.updateFile(new_url, this)
                    alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (new_timer * 60 * 1000).toLong(), (new_timer * 60 * 1000).toLong(), pendingIntent)
                }
            }

        }

        back.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
}
