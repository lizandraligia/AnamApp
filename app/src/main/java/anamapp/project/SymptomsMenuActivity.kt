package anamapp.project

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_symptoms_menu.*

class SymptomsMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptoms_menu)


        btnFinishSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, PatientActivity::class.java)
            startActivity(intent)
        }

        menu_image_view_back3.setOnClickListener {
            finish()
        }

        btnGeneralSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            startActivity(intent)
        }

        btnHeadAndNeckSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            startActivity(intent)
        }

        btnEyeSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            startActivity(intent)
        }

        btnEarSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            startActivity(intent)
        }

        btnNoseSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            startActivity(intent)
        }

        btnRespiratorySystemSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            startActivity(intent)
        }

        btnGenitourinarySystemSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            startActivity(intent)
        }

        btnGastrointestinalSystemSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            startActivity(intent)
        }

        btnLocomotorSystemSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            startActivity(intent)
        }

        btnNervousSystem.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            startActivity(intent)
        }

        btnPsycheSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            startActivity(intent)
        }
    }


}
