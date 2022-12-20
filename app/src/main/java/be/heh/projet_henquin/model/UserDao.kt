package be.heh.projet_henquin.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    /*
    @Query("SELECT * FROM user WHERE email LIKE :user_email "+"LIMIT 1")
    fun findByEmail(first: String): User*/

    @Insert
    fun insertUser(vararg listCategories: User)

    @Delete
    fun delete(user: User)
}