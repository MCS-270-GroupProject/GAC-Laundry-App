package com.example.wholettheclothesout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import layout.UserModal
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset


private const val TAG = "LoginActivity"

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        private lateinit var auth: FirebaseAuth
        // ...
        // Initialize Firebase Auth
        auth = Firebase.auth
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload();
        }
    }
}