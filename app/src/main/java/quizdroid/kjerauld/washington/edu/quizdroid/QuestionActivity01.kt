package quizdroid.kjerauld.washington.edu.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView

class QuestionActivity01 : AppCompatActivity() {
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
        setContentView(R.layout.activity_question01)
        val submit: Button = findViewById(R.id.button4)
        submit.setEnabled(false)

        val passedTitle = intent.getStringExtra("Title")
        val passedDescription = intent.getStringExtra("Description")
        val passedLength = intent.getStringExtra("Length")
        val passedQuestions = intent.getStringArrayListExtra("QuestionList")
        val pos = intent.getStringExtra("Pos")
        val qNumber = intent.getStringExtra("qNumber").toInt()
        var aNumber = 0
        var cNumber = intent.getStringExtra("cNumber")
        var answerKey = intent.getIntArrayExtra("key")

        if(answerKey[qNumber] == 1) {
            cNumber = (cNumber.toInt() - 1).toString()
            answerKey[qNumber] = 0
        }

        val question: TextView = findViewById(R.id.textView4)
        question.text = passedQuestions[qNumber]

        val radio01: RadioButton = findViewById(R.id.radioButton)
        radio01.text = answers[pos.toInt()][qNumber][0].toString()
        radio01.setOnClickListener() {
            aNumber = 0
            submit.setEnabled(true)
        }

        val radio02: RadioButton = findViewById(R.id.radioButton2)
        radio02.text = answers[pos.toInt()][qNumber][1].toString()
        radio02.setOnClickListener() {
            aNumber = 1
            submit.setEnabled(true)
        }

        val radio03: RadioButton = findViewById(R.id.radioButton3)
        radio03.text = answers[pos.toInt()][qNumber][2].toString()
        radio03.setOnClickListener() {
            aNumber = 2
            submit.setEnabled(true)
        }

        val radio04: RadioButton = findViewById(R.id.radioButton4)
        radio04.text = answers[pos.toInt()][qNumber][3].toString()
        radio04.setOnClickListener() {
            aNumber = 3
            submit.setEnabled(true)
        }

        submit.setOnClickListener() {
            val intent = Intent(this, AnswerActivity01::class.java)
            intent.putExtra("Title", passedTitle)
            intent.putExtra("Description", passedDescription)
            intent.putExtra("Length", passedLength)
            intent.putExtra("QuestionList", passedQuestions)
            intent.putExtra("Pos", pos)
            intent.putExtra("qNumber", qNumber.toString())
            intent.putExtra("aNumber", aNumber.toString())
            intent.putExtra("cNumber", cNumber)
            intent.putExtra("key", answerKey)
            startActivity(intent)
        }


        val back: Button = findViewById(R.id.button3)

        back.setOnClickListener() {
            if(qNumber == 0) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, QuestionActivity01::class.java)
                intent.putExtra("Title", passedTitle)
                intent.putExtra("Description", passedDescription)
                intent.putExtra("Length", passedLength)
                intent.putExtra("QuestionList", passedQuestions)
                intent.putExtra("Pos", pos)
                intent.putExtra("qNumber", (qNumber - 1).toString())
                intent.putExtra("cNumber", cNumber)
                intent.putExtra("key", answerKey)
                startActivity(intent)
            }
        }
    }
}
