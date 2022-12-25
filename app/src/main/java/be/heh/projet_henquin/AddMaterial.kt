package be.heh.projet_henquin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import be.heh.projet_henquin.db.AppDatabase
import be.heh.projet_henquin.db.material.Material
import be.heh.projet_henquin.db.material.MaterialDao
import be.heh.projet_henquin.db.material.MaterialRecord
import be.heh.projet_henquin.db.user.UserRecord
import kotlinx.android.synthetic.main.main.*

class AddMaterial : Activity (){
    private var textType : EditText? = null
    private var textModel : EditText? = null
    private var textRef : EditText? = null
    private var textLink : EditText? = null
    private var db: AppDatabase?=null
    private var dao: MaterialDao?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addmaterial)

        this.textType = findViewById<View>(R.id.type) as EditText
        this.textModel = findViewById<View>(R.id.model) as EditText
        this.textRef = findViewById<View>(R.id.ref) as EditText
        this.textLink = findViewById<View>(R.id.link) as EditText

        this.db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "MyDataBase"
        ).allowMainThreadQueries().build()
        this.dao = db?.materialDao()
        Log.i("User Mail : ", INTENT_USER_MAIL.toString())

    }
    fun addMaterialClicked(v : View){
        val materialType = this.textType?.text.toString()
        val materialModel = this.textModel?.text.toString()
        val materialRef = this.textRef?.text.toString()
        val materialLink = this.textLink?.text.toString()

        if(materialType == "" || materialModel == "" || materialRef == "" || materialLink == ""){
            Toast.makeText(this, "Please complete all fields.", Toast.LENGTH_LONG).show()
        }else {
            val m = Material(0, materialType, materialModel, materialRef, materialLink, INTENT_USER_MAIL.toString())
            val m1 = MaterialRecord(0, m.type, m.model, m.ref, m.link, m.createdBy)
            this.dao?.insertMaterial(m1)
            Toast.makeText(this, "Material created with success.", Toast.LENGTH_LONG).show()
            val intent = Main.newIntent(this, INTENT_USER_MAIL.toString())
            startActivity(intent)


        }

    }
    companion object {

        private var INTENT_USER_MAIL : String ?= null

        fun addMaterialIntent(context: Context, userMail: String): Intent {
            val intent = Intent(context, AddMaterial::class.java)
            intent.putExtra(INTENT_USER_MAIL, userMail)
            this.INTENT_USER_MAIL = userMail
            return intent
        }
    }
}