package be.heh.projet_henquin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.room.Room
import be.heh.projet_henquin.db.AppDatabase
import be.heh.projet_henquin.db.user.UserDao
import be.heh.projet_henquin.db.user.UserRecord

import android.widget.LinearLayout
import be.heh.projet_henquin.db.material.MaterialDao
import be.heh.projet_henquin.db.material.MaterialRecord
import android.widget.TextView


class Main: Activity() {
    private var userMailTextView  : TextView ?=null
    private var userListButton : Button ?= null
    private var user : UserRecord ?= null
    private var db:AppDatabase?=null
    private var userDao: UserDao?=null
    private var materialDao : MaterialDao?= null
    private var materialList : List<MaterialRecord>?= null
    private var linearLayout : LinearLayout ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        this.dataBaseConstruct()
        userMailTextView = findViewById<View>(R.id.userMail) as TextView
        userMailTextView!!.text = INTENT_USER_MAIL
        userListButton = findViewById<View>(R.id.userListButton) as Button

        this.user = INTENT_USER_MAIL?.let { this.userDao?.findByEmail(it) }


        Log.i("User information", this.user.toString())
        //Affichage de la liste utilisateur pour l'admin
        if(user?.isAdmin == true){
            val params: LinearLayout.LayoutParams = userListButton!!.getLayoutParams() as LinearLayout.LayoutParams
            userListButton!!.setLayoutParams(params)
            params.width = 300
            userListButton!!.setVisibility(View.VISIBLE)
            userListButton!!.setClickable(true)
        }

        materialList = materialDao?.getAll()
        for(material in materialList!!){
            this.materialView(material)
        }
        Log.i("Material list :", materialList.toString())


    }

    fun toAddMaterial(v : View) {
        val toAddMaterial = AddMaterial.addMaterialIntent(this, INTENT_USER_MAIL.toString())
        startActivity(toAddMaterial)
    }
    fun toUserList(v : View) {
        val toUserList = Intent(this, UserList::class.java)
        startActivity(toUserList)
    }

    fun dataBaseConstruct(){
        this.db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "MyDataBase"
        ).allowMainThreadQueries().build()
        this.userDao = db?.userDao()
        this.materialDao = db?.materialDao()

    }
    fun materialView(materialRecord: MaterialRecord){
        /*
        linearLayout = findViewById(R.id.materialView);
        var materialView = MaterialComponent(
            this,
            null,
            materialRecord.type,
            materialRecord.model,
            materialRecord.ref,
            materialRecord.link
        )
        with(linearLayout) {
            this?.addView(materialView)
        }

         */

        linearLayout = findViewById(R.id.materialView);
        val typeView = TextView(this)
        val modelView = TextView(this)
        val refView = TextView(this)
        val linkView = TextView(this)
        with(linearLayout) {
            typeView.setText("Type : " + materialRecord.type);
            modelView.setText("Model : " + materialRecord.model);
            refView.setText("Ref : " + materialRecord.ref);
            linkView.setText("Link : " + materialRecord.link);
            this?.addView(typeView)
            this?.addView(modelView)
            this?.addView(refView)
            this?.addView(linkView)
        }
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