package be.heh.projet_henquin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import be.heh.projet_henquin.db.AppDatabase
import be.heh.projet_henquin.db.User
import be.heh.projet_henquin.db.UserDao
import be.heh.projet_henquin.db.UserRecord

class Login : AppCompatActivity() {

    private var textMail:EditText?=null
    private var textPassword:EditText?=null
    private var db:AppDatabase?=null
    private var dao:UserDao?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        textMail = findViewById<View>(R.id.userMailId) as EditText
        textPassword = findViewById<View>(R.id.userPwdId) as EditText
        this.db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "MyDataBase"
        ).allowMainThreadQueries().build()
        this.dao = db?.userDao()

    }

    fun onLoginClick(v : View){
        val userMail = this.textMail?.text.toString()
        val userPassword = this.textPassword?.text.toString()
        if(userMail == "" || userPassword == ""){
            Toast.makeText(this, "Please complete all fields.", Toast.LENGTH_LONG).show()
        }else{

            val userList = dao?.getAll()
            val userEntry = UserRecord(0, userMail, userPassword)
            if(userList!!.contains(userEntry)){
                val user = dao?.findByEmail(userMail)
                if(user!!.password == userPassword){
                    val toMain = Intent(this, Main::class.java)
                    startActivity(toMain)
                }else{
                    Toast.makeText(this, "Bad password.", Toast.LENGTH_LONG).show()
                }
            }else {
                Toast.makeText(this, "This account don't exist.", Toast.LENGTH_LONG).show()
            }


        }

    }

    fun toRegisterClick(v : View){
        val toRegisterActivity = Intent(this, Register::class.java)
        startActivity(toRegisterActivity)
    }
}