package quizdroid.kjerauld.washington.edu.quizdroid

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button
import android.widget.TextView


class AnswerFragment : Fragment() {
    var title = ""
    var description = ""
    var length = ""
    var questionList: ArrayList<String> = ArrayList<String>()
    var pos = 0
    var qNumber = 0
    var aNumber = 0
    var cNumber = 0
    var answerKey = IntArray(0)

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
        if (arguments != null) {
            title = arguments.getString("Title")
            description = arguments.getString("Description")
            length = arguments.getString("Length")
            questionList.addAll(arguments.getStringArrayList("QuestionList"))
            pos = arguments.getString("Pos").toInt()
            aNumber = arguments.getInt("aNumber")
            qNumber = arguments.getInt("qNumber")
            cNumber = arguments.getInt("cNumber")
            answerKey = arguments.getIntArray("answerKey")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_answer,
                container, false) as View

        if (answers[pos.toInt()][qNumber][aNumber] == answers[pos.toInt()][qNumber][4]) {
            cNumber = cNumber + 1
            answerKey[qNumber] = 1
        }

        val yourAnswer: TextView = view.findViewById(R.id.textView5)
        yourAnswer.text = "Correct Answer: " + answers[pos.toInt()][qNumber][4]
        val correctAnswer: TextView = view.findViewById(R.id.textView7)
        correctAnswer.text = "Your Answer: " + answers[pos.toInt()][qNumber][aNumber]
        val correctDisplay: TextView = view.findViewById(R.id.textView6)
        correctDisplay.text = cNumber.toString() + "/" +  questionList.size + " Correct"
        val next: Button = view.findViewById(R.id.button5)
        if(qNumber < questionList.size - 1) {
            next.text = "Next"
        } else {
            next.text = "Finish"
        }

        qNumber = qNumber + 1

        val bundler: Bundle = Bundle()
        bundler.putString("Title", title)
        bundler.putString("Description", description)
        bundler.putString("Length", length)
        bundler.putStringArrayList("QuestionList", questionList)
        bundler.putString("Pos", pos.toString())
        bundler.putInt("aNumber", aNumber)
        bundler.putInt("qNumber", qNumber)
        bundler.putInt("cNumber", cNumber)
        bundler.putIntArray("answerKey", answerKey)

        next.setOnClickListener(){
            if(qNumber < questionList.size) {
                (activity as SecondaryActivity).ShowQuestionFragment(bundler)
            } else {
                (activity as SecondaryActivity).backToStart()
            }
        }

        val back: Button = view.findViewById(R.id.button6)

        back.setOnClickListener() {

            aNumber = 0
            qNumber = qNumber - 1

            val bundler: Bundle = Bundle()
            bundler.putString("Title", title)
            bundler.putString("Description", description)
            bundler.putString("Length", length)
            bundler.putStringArrayList("QuestionList", questionList)
            bundler.putString("Pos", pos.toString())
            bundler.putInt("aNumber", aNumber)
            bundler.putInt("qNumber", qNumber)
            bundler.putInt("cNumber", cNumber)
            bundler.putIntArray("answerKey", answerKey)

            (activity as SecondaryActivity).ShowQuestionFragment(bundler)


        }

        return view
    }
}
