package be.heh.projet_henquin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.EditText

import android.widget.Toast
import androidx.room.Room
import be.heh.projet_henquin.db.AppDatabase
import be.heh.projet_henquin.db.User
import be.heh.projet_henquin.db.UserRecord


class Register : Activity(){

    private var textMail : EditText? = null
    private var textPassword : EditText? = null
    private var textRepassword : EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        /*
        if(BuildConfig.DEBUG)
            StrictMode.enableDefaults();*/
        this.textMail = findViewById<View>(R.id.user_mail) as EditText
        this.textPassword = findViewById<View>(R.id.user_password) as EditText
        this.textRepassword = findViewById<View>(R.id.user_repassword) as EditText

        Log.i("test", "test")

    }

    fun toLoginClick(v : View) {
        val toLoginIntent = Intent(this, Login::class.java)
        startActivity(toLoginIntent)
    }
    fun buttonRegisterClicked(v: View ) {
            val userMail = this.textMail?.text.toString()
            val userPassword = this.textPassword?.text.toString()
            val userRepassword = this.textRepassword?.text.toString()
            if(userMail == "" || userPassword == "" || userRepassword == ""){
                Toast.makeText(this, "Please complete all fields.", Toast.LENGTH_LONG).show()
            }else {
                if(userPassword != userRepassword){
                    Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_LONG).show()
                }else{
                    val u = User(0, userMail, userPassword )
                    val db = Room.databaseBuilder(
                        applicationContext,
                        AppDatabase::class.java, "MyDataBase"
                    ).allowMainThreadQueries().build()
                    val dao = db.userDao()
                    val u1 = UserRecord(0, u.email, u.password,)
                    dao.insertUser(u1)
                    Toast.makeText(this,"User has been successfully created.",Toast.LENGTH_LONG).show()
                    val toMain = Intent(this, Main::class.java)
                    startActivity(toMain)
                }
            }

        }

}