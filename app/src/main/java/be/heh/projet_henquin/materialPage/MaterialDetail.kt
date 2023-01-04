package be.heh.projet_henquin.materialPage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.*
import androidx.room.Room
import be.heh.projet_henquin.Main
import be.heh.projet_henquin.R
import be.heh.projet_henquin.db.AppDatabase
import be.heh.projet_henquin.db.material.Material
import be.heh.projet_henquin.db.material.MaterialDao
import be.heh.projet_henquin.db.material.MaterialRecord
import be.heh.projet_henquin.db.user.UserDao
import be.heh.projet_henquin.db.user.UserRecord
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.main.*
import kotlinx.android.synthetic.main.materialdetail.*
import java.io.ByteArrayOutputStream

class MaterialDetail : Activity() {
    private var textRef: TextView?=null
    private var textModel: TextView?=null
    private var textType: TextView?=null
    private var textLink: TextView?=null
    private var typeEdit : EditText?=null
    private var modelEdit : EditText?=null
    private var refEdit : EditText?=null
    private var linkEdit : EditText?=null
    private var modifyButton : Button?=null
    private var deleteButton : Button?=null
    private var validateButton : Button?=null
    private var textIsAvailable: TextView?=null
    private var qrCodeImg: ImageView?=null
    private var db: AppDatabase?=null
    private var materialDao : MaterialDao?= null
    private var userDao: UserDao?=null
    private var material : MaterialRecord?= null
    private var user : UserRecord?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.materialdetail)
        textRef = findViewById<View>(R.id.text_ref) as TextView
        textModel = findViewById<View>(R.id.text_model) as TextView
        textType = findViewById<View>(R.id.text_type) as TextView
        textLink = findViewById<View>(R.id.text_link) as TextView
        qrCodeImg = findViewById(R.id.image_view_qr_code) as ImageView
        modifyButton = findViewById(R.id.modifyButtonId) as Button
        deleteButton = findViewById(R.id.deleteButtonId) as Button
        validateButton = findViewById(R.id.validateButtonId) as Button
        textIsAvailable = findViewById(R.id.text_isAvailable) as TextView
        typeEdit = findViewById(R.id.type) as EditText
        modelEdit = findViewById(R.id.model) as EditText
        refEdit = findViewById(R.id.ref) as EditText
        linkEdit = findViewById(R.id.link) as EditText

        this.db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "MyDataBase"
        ).allowMainThreadQueries().build()
        this.materialDao = db?.materialDao()
        this.userDao = db?.userDao()
        this.user = intent_mail?.let { this.userDao?.findByEmail(it) }
        material = intent_ref?.let { this.materialDao?.getByRef(it) }

        textRef!!.setText(material?.ref )
        textModel!!.setText(material?.model )
        textType!!.setText(material?.type )
        textLink!!.setText(material?.link )
        val bitmap = material?.qrCode?.let { BitmapFactory.decodeByteArray(it  , 0, it.size) }
        qrCodeImg!!.setImageBitmap(bitmap)

        if(user?.isAdmin == true){
            val params: LinearLayout.LayoutParams = modifyButton!!.getLayoutParams() as LinearLayout.LayoutParams
            modifyButton!!.setLayoutParams(params)
            deleteButton!!.setLayoutParams(params)
            params.width = 470
            modifyButton!!.setVisibility(View.VISIBLE)
            modifyButton!!.setClickable(true)
            deleteButton!!.setVisibility(View.VISIBLE)
            deleteButton!!.setClickable(true)
        }

    }
    fun modifyButton(v : View){
        typeEdit?.setVisibility(View.VISIBLE)
        textType?.setVisibility(View.GONE)
        modelEdit?.setVisibility(View.VISIBLE)
        textModel?.setVisibility(View.GONE)
        refEdit?.setVisibility(View.VISIBLE)
        textRef?.setVisibility(View.GONE)
        linkEdit?.setVisibility(View.VISIBLE)
        textLink?.setVisibility(View.GONE)
        textIsAvailable?.setVisibility(View.GONE)
        val params: LinearLayout.LayoutParams = validateButton!!.getLayoutParams() as LinearLayout.LayoutParams
        validateButton!!.setLayoutParams(params)
        params.width = 470
        validateButton!!.setVisibility(View.VISIBLE)
        validateButton!!.setClickable(true)
        typeEdit?.text ?: textType?.text.toString()
    }

    fun validateButton(v : View){

        val materialType = this.typeEdit?.text.toString()
        val materialModel = this.modelEdit?.text.toString()
        val materialRef = this.refEdit?.text.toString()
        val materialLink = this.linkEdit?.text.toString()
        val bitmap = image_view_qr_code(materialRef)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val imageBytes = outputStream.toByteArray()
        var materialUpdated = material?.let { MaterialRecord(it.id,materialType, materialModel, materialRef, materialLink, imageBytes, material!!.isAvailable, material!!.createdBy) }
        materialUpdated?.let { this.materialDao?.updateMaterial(it) }
        Toast.makeText(this, "Material created with success.", Toast.LENGTH_LONG).show()
        val intent = Main.newIntent(this, intent_mail.toString())
        startActivity(intent)
    }
    fun image_view_qr_code(ref : String): Bitmap {
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
    fun deleteButton(v : View) {
        material?.let { this.materialDao?.deleteMaterial(it) }
        val toMain = Main.newIntent(this, intent_mail.toString())
        startActivity(toMain)
    }

    fun toLink(v : View){
        val link = Intent(Intent.ACTION_VIEW, Uri.parse(textLink?.text.toString()))
        startActivity(link)
    }
    companion object {

        private var intent_ref : String ?= null
        private var intent_mail : String ?= null

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