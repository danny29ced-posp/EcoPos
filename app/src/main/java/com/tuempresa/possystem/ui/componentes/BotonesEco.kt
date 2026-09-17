package com.tuempresa.possystem.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tuempresa.possystem.ui.theme.EcoInput
import com.tuempresa.possystem.ui.theme.EcoTextoPrimario
import com.tuempresa.possystem.ui.theme.EcoTextoSecundario
import com.tuempresa.possystem.ui.theme.EcoVerde
import com.tuempresa.possystem.ui.theme.EcoVerdeTexto

/**
 * Botón principal tipo "GUARDAR" / "DESPUÉS": píldora verde, texto oscuro
 * en mayúsculas semibold. Cuando [habilitado] es false se atenúa (como
 * "DESPUÉS" y "GUARDAR" deshabilitados en los formularios de la referencia).
 */
@Composable
fun BotonPrimarioEco(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    habilitado: Boolean = true
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(EcoVerde)
            .alpha(if (habilitado) 1f else 0.5f)
            .clickable(enabled = habilitado, onClick = onClick)
            .padding(vertical = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto.uppercase(),
            color = EcoVerdeTexto,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

/** Botón secundario en píldora gris (usado por ejemplo para "BUSCAR" de archivos, o acciones neutrales). */
@Composable
fun BotonSecundarioEco(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(28.dp))
            .background(EcoInput)
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto.uppercase(),
            color = EcoTextoPrimario,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

/** Enlace de texto plano tipo "SALTAR" al pie de una pantalla. */
@Composable
fun BotonTextoEco(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = texto.uppercase(),
        color = EcoTextoPrimario,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
    )
}
