package com.example.smarttasks.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smarttasks.model.Tarea

@Composable
fun TaskItem(tarea: Tarea, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { onCheckedChange(!tarea.completada) },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = tarea.titulo,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = tarea.descripcion,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Checkbox(
            checked = tarea.completada,
            onCheckedChange = onCheckedChange
        )
    }
}
