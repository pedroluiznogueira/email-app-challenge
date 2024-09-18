package com.example.emailapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emailapp.model.EventEntity
import com.example.emailapp.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import android.util.Log

class CalendarViewModel(private val repository: Repository) : ViewModel() {
    private val _events = MutableStateFlow<Map<LocalDate, List<EventEntity>>>(emptyMap())
    val events = _events.asStateFlow()

    fun addEvent(event: EventEntity) {
        viewModelScope.launch {
            Log.d("CalendarViewModel", "Adding event: $event") // Log the event being added
            repository.addEvent(event)
        }
    }

    fun getEventsForDate(date: String) {  // Accepting date as a String
        viewModelScope.launch {
            Log.d("CalendarViewModel", "Fetching events for date: $date") // Log the date being queried
            val eventList = repository.getEventsByDate(date)
            _events.value = _events.value.toMutableMap().apply { put(LocalDate.parse(date), eventList) }
        }
    }

    fun deleteEvent(event: EventEntity) {
        viewModelScope.launch {
            Log.d("CalendarViewModel", "Deleting event: $event") // Log the event being deleted
            repository.deleteEvent(event.id)
            getEventsForDate(event.date.toString())  // Convert LocalDate to String
        }
    }
}
