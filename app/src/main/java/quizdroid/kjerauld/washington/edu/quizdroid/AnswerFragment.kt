package quizdroid.kjerauld.washington.edu.quizdroid

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button
import android.widget.TextView


class AnswerFragment : Fragment() {
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

        if (aNumber == topics[pos].questions[qNumber].answer) {
            cNumber = cNumber + 1
            answerKey[qNumber] = 1
        }

        val yourAnswer: TextView = view.findViewById(R.id.textView5)
        yourAnswer.text = "Correct Answer: " + topics[pos].questions[qNumber].answers[topics[pos].questions[qNumber].answer]
        val correctAnswer: TextView = view.findViewById(R.id.textView7)
        correctAnswer.text = "Your Answer: " + topics[pos].questions[qNumber].answers[aNumber]
        val correctDisplay: TextView = view.findViewById(R.id.textView6)
        correctDisplay.text = cNumber.toString() + "/" +  topics[pos].questions.size.toString() + " Correct"
        val next: Button = view.findViewById(R.id.button5)
        if(qNumber < topics[pos].questions.size - 1) {
            next.text = "Next"
        } else {
            next.text = "Finish"
        }

        qNumber = qNumber + 1

        val bundler: Bundle = Bundle()
        bundler.putString("Pos", pos.toString())
        bundler.putInt("aNumber", aNumber)
        bundler.putInt("qNumber", qNumber)
        bundler.putInt("cNumber", cNumber)
        bundler.putIntArray("answerKey", answerKey)

        next.setOnClickListener(){
            if(qNumber < topics[pos].questions.size) {
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
