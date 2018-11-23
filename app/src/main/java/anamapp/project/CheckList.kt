package anamapp.project

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView



class CheckList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_list)
        var txtView = findViewById<View>(R.id.textView3) as TextView
        val nome: String = intent.getStringExtra("sintoma")
        txtView.setText(nome)

    }
}
