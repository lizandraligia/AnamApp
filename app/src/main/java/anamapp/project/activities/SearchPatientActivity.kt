package anamapp.project.activities

import anamapp.project.R
import anamapp.project.adapter.PatientAdapter
import anamapp.project.bean.Nurse
import anamapp.project.bean.Patient
import anamapp.project.bean.prefs
//import android.R
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_search_nurse.*
import kotlinx.android.synthetic.main.activity_search_patient.*

class SearchPatientActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PatientAdapter

    lateinit var mAuth: FirebaseAuth
    var query: Query = FirebaseDatabase.getInstance().getReference("patient")

    var listPatient: ArrayList<Patient> = ArrayList<Patient>()

    var spinnerInfo: ArrayList<String> = ArrayList<String>()

    companion object {
        var ID = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_patient)



        mAuth = FirebaseAuth.getInstance()

        recyclerView = findViewById(R.id.recyclerViewPatient)

        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PatientAdapter(this, listPatient)


        recyclerView.adapter = adapter


        SearchNurse.ID = prefs.uid


        spinnerInfo.add("")
        spinnerInfo.add(getString(R.string.spinner_medical_record))
        spinnerInfo.add(getString(R.string.spinner_patient_name))

        var arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this,
                R.layout.simple_spinner_dropdown_item,spinnerInfo
        )

        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_item)

        spinnerPatientSearch.adapter = arrayAdapter

        spinnerPatientSearch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent!!.getItemAtPosition(position).toString().equals("")) {
                    return

                }else if (parent!!.getItemAtPosition(position).toString().equals(getString(R.string.spinner_medical_record))) {
                    if (search_patient_edit_text.text.isBlank()) {
                        search_patient_edit_text.setError(getString(R.string.cannot_be_empty))
                        search_patient_edit_text.requestFocus()
                        spinnerPatientSearch.setSelection(0)
                    }
                    if (search_patient_edit_text.isFocused) {
                        search_patient_edit_text.clearFocus()
                    }
                    progressBar5.visibility = View.VISIBLE
                    query = FirebaseDatabase.getInstance().getReference("patient").child(SearchNurse.ID)
                            .orderByChild("medical_record")
                            .equalTo(search_patient_edit_text.text.toString())
                    query.addListenerForSingleValueEvent(valueEventListener)
                    spinnerPatientSearch.setSelection(0)
                } else if (parent!!.getItemAtPosition(position).toString().equals(getString(R.string.spinner_patient_name))) {
                    if (search_patient_edit_text.text.isBlank()) {
                        search_patient_edit_text.setError(getString(R.string.cannot_be_empty))
                        search_patient_edit_text.requestFocus()
                        spinnerPatientSearch.setSelection(0)
                        return
                    }
                    if (search_patient_edit_text.isFocused) {
                        search_patient_edit_text.clearFocus()
                    }
                    progressBar5.visibility = View.VISIBLE
                    query = FirebaseDatabase.getInstance().getReference("patient").child(SearchNurse.ID)
                            .orderByChild("email")
                            .equalTo(search_patient_edit_text.text.toString())
                    query.addListenerForSingleValueEvent(valueEventListener)
                    spinnerPatientSearch.setSelection(0)

                } else {
                    if (search_patient_edit_text.text.isBlank()) {
                        search_patient_edit_text.setError(getString(R.string.cannot_be_empty))
                        search_patient_edit_text.requestFocus()
                        spinnerPatientSearch.setSelection(0)
                        return
                    }
                    if (search_patient_edit_text.isFocused) {
                        search_patient_edit_text.clearFocus()
                    }
                    progressBar5.visibility = View.VISIBLE
                    query = FirebaseDatabase.getInstance().getReference("patient").child(SearchNurse.ID)
                            .orderByChild("name")
                            .equalTo(search_patient_edit_text.text.toString())
                    query.addListenerForSingleValueEvent(valueEventListener)
                    spinnerPatientSearch.setSelection(0)
                }
            }

        }


    }


    override fun onDestroy() {
        super.onDestroy()
        query.removeEventListener(valueEventListener)

    }


    var valueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            var p = prefs.uid
            if (dataSnapshot.exists()) {
                listPatient.clear()

                for (nurseSnapshot in dataSnapshot.children) {
                    var nurse = nurseSnapshot.getValue(Nurse::class.java)

                    listPatient.add(nurse!!)


                }

                adapter.notifyDataSetChanged()
            }else {
                Toast.makeText(applicationContext, getString(R.string.no_data_found), Toast.LENGTH_SHORT).show()
            }
            progressBar5.visibility = View.GONE


        }

        override fun onCancelled(databaseError: DatabaseError) {
            //TODO
        }
    }
}
