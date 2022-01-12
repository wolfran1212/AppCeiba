package com.wolfran.appceiba.utils

import com.wolfran.appceiba.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    fun getUsers(@Url url:String): Call<List<UserModel>>
}