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
import be.heh.projet_henquin.materialPage.AddMaterial
import be.heh.projet_henquin.materialPage.MaterialDetail
import be.heh.projet_henquin.materialPage.MaterialListAdapter
import be.heh.projet_henquin.materialPage.ScanMaterial
import be.heh.projet_henquin.userPage.UserList


class Main : Activity() {
    private var userMailTextView: TextView? = null
    private var userListButton: Button? = null
    private var user: UserRecord? = null
    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var materialDao: MaterialDao? = null
    private var materialList: List<MaterialRecord>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        this.dataBaseConstruct()
        userMailTextView = findViewById<View>(R.id.userMail) as TextView
        userMailTextView!!.text = INTENT_USER_MAIL
        userListButton = findViewById<View>(R.id.userListButton) as Button

        this.user = INTENT_USER_MAIL?.let { this.userDao?.findByEmail(it) }

        Log.i("User information", this.user.toString())
        //User list for admin
        if (user?.isAdmin == true) {
            val params: LinearLayout.LayoutParams =
                userListButton!!.layoutParams as LinearLayout.LayoutParams
            userListButton!!.layoutParams = params
            params.width = 470
            userListButton!!.visibility = View.VISIBLE
            userListButton!!.isClickable = true
        }

        materialList = materialDao?.getAll()
        materialList?.let { materialView(it) }

    }

    fun toAddMaterial(v: View) {
        val toAddMaterial = AddMaterial.addMaterialIntent(this, INTENT_USER_MAIL.toString())
        startActivity(toAddMaterial)
    }

    fun toScanMaterial(v: View) {
        val toScanMaterial = ScanMaterial.newIntent(this, INTENT_USER_MAIL.toString())
        startActivity(toScanMaterial)
    }

    fun toUserList(v: View) {
        val toUserList = Intent(this, UserList::class.java)
        startActivity(toUserList)
    }

    fun dataBaseConstruct() {
        this.db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "MyDataBase"
        ).allowMainThreadQueries().build()
        this.userDao = db?.userDao()
        this.materialDao = db?.materialDao()

    }

    fun materialView(materialRecordList: List<MaterialRecord>) {
        var mListView = findViewById<ListView>(R.id.listMaterialItem)
        mListView.isClickable = true
        mListView.adapter =
            MaterialListAdapter(this, materialRecordList as ArrayList<MaterialRecord>)
        mListView.setOnItemClickListener { adapterView, view, position, l ->
            var selectedItem: MaterialRecord? = null
            selectedItem = mListView.adapter.getItem(position) as MaterialRecord

            val toMaterialDetail =
                user?.let { MaterialDetail.newIntent(this, selectedItem.ref, it.email) }
            startActivity(toMaterialDetail)

        }

    }

    companion object {
        private var INTENT_USER_MAIL: String? = null
        fun newIntent(context: Context, userMail: String): Intent {
            val intent = Intent(context, Main::class.java)
            intent.putExtra(INTENT_USER_MAIL, userMail)
            this.INTENT_USER_MAIL = userMail
            return intent
        }
    }
}