package com.nevosap.listy.users

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.nevosap.listy.MainActivity
import com.nevosap.listy.R
import com.nevosap.listy.networking.FirebaseModule

class UsersActivity:AppCompatActivity() {
    companion object {
        private const val RC_SIGN_IN = 123
        const val ACTION_SIGN_IN = "com.nevosap.listy.users.UsersActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startSignFlow()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                //setting the user in the network module
                user?.let {
                    FirebaseModule.initUser(it)
                }
                //starting main activity
                val userIntent = Intent(this,MainActivity::class.java)
                startActivity(userIntent)
                // ...
            } else {
                Toast.makeText(this,getString(R.string.sign_in_failed),Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun startSignFlow(){
        val provider = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build())
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(provider)
                .build(),
            RC_SIGN_IN)
    }
}