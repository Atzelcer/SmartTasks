package com.example.smarttasks.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarttasks.viewmodel.ThemeViewModel
import kotlinx.coroutines.delay
import kotlin.math.*
import kotlin.random.Random

data class Particle(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    var alpha: Float,
    var size: Float,
    var color: Color,
    var life: Float = 1f
)

@Composable
fun AnimatedThemeToggle(
    themeViewModel: ThemeViewModel,
    onDismiss: () -> Unit
) {
    val isDark by themeViewModel.isDarkTheme.collectAsState()
    var particles by remember { mutableStateOf(listOf<Particle>()) }
    var showParticles by remember { mutableStateOf(false) }

    // Animaciones principales
    val scale by animateFloatAsState(
        targetValue = if (showParticles) 1.2f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 800f),
        label = "scale"
    )

    val rotation by animateFloatAsState(
        targetValue = if (isDark) 360f else 0f,
        animationSpec = tween(1500, easing = FastOutSlowInEasing),
        label = "rotation"
    )

    val backgroundBrush = if (isDark) {
        Brush.radialGradient(
            colors = listOf(
                Color(0xFF1A1D29),
                Color(0xFF0B0E1A),
                Color.Black
            )
        )
    } else {
        Brush.radialGradient(
            colors = listOf(
                Color(0xFFE8F4F8),
                Color(0xFFF8FFFE),
                Color.White
            )
        )
    }

    // Generador de partículas
    LaunchedEffect(showParticles) {
        if (showParticles) {
            repeat(50) { i ->
                delay(i * 20L)
                val newParticle = Particle(
                    x = Random.nextFloat() * 400f,
                    y = Random.nextFloat() * 800f,
                    vx = (Random.nextFloat() - 0.5f) * 10f,
                    vy = (Random.nextFloat() - 0.5f) * 10f,
                    alpha = Random.nextFloat() * 0.8f + 0.2f,
                    size = Random.nextFloat() * 8f + 2f,
                    color = if (isDark) Color(0xFF00FFF0) else Color(0xFF00D4FF)
                )
                particles = particles + newParticle
            }

            // Animación continua de partículas
            while (showParticles) {
                delay(16L) // 60 FPS
                particles = particles.mapNotNull { particle ->
                    val newParticle = particle.copy(
                        x = particle.x + particle.vx,
                        y = particle.y + particle.vy,
                        alpha = particle.alpha - 0.01f,
                        life = particle.life - 0.01f
                    )
                    if (newParticle.life > 0f && newParticle.alpha > 0f) newParticle else null
                }
            }
        }
    }

    ModalDrawerSheet(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .fillMaxHeight()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
        ) {
            // Canvas para partículas de fondo
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                // Dibuja partículas flotantes
                particles.forEach { particle ->
                    drawCircle(
                        color = particle.color.copy(alpha = particle.alpha),
                        radius = particle.size,
                        center = androidx.compose.ui.geometry.Offset(particle.x, particle.y)
                    )
                }

                // Efecto de ondas expansivas
                if (showParticles) {
                    val centerX = size.width / 2
                    val centerY = size.height / 2

                    repeat(3) { i ->
                        val radius = (scale * 100f * (i + 1)).coerceAtMost(size.width / 2)
                        drawCircle(
                            color = if (isDark) Color(0xFF00FFF0).copy(alpha = 0.1f)
                                   else Color(0xFF00D4FF).copy(alpha = 0.1f),
                            radius = radius,
                            center = androidx.compose.ui.geometry.Offset(centerX, centerY),
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx())
                        )
                    }
                }

                // Estrellas brillantes para modo oscuro
                if (isDark) {
                    repeat(20) { i ->
                        val x = (i * 37f) % size.width
                        val y = (i * 73f) % size.height
                        val sparkle = sin(rotation * 0.1f + i) * 0.5f + 0.5f

                        drawCircle(
                            color = Color.White.copy(alpha = sparkle * 0.8f),
                            radius = 2f,
                            center = androidx.compose.ui.geometry.Offset(x, y)
                        )
                    }
                }
            }

            // Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Botón de cerrar con animación
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .scale(scale)
                            .clip(CircleShape)
                            .background(
                                if (isDark) Color(0xFF1A1D29).copy(alpha = 0.8f)
                                else Color.White.copy(alpha = 0.8f)
                            )
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = if (isDark) Color(0xFF00FFF0) else Color(0xFF00D4FF)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Título con efecto brillante
                Text(
                    text = "✨ CONTROL DE TEMA ✨",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 20.sp,
                        letterSpacing = 2.sp
                    ),
                    color = if (isDark) Color(0xFF00FFF0) else Color(0xFF00D4FF),
                    modifier = Modifier
                        .scale(scale)
                        .alpha(0.9f)
                )

                Spacer(modifier = Modifier.height(60.dp))

                // Interruptor principal ultra animado
                Card(
                    modifier = Modifier
                        .size(200.dp)
                        .scale(scale)
                        .rotate(rotation * 0.1f)
                        .clickable {
                            themeViewModel.toggleTheme()
                            showParticles = true
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if (isDark) Color(0xFF1A1D29).copy(alpha = 0.9f)
                                        else Color.White.copy(alpha = 0.9f)
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = if (showParticles) 20.dp else 8.dp
                    ),
                    shape = CircleShape
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                width = 3.dp,
                                brush = Brush.radialGradient(
                                    colors = if (isDark) listOf(
                                        Color(0xFF00FFF0),
                                        Color(0xFF0099CC),
                                        Color.Transparent
                                    ) else listOf(
                                        Color(0xFF00D4FF),
                                        Color(0xFF4ECDC4),
                                        Color.Transparent
                                    )
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        // En lugar de usar iconos problemáticos, usaremos texto emoji
                        Text(
                            text = if (isDark) "🌙" else "☀️",
                            fontSize = 48.sp,
                            modifier = Modifier
                                .rotate(rotation)
                                .scale(scale)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Texto descriptivo con animación
                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically() + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    Text(
                        text = if (isDark)
                            "🌙 Modo Nocturno Activado\nPerfecto para trabajar en la oscuridad"
                        else
                            "☀️ Modo Diurno Activado\nIdeal para máxima productividad",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 24.sp
                        ),
                        color = if (isDark) Color(0xFFE0F8FF).copy(alpha = 0.8f)
                               else Color(0xFF1B2951).copy(alpha = 0.8f),
                        modifier = Modifier.scale(scale * 0.9f)
                    )
                }

                Spacer(modifier = Modifier.height(60.dp))

                // Botones de acción adicionales
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Botón de efectos
                    Button(
                        onClick = {
                            showParticles = !showParticles
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isDark) Color(0xFF006B7D) else Color(0xFF4ECDC4)
                        ),
                        modifier = Modifier.scale(scale * 0.8f)
                    ) {
                        Text("🎆 Efectos")
                    }

                    // Botón de aplicar
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isDark) Color(0xFF00FFF0) else Color(0xFF00D4FF),
                            contentColor = if (isDark) Color.Black else Color.White
                        ),
                        modifier = Modifier.scale(scale * 0.8f)
                    ) {
                        Text("✓ Aplicar")
                    }
                }
            }

            // Efecto de resplandor en las esquinas
            if (showParticles) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(20.dp)
                ) {
                    drawCircle(
                        color = if (isDark) Color(0xFF00FFF0).copy(alpha = 0.3f)
                               else Color(0xFF00D4FF).copy(alpha = 0.3f),
                        radius = 100f,
                        center = androidx.compose.ui.geometry.Offset(0f, 0f)
                    )

                    drawCircle(
                        color = if (isDark) Color(0xFF0099CC).copy(alpha = 0.3f)
                               else Color(0xFF4ECDC4).copy(alpha = 0.3f),
                        radius = 100f,
                        center = androidx.compose.ui.geometry.Offset(size.width, size.height)
                    )
                }
            }
        }
    }

    // Reset de partículas después de la animación
    LaunchedEffect(isDark) {
        delay(2000L)
        showParticles = false
        particles = emptyList()
    }
}
