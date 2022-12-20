package be.heh.projet_henquin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name="id") @PrimaryKey(autoGenerate = true) val id: Int=0,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "password") val password: String?
)
