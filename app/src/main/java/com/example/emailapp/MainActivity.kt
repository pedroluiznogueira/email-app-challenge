package com.example.emailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.emailapp.repository.Repository
import com.example.emailapp.ui.theme.EmailAppTheme
import com.example.emailapp.view.screens.calendar.CalendarScreen
import com.example.emailapp.view.screens.emaillist.EmailListScreen
import com.example.emailapp.view.screens.settings.SettingsScreen
import com.example.emailapp.viewmodel.CalendarViewModel
import com.example.emailapp.viewmodel.EmailViewModel
import com.example.emailapp.viewmodel.PreferencesViewModel

class MainActivity : ComponentActivity() {
    private lateinit var repository: Repository
    private val userId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = Repository()

        setContent {
            EmailAppTheme {
                AppNavigation(repository, userId)
            }
        }
    }
}

@Composable
fun AppNavigation(repository: Repository, userId: Long) {
    val navController = rememberNavController()
    val emailViewModel: EmailViewModel = viewModel(factory = EmailViewModelFactory(repository))
    val calendarViewModel: CalendarViewModel = viewModel(factory = CalendarViewModelFactory(repository))
    val preferencesViewModel: PreferencesViewModel = viewModel(factory = PreferencesViewModelFactory(repository))

    NavHost(navController = navController, startDestination = "email_list") {
        composable("email_list") { EmailListScreen(navController, emailViewModel) }
        composable("calendar") { CalendarScreen(navController, calendarViewModel) }
        composable("settings") { SettingsScreen(preferencesViewModel, userId, navController) }
    }
}

class EmailViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(EmailViewModel::class.java) -> EmailViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

class CalendarViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CalendarViewModel::class.java) -> CalendarViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

class PreferencesViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PreferencesViewModel::class.java) -> PreferencesViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

