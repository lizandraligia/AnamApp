package anamapp.project.activities

import anamapp.project.R
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register_patient.*
import kotlinx.android.synthetic.main.activity_search_patient.*

class RegisterPatientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_patient)


        register_patient_button_registrate.setOnClickListener {
            val intent = Intent(applicationContext, SymptomsMenuActivity::class.java)
            startActivity(intent)
        }

        register_patient_image_view_back.setOnClickListener {
            finish()
        }
    }




}
