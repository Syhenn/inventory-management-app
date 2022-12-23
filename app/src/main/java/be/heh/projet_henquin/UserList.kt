package be.heh.projet_henquin

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.room.Room
import be.heh.projet_henquin.db.AppDatabase
import be.heh.projet_henquin.db.user.UserDao
import be.heh.projet_henquin.db.user.UserRecord

class UserList : Activity() {
    private var userList: List<UserRecord>? = null
    private var db: AppDatabase?=null
    private var dao: UserDao?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.userlist)

        this.db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "MyDataBase"
        ).allowMainThreadQueries().build()
        this.dao = db?.userDao()
        userList = this.dao?.getAll()

        val arrayAdapter: ArrayAdapter<*>
        var mListView = findViewById<ListView>(R.id.userListView)
        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, arrayOf(userList))
        mListView.adapter = arrayAdapter

    }
}