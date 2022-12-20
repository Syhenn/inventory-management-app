package be.heh.projet_henquin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import be.heh.projet_henquin.model.User
import be.heh.projet_henquin.db.DataBaseHelper

import android.widget.Toast


class Register : Activity(){


    private var textMail : EditText? = null
    private var textPassword : EditText? = null
    private var textRepassword : EditText? = null
    private var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        this.textMail = findViewById<View>(R.id.user_mail) as EditText
        this.textPassword = findViewById<View>(R.id.user_password) as EditText
        this.textRepassword = findViewById<View>(R.id.user_repassword) as EditText

    }

    fun toLoginClick(v : View) {
        val toLoginIntent = Intent(this, MainActivity::class.java)
        startActivity(toLoginIntent)
    }
    fun buttonRegisterClicked(v : View) {
        val db = DataBaseHelper(this, null)
        val userMail: String = this.textMail?.getText().toString()
        val userPassword: String = this.textPassword?.getText().toString()
        val userRepassword: String = this.textRepassword?.getText().toString()
        if (userMail == "" || userPassword == "" || userRepassword == "") {
            Toast.makeText(
                applicationContext,
                "Veuillez remplir tout les champs", Toast.LENGTH_LONG
            ).show()
            return
        }else{
            this.user = User(userMail, userPassword)
            db.addName(user!!)
        }

    }
}