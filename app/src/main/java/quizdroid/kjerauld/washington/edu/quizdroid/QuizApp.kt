package quizdroid.kjerauld.washington.edu.quizdroid

import android.app.Application
import android.util.Log

object QuizApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Required initialization logic here!
        Log.i("Startup", "Application Class Initialized")
    }

}

interface TopicRepository {

}

data class Topic(val title: String, val shortDesc: String, val longDesc: String, val questions: Collection<Question>)

data class Question(val question: String, val answers: ArrayList<String>, var correctAnswer: Int)