package be.heh.projet_henquin.db.user

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<UserRecord>


    @Query("SELECT * FROM user WHERE email = :user_email "+"LIMIT 1")
    fun findByEmail(user_email: String): UserRecord

    @Insert
    fun insertUser(vararg listCategories: UserRecord)

    @Delete
    fun delete(user: UserRecord)
}