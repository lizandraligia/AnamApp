package anamapp.project.activities

import anamapp.project.R
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_menu_nurse.*

class MenuNurseActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_nurse)

        btnBuscarPacienteNurseProf.setOnClickListener {
            val intent = Intent(applicationContext, SearchPatientActivity::class.java)
            startActivity(intent)
        }


        btnRegPacienteNurseProf.setOnClickListener {
            val intent = Intent(applicationContext, RegisterPatientActivity::class.java)
            startActivity(intent)
        }

        menu_image_view_back_nurse_prof.setOnClickListener {
            onBackPressed()
        }

        logOutNurseProf.setOnClickListener {
            finish()
        }


    }

    override fun onBackPressed() {
        this.moveTaskToBack(true)
    }
}