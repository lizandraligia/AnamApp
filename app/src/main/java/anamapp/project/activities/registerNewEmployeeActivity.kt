package anamapp.project.activities

import anamapp.project.R
import anamapp.project.bean.Hospital
import anamapp.project.bean.Nurse
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_register_new_employee.*
import kotlinx.android.synthetic.main.activity_register_patient.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.lang.IllegalStateException

class registerNewEmployeeActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var mAuth: FirebaseAuth
    lateinit var mAuthLogged: FirebaseAuth
    lateinit var databaseNurse: DatabaseReference
    lateinit var dataBaseHospital: DatabaseReference

    var list: ArrayList<Hospital> = ArrayList<Hospital>()


    companion object {
        var ID = ""
    }
    override fun onClick(v: View?) {
        if(v == reg_nurse_button) {
            try {
                createAccount(reg_nurse_email.text.toString(), reg_nurse_pass.text.toString(),
                    reg_nurse_confirmPass.text.toString(), reg_nurse_coren.text.toString(), reg_nurse_name.text.toString())
            }catch(e: Exception) {
                createAccount()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_new_employee)


        menu_image_view_back6.setOnClickListener {
            finish()
        }

        reg_nurse_button.setOnClickListener(this)




        dataBaseHospital = FirebaseDatabase.getInstance().getReference("hospital")


        mAuth = FirebaseAuth.getInstance()

        mAuthLogged = mAuth
    }

    public override fun onStart() {
        super.onStart()

        // Add value event listener to the post
        // [START post_value_event_listener]
        dataBaseHospital.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                list.clear()
                for(hospitalSnapshot in dataSnapshot.children) {
                    var hospital = hospitalSnapshot.getValue(Hospital::class.java)
                    list.add(hospital!!)

                    var email = ""
                    val user = mAuthLogged.currentUser
                    user?.let {
                        email = user.email!!
                    }
                    for(hospital in list) {
                        if(hospital.email == email) {
                            ID = hospital.id
                        }
                    }
                    databaseNurse = FirebaseDatabase.getInstance().getReference("nurses").child(ID)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                //TODO
            }
        })


    }

    private fun addNurses() {

        var email =""
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            email = user.email!!

        }


        val id: String = databaseNurse.push().key!!
        val nurse: Nurse = Nurse(id,reg_nurse_name.text.toString(), reg_nurse_coren.text.toString(),
            email)

        databaseNurse.child(id).setValue(nurse)

    }

    private fun createAccount(
        email: String = "",
        password: String = "",
        confirmPass: String = "",
        coren: String = "",
        name: String = ""
    ) {
        if (!validateForm(email, password, confirmPass, coren, name)) {
            return
        }


        progressBar3.visibility = View.VISIBLE
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = mAuth.currentUser
                    if (saveNurseInformation(user!!)) {

                        addNurses()

                        Toast.makeText(
                            baseContext, getString(R.string.authentication_sucessfull),
                            Toast.LENGTH_LONG
                        ).show()

                        reg_nurse_confirmPass.setText("")
                        reg_nurse_pass.setText("")
                        reg_nurse_name.setText("")
                        reg_nurse_coren.setText("")
                        reg_nurse_email.setText("")

                    }
                    progressBar3.visibility = View.GONE
                    Toast.makeText(
                        baseContext, getString(R.string.authentication_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthUserCollisionException) {
                        reg_nurse_email.setError(getString(R.string.user_already_exists))
                        reg_nurse_email.requestFocus()
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        reg_nurse_pass.setError(getString(R.string.weak_pass))
                        reg_nurse_pass.requestFocus()
                    }
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, getString(R.string.authentication_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBar3.visibility = View.GONE
                    //updateUI(null)
                }


            }
        // [END create_user_with_email]
    }

    fun validateForm(email: String, password: String, confirmPass: String, coren: String, name: String): Boolean {
        var aux: Boolean = false

        if (password.equals("") || confirmPass.equals("") || coren.equals("") || name.equals("")) {
            if (password.equals("")) {
                reg_nurse_pass.setError(getString(R.string.cannot_be_empty))
                reg_nurse_pass.requestFocus()
            }
            if(name.equals("")) {
                reg_nurse_name.setError(getString(R.string.cannot_be_empty))
                reg_nurse_name.requestFocus()
            }
            if (confirmPass.equals("")) {
                reg_nurse_confirmPass.setError(getString(R.string.cannot_be_empty))
                reg_nurse_confirmPass.requestFocus()
            }
            if (coren.equals("")) {
                reg_nurse_coren.setError(getString(R.string.cannot_be_empty))
                reg_nurse_coren.requestFocus()
            }
            aux = false
        } else if (!password.equals(confirmPass)) {
            Toast.makeText(this, getString(R.string.password_invalids), Toast.LENGTH_LONG).show()
            aux = false
        } else {
            aux = true
        }




        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || !aux) {


            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                reg_nurse_email.setError(getString(R.string.invalid_email))
                reg_nurse_email.requestFocus()
            }

            aux = false
        } else {
            aux = true
        }

        return aux

    }


    private fun saveNurseInformation(user: FirebaseUser): Boolean {
        var bool = false

        if (user != null) {
            var profile = UserProfileChangeRequest.Builder()
                .setDisplayName(reg_nurse_name.text.toString())
                .build()

            try {
                user.updateProfile(profile)
                bool = true
            } catch (e: Exception) {

                user.delete()
                Toast.makeText(
                    baseContext, getString(R.string.authentication_failed),
                    Toast.LENGTH_LONG
                ).show()
                bool = false
            }

        }
        return bool

    }




}
