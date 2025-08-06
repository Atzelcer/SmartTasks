package com.example.smarttasks.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smarttasks.ui.screens.MainScreen
import com.example.smarttasks.ui.screens.AddTaskScreen
import com.example.smarttasks.viewmodel.ThemeViewModel

@Composable
fun SmartTasksNavGraph(
    navController: NavHostController,
    themeViewModel: ThemeViewModel
) {
    NavHost(
        navController = navController,
        startDestination = SmartTasksDestinations.MAIN
    ) {
        composable(SmartTasksDestinations.MAIN) {
            MainScreen(
                onNavigateToAddTask = {
                    navController.navigate(SmartTasksDestinations.ADD_TASK)
                },
                themeViewModel = themeViewModel
            )
        }

        composable(SmartTasksDestinations.ADD_TASK) {
            AddTaskScreen(
                navController = navController
            )
        }
    }
}

// Constantes para las rutas (mejores prácticas)
object SmartTasksDestinations {
    const val MAIN = "main"
    const val ADD_TASK = "add_task"
}
