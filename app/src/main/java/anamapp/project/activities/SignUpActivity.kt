package anamapp.project.activities

import anamapp.project.R
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.lang.IllegalStateException
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var mAuth: FirebaseAuth
    override fun onClick(v: View?) {
        if (v == btn_signUp_create) {
            try {
                createAccount(
                    edit_text_email_signup.text.toString(), edit_text_password_signup.text.toString(),
                    edit_text_confirm_password_signup.text.toString()
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
    }


    private fun createAccount(email: String = "", password: String = "", confirmPass: String= "") {
        if (!validateForm(email, password, confirmPass)) {
            return
        }


        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    edit_text_confirm_password_signup.setText("")
                    edit_text_email_signup.setText("")
                    edit_text_password_signup.setText("")
                    val intent = Intent(applicationContext, MenuActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    edit_text_confirm_password_signup.setText("")
                    edit_text_email_signup.setText("")
                    edit_text_password_signup.setText("")
                    Toast.makeText(
                        baseContext, getString(R.string.authentication_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                    //updateUI(null)
                }


            }
        // [END create_user_with_email]
    }


    fun validateForm(email: String, password: String, confirmPass: String): Boolean {
        var aux: Boolean = false

        if (email.equals("") || password.equals("") || confirmPass.equals("")) {
            return aux
        } else if (password.equals(confirmPass)) {
            aux = true
        }


        val p = Pattern.compile(".+@.+\\.[a-z]+")
        val m = p.matcher(email)
        val matchFound = m.matches()

        if (!matchFound || !aux) {

            Toast.makeText(this, getString(R.string.email_or_password_invalids), Toast.LENGTH_LONG).show()
            aux = false
        } else {
            aux = true
        }

        return aux

    }


}
