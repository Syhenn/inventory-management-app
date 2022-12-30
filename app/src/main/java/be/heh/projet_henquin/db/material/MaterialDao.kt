package be.heh.projet_henquin.db.material

import androidx.room.*

@Dao
interface MaterialDao {
    @Query("SELECT * FROM material")
    fun getAll(): List<MaterialRecord>

    @Query("SELECT * FROM material WHERE ref = :ref")
    fun getByRef(ref : String): MaterialRecord

    @Insert
    fun insertMaterial(vararg listCategories: MaterialRecord)

    @Update
    fun updateMaterial(material : MaterialRecord)

    @Delete
    fun deleteMaterial(user: MaterialRecord)
}