package anamapp.project

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.support.v4.app.FragmentManager
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
            intent.putExtra("sintoma", btnGeneralSymptoms.text.toString())
            startActivity(intent)
        }
        btnHeadAndNeckSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            intent.putExtra("sintoma", btnHeadAndNeckSymptoms.text.toString())
            startActivity(intent)
        }
        btnEyeSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            intent.putExtra("sintoma", btnEyeSymptoms.text.toString())
            startActivity(intent)
        }
        btnEarSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            intent.putExtra("sintoma", btnEarSymptoms.text.toString())
            startActivity(intent)
        }
        btnNoseSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            intent.putExtra("sintoma", btnNoseSymptoms.text.toString())
            startActivity(intent)
        }
        btnRespiratorySystemSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            intent.putExtra("sintoma", btnRespiratorySystemSymptoms.text.toString())
            startActivity(intent)
        }
        btnGenitourinarySystemSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            intent.putExtra("sintoma", btnGenitourinarySystemSymptoms.text.toString())
            startActivity(intent)
        }
        btnGastrointestinalSystemSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            intent.putExtra("sintoma", btnGastrointestinalSystemSymptoms.text.toString())
            startActivity(intent)
        }
        btnLocomotorSystemSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            intent.putExtra("sintoma", btnLocomotorSystemSymptoms.text.toString())
            startActivity(intent)
        }
        btnNervousSystem.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            intent.putExtra("sintoma", btnNervousSystem.text.toString())
            startActivity(intent)
        }
        btnPsycheSymptoms.setOnClickListener {
            val intent = Intent(applicationContext, CheckList::class.java)
            intent.putExtra("sintoma", btnPsycheSymptoms.text.toString())
            startActivity(intent)
        }
    }
}