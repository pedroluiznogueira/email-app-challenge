package com.example.emailapp.model

data class DevicePreferences(
    val id: Long? = null,
    val userId: Long,
    val theme: String,
    val primaryColor: String,
    val labelCategory: String
)

