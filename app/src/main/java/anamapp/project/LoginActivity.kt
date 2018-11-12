package anamapp.project

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_enter.setOnClickListener {
            val intent = Intent(applicationContext, MenuActivity::class.java)
            startActivity(intent)
        }
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

}
