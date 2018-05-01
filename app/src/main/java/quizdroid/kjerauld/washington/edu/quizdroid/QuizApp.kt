package quizdroid.kjerauld.washington.edu.quizdroid

import android.app.Application
import android.util.Log

class QuizApp : Application(), TopicRepository {


    companion object Companion {
        fun create(): QuizApp = QuizApp()

        var topicList = ArrayList<Topic>()

        fun setTopics(input: ArrayList<Topic>) {
            topicList = input
        }

        fun getTopics(): ArrayList<Topic> {
            return topicList
        }
    }

    var topics_holder: ArrayList<Topic> = ArrayList<Topic>()

    override fun onCreate() {
        super.onCreate()
        // Required initialization logic here!

        Log.i("Startup", "Application Class Initialized")

        val topic02_q01 = Question("Which of the following is not a type of energy?",
                arrayListOf<String>(
                        "Kinetic", "Wooden", "Thermal", "Potential"
                ), 1)

        val topic02_q02 = Question("Which of these is a unit of work?",
                arrayListOf<String>(
                        "Watt", "Meter", "Joule", "Newton"
                ), 2)

        val topic02_q03 = Question("Which of Newton's Laws states that every action has an equal and opposite reaction?",
                arrayListOf<String>(
                        "First", "Second", "Third", "Fourth"
                ), 2)

        val topic02_questions: ArrayList<Question> = arrayListOf(topic02_q01, topic02_q02, topic02_q03)

        val topic01_q01 = Question("What is the derivative of 7x?",
                arrayListOf<String>(
                        "3.5x", "7x", "3.5x","7"
                ), 3)

        val topic01_q02 = Question("What is 9 x 9?",
                arrayListOf<String>(
                        "52", "81", "36", "18", "81"
                ), 1)

        val topic01_q03 = Question("What is 5/1?",
                arrayListOf<String>(
                        "5", "1", "10", "42", "5"
                ), 0)

        val topic01_questions: ArrayList<Question> = arrayListOf(topic01_q01, topic01_q02, topic01_q03)

        val topic03_q01 = Question("Which Hero has the top grossing solo film of all time?",
                arrayListOf<String>(
                        "Iron Man", "Captain America", "Black Panther", "Thor"
                ), 2)

        val topic03_q02 = Question("What is the name of the upcoming Avengers Movie?",
                arrayListOf<String>(
                        "Age of Ultron", "War of Thanos", "Battle for Earth", "Infinity War"
                ), 3)

        val topic03_q03 = Question("Which hero has the power to fly?",
                arrayListOf<String>(
                        "Vision", "Hawkeye", "Black Widow", "Captain America"
                ), 0)

        val topic03_q04 = Question("Where is Black Panther from?",
                arrayListOf<String>(
                        "Kenya", "Ethiopia", "Azerbaijan", "Wakanda"
                ), 3)


        val topic03_questions: ArrayList<Question> = arrayListOf(topic03_q01, topic03_q02, topic03_q03, topic03_q04)

        val topic01 = createTopics("Math", "Test your Math Skills", "Take a basic math test" +
                " to see if you are smarter than a 5th grader!", topic01_questions)

        val topic02 = createTopics("Physics", "Are you a Physics Wizard?", "Take this test to find " +
                "out if you were made to be a Physics Major!", topic02_questions)

        val topic03 = createTopics("Marvel Super Heroes", "Test Your Wisdom Before Thanos!", "Take this" +
                " Quiz to find out how much you truly know about the MCU!", topic03_questions)

        val topics: ArrayList<Topic> = arrayListOf<Topic>(topic01, topic02, topic03)

        topics_holder = topics
        Companion.setTopics(topics)


    }

    override fun createTopics(title: String, shortDesc: String, longDesc: String, questions: ArrayList<Question>): Topic {
        val newTopic: Topic = Topic(title, shortDesc, longDesc, questions)
        return newTopic
    }

    fun getTopics(): ArrayList<Topic> {
        return topics_holder
    }

}

interface TopicRepository {
    fun createTopics(title: String, shortDesc: String, longDesc: String, questions: ArrayList<Question>): Topic {
        return Topic(title, shortDesc, longDesc, ArrayList<Question>())
    }
}

data class Topic(val title: String, val shortDesc: String, val longDesc: String, val questions: ArrayList<Question>)

data class Question(val question: String, val answers: ArrayList<String>, var correctAnswer: Int)