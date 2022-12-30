package be.heh.projet_henquin.materialPage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.room.Room
import be.heh.projet_henquin.R
import be.heh.projet_henquin.db.AppDatabase
import be.heh.projet_henquin.db.material.MaterialDao
import be.heh.projet_henquin.db.material.MaterialRecord

class MaterialDetail : Activity() {
    private var textRef: TextView?=null
    private var textModel: TextView?=null
    private var textType: TextView?=null
    private var textLink: TextView?=null
    private var textIsAvailable: TextView?=null
    private var qrCodeImg: ImageView?=null
    private var db: AppDatabase?=null
    private var materialDao : MaterialDao?= null
    private var material : MaterialRecord?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.materialdetail)
        textRef = findViewById<View>(R.id.text_ref) as TextView
        textModel = findViewById<View>(R.id.text_model) as TextView
        textType = findViewById<View>(R.id.text_type) as TextView
        textLink = findViewById<View>(R.id.text_link) as TextView
        qrCodeImg = findViewById<ImageView>(R.id.image_view_qr_code) as ImageView


        this.db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "MyDataBase"
        ).allowMainThreadQueries().build()
        this.materialDao = db?.materialDao()
        material = intent_ref?.let { this.materialDao?.getByRef(it) }

        textRef!!.setText(material?.ref )
        textModel!!.setText(material?.model )
        textType!!.setText(material?.type )
        textLink!!.setText(material?.link )

        val bitmap = material?.qrCode?.let { BitmapFactory.decodeByteArray(it  , 0, it.size) }
        qrCodeImg!!.setImageBitmap(bitmap)


        intent_ref?.let { Log.i("material detail", it) }
    }

    companion object {

        private var intent_ref : String ?= null
        private var intent_qrCode : ImageView ?= null
        fun newIntent(context: Context, ref: String): Intent {
            val intent = Intent(context, MaterialDetail::class.java)
            intent.putExtra(intent_ref, ref)
            this.intent_ref = ref
            return intent
        }
    }
}