package anamapp.project

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_symptoms_menu.*

class SymptomsMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptoms_menu)


        menu_image_view_back3.setOnClickListener {
            finish()
        }
    }


}
