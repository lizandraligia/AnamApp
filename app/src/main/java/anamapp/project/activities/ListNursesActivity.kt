package anamapp.project.activities

import anamapp.project.R
import anamapp.project.adapter.NurseAdapter
import anamapp.project.bean.App
import anamapp.project.bean.Hospital
import anamapp.project.bean.Nurse
import anamapp.project.bean.prefs
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_listnurses_nurses.*
import kotlinx.android.synthetic.main.items_list_nurse.*

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


        ID = prefs.uid


        databaseNurse = FirebaseDatabase.getInstance().getReference("nurses")

        progressBar4.visibility = View.VISIBLE

        databaseNurse.addValueEventListener(valueEventListener)


    }


    override fun onDestroy() {
        super.onDestroy()

        databaseNurse.removeEventListener(valueEventListener)
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
            }else {
                Toast.makeText(applicationContext, getString(R.string.no_data_found), Toast.LENGTH_SHORT).show()
            }
            progressBar4.visibility = View.GONE

        }

        override fun onCancelled(databaseError: DatabaseError) {
            //TODO
        }
    }





}




