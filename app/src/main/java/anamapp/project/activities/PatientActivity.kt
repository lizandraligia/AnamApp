package anamapp.project.activities

import anamapp.project.R
import anamapp.project.bean.Patient
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_patient.*


/* Esta Activity receberá um paciente e ajustará seus dados na tela */


class PatientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)

        val r = intent.getSerializableExtra("objeto") as Patient

        patient_text_view_name.text = r.name
        patient_text_view_medical_record.text = r.medical_record

        //val patient = PatientActivity.patient

        patient_text_view_name.text = r.name
        patient_text_view_medical_record.text = r.medical_record

        //patient_text_view_birth_date.text = patient.birth_date
        //patient_text_view_age.text = patient.age.toString()
        //patient_text_view_fone.text = patient.fone


        patient_image_view_back.setOnClickListener {
            finish()
        }
    }
}