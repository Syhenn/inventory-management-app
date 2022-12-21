package be.heh.projet_henquin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.room.Room
import be.heh.projet_henquin.db.AppDatabase
import be.heh.projet_henquin.db.user.UserRecord

class Main: Activity() {
    private var userList: List<UserRecord>? = null
    private var userMailTextView  : TextView ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        userMailTextView = findViewById<View>(R.id.userMail) as TextView
        userMailTextView!!.text = INTENT_USER_MAIL


    }

    fun toAddMaterial(v: View) {
        val toAddMaterial = Intent(this, AddMaterial::class.java)
        startActivity(toAddMaterial)
    }
    companion object {

        private var INTENT_USER_MAIL : String ?= null

        fun newIntent(context: Context, userMail: String): Intent {
            val intent = Intent(context, Main::class.java)
            intent.putExtra(INTENT_USER_MAIL, userMail)
            this.INTENT_USER_MAIL = userMail
            return intent
        }
    }
}