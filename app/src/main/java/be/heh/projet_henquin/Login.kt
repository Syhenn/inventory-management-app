package be.heh.projet_henquin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import be.heh.projet_henquin.db.AppDatabase
import be.heh.projet_henquin.db.user.UserDao
import be.heh.projet_henquin.db.user.UserRecord

class Login : AppCompatActivity() {

    private var textMail:EditText?=null
    private var textPassword:EditText?=null
    private var db:AppDatabase?=null
    private var dao: UserDao?=null

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
        val u1 = UserRecord(0, userMail, userPassword,false)
        if(userMail == "" || userPassword == ""){
            Toast.makeText(this, "Please complete all fields.", Toast.LENGTH_LONG).show()
        }else{

            val userList = dao?.getAll()

            if (userList != null) {
                for(user in userList){
                    if(user.email == userMail){
                        if(user.password == userPassword){
                            val toMain = Intent(this, Main::class.java)
                            val intent = Main.newIntent(this, userMail)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, "Wrong password.", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

        }

    }

    fun toRegisterClick(v : View){
        val toRegisterActivity = Intent(this, Register::class.java)
        startActivity(toRegisterActivity)
    }
}