package quizdroid.kjerauld.washington.edu.quizdroid

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.annotations.SerializedName
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import okhttp3.OkHttpClient
import java.io.BufferedReader
import java.io.File
import android.provider.Settings
import android.support.v4.content.ContextCompat.startActivity


val prefs: Prefs by lazy {
    QuizApp.prefs!!
}


class QuizApp : Application(), TopicRepository {

    companion object Companion {
        fun create(): QuizApp = QuizApp()

        var topicList = ArrayList<Topic>()

        var appContext: Context? = null

        var prefs: Prefs? = null

        fun setTopics(input: ArrayList<Topic>) {
            topicList = input
        }

        fun getTopics(): ArrayList<Topic> {
            return topicList
        }

        fun getContext(): Context? {
            return appContext
        }

        fun setContext(input: Context) {
            appContext = input
        }

        fun sortArray(json: String): ArrayList<Topic> {
            val json_data = JSONArray(json)

            var my_topics: ArrayList<Topic> = ArrayList<Topic>()

            for (a in 0..(json_data.length() - 1)) {
                val title = json_data.getJSONObject(a).getString("title")
                val desc = json_data.getJSONObject(a).getString("desc")
                val questions = json_data.getJSONObject(a).getJSONArray("questions")

                val question_list_data = ArrayList<Question>()
                for (i in 0..(questions.length() - 1)) {
                    val current_question = questions.getJSONObject(i)
                    val question_title = current_question.getString("text")
                    val answer = current_question.getInt("answer") - 1
                    val answers_list = current_question.getJSONArray("answers")
                    val answer01 = answers_list[0].toString()
                    val answer02 = answers_list[1].toString()
                    val answer03 = answers_list[2].toString()
                    val answer04 = answers_list[3].toString()

                    val answer_list_data: ArrayList<String> = arrayListOf(answer01, answer02, answer03, answer04)

                    val question_object = Question(question_title, answer, answer_list_data)

                    question_list_data.add(question_object)
                }

                val topic_object = Topic(title, desc, question_list_data)
                my_topics.add(topic_object)
            }
            return my_topics
        }

        fun updateFile(given_url: String, context: Context) {
            var url = given_url
            var my_topics: ArrayList<Topic> = ArrayList<Topic>()

            var downloading: String = "Not Started"
            var writing: String = "Not Started"

            if(url != "") {
                var json = ""
                val downloadDialog: AlertDialog = alertDialogueBuilderDownloading(context, given_url)
                downloadDialog.show()
                val client = OkHttpClient()
                val request = Request.Builder()
                        .url(url)
                        .build()

                downloading = "Downloading"
                writing = "Writing"

                println(Thread.currentThread())
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        downloading = "Failed"
                        writing = "Failed"
                        e?.printStackTrace()
                    }

                    override fun onResponse(call: Call?, response: Response) {
                        if (!response.isSuccessful) {
                            System.err.println("Response not successful")
                            downloading = "Failed"
                            writing = "Failed"
                            return
                        }
                        json = response.body()!!.string()
                        Companion.setTopics(sortArray(json))
                        downloading = "Finished"

                        val fileName = "/sdcard/questions.json"
                        val myFile: File? = File(fileName)
                        myFile?.bufferedWriter().use { out ->
                            out?.write(json)
                        }

                        writing = "Finished"

                        prefs?.check_download_status = true
                    }

                })
                // Shutdown the executor as soon as the request is handled
                client.dispatcher().executorService().shutdown()
                while (downloading == "Downloading") {
                    // do nothing
                }

                if (downloading == "Failed") {
                    downloadDialog.dismiss()
                    alertDialogueBuilderUpdate(context, given_url)
                } else {
                    println("Download Success!")
                }

                while (writing == "Writing") {
                    // do nothing
                }

                if (writing == "Finished") {
                    downloadDialog.dismiss()
                    alertDialogueBuilderWritten(context)
                }

                downloadDialog.dismiss()

            }
        }

        fun readFile(): ArrayList<Topic> {
            var my_topics = ArrayList<Topic>()
            if(deviceReader() != "nullFile") {
                //val json = deviceReader()
                //my_topics = sortArray(json)
                //topicList = my_topics

                Companion.setTopics(sortArray(deviceReader()))
                //topicList = my_topics
                println("DONE WRITING TOPICS")
                return sortArray(deviceReader())
            }
            return my_topics
        }

        fun setup(given_url:String): ArrayList<Topic> {
            var url = given_url
            var my_topics = ArrayList<Topic>()

            my_topics = readFile()

            return readFile()
        }

        fun isNetworkAvailable(context: Context?): Boolean {
            if (context == null) {
                return false
            }
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            // if no network is available networkInfo will be null, otherwise check if we are connected
            try {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    return true
                }
            } catch (e: Exception) {
                Log.e("isNetworkEvailable", "Network Unavailable")
            }

            return false
        }

        fun isAirplaneModeOn(context: Context): Boolean {

            return Settings.System.getInt(context.contentResolver,
                    Settings.System.AIRPLANE_MODE_ON, 0) !== 0

        }

        fun alertDialogueBuilderWritten(context: Context) {
            val builder = AlertDialog.Builder(context)

            builder.setTitle("New Data Downloaded")
            builder.setMessage("We have finished downloading your data. Please reload the page.")


            builder.setPositiveButton("LET'S GO!"){dialog, which ->
                startActivity(context, Intent(context, MainActivity::class.java), Bundle.EMPTY)

            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }


        fun alertDialogueBuilderUpdate(context: Context, given_url: String) {
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Data Download Failed")
            builder.setMessage("Your data could not be downloaded at this time. Would you like to retry now?")

            builder.setPositiveButton("YES"){dialog, which ->
                updateFile(given_url, context)

            }

            builder.setNegativeButton("No"){dialog,which ->
                // Does nothing
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        fun alertDialogueBuilderDownloading(context: Context, given_url: String): AlertDialog {
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Download In Progress")
            builder.setMessage("Data is being downloaded from: " + given_url + ". Please sit tight. We'll" +
                    " let you know when it is done!")

            val dialog: AlertDialog = builder.create()
            return dialog
        }

        fun alertDialogueBuilder(context: Context) {
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Airplane Mode Active")
            builder.setMessage("Would you like to go to settings to change it?")

            builder.setPositiveButton("YES"){dialog, which ->
                startActivity(context, Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS), null)

            }

            builder.setNegativeButton("No"){dialog,which ->
                Toast.makeText(context,"Airplane Mode Still On",Toast.LENGTH_SHORT).show()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }



        fun deviceReader(): String {
            val fileName = "/sdcard/questions.json"
            val myFile: File? = File(fileName)
            if(myFile == null) {
                return "nullFile"
            }

            val bufferedReader: BufferedReader = myFile.bufferedReader()

            val inputString = bufferedReader.use { it.readText() }


            return inputString
        }

    }



    var topics_holder: ArrayList<Topic> = ArrayList<Topic>()

    override fun onCreate() {
        prefs = Prefs(applicationContext)
        super.onCreate()


        Log.i("Startup", "Application Class Initialized")


    }

    override fun createTopics(title: String, desc: String, longDesc: String, questions: ArrayList<Question>): Topic {
        val newTopic: Topic = Topic(title, desc, questions)
        return newTopic
    }



    fun getTopics(): ArrayList<Topic> {
        return topics_holder
    }

}



interface TopicRepository {
    fun createTopics(title: String, desc: String, longDesc: String, questions: ArrayList<Question>): Topic {
        return Topic(title, desc, ArrayList<Question>())
    }
}

data class Topics(
        @SerializedName("id") val topics_list: ArrayList<Topic>
)

data class Topic(
        @SerializedName("title") val title: String,
        @SerializedName("desc") val desc: String,
        @SerializedName("questions") val questions: ArrayList<Question>)

data class Question(
        @SerializedName("text") val text: String,
        @SerializedName("answer") var answer: Int,
        @SerializedName("answers") val answers: ArrayList<String>
)

class Prefs (context: Context) {
    val PREFS_FILENAME = "quizdroid.kjerauld.washington.edu.quizdroid.prefs"
    val URL_TITLE = "URL"
    val CHECK_NUMBER = "Check_Numer"
    val CHECK_STATUS = "Status"
    val CHECK_DOWNLOAD_STATUS = "Download_Status"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

    var check_url: String
        get() = prefs.getString(URL_TITLE, "")
        set(value) = prefs.edit().putString(URL_TITLE, value).apply()

    var check_number: Int
        get() = prefs.getInt(CHECK_NUMBER, 0)
        set(value) = prefs.edit().putInt(CHECK_NUMBER, value).apply()

    var check_status: Boolean
        get() = prefs.getBoolean(CHECK_STATUS, true)
        set(value) = prefs.edit().putBoolean(CHECK_STATUS, value).apply()

    var check_download_status: Boolean
        get() = prefs.getBoolean(CHECK_DOWNLOAD_STATUS, false)
        set(value) = prefs.edit().putBoolean(CHECK_DOWNLOAD_STATUS, value).apply()

}
