package com.wolfran.appceiba.helpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.wolfran.appceiba.models.Address
import com.wolfran.appceiba.models.Company
import com.wolfran.appceiba.models.UserModel
import java.lang.Exception

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object{
        // define variables BD
        // nombre de la BD
        private val DATABASE_NAME = "ceiba"
        // version BD
        private val DATABASE_VERSION = 1

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table user(id int primary key, name text not null, username text, email text not null, address text, phone text not null, website text, company text)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS user")
        onCreate(db)
    }

    fun insertUser(user: UserModel){

        // insertando valores en el mapa
        val values = ContentValues()
        values.put("id", user.id)
        values.put("name", user.name)
        values.put("username", user.username)
        values.put("email", user.email)
        values.put("address", "")
        values.put("phone", user.phone)
        values.put("website", user.website)
        values.put("company", "")

        // insert valores en la BD
        val db = this.writableDatabase
        db.insert("user", null, values)
        // cerrar conexion BD
        db.close()
    }

    fun getUsers(): ArrayList<UserModel> {
        val users: ArrayList<UserModel> = ArrayList<UserModel>()

        // abrir conexion lectura a la BD
        val db = this.readableDatabase

        // select user a la BD
        try {
            val cursor = db.rawQuery("SELECT * FROM user", null)
            if (cursor.moveToFirst()) {
                do {
                    val user = UserModel(
                        cursor.getString(0).toInt(),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        null,
                        cursor.getString(5),
                        cursor.getString(6),
                        null,
                    )
                    users.add(user)
                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: Exception) {
            print(e.message.toString())
        }
        // cerrar conexion BD
        db.close()
        return users

    }


}