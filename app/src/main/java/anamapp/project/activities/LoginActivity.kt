package anamapp.project.activities

import anamapp.project.R
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.IllegalStateException
import android.widget.ProgressBar
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mAuth: FirebaseAuth

    override fun onClick(v: View?) {
        if (v == login_button_enter) {

            try {

                signIn(login_edit_text_login.text.toString(), login_edit_text_password.text.toString())
            }catch(e: IllegalStateException) {
                signIn()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        login_button_enter.setOnClickListener(this)

        text_field_sign_up.setOnClickListener {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }


        mAuth = FirebaseAuth.getInstance()
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
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


        progressBar.visibility = View.VISIBLE

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    //updateUI(user)
                    login_edit_text_login.setText("")
                    login_edit_text_password.setText("")
                    val intent = Intent(applicationContext, MenuActivity::class.java)

                    progressBar.visibility = View.GONE
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
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
            if(password.equals("")) {
                login_edit_text_password.setError(getString(R.string.cannot_be_empty))
                login_edit_text_password.requestFocus()
            }

        }else {
            aux = true
        }



        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || !aux) {
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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
