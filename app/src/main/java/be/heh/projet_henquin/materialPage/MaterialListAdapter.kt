package be.heh.projet_henquin.materialPage

import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import be.heh.projet_henquin.R
import be.heh.projet_henquin.db.material.MaterialRecord

class MaterialListAdapter(private val context : Activity, private val materials: List<MaterialRecord>) : ArrayAdapter<MaterialRecord>(context,
    R.layout.materialcomponent, materials) {

    override fun getView(position : Int, convertView : View?, parent: ViewGroup) : View{

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.materialcomponent, null)
        val material = getItem(position)
        val text_type : TextView = view.findViewById(R.id.text_type)
        val text_ref : TextView = view.findViewById(R.id.text_ref)
        val codeQr : ImageView = view.findViewById(R.id.image_view_qr_code)
        val text_available : TextView = view.findViewById(R.id.text_available)

        text_type.text = material!!.type
        text_ref.text = material.ref
        if(material.isAvailable){
            text_available!!.setTextColor(Color.GREEN)
            text_available.text = "Available"
        }
        else{
            text_available.text = "Not available"
            text_available!!.setTextColor(Color.RED)
        }
        val bitmap = BitmapFactory.decodeByteArray(material.qrCode, 0, material.qrCode.size)
        codeQr.setImageBitmap(bitmap)

        return view

    }

}