package anamapp.project.activities

import anamapp.project.R
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnBuscarPaciente.setOnClickListener {
            val intent = Intent(applicationContext, SearchPatientActivity::class.java)
            startActivity(intent)
        }

        btnRegPaciente.setOnClickListener {
            val intent = Intent(applicationContext, SymptomsMenuActivity::class.java)
            startActivity(intent)
        }

        menu_image_view_back.setOnClickListener {
            onBackPressed()
        }

        btnInfo.setOnClickListener {
            val intent = Intent(applicationContext, HospitalProfile::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        this.moveTaskToBack(true)
    }
}