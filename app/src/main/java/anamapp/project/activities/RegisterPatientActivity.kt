package anamapp.project.activities

import anamapp.project.R
import anamapp.project.bean.Constant
import anamapp.project.bean.Hospital
import anamapp.project.bean.Patient
import anamapp.project.bean.prefs
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
//import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_register_patient.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream

class RegisterPatientActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mAuth: FirebaseAuth
    lateinit var mAuthLogged: FirebaseAuth
    lateinit var databaseHospital: DatabaseReference
    lateinit var databasePatient: DatabaseReference
    lateinit var query: Query


    var auxBool = false
    var list: ArrayList<Patient> = ArrayList<Patient>()

    //lateinit var uriProfileImage: Uri


    companion object {
        var ID = ""
        var REQUEST_CODE = 204
        var REQUEST_CODE_SYMPTOMS = 20
    }

    override fun onClick(v: View?) {

        if (v == register_patient_button_registrate) {

            val intent: Intent = Intent(applicationContext, SymptomsMenuActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SYMPTOMS)

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_patient)


        register_patient_button_registrate.setOnClickListener(this)

        register_patient_image_view_back.setOnClickListener {
            finish()
        }

        mAuth = FirebaseAuth.getInstance()
        databasePatient = FirebaseDatabase.getInstance().getReference("patient").child(prefs.uid)
        mAuthLogged = mAuth
    }


    public override fun onStart() {
        super.onStart()

        // Add value event listener to the post
        // [START post_value_event_listener]

    }

    override fun onDestroy() {
        super.onDestroy()

        //databaseHospital.removeEventListener(listenerVar)
    }

    private fun addPatient(user: FirebaseUser, jSon: String) {

        var name = ""
        var uid = ""
        //val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            name = user.displayName!!
            uid = user.uid
        }


        val id: String = databasePatient.push().key!!
        val patient: Patient = Patient(
            register_patient_edit_text_name.text.toString(),
            register_patient_edit_text_medical_record.text.toString(), jSon,
            id
        )

        databasePatient.child(id).setValue(patient)

    }

    private fun createAccount( // INSERIR METODO AQUI PARA CRIAR CONTA. O CODIGO DAQUI É PRA AUTENTICAÇÃO, PELO QUE ENTENDI
        medical_record: String = "",
        name: String = "",
        jSon: String = ""
    ) {
        if (!validateForm(medical_record, name)) {
            return
        }

        //progressBar.visibility = View.VISIBLE
        val user = mAuth.currentUser
        if (savePatientInformation(user!!)) {
            addPatient(user, jSon)

            Toast.makeText(
                baseContext, getString(R.string.authentication_sucessfull),
                Toast.LENGTH_LONG
            ).show()
            register_patient_edit_text_name.setText("")
            register_patient_edit_text_medical_record.setText("")

        } else {
            Toast.makeText(
                baseContext, getString(R.string.authentication_failed),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun validateForm(medical_record: String, name: String): Boolean {

        if (medical_record.equals("")) {
            register_patient_edit_text_medical_record.setError(getString(R.string.cannot_be_empty))
            register_patient_edit_text_medical_record.requestFocus()
        }
        if (name.equals("")) {
            register_patient_edit_text_name.setError(getString(R.string.cannot_be_empty))
            register_patient_edit_text_name.requestFocus()
        }
        return true

    }

    private fun savePatientInformation(user: FirebaseUser): Boolean {
        var bool = false

        if (user != null) {
            var profile = UserProfileChangeRequest.Builder()
                .setDisplayName(register_patient_edit_text_name.text.toString())
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SYMPTOMS && resultCode == Activity.RESULT_OK && data != null) {
            val jsonResult = data!!.getStringExtra(Constant.JSON_RESULT)


            try {
                createAccount(
                    register_patient_edit_text_name.text.toString(),
                    register_patient_edit_text_medical_record.text.toString(),
                    jsonResult
                )
            } catch (e: Exception) {
                createAccount()
            }

        } else {

            // TODO
            //Faz aqui alguma coisa para caso o cara não tenha concluido os sintomas e tal.


        }

    }
}



