package com.wolfran.appceiba.utils

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.wolfran.appceiba.DetallesActivity
import com.wolfran.appceiba.MainActivity
import com.wolfran.appceiba.R
import com.wolfran.appceiba.models.PostModel
import com.wolfran.appceiba.models.UserModel
import java.time.Duration

class MyAdapterPost(private val mList: List<PostModel>, actividad:Activity) : RecyclerView.Adapter<MyAdapterPost.ViewHolder>() {

    val activity = actividad

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.elemento_lista_posts, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val PostModel = mList[position]
        holder.textViewTitle.text = PostModel.title
        holder.textViewBody.text = PostModel.body

    }

    // retorna numero de items en la lista
    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val textViewBody: TextView = itemView.findViewById(R.id.tvBody)
    }
}
