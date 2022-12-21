package be.heh.projet_henquin.db.user

import androidx.room.*

@Entity(tableName = "user")
data class UserRecord(
    @ColumnInfo(name="id") @PrimaryKey(autoGenerate = true) var id: Int=0,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "isAdmin") var isAdmin: Boolean=false
)
