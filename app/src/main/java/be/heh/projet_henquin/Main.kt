package be.heh.projet_henquin

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.room.Room
import be.heh.projet_henquin.db.AppDatabase
import be.heh.projet_henquin.model.User

class Main : Activity() {
    private var userList : List<User> ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        var mListView = findViewById<ListView>(R.id.userListView)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "bdd"
        ).build()
        val dao = db.userDao()
        userList = dao.getAll()

        val arrayAdapter: ArrayAdapter<*>
        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, userList!!)
        mListView.adapter = arrayAdapter

    }
}