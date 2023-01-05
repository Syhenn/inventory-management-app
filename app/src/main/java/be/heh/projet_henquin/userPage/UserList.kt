package be.heh.projet_henquin.userPage

import android.app.Activity
import android.os.Bundle

import android.widget.ListView
import androidx.room.Room
import be.heh.projet_henquin.R
import be.heh.projet_henquin.db.AppDatabase
import be.heh.projet_henquin.db.user.UserDao
import be.heh.projet_henquin.db.user.UserRecord

class UserList : Activity() {
    private var userList: List<UserRecord>? = null
    private var db: AppDatabase? = null
    private var dao: UserDao? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.userlist)

        this.db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "MyDataBase"
        ).allowMainThreadQueries().build()
        this.dao = db?.userDao()
        userList = this.dao?.getAll()
        userList?.let { userListView(it) }

    }

    fun userListView(userList: List<UserRecord>) {
        var mListView = findViewById<ListView>(R.id.listUserItem)
        mListView.adapter = UserListAdapter(this, userList as ArrayList<UserRecord>)
    }
}