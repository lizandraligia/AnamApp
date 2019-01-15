package anamapp.project.activities

import anamapp.project.R
import anamapp.project.bean.App
import anamapp.project.bean.Hospital
import anamapp.project.bean.Nurse
import anamapp.project.bean.prefs
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.internal.FirebaseAppHelper.getUid
import com.google.firebase.auth.FirebaseUser
import android.support.annotation.NonNull
import com.google.firebase.auth.FirebaseAuthProvider
import com.google.firebase.database.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var mAuth: FirebaseAuth

    lateinit var query: Query

    var bool = false
    var bool2 = false

    override fun onClick(v: View?) {
/*
        val intent = Intent(applicationContext, MenuActivity::class.java)

        progressBar.visibility = View.GONE
        startActivity(intent)

        */
        if (v == login_button_enter) {
            try {

                query.addValueEventListener(valueEventListener)
                signIn(login_edit_text_login.text.toString(), login_edit_text_password.text.toString())
            } catch (e: IllegalStateException) {
                signIn()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        mAuth = FirebaseAuth.getInstance()

        login_button_enter.setOnClickListener(this)

        text_field_sign_up.setOnClickListener {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }

        mAuth.addAuthStateListener(authListener)
        bool = true


    }

    public override fun onStart() {
        super.onStart()
        if(!bool) {
            mAuth.addAuthStateListener(authListener)
        }
        val currentUser = mAuth.currentUser
        if (currentUser != null && intent.getBooleanExtra("Boolean", false)) {
            val intent = Intent(applicationContext, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    public override fun onRestart() {
        super.onRestart()
        signOut()
    }

    override fun onResume() {
        super.onResume()
        signOut()
        if(!bool) {
            mAuth.addAuthStateListener(authListener)
        }
    }


    override fun onPause() {
        super.onPause()
        bool = false
        bool2 = false
        mAuth.removeAuthStateListener(authListener)


    }

    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener(authListener)
        query.removeEventListener(valueEventListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        mAuth.removeAuthStateListener(authListener)
        query.removeEventListener(valueEventListener)
    }


    var valueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                for (nurseSnapshot in dataSnapshot.children) {
                    bool2 = true
                    var hospital = nurseSnapshot.getValue(Hospital::class.java)
                    prefs.hospitalName = hospital!!.name
                    prefs.address = hospital!!.address
                    prefs.city = hospital!!.city
                    prefs.state = hospital!!.state
                    println(bool2)


                }
            }else {

            }

        }

        override fun onCancelled(databaseError: DatabaseError) {
            //TODO
        }
    }


    var authListener: FirebaseAuth.AuthStateListener =
        FirebaseAuth.AuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser != null) {

                prefs.uid = firebaseUser!!.uid
                prefs.email = firebaseUser.email!!.toString()
            }

            query = FirebaseDatabase.getInstance().getReference("hospital").orderByChild("email").equalTo(prefs.email)
            query.addValueEventListener(valueEventListener)
        }


    /* Save data through rotations */
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        val login = login_edit_text_login.text.toString()
        val password = login_edit_text_password.text.toString()

        outState?.putString("login_edit_text_login", login)
        outState?.putString("login_edit_text_password", password)
    }

    /* Restore data through rotations */
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        val login = savedInstanceState?.getString("login_edit_text_login")
        val password = savedInstanceState?.getString("login_edit_text_password")

        login_edit_text_login.setText(login)
        login_edit_text_password.setText(password)
    }


    private fun signOut() {
        mAuth.signOut()

    }

    private fun signIn(email: String = "", password: String = "") {
        if (!validateForm(email, password)) {
            return
        }
        println("TESTE");
        println(bool2);


        progressBar.visibility = View.VISIBLE



        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser

                    //updateUI(user)
                    login_edit_text_login.setText("")
                    login_edit_text_password.setText("")

                    println("TESTE");
                    println(bool2);

                    if(prefs.hospitalName!= "") {
                        val intent = Intent(applicationContext, MenuActivity::class.java)
                        startActivity(intent)
                    }else {
                        val intent = Intent(applicationContext, MenuNurseActivity::class.java)
                        startActivity(intent)
                    }


                    progressBar.visibility = View.GONE



                } else {
                    // If sign in fails, display a message to the user.
                    bool2 = false
                    Toast.makeText(
                        baseContext, getString(R.string.authentication_failed),
                        Toast.LENGTH_SHORT
                    ).show()

                    progressBar.visibility = View.GONE

                    login_edit_text_login.setText("")
                    login_edit_text_password.setText("")
                    //updateUI(null)
                }


            }
        // [END sign_in_with_email]
    }

    fun validateForm(email: String, password: String): Boolean {
        var aux: Boolean = false

        if (password.equals("")) {
            if (password.equals("")) {
                login_edit_text_password.setError(getString(R.string.cannot_be_empty))
                login_edit_text_password.requestFocus()
            }

        } else {
            aux = true
        }



        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || !aux) {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                login_edit_text_login.setError(getString(R.string.invalid_email))
                login_edit_text_login.requestFocus()
            }
            aux = false
        } else {
            aux = true
        }

        return aux

    }


}
