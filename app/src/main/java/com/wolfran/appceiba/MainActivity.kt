package com.wolfran.appceiba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wolfran.appceiba.helpers.DBHelper
import com.wolfran.appceiba.models.UserModel
import com.wolfran.appceiba.utils.APIService
import com.wolfran.appceiba.utils.MyAdapter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val db = DBHelper(this, null)
    lateinit var recyclerview : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // getting the recyclerview by its id
        recyclerview = findViewById<RecyclerView>(R.id.rvUsers)
        recyclerview.layoutManager = LinearLayoutManager(this)

        val users = db.getUsers()

        //validar si ya estan guardados localmente los usuarios
        if(users.count() > 0){
            // pasar arrayList al adapter
            val adapter = MyAdapter(users)
            // asignar el adapter al recyclerview
            recyclerview.adapter = adapter
        }else{
            obtenerUsers()
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun obtenerUsers() {
        doAsync {
            val call = getRetrofit().create(APIService::class.java).getUsers("users").execute()
            val users = call.body() as List<UserModel>
            uiThread {
                print(users.toString())
                // pasar arrayList al adapter
                val adapter = MyAdapter(users)
                // asignar el adapter al recyclerview
                recyclerview.adapter = adapter
            }
        }
    }
}