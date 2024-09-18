package com.example.emailapp.view.screens.emaillist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.emailapp.model.EmailEntity

@Composable
fun EmailItem(
    email: EmailEntity,
    onImportantToggle: () -> Unit,
    onDeleteEmail: () -> Unit,
    onUpdateEmail: (EmailEntity) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var editingSubject by remember { mutableStateOf(false) }
    var editingSender by remember { mutableStateOf(false) }
    var newSubject by remember { mutableStateOf(email.subject) }
    var newSender by remember { mutableStateOf(email.sender) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = email.subject,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Icon(
                    imageVector = if (email.isImportant) Icons.Filled.Star else Icons.Outlined.StarBorder,
                    contentDescription = "Important",
                    tint = Color.Yellow,
                    modifier = Modifier.clickable { onImportantToggle() }
                )
            }
            Text(
                text = email.sender,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (email.tags.isNotEmpty()) {
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    email.tags.forEach { tag ->
                        Text(
                            text = tag,
                            modifier = Modifier
                                .background(Color.Gray, RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // DropdownMenu para opções
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "More options")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Alterar Assunto") },
                        onClick = {
                            expanded = false
                            editingSubject = true
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Alterar Remetente") },
                        onClick = {
                            expanded = false
                            editingSender = true
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Deletar Email") },
                        onClick = {
                            expanded = false
                            onDeleteEmail()
                        }
                    )
                }
            }

            // Diálogo para editar o assunto
            if (editingSubject) {
                AlertDialog(
                    onDismissRequest = { editingSubject = false },
                    title = { Text("Alterar Assunto") },
                    text = {
                        TextField(
                            value = newSubject,
                            onValueChange = { newSubject = it },
                            label = { Text("Novo Assunto") }
                        )
                    },
                    confirmButton = {
                        Button(onClick = {
                            onUpdateEmail(email.copy(subject = newSubject))
                            editingSubject = false
                        }) {
                            Text("Salvar")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { editingSubject = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }

            // Diálogo para editar o remetente
            if (editingSender) {
                AlertDialog(
                    onDismissRequest = { editingSender = false },
                    title = { Text("Alterar Remetente") },
                    text = {
                        TextField(
                            value = newSender,
                            onValueChange = { newSender = it },
                            label = { Text("Novo Remetente") }
                        )
                    },
                    confirmButton = {
                        Button(onClick = {
                            onUpdateEmail(email.copy(sender = newSender))
                            editingSender = false
                        }) {
                            Text("Salvar")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { editingSender = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}
