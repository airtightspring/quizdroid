package quizdroid.kjerauld.washington.edu.quizdroid

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.widget.Button


class SecondaryActivity : FragmentActivity() {

    // getting data

    var isFragmentOneLoaded = true
    val manager = fragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondary)

        val passedTitle = intent.getStringExtra("Title")
        val passedDescription = intent.getStringExtra("Description")
        val passedLength = intent.getStringExtra("Length")
        val passedQuestions = intent.getStringArrayListExtra("QuestionList")
        val pos = intent.getStringExtra("Pos")

        val bundler: Bundle = Bundle()
            bundler.putString("Title", passedTitle)
            bundler.putString("Description", passedDescription)
            bundler.putString("Length", passedLength)
            bundler.putStringArrayList("QuestionList", passedQuestions)
            bundler.putString("Pos", pos)


        ShowTopicFragment(bundler)


    }

    fun ShowTopicFragment(bundler: Bundle) {
        val firstFragment = TopicFragment()
        firstFragment.setArguments(bundler)
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_holder, firstFragment)
        transaction.commit()
        isFragmentOneLoaded = true
    }

    fun ShowQuestionFragment(bundler: Bundle) {
        val secondFragment = QuestionFragment()
        secondFragment.setArguments(bundler)
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_holder, secondFragment)
        transaction.commit()
        isFragmentOneLoaded = false
    }

    fun ShowAnswerFragment(bundler: Bundle) {
        val thirdFragment = AnswerFragment()
        thirdFragment.setArguments(bundler)
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_holder, thirdFragment)
        transaction.commit()
        isFragmentOneLoaded = false
    }

    fun backToStart() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}
