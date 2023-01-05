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

    private var textMail: EditText? = null
    private var textPassword: EditText? = null
    private var db: AppDatabase? = null
    private var dao: UserDao? = null

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

    //Pour se login
    fun onLoginClick(v: View) {
        val userMail = this.textMail?.text.toString()
        val userPassword = this.textPassword?.text.toString()
        if (userMail == "" || userPassword == "") {
            Toast.makeText(this, "Veuillez remplir tous les champs.", Toast.LENGTH_LONG).show()
        } else {

            val userList = dao?.getAll()

            if (userList != null) {
                for (user in userList) {
                    if (user.email == userMail) {
                        if (user.password == userPassword) {
                            val intent = Main.newIntent(this, userMail)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Mot de passe incorrect.", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }

        }

    }

    //Vers le Register
    fun toRegisterClick(v: View) {
        val toRegisterActivity = Intent(this, Register::class.java)
        startActivity(toRegisterActivity)
    }
}