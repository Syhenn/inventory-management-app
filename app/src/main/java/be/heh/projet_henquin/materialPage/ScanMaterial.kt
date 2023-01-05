package be.heh.projet_henquin.materialPage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import be.heh.projet_henquin.Main
import be.heh.projet_henquin.R
import be.heh.projet_henquin.db.AppDatabase
import be.heh.projet_henquin.db.material.MaterialDao
import com.google.zxing.integration.android.IntentIntegrator

class ScanMaterial : AppCompatActivity() {

    private var db: AppDatabase? = null
    private var materialDao: MaterialDao? = null
    private var textRef: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scanmaterial)

        textRef = findViewById<View>(R.id.materialRef) as EditText

        this.db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "MyDataBase"
        ).allowMainThreadQueries().build()
        this.materialDao = db?.materialDao()

        val integrator = IntentIntegrator(this)
        integrator.setOrientationLocked(false)
        integrator.initiateScan()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Scan manuel", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Data : ${result.contents}", Toast.LENGTH_LONG).show()
                val ref = result.contents
                val material = this.materialDao?.getByRef(ref)
                if (material != null) {
                    material.isAvailable = material.isAvailable != true
                    this.materialDao?.updateMaterial(material)
                    val toMain = Main.newIntent(this, INTENT_USER_MAIL.toString())
                    startActivity(toMain)
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun useMaterial(v: View) {

        val materialRef = this.textRef?.text.toString()
        val material = this.materialDao?.getByRef(materialRef)
        if (material != null) {
            material.isAvailable = material.isAvailable != true
            this.materialDao?.updateMaterial(material)
            val toMain = Main.newIntent(this, INTENT_USER_MAIL.toString())
            startActivity(toMain)
        }

    }

    companion object {

        private var INTENT_USER_MAIL: String? = null

        fun newIntent(context: Context, userMail: String): Intent {
            val intent = Intent(context, ScanMaterial::class.java)
            intent.putExtra(INTENT_USER_MAIL, userMail)
            this.INTENT_USER_MAIL = userMail
            return intent
        }
    }
}