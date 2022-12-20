package be.heh.projet_henquin.model


class User(userEmail: String? = null, userPassword: String? = null){
    private var userId : Int= 0;
    private var userEmail : String? = userEmail;
    private var userPassword : String? = userPassword;

    constructor(id:Int, userEmail: String?,userPassword: String?,) : this() {
        this.userId = id;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public fun setUserId(id:Int): Unit {
        this.userId = id;
    }
    public fun getUserId() : Int{
        return this.userId;
    }

    public fun setUserEmail(email:String?): Unit {
        this.userEmail = email;
    }
    public fun getUserEmail() : String?{
        return this.userEmail;
    }

    public fun setUserPassword(password:String): Unit {
        this.userPassword = password;
    }
    public fun getUserPassword():String? {
        return this.userPassword;
    }
}