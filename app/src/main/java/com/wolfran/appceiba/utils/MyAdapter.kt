package com.wolfran.appceiba.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wolfran.appceiba.R
import com.wolfran.appceiba.models.UserModel

class MyAdapter(private val mList: List<UserModel>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.elemento_lista, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val UserModel = mList[position]
        holder.textViewNombre.text = UserModel.name
        holder.textViewTelefono.text = UserModel.phone
        holder.textViewEmail.text = UserModel.email

    }

    // retorna numero de intems en la lista
    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewNombre: TextView = itemView.findViewById(R.id.tvUserName)
        val textViewTelefono: TextView = itemView.findViewById(R.id.tvPhone)
        val textViewEmail: TextView = itemView.findViewById(R.id.tvEmail)
    }
}
