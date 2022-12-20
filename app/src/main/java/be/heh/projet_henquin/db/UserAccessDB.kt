package be.heh.projet_henquin.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.database.Cursor

import be.heh.projet_henquin.model.User
import be.heh.projet_henquin.model.User
import be.heh.projet_henquin.model.User
import java.util.*


class UserAccessDB {
    private var db: SQLiteDatabase? = null
    private var userdb: UserBddSqlite? = null
    fun UserAccessBDD(c: Context?) {
        userdb = UserBddSqlite(c, NAME_DB, null, VERSION)
    }

    fun openForWrite() {
        db = userdb!!.writableDatabase
    }

    fun openForRead() {
        db = userdb!!.readableDatabase
    }

    fun Close() {
        db!!.close()
    }
    fun insertUser(u: User): Long {
        val content = ContentValues()
        content.put(COL_PASSWORD, u.getUserEmail())
        content.put(COL_EMAIL, u.getUserPassword())
        return db!!.insert(TABLE_USER, null, content)
    }
    fun updateUser(i: Int, u: User): Int {
        val content = ContentValues()
        content.put(COL_PASSWORD, u.getUserPassword())
        content.put(COL_EMAIL, u.getUserEmail())
        return db!!.update(TABLE_USER, content, "$COL_ID = $i", null)
    }
    fun removeUser(id: Int): Int {
        return db!!.delete(
            TABLE_USER, COL_ID.toString() + " = " +
                    id, null
        )
    }
    fun getAllUser(): ArrayList<User?>? {
        val c: Cursor = db!!.query(
            TABLE_USER, arrayOf(
                COL_ID,  COL_EMAIL, COL_PASSWORD
            ),
            null, null, null, null
        )
        val tabUser: ArrayList<User?> = ArrayList<User>()
        if (c.getCount() === 0) {
            c.close()
            return tabUser
        }
        while (c.moveToNext()) {
            val user1 = User()
            user1.setUserId(c.getInt(NUM_COL_ID))
            user1.setUserEmail(c.getString(NUM_COL_EMAIL))
            user1.setUserPassword(c.getString(NUM_COL_PASSWORD))
            tabUser.add(user1)
        }
        c.close()
        return tabUser
    }
    companion object {
        private const val VERSION = 1
        private const val NAME_DB = "User.db"
        private const val TABLE_USER = "table_user"
        private const val COL_ID = "ID"
        private const val NUM_COL_ID = 0
        private const val COL_PASSWORD = "EMAIL"
        private const val NUM_COL_PASSWORD = 1
        private const val COL_EMAIL = "PASSWORD"
        private const val NUM_COL_EMAIL = 2
    }
}