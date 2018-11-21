package anamapp.project

import anamapp.project.bean.Patient
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_search_patient.*

class SearchPatientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_patient)

        search_patient_button_search.setOnClickListener {
            val medical_record = search_patient_edit_text_patient_number.text.toString()

            PatientSearchResult.patient = Patient("Jonas Freire de Alcantara Marques de Barros", medical_record, "09/08/1935", "98765-1423", 47)

            val intent = Intent(this, PatientActivity::class.java)
            startActivity(intent)

        }

        search_patient_image_view_back.setOnClickListener {
            finish()
        }
    }
}
