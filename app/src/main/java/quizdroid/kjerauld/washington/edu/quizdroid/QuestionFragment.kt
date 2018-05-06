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

    val app = QuizApp.Companion
    val topics = app.getTopics()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
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
        questionDisplay.text = topics[pos].questions[qNumber].text

        if(answerKey[qNumber] == 1) {
            cNumber = cNumber - 1
            answerKey[qNumber] = 0
        }


        val radio01: RadioButton = view.findViewById(R.id.radioButton)
        radio01.text =  topics[pos].questions[qNumber].answers[0]
        radio01.setOnClickListener() {
            aNumber = 0
            submit.setEnabled(true)
        }

        val radio02: RadioButton = view.findViewById(R.id.radioButton2)
        radio02.text = topics[pos].questions[qNumber].answers[1]
        radio02.setOnClickListener() {
            aNumber = 1
            submit.setEnabled(true)
        }

        val radio03: RadioButton = view.findViewById(R.id.radioButton3)
        radio03.text = topics[pos].questions[qNumber].answers[2]
        radio03.setOnClickListener() {
            aNumber = 2
            submit.setEnabled(true)
        }

        val radio04: RadioButton = view.findViewById(R.id.radioButton4)
        radio04.text = topics[pos].questions[qNumber].answers[3]
        radio04.setOnClickListener() {
            aNumber = 3
            submit.setEnabled(true)
        }


        submit.setOnClickListener(){
            val bundler: Bundle = Bundle()
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
