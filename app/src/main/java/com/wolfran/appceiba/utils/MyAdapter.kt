package com.wolfran.appceiba.utils

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.wolfran.appceiba.DetallesActivity
import com.wolfran.appceiba.MainActivity
import com.wolfran.appceiba.R
import com.wolfran.appceiba.models.UserModel
import java.time.Duration
import android.os.Build
import android.widget.Filterable
import java.util.stream.Collectors


class MyAdapter(private val mList: List<UserModel>, actividad:Activity) : RecyclerView.Adapter<MyAdapter.ViewHolder>(),Filterable{

    val activity = actividad
    var userList: List<UserModel>

    init {
        userList = mList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.elemento_lista, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val UserModel = userList[position]
        holder.textViewNombre.text = UserModel.name
        holder.textViewTelefono.text = UserModel.phone
        holder.textViewEmail.text = UserModel.email
        holder.textViewPublicaciones.setOnClickListener(View.OnClickListener {
//            Toast.makeText(activity,"click",Toast.LENGTH_SHORT).show()
            val intent:Intent = Intent(activity, DetallesActivity::class.java)
            intent.putExtra("id", UserModel.id)
            intent.putExtra("nombre", UserModel.name)
            intent.putExtra("telefono", UserModel.phone)
            intent.putExtra("correo", UserModel.email)
            activity.startActivity(intent)
        })

    }

    // retorna numero de intems en la lista
    override fun getItemCount(): Int {
        return userList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    userList = mList
                } else {
                    val resultList = ArrayList<UserModel>()
                    for (row in mList) {
                        if (row.name.lowercase().contains(charSearch.lowercase())) {
                            resultList.add(row)
                        }
                    }
                    userList = resultList
                }
                if(userList.count()==0) Toast.makeText(activity,"List is empty",Toast.LENGTH_SHORT).show()
                val filterResults = FilterResults()
                filterResults.values = userList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                userList = results?.values as ArrayList<UserModel>
                notifyDataSetChanged()
            }

        }
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewNombre: TextView = itemView.findViewById(R.id.tvUserName)
        val textViewTelefono: TextView = itemView.findViewById(R.id.tvPhone)
        val textViewEmail: TextView = itemView.findViewById(R.id.tvEmail)
        val textViewPublicaciones: TextView = itemView.findViewById(R.id.tvPublicaciones)
    }



}
