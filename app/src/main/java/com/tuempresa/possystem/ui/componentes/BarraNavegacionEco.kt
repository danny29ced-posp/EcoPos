package com.tuempresa.possystem.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.tuempresa.possystem.ui.theme.EcoFondoHeader
import com.tuempresa.possystem.ui.theme.EcoTextoPrimario
import com.tuempresa.possystem.ui.theme.EcoVerde
import com.tuempresa.possystem.ui.theme.EcoVerdeTexto

/** Una pestaña de la barra inferior. */
data class ItemBarraEco(
    val id: String,
    val icono: ImageVector,
    val descripcion: String
)

/**
 * Barra inferior de 5 accesos (Caja, Productos, Transacciones, Reportes, Menú)
 * igual a la de la referencia: el ítem activo muestra su ícono sobre una
 * píldora verde; los inactivos son solo el ícono en blanco sobre negro.
 */
@Composable
fun BarraNavegacionEco(
    items: List<ItemBarraEco>,
    seleccionadoId: String,
    onSeleccionar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(EcoFondoHeader)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val activo = item.id == seleccionadoId
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (activo) EcoVerde else androidx.compose.ui.graphics.Color.Transparent)
                    .clickable { onSeleccionar(item.id) }
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = item.icono,
                    contentDescription = item.descripcion,
                    tint = if (activo) EcoVerdeTexto else EcoTextoPrimario,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
