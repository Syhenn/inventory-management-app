package be.heh.projet_henquin

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView

@SuppressLint("WrongViewCast")
class MaterialComponent(
    context: Context,
    attrs: AttributeSet ?=null,
    textType: String,
    textModel: String,
    textRef: String,
    textLink: String
) : LinearLayout(context, attrs) {
    private var textType : String = textType
    private var textModel : String = textModel
    private var textRef : String = textRef
    private var textLink : String = textLink
    init {
        inflate(context, R.layout.materialcomponent, this)

        val customAttributesStyle = context.obtainStyledAttributes(attrs, R.styleable.MaterialTextComponent, 0, 0)

        val type = findViewById<TextView>(R.id.text_type)
        val model = findViewById<TextView>(R.id.text_model)
        val ref = findViewById<TextView>(R.id.text_ref)
        val link = findViewById<TextView>(R.id.text_link)

        try {
            type.text = customAttributesStyle.getString(R.styleable.MaterialTextComponent_typeText)
            model.text = customAttributesStyle.getString(R.styleable.MaterialTextComponent_modelText)
            ref.text = customAttributesStyle.getString(R.styleable.MaterialTextComponent_refText)
            link.text = customAttributesStyle.getString(R.styleable.MaterialTextComponent_linkText)
        } finally {
            customAttributesStyle.recycle()
        }
        /*
        button2.setOnClickListener {
            // Handle button2 click event...
        }*/
    }
}