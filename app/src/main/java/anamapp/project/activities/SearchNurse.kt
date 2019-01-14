package anamapp.project.activities

import anamapp.project.R
import anamapp.project.adapter.NurseAdapter
import anamapp.project.bean.Nurse
import anamapp.project.bean.prefs
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

class SearchNurse : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NurseAdapter

    lateinit var mAuth: FirebaseAuth
    var query: Query = FirebaseDatabase.getInstance().getReference("nurse")


    var listNurse: ArrayList<Nurse> = ArrayList<Nurse>()

    var spinnerInfo: ArrayList<String> = ArrayList<String>()

    companion object {
        var ID = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_nurse)


        mAuth = FirebaseAuth.getInstance()

        recyclerView = findViewById(R.id.recyclerViewNurse)

        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = NurseAdapter(this, listNurse)


        recyclerView.adapter = adapter


        ID = prefs.uid


        search_nurse_image_view_back.setOnClickListener {
            finish()
        }


        spinnerInfo.add("")
        spinnerInfo.add(getString(R.string.spinner_coren))
        spinnerInfo.add(getString(R.string._spinner_email))
        spinnerInfo.add(getString(R.string.spinner_name))

        var arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,spinnerInfo
        )

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)

        spinnerNurseSearch.adapter = arrayAdapter

        spinnerNurseSearch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent!!.getItemAtPosition(position).toString().equals("")) {
                    return

                }else if (parent!!.getItemAtPosition(position).toString().equals(getString(R.string.spinner_coren))) {
                    if (search_nurse_edit_text.text.isBlank()) {
                        search_nurse_edit_text.setError(getString(R.string.cannot_be_empty))
                        search_nurse_edit_text.requestFocus()
                    }
                    if (search_nurse_edit_text.isFocused) {
                        search_nurse_edit_text.clearFocus()
                    }
                    query = FirebaseDatabase.getInstance().getReference("nurses").child(ID)
                        .orderByChild("nurseCoren")
                        .equalTo(search_nurse_edit_text.text.toString())
                    query.addListenerForSingleValueEvent(valueEventListener)
                } else if (parent!!.getItemAtPosition(position).toString().equals(getString(R.string._spinner_email))) {
                    if (search_nurse_edit_text.text.isBlank()) {
                        search_nurse_edit_text.setError(getString(R.string.cannot_be_empty))
                        search_nurse_edit_text.requestFocus()
                        return
                    }
                    if (search_nurse_edit_text.isFocused) {
                        search_nurse_edit_text.clearFocus()
                    }
                    query = FirebaseDatabase.getInstance().getReference("nurses").child(ID)
                        .orderByChild("email")
                        .equalTo(search_nurse_edit_text.text.toString())
                    query.addListenerForSingleValueEvent(valueEventListener)

                } else {
                    if (search_nurse_edit_text.text.isBlank()) {
                        search_nurse_edit_text.setError(getString(R.string.cannot_be_empty))
                        search_nurse_edit_text.requestFocus()
                        return
                    }
                    if (search_nurse_edit_text.isFocused) {
                        search_nurse_edit_text.clearFocus()
                    }
                    query = FirebaseDatabase.getInstance().getReference("nurses").child(ID)
                        .orderByChild("nurseName")
                        .equalTo(search_nurse_edit_text.text.toString())
                    query.addListenerForSingleValueEvent(valueEventListener)
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
                listNurse.clear()

                for (nurseSnapshot in dataSnapshot.children) {
                    var nurse = nurseSnapshot.getValue(Nurse::class.java)

                    listNurse.add(nurse!!)


                }
                adapter.notifyDataSetChanged()
            }else {
                Toast.makeText(applicationContext, getString(R.string.no_data_found), Toast.LENGTH_SHORT).show()
            }

        }

        override fun onCancelled(databaseError: DatabaseError) {
            //TODO
        }
    }


}
