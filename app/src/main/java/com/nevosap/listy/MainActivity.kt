package com.nevosap.listy

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.nevosap.listy.networking.FirebaseModule

class MainActivity : AppCompatActivity() {
    var key :String? =null
    val bundle = Bundle()
    companion object{
        const val DEEP_LINK_KEY ="linkKey"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(!FirebaseModule.checkInit()){
            FirebaseModule.initUser(FirebaseAuth.getInstance().currentUser!!)
        }
        handleDeepLinkReceived()
    }

    private fun handleDeepLinkReceived(){
        FirebaseDynamicLinks.getInstance().getDynamicLink(intent)
            .addOnSuccessListener{ pendingDynamicLinkData ->
            // Get deep link from result (may be null if no link is found)
            var deepLink: Uri? = null
            if (pendingDynamicLinkData != null) {
                deepLink = pendingDynamicLinkData.link
                key = deepLink!!.getQueryParameter("id")
                bundle.putString(DEEP_LINK_KEY,key)
                findNavController(R.id.nav_host_fragment)
                    .setGraph(R.navigation.main_nav_graph,bundle)
            }else{
                bundle.putString(DEEP_LINK_KEY,null)
                findNavController(R.id.nav_host_fragment)
                    .setGraph(R.navigation.main_nav_graph,bundle)
            }
        }
    }
}
