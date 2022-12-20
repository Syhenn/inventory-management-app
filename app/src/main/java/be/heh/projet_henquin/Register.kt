package be.heh.projet_henquin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.EditText

import android.widget.Toast
import androidx.room.Room
import be.heh.projet_henquin.db.AppDatabase
import be.heh.projet_henquin.model.User


class Register : Activity(){

    private var textMail : EditText? = null
    private var textPassword : EditText? = null
    private var textRepassword : EditText? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        if(BuildConfig.DEBUG)
            StrictMode.enableDefaults();
        this.textMail = findViewById<View>(R.id.user_mail) as EditText
        this.textPassword = findViewById<View>(R.id.user_password) as EditText
        this.textRepassword = findViewById<View>(R.id.user_repassword) as EditText

    }

    fun toLoginClick(v : View) {
        val toLoginIntent = Intent(this, Login::class.java)
        startActivity(toLoginIntent)
    }
    fun buttonRegisterClicked(v : View) {
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
            val u1 = User(0, userMail, userPassword)
            try{
                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "bdd"
                ).build()
                val dao = db.userDao()
                dao.insertUser(u1)
                val toMain = Intent(this, Main::class.java)
                startActivity(toMain)
            }catch (e : Error){
                System.out.println(e)
            }


        }

    }
}