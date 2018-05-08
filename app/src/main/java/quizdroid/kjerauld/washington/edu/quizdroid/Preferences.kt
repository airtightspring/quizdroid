package quizdroid.kjerauld.washington.edu.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class Preferences : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        val submit: Button = findViewById(R.id.button7)
        val back: Button = findViewById(R.id.button8)
        val url: EditText =  findViewById(R.id.editText2)
        val checker: EditText = findViewById(R.id.editText)
        val box01: TextView = findViewById(R.id.textView9)
        val box02: TextView = findViewById(R.id.textView10)
        val box03: TextView = findViewById(R.id.textView8)

        box01.setText("Quiz JSON File URL")
        box02.setText("Update Time to Check For Data")
        box03.setText("in Minutes")

        submit.setOnClickListener() {
            if(url.getText().toString() != "" && checker.getText().toString() != "") {
                val new_url = url.getText().toString()
                val new_timer: Int = checker.getText().toString().toDouble().toInt()
                prefs.check_url = new_url
                prefs.check_number - new_timer

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }

        back.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
