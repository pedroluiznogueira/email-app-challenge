package com.example.emailapp.network

import com.example.emailapp.model.DevicePreferences
import com.example.emailapp.model.EmailEntity
import com.example.emailapp.model.EventEntity
import retrofit2.http.*
import retrofit2.Call

interface ApiService {
    @GET("api/preferences/{userId}")
    fun getPreferences(@Path("userId") userId: Long): Call<DevicePreferences>

    @POST("api/preferences")
    fun savePreferences(@Body preferences: DevicePreferences): Call<DevicePreferences>

    @GET("api/emails")
    fun getAllEmails(): Call<List<EmailEntity>>

    @POST("api/emails")
    fun addEmail(@Body email: EmailEntity): Call<EmailEntity>

    @PUT("api/emails/{id}")
    fun updateEmail(@Path("id") id: Int, @Body email: EmailEntity): Call<EmailEntity>

    @DELETE("api/emails/{id}")
    fun deleteEmail(@Path("id") id: Int): Call<Void>

    @GET("api/events")
    fun getEventsByDate(@Query("date") date: String): Call<List<EventEntity>>

    @POST("api/events")
    fun addEvent(@Body event: EventEntity): Call<EventEntity>

    @DELETE("api/events/{id}")
    fun deleteEvent(@Path("id") id: Int): Call<Void>
}
