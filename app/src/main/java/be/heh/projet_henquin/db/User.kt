package be.heh.projet_henquin.db

class User(i : Int) {
    var id: Int = 0
        public get
        private set
    var email: String = "null"
        public get
        private set
    var password: String = "null"
        public get
        private set


    constructor(id: Int, email: String, password: String, ) : this(id) {
        this.id = id
        this.email = email
        this.password = password
    }
    override fun toString() : String {
        val sb = StringBuilder()
        sb.append("ID : " + id.toString() +
                "\n" +
                "Password : " + password + "\n" +
                "Email : " + email)
        return sb.toString()
    }
}