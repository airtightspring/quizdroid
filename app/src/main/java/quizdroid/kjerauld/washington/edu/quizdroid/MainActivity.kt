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

    val app = QuizApp.Companion
    val topics = app.getTopics()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = myCustomAdapter(this, topics)

        listView.setOnItemClickListener{ parent, view, position, id ->
            val intent = Intent(this, SecondaryActivity::class.java)
            intent.putExtra("Pos", position.toString())
            startActivity(intent)
        }
    }

    private class myCustomAdapter(context: Context, topics: ArrayList<Topic>): BaseAdapter() {
        private val mContext: Context

        val topic_list = topics

        init {
            this.mContext = context
        }
        // responsible for number of rows in my list
        override fun getCount(): Int {
            return topic_list.size
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
            val current_topic = topic_list[position]
            descTextView.text = current_topic.shortDesc

            val nameTextView = rowMain.findViewById<TextView>(R.id.title_textview)
            nameTextView.text = current_topic.title

            return rowMain
        }
    }

}
