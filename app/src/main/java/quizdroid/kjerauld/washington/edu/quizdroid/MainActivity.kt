package quizdroid.kjerauld.washington.edu.quizdroid

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    val titles = arrayListOf<String>(
            "Math",
            "Physics",
            "Marvel Super Heroes"
    )

    val descriptions = arrayListOf<String>(
            "Test your Math Skills!",
            "Are you a Physics Wizard?",
            "Test Your Knowledge Before Thanos!"
    )

    val questions = arrayListOf<ArrayList<String>>(
            arrayListOf<String>(
                    "What is the derivative of 7x?",
                    "What is 9 x 9?",
                    "What is 5/1?"
            ), arrayListOf<String>(
            "Which of the following is not a type of energy?",
            "Which of these is a unit of work?",
            "Which of Newton's Laws states that every action has an equal and opposite reaction?"
    ), arrayListOf<String>(
            "Which Hero has the top grossing solo film of all time?",
            "What is the name of the upcoming Avengers Movie?",
            "Which hero has the power to fly?",
            "Where is Black Panther from?"
    )
    )

    val lengths = arrayOf(
            3, 3, 4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = myCustomAdapter(this)

        listView.setOnItemClickListener{ parent, view, position, id ->
            val intent = Intent(this, SecondaryActivity::class.java)
            intent.putExtra("Title",titles[position])
            intent.putExtra("Description", descriptions[position])
            intent.putExtra("Length", lengths[position].toString())
            intent.putExtra("QuestionList", questions[position])
            intent.putExtra("Pos", position.toString())
            startActivity(intent)
        }
    }

    private class myCustomAdapter(context: Context): BaseAdapter() {
        private val mContext: Context

        val titles = arrayListOf<String>(
                "Math",
                "Physics",
                "Marvel Super Heroes"
        )

        val descriptions = arrayOf(
                "Test your Math Skills!",
                "Are you a Physics Wizard?",
                "Test Your Knowledge Before Thanos!"
        )

        init {
            this.mContext = context
        }
        // responsible for number of rows in my list
        override fun getCount(): Int {
            return titles.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return "TEST STRING"
        }

        // responsible for rendering each row
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.row_main, parent, false)
            val descTextView = rowMain.findViewById<TextView>(R.id.description_textview)
            descTextView.text = descriptions.get(position)

            val nameTextView = rowMain.findViewById<TextView>(R.id.title_textview)
            nameTextView.text = titles.get(position)

            return rowMain
        }
    }

}
