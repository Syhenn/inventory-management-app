package be.heh.projet_henquin.db

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory


class UserBddSqlite(
    context: Context?,
    name: String?,
    factory: CursorFactory?,
    version: Int?
) : SQLiteOpenHelper(context, name, factory, version!!) {
    private val TABLE_USER = "TABLE_USER"
    private val COL_ID = "ID"
    private val COL_PASSWORD = "PASSWORD"
    private val COL_EMAIL = "EMAIL"

    private val CREATE_BDD = "CREATE TABLE " +
            TABLE_USER + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_PASSWORD + " TEXT NOT NULL, " + COL_EMAIL + " TEXT NOT NULL);"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_BDD)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//Dans cette méthode, vous devez gérer les révisions de version de votre base de données.
        db.execSQL("DROP TABLE $TABLE_USER")
        onCreate(db)
    }


}