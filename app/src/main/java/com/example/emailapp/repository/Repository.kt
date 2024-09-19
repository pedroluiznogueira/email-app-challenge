package com.example.emailapp.repository

import android.util.Log
import com.example.emailapp.model.DevicePreferences
import com.example.emailapp.model.EmailEntity
import com.example.emailapp.model.EventEntity
import com.example.emailapp.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {

    suspend fun getAllEmails(): List<EmailEntity> {
        return withContext(Dispatchers.IO) {
            val response = RetrofitClient.apiService.getAllEmails().execute()
            if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
        }
    }

    suspend fun addEmail(email: EmailEntity): EmailEntity? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitClient.apiService.addEmail(email).execute()
            if (response.isSuccessful) response.body() else null
        }
    }

    suspend fun updateEmail(id: Int, email: EmailEntity): EmailEntity? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitClient.apiService.updateEmail(id, email).execute()
            if (response.isSuccessful) response.body() else null
        }
    }

    suspend fun deleteEmail(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            val response = RetrofitClient.apiService.deleteEmail(id).execute()
            response.isSuccessful
        }
    }

    suspend fun getEventsByDate(date: String): List<EventEntity> {
        return withContext(Dispatchers.IO) {
            val response = RetrofitClient.apiService.getEventsByDate(date).execute()
            if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
        }
    }

    suspend fun addEvent(event: EventEntity): EventEntity? {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.addEvent(event).execute()
                if (response.isSuccessful) {
                    Log.d("Repository", "Event added successfully: ${response.body()}")
                    response.body()
                } else {
                    Log.e("Repository", "Failed to add event: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("Repository", "Exception occurred while adding event", e)
                null
            }
        }
    }


    suspend fun deleteEvent(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            val response = RetrofitClient.apiService.deleteEvent(id).execute()
            response.isSuccessful
        }
    }

    suspend fun getPreferences(userId: Long): DevicePreferences? {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.getPreferences(userId).execute()
                if (response.isSuccessful) {
                    Log.d("Repository", "Preferences fetched successfully: ${response.body()}")
                    response.body()
                } else {
                    Log.e("Repository", "Failed to fetch preferences: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("Repository", "Exception occurred while fetching preferences", e)
                null
            }
        }
    }

    suspend fun savePreferences(preferences: DevicePreferences): DevicePreferences? {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.savePreferences(preferences).execute()
                if (response.isSuccessful) {
                    Log.d("Repository", "Preferences saved successfully: ${response.body()}")
                    response.body()
                } else {
                    Log.e("Repository", "Failed to save preferences: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("Repository", "Exception occurred while saving preferences", e)
                null
            }
        }
    }
}
