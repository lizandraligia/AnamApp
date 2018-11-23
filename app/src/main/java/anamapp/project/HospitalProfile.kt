package anamapp.project

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_hospital_profile.*

class HospitalProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_profile)

        btnFuncionario.setOnClickListener {
            val intent = Intent(applicationContext, ListNursesActivity::class.java)
            startActivity(intent)
        }
    }
}
