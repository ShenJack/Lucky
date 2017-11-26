package com.example.shenjack.lucky.data.remote

import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.*

/**
 * Created by shenjack on 17-11-26.
 */
interface Api {
    @POST("/login")
    fun login(@Field("username") username: String,
              @Field("password") password: String)

    @GET("/logout")
    fun logout()

    @POST("/register")
    fun register(@Field("username") username: String,
                 @Field("password") password: String)

    @POST("/create")
    fun createRoom(@Field("room_id")roomId:String)

    @GET("/getRooms")
    fun getRooms():Observable<List<Room>>

    companion object {
        var BASE_URL: String = "Http://192.168.31.108:8000/"
    }

}