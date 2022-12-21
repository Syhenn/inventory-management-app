package be.heh.projet_henquin

import android.app.Activity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.room.Room
import be.heh.projet_henquin.db.AppDatabase
import be.heh.projet_henquin.db.UserRecord

class Main : Activity() {
    private var userList : List<UserRecord> ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "MyDataBase"
        ).allowMainThreadQueries().build()
        val dao = db.userDao()
        val liste = dao.getAll()
        liste.forEach { item -> Log.i("READ", item.toString())}
        var mListView = findViewById<ListView>(R.id.userListView)
        var arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, liste)
        mListView.adapter = arrayAdapter


        }
}