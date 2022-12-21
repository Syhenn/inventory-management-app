package be.heh.projet_henquin.db.material

import androidx.room.*

@Dao
interface MaterialDao {
    @Query("SELECT * FROM material")
    fun getAll(): List<MaterialRecord>

    @Insert
    fun insertMaterial(vararg listCategories: MaterialRecord)

    @Delete
    fun deleteMaterial(user: MaterialRecord)
}