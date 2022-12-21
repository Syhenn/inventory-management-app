package be.heh.projet_henquin.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserRecord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}