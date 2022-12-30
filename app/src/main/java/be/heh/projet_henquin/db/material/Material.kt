package be.heh.projet_henquin.db.material

import android.graphics.Bitmap

class Material(i : Int){
        var id: Int = 0
            public get
            private set
        var type: String = "null"
            public get
            private set
        var model: String = "null"
            public get
            private set
        var ref: String = "null"
            public get
            private set
        var link: String = "null"
            public get
            private set
        var qrCode: ByteArray ?= null
            public get
            private set
        var isAvailable: Boolean = true
            public get
            private set
        var createdBy: String = "null"
            public get
            private set

        constructor(
            id: Int, type: String, model: String, ref: String, link: String, qrCode: ByteArray, isAvailable : Boolean,
            createdBy: String) : this(id) {
            this.id = id
            this.type = type
            this.model = model
            this.ref = ref
            this.link = link
            this.createdBy = createdBy
            this.isAvailable = isAvailable
            this.qrCode = qrCode
        }
        override fun toString() : String {
            val sb = StringBuilder()
            sb.append("ID : " + id.toString() +
                    "\n" +
                    "type : " + type + "\n" +
                    "model : " + model + "\n"+
                    "ref : " + ref + "\n" +
                    "link : " + link)
            return sb.toString()
        }
}