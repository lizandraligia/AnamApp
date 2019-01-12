package anamapp.project.activities

import anamapp.project.R
import anamapp.project.bean.Auxiliar
import anamapp.project.bean.Hospital
import anamapp.project.bean.Nurse
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_register_new_employee.*
import kotlinx.android.synthetic.main.activity_register_patient.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.lang.IllegalStateException

class registerNewEmployeeActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var mAuth: FirebaseAuth
    lateinit var mAuthLogged: FirebaseAuth
    lateinit var databaseHospital: DatabaseReference
    lateinit var databaseNurse: DatabaseReference
    lateinit var query: Query

    lateinit var bitmap: Bitmap

    var auxBool = false
    var list: ArrayList<Hospital> = ArrayList<Hospital>()

    lateinit var uriProfileImage: Uri


    companion object {
        var ID = ""
        var REQUEST_CODE = 204
    }

    override fun onClick(v: View?) {
        if (v == reg_nurse_button) {
            try {
                createAccount(
                    reg_nurse_email.text.toString(),
                    reg_nurse_pass.text.toString(),
                    reg_nurse_confirmPass.text.toString(),
                    reg_nurse_coren.text.toString(),
                    reg_nurse_name.text.toString()
                )
            } catch (e: Exception) {
                createAccount()
            }
        } else if (v == imageChooser) {
            showImageChooser()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_new_employee)


        menu_image_view_back6.setOnClickListener {
            finish()
        }

        reg_nurse_button.setOnClickListener(this)

        imageChooser.setOnClickListener(this)



        databaseHospital = FirebaseDatabase.getInstance().getReference("hospital")

        databaseHospital.addListenerForSingleValueEvent(listenerVar)


        mAuth = FirebaseAuth.getInstance()

        mAuthLogged = mAuth


    }

    var listenerVar = (object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {

            list.clear()
            for (hospitalSnapshot in dataSnapshot.children) {
                var hospital = hospitalSnapshot.getValue(Hospital::class.java)
                list.add(hospital!!)

                var email = ""
                val user = mAuthLogged.currentUser
                user?.let {
                    email = user.email!!
                }
                for (hospital in list) {
                    if (hospital.email == email) {
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


    public override fun onStart() {
        super.onStart()

        // Add value event listener to the post
        // [START post_value_event_listener]



    }

    override fun onDestroy() {
        super.onDestroy()

        databaseHospital.removeEventListener(listenerVar)
    }
    private fun addNurses() {

        var email = ""
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            email = user.email!!

        }


        val id: String = databaseNurse.push().key!!
        val nurse: Nurse = Nurse(
            id, reg_nurse_name.text.toString(), reg_nurse_coren.text.toString(),
            email, Auxiliar.BitMapToString(bitmap)
        )

        databaseNurse.child(id).setValue(nurse)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data!!.data != null && data != null) {
            uriProfileImage = data!!.data


            val stream = ByteArrayOutputStream()
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uriProfileImage)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream)

                val byteArray = stream.toByteArray()
                val compressedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

                bitmap = compressedBitmap
                imageChooser.setImageBitmap(bitmap)
                auxBool = true
            } catch (e: IOException) {

            }
        }
    }

    fun showImageChooser() {
        intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE)
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
                if(auxBool == false) {
                    val stream = ByteArrayOutputStream()
                    bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.avatar)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream)
                    val byteArray = stream.toByteArray()
                    val compressedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    bitmap = compressedBitmap
                }else {
                    auxBool = false
                    addNurses()
                }

                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = mAuth.currentUser
                    if (saveNurseInformation(user!!)) {


                        Toast.makeText(
                            baseContext, getString(R.string.authentication_sucessfull),
                            Toast.LENGTH_LONG
                        ).show()

                        reg_nurse_confirmPass.setText("")
                        reg_nurse_pass.setText("")
                        reg_nurse_name.setText("")
                        reg_nurse_coren.setText("")
                        reg_nurse_email.setText("")

                    } else {
                        Toast.makeText(
                            baseContext, getString(R.string.authentication_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                    val stream = ByteArrayOutputStream()

                    progressBar3.visibility = View.GONE
                    var bitmap2 = BitmapFactory.decodeResource(applicationContext.resources, R.mipmap.camera_icon)

                    imageChooser.setImageBitmap(bitmap2)

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

                    val stream = ByteArrayOutputStream()

                    progressBar3.visibility = View.GONE

                    var bitmap2 = BitmapFactory.decodeResource(applicationContext.resources, R.mipmap.camera_icon)

                    imageChooser.setImageBitmap(bitmap2)
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
            if (name.equals("")) {
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
