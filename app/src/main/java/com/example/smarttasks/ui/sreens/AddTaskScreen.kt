package com.example.smarttasks.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smarttasks.viewmodel.TaskViewModel
import com.example.smarttasks.model.Tarea
import com.example.smarttasks.model.PrioridadTarea
import com.example.smarttasks.ui.components.PrioritySelector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    navController: NavController,
    viewModel: TaskViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var newTaskTitle by remember { mutableStateOf("") }
    var newTaskDescription by remember { mutableStateOf("") }
    var selectedPriority by remember { mutableStateOf(PrioridadTarea.MEDIA) }
    var isSaving by remember { mutableStateOf(false) }

    val isFormValid = newTaskTitle.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Tarea") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            if (isFormValid && !isSaving) {
                                isSaving = true
                                val newTask = Tarea(
                                    titulo = newTaskTitle.trim(),
                                    descripcion = newTaskDescription.trim(),
                                    prioridad = selectedPriority
                                )
                                viewModel.agregarTarea(newTask)
                                navController.popBackStack()
                            }
                        },
                        enabled = isFormValid && !isSaving
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Row {
                                Icon(
                                    Icons.Default.Done,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Guardar")
                            }
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Campo de título
            OutlinedTextField(
                value = newTaskTitle,
                onValueChange = { newTaskTitle = it },
                label = { Text("Título de la tarea *") },
                placeholder = { Text("Ej: Estudiar Kotlin") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = newTaskTitle.isBlank() && newTaskTitle.isNotEmpty(),
                supportingText = {
                    if (newTaskTitle.isBlank() && newTaskTitle.isNotEmpty()) {
                        Text("El título es obligatorio")
                    }
                }
            )

            // Campo de descripción
            OutlinedTextField(
                value = newTaskDescription,
                onValueChange = { newTaskDescription = it },
                label = { Text("Descripción (opcional)") },
                placeholder = { Text("Añade detalles sobre tu tarea...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 6
            )

            // Selector de prioridad
            PrioritySelector(
                selectedPriority = selectedPriority,
                onPriorityChange = { selectedPriority = it }
            )

            // Información adicional
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "💡 Consejos",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• Usa títulos claros y específicos\n• La descripción te ayudará a recordar detalles\n• Ajusta la prioridad según la importancia",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}
