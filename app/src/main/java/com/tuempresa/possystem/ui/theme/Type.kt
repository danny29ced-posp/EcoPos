package com.tuempresa.possystem.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * La interfaz de referencia usa mucho peso bold en títulos y secciones,
 * con texto secundario regular más pequeño. Se define aquí de forma
 * centralizada para no repetir fontSize/fontWeight en cada pantalla.
 */
val TipografiaEco = Typography(
    headlineSmall = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
    titleLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
    titleMedium = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp),
    bodyMedium = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp),
    bodySmall = TextStyle(fontWeight = FontWeight.Normal, fontSize = 12.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),
    labelMedium = TextStyle(fontWeight = FontWeight.Medium, fontSize = 12.sp)
)

// Tamaños sueltos usados en textos "custom" (headers grandes tipo "Hoy", logo, etc.)
val EcoTamTituloHeader = 26.sp
val EcoTamLogo = 22.sp
