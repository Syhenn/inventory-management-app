package be.heh.projet_henquin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import be.heh.projet_henquin.db.material.MaterialDao
import be.heh.projet_henquin.db.material.MaterialRecord
import be.heh.projet_henquin.db.user.UserDao
import be.heh.projet_henquin.db.user.UserRecord

@Database(entities = [UserRecord::class, MaterialRecord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun materialDao(): MaterialDao
}