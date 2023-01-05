package be.heh.projet_henquin.userPage

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import be.heh.projet_henquin.R
import be.heh.projet_henquin.db.user.UserRecord

class UserListAdapter(private val context: Activity, private val users: List<UserRecord>) :
    ArrayAdapter<UserRecord>(
        context,
        R.layout.userlistcomponent, users
    ) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.userlistcomponent, null)
        val user = getItem(position)
        val text_email: TextView = view.findViewById(R.id.text_email)
        val text_password: TextView = view.findViewById(R.id.text_password)
        val text_isAdmin: TextView = view.findViewById(R.id.text_isAdmin)

        text_email.text = user!!.email
        text_password.text = user.password
        if (user.isAdmin) text_isAdmin.text = "admin"
        else text_isAdmin.text = "utilisateur"

        return view
    }
}