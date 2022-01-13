package com.wolfran.appceiba

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wolfran.appceiba.helpers.DBHelper
import com.wolfran.appceiba.models.UserModel
import com.wolfran.appceiba.utils.APIService
import com.wolfran.appceiba.utils.CargandoDialog
import com.wolfran.appceiba.utils.MyAdapter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale.filter

class MainActivity : AppCompatActivity(), TextWatcher {

    val db = DBHelper(this, null)
    lateinit var recyclerview : RecyclerView
    lateinit var buscarUser : EditText
    lateinit var dialog : CargandoDialog
    lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buscarUser = findViewById<EditText>(R.id.svUserSearch)
        buscarUser.addTextChangedListener(this)

        // getting the recyclerview by its id
        recyclerview = findViewById<RecyclerView>(R.id.rvUsers)
        recyclerview.layoutManager = LinearLayoutManager(this)
        dialog = CargandoDialog(this)

        //consultar usuarios en la BD
        val users = db.getUsers()

        //validar si ya estan guardados localmente los usuarios
        if(users.count() > 0){
            // pasar arrayList al adapter
            val adapter = MyAdapter(users,this)
            // asignar el adapter al recyclerview
            recyclerview.adapter = adapter
            myAdapter = adapter
        }else{
            //abrir dialogo cargando
            dialog.startAlertDialog()
            //consultar servicio user
            obtenerUsers(this)
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun obtenerUsers(actividad:Activity) {
        doAsync {
            val call = getRetrofit().create(APIService::class.java).getUsers("users").execute()
            val users = call.body() as List<UserModel>
            uiThread {
                print(users.toString())
                //guardar usuarios en la BD
                for (user in users){
                    db.insertUser(user)
                }
                // pasar arrayList al adapter
                val adapter = MyAdapter(users,actividad)
                // asignar el adapter al recyclerview
                recyclerview.adapter = adapter
                myAdapter = adapter
                dialog.stopAlertDialog()
            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
        myAdapter.filter.filter(p0)
    }

}