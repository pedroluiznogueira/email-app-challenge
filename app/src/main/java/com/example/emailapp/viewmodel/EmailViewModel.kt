package com.example.emailapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emailapp.model.EmailEntity
import com.example.emailapp.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmailViewModel(private val repository: Repository) : ViewModel() {

    private val _emails = MutableStateFlow<List<EmailEntity>>(emptyList())
    val emails = _emails.asStateFlow()

    init {
        viewModelScope.launch {
            _emails.value = repository.getAllEmails()
        }
    }

    fun addEmail(email: EmailEntity) {
        viewModelScope.launch {
            repository.addEmail(email)?.let {
                _emails.value = _emails.value + it
            }
        }
    }

    fun updateEmail(email: EmailEntity) {
        viewModelScope.launch {
            repository.updateEmail(email.id, email)?.let { updatedEmail ->
                _emails.value = _emails.value.map { if (it.id == updatedEmail.id) updatedEmail else it }
            }
        }
    }

    fun deleteEmail(id: Int) {
        viewModelScope.launch {
            if (repository.deleteEmail(id)) {
                _emails.value = _emails.value.filter { it.id.toLong().toInt() != id }
            }
        }
    }
}
