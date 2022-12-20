package be.heh.projet_henquin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    var userMail:EditText? = null
    var userPwd:EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userMail = findViewById<View>(R.id.userMailId) as EditText
        userPwd = findViewById<View>(R.id.userPwdId) as EditText
    }

    fun onLoginClick(v : View){
        when(v.getId()){

        }
    }

    fun toRegisterClick(v : View){
        val toRegisterActivity = Intent(this, Register::class.java)
        startActivity(toRegisterActivity)
    }
}