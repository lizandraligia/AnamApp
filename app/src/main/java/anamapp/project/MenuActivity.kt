package anamapp.project

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

        btnListEmployee.setOnClickListener{
            val intent = Intent(applicationContext, ListNursesActivity::class.java)
            startActivity(intent)
        }
        menu_image_view_back.setOnClickListener {
            finish()
        }

    }
}