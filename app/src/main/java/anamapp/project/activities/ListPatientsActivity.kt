package anamapp.project.activities

import anamapp.project.R
import anamapp.project.R.id.menu_image_view_back2
import anamapp.project.adapter.PatientAdapter
import anamapp.project.bean.Hospital
import anamapp.project.bean.Nurse
import anamapp.project.bean.Patient
import anamapp.project.bean.prefs
import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_list_patients.*

class ListPatientsActivity : Activity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PatientAdapter

    lateinit var databaseNurse: DatabaseReference
    lateinit var dataBaseHospital: DatabaseReference
    lateinit var dataBasePatient: DatabaseReference
    lateinit var mAuth: FirebaseAuth



    var list: ArrayList<Hospital> = ArrayList<Hospital>()

    var listNurse: ArrayList<Nurse> = ArrayList<Nurse>()

    var listPatient: ArrayList<Patient> = ArrayList<Patient>()

    companion object {
        var ID = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_patients)


        menu_image_view_back2.setOnClickListener {
            finish()
        }



        mAuth = FirebaseAuth.getInstance()

        recyclerView = findViewById(R.id.recyclerViewPatient)

        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PatientAdapter(this, listPatient)




        recyclerView.adapter = adapter


        ID = prefs.uid


        dataBasePatient = FirebaseDatabase.getInstance().getReference("patient")


        dataBasePatient.addValueEventListener(valueEventListener)


    }


    override fun onDestroy() {
        super.onDestroy()

        dataBasePatient.removeEventListener(valueEventListener)
    }




    var valueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {

            if (dataSnapshot.exists()) {
                listPatient.clear()
                for (patientSnapshot in dataSnapshot.child(ID).children) {
                    var patient = patientSnapshot.getValue(Patient::class.java)

                    listPatient.add(patient!!)


                }
                adapter.notifyDataSetChanged()
            }


        }

        override fun onCancelled(databaseError: DatabaseError) {
            //TODO
        }
    }
}
