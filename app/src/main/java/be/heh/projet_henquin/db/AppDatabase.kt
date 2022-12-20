package be.heh.projet_henquin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import be.heh.projet_henquin.model.User
import be.heh.projet_henquin.model.UserDao

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}