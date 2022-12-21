package be.heh.projet_henquin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText

import android.widget.Toast
import androidx.room.Room
import be.heh.projet_henquin.db.AppDatabase
import be.heh.projet_henquin.db.user.User
import be.heh.projet_henquin.db.user.UserDao
import be.heh.projet_henquin.db.user.UserRecord


class Register : Activity(){

    private var textMail : EditText? = null
    private var textPassword : EditText? = null
    private var textRepassword : EditText? = null
    private var db:AppDatabase?=null
    private var dao: UserDao?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        this.textMail = findViewById<View>(R.id.user_mail) as EditText
        this.textPassword = findViewById<View>(R.id.user_password) as EditText
        this.textRepassword = findViewById<View>(R.id.user_repassword) as EditText

        this.db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "MyDataBase"
        ).allowMainThreadQueries().build()
        this.dao = db?.userDao()


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

                    val userList = this.dao?.getAll()
                    var userExist = false
                    if (userList != null) {
                        for(user in userList){
                            if(user.email == userMail){
                                userExist = true
                            }
                        }
                    }
                    if(userExist)Toast.makeText(this, "This account already exist.", Toast.LENGTH_LONG).show()
                    else{
                        val u = User(0, userMail, userPassword, false)
                        val u1 = UserRecord(0, u.email, u.password,false)
                        Log.i("User to create" , u1.toString())
                        this.dao?.insertUser(u1)
                        Toast.makeText(this,"User has been successfully created.",Toast.LENGTH_LONG).show()
                        val toMain = Intent(this, Main::class.java)
                        val intent = Main.newIntent(this, userMail)
                        startActivity(intent)
                    }

                }
            }

        }

}