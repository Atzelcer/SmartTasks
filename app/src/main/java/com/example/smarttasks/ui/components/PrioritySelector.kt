package com.example.smarttasks.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.smarttasks.model.PrioridadTarea

@Composable
fun PrioritySelector(
    selectedPriority: PrioridadTarea,
    onPriorityChange: (PrioridadTarea) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Prioridad",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PrioridadTarea.entries.forEach { prioridad ->
                val isSelected = selectedPriority == prioridad
                val (emoji, texto, color) = when (prioridad) {
                    PrioridadTarea.ALTA -> Triple("🔴", "Alta", MaterialTheme.colorScheme.error)
                    PrioridadTarea.MEDIA -> Triple("🟡", "Media", MaterialTheme.colorScheme.primary)
                    PrioridadTarea.BAJA -> Triple("🟢", "Baja", MaterialTheme.colorScheme.secondary)
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .selectable(
                            selected = isSelected,
                            onClick = { onPriorityChange(prioridad) },
                            role = Role.RadioButton
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) {
                            color.copy(alpha = 0.2f)
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        }
                    ),
                    border = if (isSelected) {
                        CardDefaults.outlinedCardBorder().copy(
                            brush = androidx.compose.ui.graphics.SolidColor(color),
                            width = 2.dp
                        )
                    } else null
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = emoji,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = texto,
                            style = MaterialTheme.typography.labelMedium,
                            color = if (isSelected) color else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
