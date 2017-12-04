package com.example.shenjack.lucky.data.remote

import com.example.shenjack.lucky.data.Message
import com.example.shenjack.lucky.data.Response
import com.example.shenjack.lucky.data.Room
import com.example.shenjack.lucky.data.UserBean
import io.reactivex.Observable
import io.reactivex.Observer
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by shenjack on 17-11-26.
 */
interface Api {
    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("username") username: String,
              @Field("password") password: String):Observable<Response<UserBean>>

    @GET("user/logout")
    fun logout()

    @FormUrlEncoded
    @POST("user/register")
    fun register(@Field("username") username: String,
                 @Field("password") password: String):Observable<Response<UserBean>>

    @FormUrlEncoded
    @POST("room/create")
    fun createRoom(@Field("room_id")roomId:String)

    @GET("room/getRooms")
    fun getRooms(): Observable<Response<List<Room>>>

    @FormUrlEncoded
    @POST("room/enter")
    fun enterRoom(@Field("room_id")roomId: String): Observable<Response<List<Message>>>

    companion object {
        var BASE_IP:String = "192.168.31.209"
        var BASE_URL: String = "Http://"+ BASE_IP+ ":8000/"
        var BASE_WS_URL:String = "ws://"+ BASE_IP+ ":8000/"
    }

}