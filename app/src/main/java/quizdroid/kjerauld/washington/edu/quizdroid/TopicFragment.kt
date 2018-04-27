package quizdroid.kjerauld.washington.edu.quizdroid

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button
import android.widget.TextView





class TopicFragment : Fragment() {

    var title = ""
    var description = ""
    var length = ""
    var questionList: ArrayList<String> = ArrayList<String>()
    var pos = 0
    var cNumber = 0
    var qNumber = 0
    var answerKey = IntArray(0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            title = arguments.getString("Title")
            description = arguments.getString("Description")
            length = arguments.getString("Length")
            questionList = arguments.getStringArrayList("QuestionList")
            pos = arguments.getString("Pos").toInt()
            answerKey = IntArray(questionList.size)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_topic,
        container, false) as View

        // Inflate the layout for this fragment
        val titleBox: TextView = view.findViewById(R.id.textView)
        titleBox.text = title

        val descriptionBox: TextView = view.findViewById(R.id.textView2)
        descriptionBox.text = description

        val lengthBox: TextView = view.findViewById(R.id.textView3)
        val lengthString = questionList.size.toString() + " Questions"
        lengthBox.text = lengthString

        val startButton: Button = view.findViewById(R.id.button)
        startButton.text = "Start"

        val bundler: Bundle = Bundle()
        bundler.putString("Title", title)
        bundler.putString("Description", description)
        bundler.putString("Length", length)
        bundler.putStringArrayList("QuestionList", questionList)
        bundler.putString("Pos", pos.toString())
        bundler.putInt("cNumber", cNumber)
        bundler.putInt("qNumber", qNumber)
        bundler.putIntArray("answerKey", answerKey)

        startButton.setOnClickListener(){
            (activity as SecondaryActivity).ShowQuestionFragment(bundler)
        }

        val back: Button = view.findViewById(R.id.button2)
        back.setOnClickListener() {
            (activity as SecondaryActivity).backToStart()
        }


        return view
    }
}