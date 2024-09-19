package com.example.emailapp.view.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.emailapp.model.EventEntity
import com.example.emailapp.viewmodel.CalendarViewModel
import java.time.LocalDate
import java.time.YearMonth
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import com.example.emailapp.viewmodel.PreferencesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: PreferencesViewModel, userId: Long, navController: NavHostController) {
    val preferences by viewModel.preferences.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPreferences(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            preferences?.let {
                var theme by remember { mutableStateOf(it.theme) }
                var primaryColor by remember { mutableStateOf(it.primaryColor) }

                Column(modifier = Modifier.padding(paddingValues).fillMaxWidth().padding(16.dp)) {
                    // Theme input
                    TextField(
                        value = theme,
                        onValueChange = { newTheme ->
                            theme = newTheme
                        },
                        label = { Text("Theme (dark or light)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Primary Color input
                    TextField(
                        value = primaryColor,
                        onValueChange = { newColor ->
                            primaryColor = newColor
                        },
                        label = { Text("Primary Color (e.g., #FF5733)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Save Preferences button
                    Button(
                        onClick = {
                            viewModel.savePreferences(it.copy(theme = theme, primaryColor = primaryColor))
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save Preferences")
                    }
                }
            } ?: run {
                // Display a loading message if preferences are not yet loaded
                Column(modifier = Modifier.padding(paddingValues).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(text = "Loading preferences...")
                }
            }
        }
    )
}