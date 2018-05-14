package quizdroid.kjerauld.washington.edu.quizdroid

import android.app.IntentService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyAlarm : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        var logMessage = QuizApp.prefs?.check_url
        val app = QuizApp.Companion

        val contextHolder: Context? = app.getContext()

        if(contextHolder != null) {
            //Toast.makeText(context, logMessage, Toast.LENGTH_LONG).show()
            val myContext: Context = contextHolder
            app.updateFile(logMessage.toString(), myContext)
            Log.d("AlarmLogTest", logMessage)
        }

    }

}