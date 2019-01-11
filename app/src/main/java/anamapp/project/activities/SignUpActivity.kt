package anamapp.project.activities

import anamapp.project.R
import anamapp.project.bean.Hospital
import anamapp.project.bean.Nurse
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.renderscript.Sampler
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.lang.IllegalStateException
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var mAuth: FirebaseAuth
    lateinit var mAuthLogged: FirebaseAuth
    lateinit var dataBaseHospital: DatabaseReference

    lateinit var list: ArrayList<Hospital>

    override fun onClick(v: View?) {
        if (v == btn_signUp_create) {
            try {
                createAccount(
                    edit_text_email_signup.text.toString(),
                    edit_text_password_signup.text.toString(),
                    edit_text_confirm_password_signup.text.toString(),
                    tfHospitalName.text.toString(),
                    tfCity.text.toString(),
                    tfState.text.toString(),
                    tfAddress.text.toString()
                )
            } catch (e: IllegalStateException) {
                createAccount()
            }
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btn_signUp_create.setOnClickListener(this)



        mAuth = FirebaseAuth.getInstance()


        dataBaseHospital = FirebaseDatabase.getInstance().getReference("hospital")
    }




    private fun createAccount(
        email: String = "",
        password: String = "",
        confirmPass: String = "",
        hospitalName: String = "",
        hospitalCity: String = "",
        hospitalState: String = "",
        hospitalAddress: String = ""
    ) {
        if (!validateForm(email, password, confirmPass, hospitalName, hospitalCity, hospitalState, hospitalAddress)) {
            return
        }


        progressBar2.visibility = View.VISIBLE
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = mAuth.currentUser
                    if (saveUserInformation(user!!)) {
                        addHospital()

                        Toast.makeText(
                            baseContext, getString(R.string.authentication_sucessfull),
                            Toast.LENGTH_LONG
                        ).show()

                        edit_text_confirm_password_signup.setText("")
                        edit_text_email_signup.setText("")
                        edit_text_password_signup.setText("")
                        tfAddress.setText("")
                        tfCity.setText("")
                        tfState.setText("")
                        tfHospitalName.setText("")
                        val intent = Intent(applicationContext, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.putExtra("Boolean", true)
                        progressBar2.visibility = View.GONE
                        startActivity(intent)
                    }else {
                        progressBar2.visibility = View.GONE
                        Toast.makeText(
                            baseContext, getString(R.string.authentication_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {

                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthUserCollisionException) {
                        edit_text_email_signup.setError(getString(R.string.user_already_exists))
                        edit_text_email_signup.requestFocus()
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        edit_text_password_signup.setError(getString(R.string.weak_pass))
                        edit_text_password_signup.requestFocus()
                    }
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, getString(R.string.authentication_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBar2.visibility = View.GONE
                    //updateUI(null)
                }


            }
        // [END create_user_with_email]
    }

    private fun addHospital() {

        var email = ""
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            email = user.email!!

        }


        val id: String = dataBaseHospital.push().key!!
        val hospital: Hospital = Hospital(
            id, tfHospitalName.text.toString(), tfCity.text.toString(),
            tfAddress.text.toString(), email
        )

        dataBaseHospital.child(id).setValue(hospital)

    }

    private fun saveUserInformation(user: FirebaseUser): Boolean {
        var bool = false

        if (user != null) {
            var profile = UserProfileChangeRequest.Builder()
                .setDisplayName(tfHospitalName.text.toString())
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


    fun validateForm(
        email: String, password: String, confirmPass: String, hospitalName: String, hospitalCity: String,
        hospitalState: String, hospitalAddress: String
    ): Boolean {
        var aux: Boolean = false

        if (password.equals("") || confirmPass.equals("") || hospitalName.equals("") || hospitalCity.equals("")
            || hospitalState.equals("") || hospitalAddress.equals("")
        ) {
            if (password.equals("")) {
                edit_text_password_signup.setError(getString(R.string.cannot_be_empty))
                edit_text_password_signup.requestFocus()
            }
            if (confirmPass.equals("")) {
                edit_text_confirm_password_signup.setError(getString(R.string.cannot_be_empty))
                edit_text_confirm_password_signup.requestFocus()
            }
            if (hospitalName.equals("")) {
                tfHospitalName.setError(getString(R.string.cannot_be_empty))
                tfHospitalName.requestFocus()
            }
            if (hospitalCity.equals("")) {
                tfCity.setError(getString(R.string.cannot_be_empty))
                tfCity.requestFocus()
            }
            if (hospitalState.equals("")) {
                tfState.setError(getString(R.string.cannot_be_empty))
                tfState.requestFocus()
            }
            if (hospitalAddress.equals("")) {
                tfAddress.setError(getString(R.string.cannot_be_empty))
                tfAddress.requestFocus()
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
                edit_text_email_signup.setError(getString(R.string.invalid_email))
                edit_text_email_signup.requestFocus()
            }

            aux = false
        } else {
            aux = true
        }

        return aux

    }


}
