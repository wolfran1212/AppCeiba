package com.wolfran.appceiba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wolfran.appceiba.models.UserModel
import com.wolfran.appceiba.utils.MyAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.rvUsers)
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList de UserModel
        val data = ArrayList<UserModel>()

        // se grega un usuario de prueba
        data.add(UserModel(id = 1, name = "Wolfran Pinzon", phone = "3115669761", email = "wolfran2008@gmail.com", website = "", username = "", company = null, address = null))

        // pasar arrayList al adapter
        val adapter = MyAdapter(data)

        // asignar el adapter al recyclerview
        recyclerview.adapter = adapter

    }
}