package quizdroid.kjerauld.washington.edu.quizdroid

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView


class QuestionFragment : Fragment() {
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
            cNumber = arguments.getInt("cNumber")
            qNumber = arguments.getInt("qNumber")
            answerKey = arguments.getIntArray("answerKey")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_question,
                container, false) as View

        val submit: Button = view.findViewById(R.id.button4)
        submit.setEnabled(false)

        val questionDisplay: TextView = view.findViewById(R.id.textView4)
        questionDisplay.text = questionList[qNumber]

        if(answerKey[qNumber] == 1) {
            cNumber = cNumber - 1
            answerKey[qNumber] = 0
        }


        val radio01: RadioButton = view.findViewById(R.id.radioButton)
        radio01.text = answers[pos.toInt()][qNumber][0].toString()
        radio01.setOnClickListener() {
            aNumber = 0
            submit.setEnabled(true)
        }

        val radio02: RadioButton = view.findViewById(R.id.radioButton2)
        radio02.text = answers[pos.toInt()][qNumber][1].toString()
        radio02.setOnClickListener() {
            aNumber = 1
            submit.setEnabled(true)
        }

        val radio03: RadioButton = view.findViewById(R.id.radioButton3)
        radio03.text = answers[pos.toInt()][qNumber][2].toString()
        radio03.setOnClickListener() {
            aNumber = 2
            submit.setEnabled(true)
        }

        val radio04: RadioButton = view.findViewById(R.id.radioButton4)
        radio04.text = answers[pos.toInt()][qNumber][3].toString()
        radio04.setOnClickListener() {
            aNumber = 3
            submit.setEnabled(true)
        }


        submit.setOnClickListener(){
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

            (activity as SecondaryActivity).ShowAnswerFragment(bundler)
        }

        val back: Button = view.findViewById(R.id.button3)

        back.setOnClickListener() {
            if(qNumber == 0) {
                (activity as SecondaryActivity).backToStart()
            } else {
                qNumber = qNumber - 1
                aNumber = 0

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
        }

        return view
    }
}
