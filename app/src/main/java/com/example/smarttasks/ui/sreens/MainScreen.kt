// MainScreen.kt
package com.example.smarttasks.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarttasks.ui.components.TaskList
import com.example.smarttasks.ui.components.FilterChips
import com.example.smarttasks.ui.components.AnimatedThemeToggle
import com.example.smarttasks.viewmodel.TaskViewModel
import com.example.smarttasks.viewmodel.ThemeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToAddTask: () -> Unit,
    themeViewModel: ThemeViewModel,
    viewModel: TaskViewModel = viewModel()
) {
    val tareas by viewModel.tareas.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val filtroActual by viewModel.filtroActual.collectAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AnimatedThemeToggle(
                themeViewModel = themeViewModel,
                onDismiss = {
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                "SmartTasks",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Text(
                                "${tareas.size} ${if (tareas.size == 1) "tarea" else "tareas"}",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menú de tema",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = onNavigateToAddTask,
                    icon = {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Agregar tarea"
                        )
                    },
                    text = {
                        Text("Nueva tarea")
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Chips de filtro
                FilterChips(
                    filtroActual = filtroActual,
                    onFiltroChange = viewModel::cambiarFiltro,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                // Lista de tareas
                TaskList(
                    tareas = tareas,
                    onToggleComplete = viewModel::marcarComoCompletada,
                    onDelete = viewModel::eliminarTarea,
                    isLoading = isLoading,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
