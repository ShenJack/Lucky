package com.example.shenjack.lucky.data.remote

import com.google.gson.Gson
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by shenjack on 17-11-26.
 */
object apiService {
    private var instance: Retrofit? = null
    var okHttpClient: OkHttpClient? = null

    private var apiService: Api? = null

    private val retrofitInstance: Retrofit?
        get() {
            val gson = Gson()
            if (instance != null) {
                return instance
            } else {
                okHttpClient = OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .cookieJar(object : CookieJar {
                            val cookieStore:HashMap<String,List<Cookie>> = HashMap()
                            override fun saveFromResponse(url: HttpUrl?, cookies: MutableList<Cookie>?) {
                                cookieStore.put(url!!.host(),cookies!!)
                            }

                            override fun loadForRequest(url: HttpUrl?): List<Cookie>? {
                                val cookies:List<Cookie>? = cookieStore.get(url!!.host())
                                if(cookies!=null){
                                    return cookies
                                }else{
                                    return ArrayList()
                                }
                            }
                        })
                        .build()
                instance = Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(okHttpClient!!)
                        .build()
                return instance
            }
        }

    val apiServiceInstance: Api?
        get() {
            if (apiService != null) {
                return apiService
            } else {
                apiService = retrofitInstance!!.create(Api::class.java)
            }
            return apiService
        }

    val okHttpClienInstance: OkHttpClient?
        get(){
            if (okHttpClient != null) {
                return okHttpClient
            } else {
                okHttpClient = OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build()
            }
            return okHttpClient
        }

}