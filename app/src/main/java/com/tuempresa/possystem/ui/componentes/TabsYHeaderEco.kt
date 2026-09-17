package com.tuempresa.possystem.ui.componentes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tuempresa.possystem.ui.theme.EcoAzul
import com.tuempresa.possystem.ui.theme.EcoFondoHeader
import com.tuempresa.possystem.ui.theme.EcoInput
import com.tuempresa.possystem.ui.theme.EcoTextoPrimario
import com.tuempresa.possystem.ui.theme.EcoTextoSecundario

/** Una pestaña del segmented control (ej. Info / Precio / Opciones). */
data class TabEco(val id: String, val etiqueta: String, val icono: ImageVector? = null)

/**
 * Segmented control estilo "INFO | PRECIO | OPCIONES": fondo transparente,
 * la pestaña activa tiene fondo gris redondeado.
 */
@Composable
fun TabsSegmentadosEco(
    tabs: List<TabEco>,
    seleccionadoId: String,
    onSeleccionar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        tabs.forEach { tab ->
            val activo = tab.id == seleccionadoId
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(if (activo) EcoInput else androidx.compose.ui.graphics.Color.Transparent)
                    .clickable { onSeleccionar(tab.id) }
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (tab.icono != null) {
                    Icon(
                        tab.icono,
                        contentDescription = null,
                        tint = if (activo) EcoTextoPrimario else EcoTextoSecundario,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                }
                Text(
                    tab.etiqueta.uppercase(),
                    color = if (activo) EcoTextoPrimario else EcoTextoSecundario,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

/** Switch estilo iOS con el thumb azul-violeta cuando está activado, igual al de Ajustes. */
@Composable
fun SwitchEco(checked: Boolean, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        colors = SwitchDefaults.colors(
            checkedThumbColor = EcoAzul,
            checkedTrackColor = EcoAzul.copy(alpha = 0.4f),
            uncheckedThumbColor = EcoTextoSecundario,
            uncheckedTrackColor = EcoInput
        )
    )
}

/**
 * Header estándar reutilizable: back o close a la izquierda, título (y
 * subtítulo opcional), acciones a la derecha. Cubre tanto el header simple
 * de "‹ Ajustes" como el de "✕ Añadir Producto" con tabs debajo.
 */
@Composable
fun HeaderEco(
    titulo: String,
    modifier: Modifier = Modifier,
    subtitulo: String? = null,
    usarCerrarEnVezDeAtras: Boolean = false,
    onNavegarAtras: (() -> Unit)? = null,
    acciones: (@Composable Row.() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(EcoFondoHeader)
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onNavegarAtras != null) {
            Icon(
                imageVector = if (usarCerrarEnVezDeAtras) Icons.Filled.Close else Icons.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = EcoTextoPrimario,
                modifier = Modifier
                    .clickable(onClick = onNavegarAtras)
                    .padding(end = 16.dp)
            )
        }
        Box(modifier = Modifier.weight(1f)) {
            androidx.compose.foundation.layout.Column {
                Text(
                    titulo,
                    color = EcoTextoPrimario,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
                if (subtitulo != null) {
                    Text(
                        subtitulo,
                        color = EcoTextoSecundario,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
        if (acciones != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                acciones()
            }
        }
    }
}
