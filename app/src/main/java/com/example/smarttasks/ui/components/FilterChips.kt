package com.example.smarttasks.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smarttasks.viewmodel.FiltroTareas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChips(
    filtroActual: FiltroTareas,
    onFiltroChange: (FiltroTareas) -> Unit,
    modifier: Modifier = Modifier
) {
    val filtros = listOf(
        FiltroTareas.TODAS to "Todas",
        FiltroTareas.PENDIENTES to "Pendientes",
        FiltroTareas.COMPLETADAS to "Completadas"
    )

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        items(filtros) { (filtro, nombre) ->
            FilterChip(
                selected = filtroActual == filtro,
                onClick = { onFiltroChange(filtro) },
                label = {
                    Text(
                        text = nombre,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
