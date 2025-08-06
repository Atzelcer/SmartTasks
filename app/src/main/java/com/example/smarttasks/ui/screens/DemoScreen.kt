package com.example.smarttasks.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smarttasks.model.PrioridadTarea
import com.example.smarttasks.model.Tarea
import com.example.smarttasks.ui.components.PrioritySelector
import com.example.smarttasks.ui.components.TaskCard
import com.example.smarttasks.ui.components.FilterChips
import com.example.smarttasks.viewmodel.FiltroTareas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoScreen(
    navController: NavController
) {
    var showAnimations by remember { mutableStateOf(false) }
    var selectedPriority by remember { mutableStateOf(PrioridadTarea.MEDIA) }
    var selectedFilter by remember { mutableStateOf(FiltroTareas.TODAS) }

    val animatedColor by animateColorAsState(
        targetValue = if (showAnimations) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
        animationSpec = tween(1000),
        label = "colorAnimation"
    )

    val sampleTasks = listOf(
        Tarea(1, "Estudiar Kotlin", "Aprender Jetpack Compose", false, prioridad = PrioridadTarea.ALTA),
        Tarea(2, "Hacer ejercicio", "Cardio 30 minutos", true, prioridad = PrioridadTarea.MEDIA),
        Tarea(3, "Leer un libro", "Clean Code - Capítulo 4", false, prioridad = PrioridadTarea.BAJA)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🧪 Demo SmartTasks") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Sección de Tipografía
            DemoSection(title = "📝 Tipografía Futurista") {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Display Large", style = MaterialTheme.typography.displayLarge)
                    Text("Headline Medium", style = MaterialTheme.typography.headlineMedium)
                    Text("Title Large", style = MaterialTheme.typography.titleLarge)
                    Text("Body Large", style = MaterialTheme.typography.bodyLarge)
                    Text("Label Medium", style = MaterialTheme.typography.labelMedium)
                }
            }

            // Sección de Colores
            DemoSection(title = "🎨 Paleta de Colores") {
                val colorScheme = MaterialTheme.colorScheme
                val colors = listOf(
                    "Primary" to colorScheme.primary,
                    "Secondary" to colorScheme.secondary,
                    "Tertiary" to colorScheme.tertiary,
                    "Surface" to colorScheme.surface,
                    "Error" to colorScheme.error,
                    "Outline" to colorScheme.outline
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.height(200.dp)
                ) {
                    items(colors) { (name, color) ->
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .background(color, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = name,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (name == "Surface") MaterialTheme.colorScheme.onSurface else Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Sección de Animaciones
            DemoSection(title = "✨ Animaciones") {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(
                        onClick = { showAnimations = !showAnimations },
                        colors = ButtonDefaults.buttonColors(containerColor = animatedColor)
                    ) {
                        Text("Alternar Animación de Color")
                    }

                    AnimatedVisibility(visible = showAnimations) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Text(
                                "¡Animación activada! 🚀",
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }

            // Sección de Componentes
            DemoSection(title = "🧩 Componentes Personalizados") {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Selector de Prioridad
                    PrioritySelector(
                        selectedPriority = selectedPriority,
                        onPriorityChange = { selectedPriority = it }
                    )

                    // Chips de Filtro
                    FilterChips(
                        filtroActual = selectedFilter,
                        onFiltroChange = { selectedFilter = it }
                    )
                }
            }

            // Sección de Tarjetas de Tareas
            DemoSection(title = "📋 Tarjetas de Tareas") {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    sampleTasks.forEach { tarea ->
                        TaskCard(
                            tarea = tarea,
                            onToggleComplete = { },
                            onDelete = { }
                        )
                    }
                }
            }

            // Información del tema
            DemoSection(title = "ℹ️ Información del Tema") {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "🎯 Tema Futurista y Tranquilo",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "• Paleta de colores cibernéticos\n" +
                            "• Tipografía moderna con espaciado amplio\n" +
                            "• Animaciones suaves y relajantes\n" +
                            "• Componentes personalizados\n" +
                            "• Compatible con modo claro/oscuro",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DemoSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        content()
    }
}
