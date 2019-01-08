package anamapp.project.activities

import anamapp.project.bean.Patient

class PatientSearchResult {
    companion object {
        var patients = ArrayList<Patient>()
        lateinit var patient : Patient
    }
}