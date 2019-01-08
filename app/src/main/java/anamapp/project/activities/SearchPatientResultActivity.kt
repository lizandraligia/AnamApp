package anamapp.project.activities

import anamapp.project.R
import anamapp.project.adapter.SearchPatientAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_search_patient_result.*


/*

    Esta activity irá receber uma ArrayList contendo todos os pacientes
    que correspondem a busca e irá apresentar eles na ListView

*/





class SearchPatientResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_patient_result)

//        PatientSearchResult.patients.add(Patient("Jonas", "111"))
//        PatientSearchResult.patients.add(Patient("Alejandro", "999"))
//        PatientSearchResult.patients.add(Patient("Zeca", "222"))
//        PatientSearchResult.patients.add(Patient("Turma da Mônica", "321"))
//        PatientSearchResult.patients.add(Patient("Jonas", "111"))
//        PatientSearchResult.patients.add(Patient("Alejandro", "999"))
//        PatientSearchResult.patients.add(Patient("Zeca", "222"))
//        PatientSearchResult.patients.add(Patient("Turma da Mônica", "321"))
//        PatientSearchResult.patients.add(Patient("Jonas", "111"))
//        PatientSearchResult.patients.add(Patient("Alejandro", "999"))
//        PatientSearchResult.patients.add(Patient("Zeca", "222"))
//        PatientSearchResult.patients.add(Patient("Turma da Mônica", "321"))
//        PatientSearchResult.patients.add(Patient("Jonas", "111"))
//        PatientSearchResult.patients.add(Patient("Alejandro", "999"))
//        PatientSearchResult.patients.add(Patient("Zeca", "222"))
//        PatientSearchResult.patients.add(Patient("Turma da Mônica", "321"))
//        PatientSearchResult.patients.add(Patient("Jonas", "111"))
//        PatientSearchResult.patients.add(Patient("Alejandro", "999"))
//        PatientSearchResult.patients.add(Patient("Zeca", "222"))
//        PatientSearchResult.patients.add(Patient("Turma da Mônica", "321"))


        var patients = PatientSearchResult.patients
        val adapter = SearchPatientAdapter(this, R.layout.search_patient_item, patients)

        search_patient_result_list_view.adapter = adapter

    }



}
