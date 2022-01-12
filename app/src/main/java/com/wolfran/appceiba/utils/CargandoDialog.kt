package com.wolfran.appceiba.utils

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import com.wolfran.appceiba.R

class CargandoDialog {

    lateinit var actividad: Activity
    lateinit var dialog: AlertDialog

    constructor(myActivity:Activity ){
        this.actividad=myActivity
    }

     fun startAlertDialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(actividad)
         val inflater: LayoutInflater = actividad.layoutInflater
         builder.setView(inflater.inflate(R.layout.custom_dialog,null))
         builder.setCancelable(false)

         dialog= builder.create()
         dialog.show()
    }

    fun stopAlertDialog(){
        dialog.dismiss()
    }
}