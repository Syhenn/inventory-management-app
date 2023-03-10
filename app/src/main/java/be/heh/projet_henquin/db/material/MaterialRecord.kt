package be.heh.projet_henquin.db.material

import android.graphics.Bitmap
import androidx.room.*

@Entity(tableName = "material")
data class MaterialRecord(
    @ColumnInfo(name="id") @PrimaryKey(autoGenerate = true) var id: Int=0,
    @ColumnInfo(name = "type") var type: String = "null",
    @ColumnInfo(name = "model") var model: String,
    @ColumnInfo(name = "ref") var ref: String,
    @ColumnInfo(name = "link") var link: String,
    @ColumnInfo(name="qrCode") var qrCode: ByteArray,
    @ColumnInfo(name="isAvailable") var isAvailable: Boolean,
    @ColumnInfo(name = "createdBy") var createdBy: String
)