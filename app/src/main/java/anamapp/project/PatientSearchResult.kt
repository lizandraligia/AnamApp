package anamapp.project

import anamapp.project.bean.Patient

class PatientSearchResult {
    companion object {
        var patients = ArrayList<Patient>()
        lateinit var patient : Patient
    }
}