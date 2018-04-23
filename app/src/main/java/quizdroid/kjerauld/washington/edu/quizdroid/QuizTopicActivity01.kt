package quizdroid.kjerauld.washington.edu.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class QuizTopicActivity01 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_topic01)

        // getting data
        val passedTitle = intent.getStringExtra("Title")
        val passedDescription = intent.getStringExtra("Description")
        val passedLength = intent.getStringExtra("Length")
        val passedQuestions = intent.getStringArrayListExtra("QuestionList")
        val pos = intent.getStringExtra("Pos")
        val cNumber = 0
        val answerKey = IntArray(passedQuestions.size)

        supportActionBar?.title = passedTitle

        val title: TextView = findViewById(R.id.textView)
        title.text = passedTitle

        val description: TextView = findViewById(R.id.textView2)
        description.text = passedDescription

        val questions: TextView = findViewById(R.id.textView3)
        val questionContent = passedLength + " Questions"
        questions.text = questionContent

        val begin: Button = findViewById(R.id.button)
        val back: Button = findViewById(R.id.button2)

        back.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        begin.setOnClickListener() {
            val qNumber = "0"
            val intent = Intent(this, QuestionActivity01::class.java)
            intent.putExtra("Title", passedTitle)
            intent.putExtra("Description", passedDescription)
            intent.putExtra("Length", passedLength)
            intent.putExtra("QuestionList", passedQuestions)
            intent.putExtra("Pos", pos)
            intent.putExtra("qNumber", qNumber)
            intent.putExtra("cNumber", cNumber.toString())
            intent.putExtra("key", answerKey)
            startActivity(intent)
        }
    }
}
