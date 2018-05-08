package quizdroid.kjerauld.washington.edu.quizdroid

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import android.util.Log
import com.google.gson.annotations.SerializedName
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import okhttp3.OkHttpClient
import java.io.BufferedReader
import java.io.File


val prefs: Prefs by lazy {
    QuizApp.prefs!!
}


class QuizApp : Application(), TopicRepository {


    companion object Companion {
        fun create(): QuizApp = QuizApp()

        var topicList = ArrayList<Topic>()

        var prefs: Prefs? = null

        fun setTopics(input: ArrayList<Topic>) {
            topicList = input
        }

        fun getTopics(): ArrayList<Topic> {
            return topicList
        }

        fun setup(given_url: String): ArrayList<Topic> {
            var url = given_url
            var my_topics: ArrayList<Topic> = ArrayList<Topic>()
            if (deviceReader() == "nullFile") {
                var json = ""
                url = "http://tednewardsandbox.site44.com/questions.json"
                val client = OkHttpClient()
                val request = Request.Builder()
                        .url(url)
                        .build()

                println(Thread.currentThread())
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        e?.printStackTrace()
                    }

                    override fun onResponse(call: Call?, response: Response) {
                        if (!response.isSuccessful) {
                            System.err.println("Response not successful")
                            return
                        }
                        json = response.body()!!.string()
                        val json_data = JSONArray(json)


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
                            //topics_holder.add(topic_object)
                        }
                    }

                })
                // Shutdown the executor as soon as the request is handled
                client.dispatcher().executorService().shutdown()

                Companion.setTopics(my_topics)
                topicList = my_topics

                return my_topics
            } else if(url != "") {
                var json = ""

                val client = OkHttpClient()
                val request = Request.Builder()
                        .url(url)
                        .build()

                println(Thread.currentThread())
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        e?.printStackTrace()
                    }

                    override fun onResponse(call: Call?, response: Response) {
                        if (!response.isSuccessful) {
                            System.err.println("Response not successful")
                            return
                        }
                        json = response.body()!!.string()
                        val json_data = JSONArray(json)


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
                            //topics_holder.add(topic_object)
                        }
                    }

                })
                // Shutdown the executor as soon as the request is handled
                client.dispatcher().executorService().shutdown()

                Companion.setTopics(my_topics)
                topicList = my_topics

                return my_topics
            } else {
                val json = deviceReader()
                val json_data = JSONArray(json)

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
                topicList = my_topics

                return my_topics
            }
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

        var url = ""
        if (prefs?.check_url != null) {
            prefs?.check_url = ""
        }


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
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

    var check_url: String
        get() = prefs.getString(URL_TITLE, "")
        set(value) = prefs.edit().putString(URL_TITLE, value).apply()

    var check_number: Int
        get() = prefs.getInt(CHECK_NUMBER, 0)
        set(value) = prefs.edit().putInt(CHECK_NUMBER, value).apply()
}