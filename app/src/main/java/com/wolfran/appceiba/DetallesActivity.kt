package com.wolfran.appceiba

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wolfran.appceiba.models.PostModel
import com.wolfran.appceiba.models.UserModel
import com.wolfran.appceiba.utils.APIService
import com.wolfran.appceiba.utils.CargandoDialog
import com.wolfran.appceiba.utils.MyAdapter
import com.wolfran.appceiba.utils.MyAdapterPost
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetallesActivity : AppCompatActivity() {

    lateinit var recyclerview : RecyclerView
    lateinit var dialog : CargandoDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)
        val id:String = intent.getIntExtra("id",0).toString()
        val nombre:String = intent.getStringExtra("nombre").toString()
        val telefono:String = intent.getStringExtra("telefono").toString()
        val correo:String = intent.getStringExtra("correo").toString()

        val textViewNombre: TextView = findViewById(R.id.tvUserName)
        val textViewTelefono: TextView = findViewById(R.id.tvPhone)
        val textViewEmail: TextView = findViewById(R.id.tvEmail)

        textViewNombre.text = nombre
        textViewTelefono.text = telefono
        textViewEmail.text = correo

        // getting the recyclerview by its id
        recyclerview = findViewById<RecyclerView>(R.id.rvPosts)
        recyclerview.layoutManager = LinearLayoutManager(this)
        dialog = CargandoDialog(this)

        //abrir dialogo cargando
        dialog.startAlertDialog()
        //consultar servicio user
        obtenerPost(this,id)

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun obtenerPost(actividad: Activity,id:String) {
        doAsync {
            val call = getRetrofit().create(APIService::class.java).getPosts("posts?userId=$id").execute()
            val posts = call.body() as List<PostModel>
            uiThread {
                print(posts.toString())
                // pasar arrayList al adapter
                val adapter = MyAdapterPost(posts,actividad)
                // asignar el adapter al recyclerview
                recyclerview.adapter = adapter
                dialog.stopAlertDialog()
            }
        }
    }
}