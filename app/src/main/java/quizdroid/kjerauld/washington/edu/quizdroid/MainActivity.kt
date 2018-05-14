package quizdroid.kjerauld.washington.edu.quizdroid

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.net.ConnectivityManager


class MainActivity : AppCompatActivity() {

    val app = QuizApp.Companion

    var topics = ArrayList<Topic>()
    var url = ""
    var check_number = 0


    fun onComposeAction(mi: MenuItem) {
        val intent = Intent(this, Preferences::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.pref, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (QuizApp.prefs?.check_url != null) {
            url = QuizApp.prefs?.check_url.toString()
        }

        var myContext: Context = this
        app.setContext(myContext)

        app.readFile()

        setSupportActionBar(findViewById(R.id.my_toolbar))

        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = myCustomAdapter(this, app.readFile())

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
            descTextView.text = current_topic.desc

            val nameTextView = rowMain.findViewById<TextView>(R.id.title_textview)
            nameTextView.text = current_topic.title

            return rowMain
        }
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}
