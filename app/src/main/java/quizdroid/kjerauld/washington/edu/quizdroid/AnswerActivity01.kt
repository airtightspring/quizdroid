package quizdroid.kjerauld.washington.edu.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class AnswerActivity01 : AppCompatActivity() {
    val answers = arrayListOf<ArrayList<ArrayList<String>>>(
            arrayListOf<ArrayList<String>>(
                    arrayListOf<String>(
                            "3.5x", "7x", "3.5x","7","7"
                    ),
                    arrayListOf<String>(
                            "52", "81", "36", "18", "81"
                    ),
                    arrayListOf<String>(
                            "5", "1", "10", "42", "5"
                    )
            ),
            arrayListOf<ArrayList<String>>(
                    arrayListOf<String>(
                            "Kinetic", "Wooden", "Thermal", "Potential", "Wooden"
                    ),
                    arrayListOf<String>(
                            "Watt", "Meter", "Joule", "Newton", "Joule"
                    ),
                    arrayListOf<String>(
                            "First", "Second", "Third", "Fourth", "Third"
                    )
            ),
            arrayListOf<ArrayList<String>>(
                    arrayListOf<String>(
                            "Iron Man", "Captain America", "Black Panther", "Thor", "Black Panther"
                    ),
                    arrayListOf<String>(
                            "Age of Ultron", "War of Thanos", "Battle for Earth", "Infinity War", "Infinity War"
                    ),
                    arrayListOf<String>(
                            "Vision", "Hawkeye", "Black Widow", "Captain America", "Vision"
                    ),
                    arrayListOf<String>(
                            "Kenya", "Ethiopia", "Azerbaijan", "Wakanda", "Wakanda"
                    )
            )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer01)

        // passed in stuff
        val passedTitle = intent.getStringExtra("Title")
        val passedDescription = intent.getStringExtra("Description")
        val passedLength = intent.getStringExtra("Length")
        val passedQuestions = intent.getStringArrayListExtra("QuestionList")
        val aNumber = intent.getStringExtra("aNumber").toInt()
        val pos = intent.getStringExtra("Pos")
        val qNumber = intent.getStringExtra("qNumber").toInt()
        var cNumber  = intent.getStringExtra("cNumber").toInt()
        var answerKey = intent.getIntArrayExtra("key")

        // correct?
        if (answers[pos.toInt()][qNumber][aNumber] == answers[pos.toInt()][qNumber][4]) {
            cNumber = cNumber + 1
            answerKey[qNumber] = 1
        }


        // other stuff
        val yourAnswer: TextView = findViewById(R.id.textView5)
        yourAnswer.text = "Correct Answer: " + answers[pos.toInt()][qNumber][4]
        val correctAnswer: TextView = findViewById(R.id.textView7)
        correctAnswer.text = "Your Answer: " + answers[pos.toInt()][qNumber][aNumber]
        val correctDisplay: TextView = findViewById(R.id.textView6)
        correctDisplay.text = cNumber.toString() + "/" +  passedQuestions.size + " Correct"
        val next: Button = findViewById(R.id.button5)
        if(qNumber < passedQuestions.size - 1) {
            next.text = "Next"
        } else {
            next.text = "Finish"
        }

        next.setOnClickListener() {
            if(qNumber < passedQuestions.size - 1) {
                val intent = Intent(this, QuestionActivity01::class.java)
                intent.putExtra("Title", passedTitle)
                intent.putExtra("Description", passedDescription)
                intent.putExtra("Length", passedLength)
                intent.putExtra("QuestionList", passedQuestions)
                intent.putExtra("Pos", pos)
                intent.putExtra("qNumber", (qNumber + 1).toString())
                intent.putExtra("cNumber", cNumber.toString())
                intent.putExtra("key", answerKey)
                startActivity(intent)
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }

        val back: Button = findViewById(R.id.button6)

        back.setOnClickListener() {
            // how many do they currently have right?
            val intent = Intent(this, QuestionActivity01::class.java)
            intent.putExtra("Title", passedTitle)
            intent.putExtra("Description", passedDescription)
            intent.putExtra("Length", passedLength)
            intent.putExtra("QuestionList", passedQuestions)
            intent.putExtra("Pos", pos)
            intent.putExtra("qNumber", qNumber.toString())
            intent.putExtra("cNumber", cNumber.toString())
            intent.putExtra("key", answerKey)
            startActivity(intent)
        }
    }
}
