package be.heh.projet_henquin.db.material

import androidx.room.*

@Entity(tableName = "material")
class MaterialRecord (
    @ColumnInfo(name="id") @PrimaryKey(autoGenerate = true) var id: Int=0,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "model") var model: String,
    @ColumnInfo(name = "ref") var ref: String,
    @ColumnInfo(name = "link") var link: String
)