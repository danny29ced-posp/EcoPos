package com.tuempresa.possystem.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val EsquemaEco = darkColorScheme(
    primary = EcoVerde,
    onPrimary = EcoTextoSobreVerde,
    secondary = EcoAzul,
    onSecondary = EcoTextoSobreAzul,
    background = EcoFondo,
    onBackground = EcoTextoPrimario,
    surface = EcoSuperficie,
    onSurface = EcoTextoPrimario,
    surfaceVariant = EcoInput,
    onSurfaceVariant = EcoTextoSecundario,
    error = EcoRojo,
    onError = EcoTextoPrimario,
    outline = EcoInputBorde
)

/**
 * Tema oscuro estilo "Eco" para pantallas nuevas. Se ignora el tema claro del
 * sistema a propósito: la referencia de diseño es siempre oscura.
 */
@Composable
fun POSSystemEcoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaEco,
        typography = TipografiaEco,
        content = content
    )
}
