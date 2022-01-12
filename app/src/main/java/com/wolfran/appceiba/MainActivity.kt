package com.wolfran.appceiba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wolfran.appceiba.helpers.DBHelper
import com.wolfran.appceiba.models.UserModel
import com.wolfran.appceiba.utils.MyAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.rvUsers)
        recyclerview.layoutManager = LinearLayoutManager(this)

        //instanciar DBHelper BD
        val db = DBHelper(this, null)

        // se crea usuarios de ejemplo
        //insertando user a la BD
        val user1 = UserModel(id = 1, name = "Wolfran Pinzon", phone = "3115669761", email = "wolfran2008@gmail.com", website = "", username = "", company = null, address = null)
        db.insertUser(user1)
        val user2 = UserModel(id = 2, name = "Pedro Perez", phone = "3114569761", email = "pedro@gmail.com", website = "", username = "", company = null, address = null)
        db.insertUser(user2)

        // ArrayList de UserModel
        val users = db.getUsers()

        // pasar arrayList al adapter
        val adapter = MyAdapter(users)

        // asignar el adapter al recyclerview
        recyclerview.adapter = adapter

    }
}