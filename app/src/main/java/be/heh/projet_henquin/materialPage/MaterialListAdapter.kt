package be.heh.projet_henquin.materialPage

import android.app.Activity
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import be.heh.projet_henquin.R
import be.heh.projet_henquin.db.material.MaterialRecord

class MaterialListAdapter(private val context : Activity, private val arrayList: ArrayList<MaterialRecord>) : ArrayAdapter<MaterialRecord>(context,
    R.layout.materialcomponent, arrayList) {

    override fun getView(position : Int, convertView : View?, parent: ViewGroup) : View{

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.materialcomponent, null)

        val text_type : TextView = view.findViewById(R.id.text_type)
        val text_model : TextView = view.findViewById(R.id.text_model)
        val text_ref : TextView = view.findViewById(R.id.text_ref)
        val text_link : TextView = view.findViewById(R.id.text_link)
        val codeQr : ImageView = view.findViewById(R.id.image_view_qr_code)
        val text_available : TextView = view.findViewById(R.id.text_available)

        text_type.text = arrayList[position].type
        text_model.text = arrayList[position].model
        text_ref.text = arrayList[position].ref
        text_link.text = arrayList[position].link
        if(arrayList[position].isAvailable) text_available.text = "Available"
        else text_available.text = "Not available"
        val bitmap = BitmapFactory.decodeByteArray(arrayList[position].qrCode, 0, arrayList[position].qrCode.size)
        codeQr.setImageBitmap(bitmap)

        return view

    }

}