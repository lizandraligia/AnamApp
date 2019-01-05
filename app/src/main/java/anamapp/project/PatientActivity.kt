package anamapp.project

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_patient.*


/* Esta Activity receberá um paciente e ajustará seus dados na tela */


class PatientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)

        val patient = PatientSearchResult.patient

        patient_text_view_medical_record.text = patient.medical_record
        patient_text_view_name.text = patient.name
        patient_text_view_birth_date.text = patient.birth_date
        patient_text_view_age.text = patient.age.toString()
        patient_text_view_fone.text = patient.fone


        patient_image_view_back.setOnClickListener {
            finish()
        }
    }
}