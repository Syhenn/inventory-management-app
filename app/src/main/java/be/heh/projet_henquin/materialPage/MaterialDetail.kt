package be.heh.projet_henquin.materialPage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.room.Room
import be.heh.projet_henquin.Main
import be.heh.projet_henquin.R
import be.heh.projet_henquin.db.AppDatabase
import be.heh.projet_henquin.db.material.MaterialDao
import be.heh.projet_henquin.db.material.MaterialRecord
import be.heh.projet_henquin.db.user.UserDao
import be.heh.projet_henquin.db.user.UserRecord
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.io.ByteArrayOutputStream

class MaterialDetail : Activity() {
    private var textRef: TextView? = null
    private var textModel: TextView? = null
    private var textType: TextView? = null
    private var textLink: TextView? = null
    private var typeEdit: EditText? = null
    private var modelEdit: EditText? = null
    private var refEdit: EditText? = null
    private var linkEdit: EditText? = null
    private var modifyButton: Button? = null
    private var deleteButton: Button? = null
    private var validateButton: Button? = null
    private var textIsAvailable: TextView? = null
    private var qrCodeImg: ImageView? = null
    private var db: AppDatabase? = null
    private var materialDao: MaterialDao? = null
    private var userDao: UserDao? = null
    private var material: MaterialRecord? = null
    private var user: UserRecord? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.materialdetail)
        textRef = findViewById<View>(R.id.text_ref) as TextView
        textModel = findViewById<View>(R.id.text_model) as TextView
        textType = findViewById<View>(R.id.text_type) as TextView
        textLink = findViewById<View>(R.id.text_link) as TextView
        qrCodeImg = findViewById<ImageView>(R.id.image_view_qr_code)
        modifyButton = findViewById<Button>(R.id.modifyButtonId)
        deleteButton = findViewById<Button>(R.id.deleteButtonId)
        validateButton = findViewById<Button>(R.id.validateButtonId)
        textIsAvailable = findViewById<View>(R.id.text_isAvailable) as TextView
        typeEdit = findViewById<EditText>(R.id.type)
        modelEdit = findViewById<EditText>(R.id.model)
        refEdit = findViewById<EditText>(R.id.ref)
        linkEdit = findViewById<EditText>(R.id.link)

        this.db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "MyDataBase"
        ).allowMainThreadQueries().build()
        this.materialDao = db?.materialDao()
        this.userDao = db?.userDao()
        this.user = intent_mail?.let { this.userDao?.findByEmail(it) }
        material = intent_ref?.let { this.materialDao?.getByRef(it) }

        textRef!!.text = material?.ref
        textModel!!.text = material?.model
        textType!!.text = material?.type
        textLink!!.text = material?.link
        if (material?.isAvailable == true) {
            textIsAvailable!!.text = "Disponible"
            textIsAvailable!!.setTextColor(Color.GREEN)
        } else {
            textIsAvailable!!.text = "Indisponible"
            textIsAvailable!!.setTextColor(Color.RED)
        }

        textIsAvailable!!.text = material?.isAvailable.toString()
        val bitmap = material?.qrCode?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
        qrCodeImg!!.setImageBitmap(bitmap)

        if (user?.isAdmin == true) {
            val params: LinearLayout.LayoutParams =
                modifyButton!!.layoutParams as LinearLayout.LayoutParams
            modifyButton!!.layoutParams = params
            deleteButton!!.layoutParams = params
            params.width = 470
            modifyButton!!.visibility = View.VISIBLE
            modifyButton!!.isClickable = true
            deleteButton!!.visibility = View.VISIBLE
            deleteButton!!.isClickable = true
        }

    }

    fun modifyButton(v: View) {
        typeEdit?.setText(textType!!.text)
        typeEdit?.visibility = View.VISIBLE
        textType?.visibility = View.GONE
        modelEdit?.setText(textModel!!.text)
        modelEdit?.visibility = View.VISIBLE
        textModel?.visibility = View.GONE
        refEdit?.setText(textRef!!.text)
        refEdit?.visibility = View.VISIBLE
        textRef?.visibility = View.GONE
        linkEdit?.setText(textLink!!.text)
        linkEdit?.visibility = View.VISIBLE
        textLink?.visibility = View.GONE
        textIsAvailable?.visibility = View.GONE
        modifyButton?.visibility = View.GONE
        val params: LinearLayout.LayoutParams =
            validateButton!!.layoutParams as LinearLayout.LayoutParams
        validateButton!!.layoutParams = params
        params.width = 470
        validateButton!!.visibility = View.VISIBLE
        validateButton!!.isClickable = true

    }

    fun validateButton(v: View) {

        val materialType = this.typeEdit?.text.toString()
        val materialModel = this.modelEdit?.text.toString()
        val materialRef = this.refEdit?.text.toString()
        val materialLink = this.linkEdit?.text.toString()
        val bitmap = image_view_qr_code(materialRef)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val imageBytes = outputStream.toByteArray()
        var materialUpdated = material?.let {
            MaterialRecord(
                it.id,
                materialType,
                materialModel,
                materialRef,
                materialLink,
                imageBytes,
                material!!.isAvailable,
                material!!.createdBy
            )
        }
        materialUpdated?.let { this.materialDao?.updateMaterial(it) }
        Toast.makeText(this, "Materiel modifié avec succès.", Toast.LENGTH_LONG).show()
        val intent = Main.newIntent(this, intent_mail.toString())
        startActivity(intent)
    }

    fun image_view_qr_code(ref: String): Bitmap {
        val data = ref
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 400, 400)
        val bitmap = toBitmap(bitMatrix)
        return bitmap
    }

    private fun toBitmap(matrix: BitMatrix): Bitmap {
        val height = matrix.height
        val width = matrix.width
        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (matrix[x, y]) Color.BLACK else Color.WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }

    fun deleteButton(v: View) {
        material?.let { this.materialDao?.deleteMaterial(it) }
        val toMain = Main.newIntent(this, intent_mail.toString())
        startActivity(toMain)
    }

    fun toLink(v: View) {
        val link = Intent(Intent.ACTION_VIEW, Uri.parse(textLink?.text.toString()))
        startActivity(link)
    }

    companion object {

        private var intent_ref: String? = null
        private var intent_mail: String? = null

        fun newIntent(context: Context, ref: String, userMail: String): Intent {
            val intent = Intent(context, MaterialDetail::class.java)
            intent.putExtra(intent_ref, ref)
            intent.putExtra(intent_mail, userMail)
            this.intent_mail = userMail
            this.intent_ref = ref
            return intent
        }
    }
}