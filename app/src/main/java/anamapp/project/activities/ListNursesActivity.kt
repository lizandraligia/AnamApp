package anamapp.project.activities

import anamapp.project.R
import anamapp.project.adapter.NurseAdapter
import anamapp.project.bean.Hospital
import anamapp.project.bean.Nurse
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_listnurses_nurses.*

class ListNursesActivity : Activity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NurseAdapter

    lateinit var databaseNurse: DatabaseReference
    lateinit var dataBaseHospital: DatabaseReference
    lateinit var mAuth: FirebaseAuth



    var list: ArrayList<Hospital> = ArrayList<Hospital>()

    var listNurse: ArrayList<Nurse> = ArrayList<Nurse>()

    companion object {
        var ID = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listnurses_nurses)


        menu_image_view_back2.setOnClickListener {
            finish()
        }

        btnRegisterNewNurses.setOnClickListener {
            val intent = Intent(applicationContext, registerNewEmployeeActivity::class.java)
            startActivity(intent)
        }

        mAuth = FirebaseAuth.getInstance()

        recyclerView = findViewById(R.id.recyclerViewNurse)

        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = NurseAdapter(this, listNurse)




        recyclerView.adapter = adapter

        dataBaseHospital = FirebaseDatabase.getInstance().getReference("hospital")
        databaseNurse = FirebaseDatabase.getInstance().getReference("nurses")


        dataBaseHospital.addListenerForSingleValueEvent(eventListenerAux)


    }


    override fun onDestroy() {
        super.onDestroy()

        databaseNurse.removeEventListener(valueEventListener)
        dataBaseHospital.removeEventListener(eventListenerAux)
    }



    var eventListenerAux = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {

            list.clear()
            for (hospitalSnapshot in dataSnapshot.children) {
                var hospital = hospitalSnapshot.getValue(Hospital::class.java)

                list.add(hospital!!)

                var email = ""
                val user = mAuth.currentUser
                user?.let {
                    email = user.email!!
                }
                for (hospital in list) {
                    if (hospital.email == email) {
                        ID = hospital.id
                        databaseNurse.addValueEventListener(valueEventListener)
                        break
                    }
                }

            }

        }

        override fun onCancelled(databaseError: DatabaseError) {
            //TODO
        }

    }



    var valueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {

            if (dataSnapshot.exists()) {
                listNurse.clear()
                for (nurseSnapshot in dataSnapshot.child(ID).children) {
                    var nurse = nurseSnapshot.getValue(Nurse::class.java)

                    listNurse.add(nurse!!)


                }
                adapter.notifyDataSetChanged()
            }

        }

        override fun onCancelled(databaseError: DatabaseError) {
            //TODO
        }
    }





}




