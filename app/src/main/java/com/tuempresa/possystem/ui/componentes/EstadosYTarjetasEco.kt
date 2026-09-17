package com.tuempresa.possystem.ui.componentes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tuempresa.possystem.ui.theme.EcoAzul
import com.tuempresa.possystem.ui.theme.EcoDivisor
import com.tuempresa.possystem.ui.theme.EcoInput
import com.tuempresa.possystem.ui.theme.EcoSuperficie
import com.tuempresa.possystem.ui.theme.EcoTextoPrimario
import com.tuempresa.possystem.ui.theme.EcoTextoSecundario
import com.tuempresa.possystem.ui.theme.EcoVerdeTexto

/**
 * Estado vacío centrado: ícono outline grande + texto gris. Igual al de
 * "No has agregado ningún producto" / "Sin transacción".
 */
@Composable
fun EstadoVacioEco(
    icono: ImageVector,
    texto: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = EcoTextoSecundario,
            modifier = Modifier.size(72.dp)
        )
        Text(
            text = texto,
            color = EcoTextoPrimario,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp, start = 24.dp, end = 24.dp)
        )
    }
}

/** Botón flotante circular azul-violeta con "+", como en Productos / Descuento / Unidad. */
@Composable
fun FabAgregarEco(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(EcoAzul)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Agregar", tint = Color.White)
    }
}

/** Una fila de la tarjeta de resumen de Reportes (Transacción, Pendiente, Ingresos...). */
data class FilaResumenEco(
    val etiqueta: String,
    val valor: String,
    val colorValor: Color = EcoTextoPrimario,
    val onClick: (() -> Unit)? = null
)

/** Tarjeta de resumen con filas separadas por divisores y chevron a la derecha. */
@Composable
fun TarjetaResumenEco(filas: List<FilaResumenEco>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(EcoSuperficie)
            .padding(horizontal = 20.dp)
    ) {
        filas.forEachIndexed { indice, fila ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(if (fila.onClick != null) Modifier.clickable(onClick = fila.onClick) else Modifier)
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(fila.etiqueta, color = EcoTextoSecundario, style = MaterialTheme.typography.bodyMedium)
                    Text(
                        fila.valor,
                        color = fila.colorValor,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = EcoTextoSecundario)
            }
            if (indice != filas.lastIndex) {
                androidx.compose.material3.Divider(color = EcoDivisor, thickness = 1.dp)
            }
        }
    }
}

/** Bloque simple "Sin datos" usado en Mejores productos / Mejores categorías. */
@Composable
fun BloqueSinDatosEco(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(EcoSuperficie)
            .padding(vertical = 28.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Sin datos", color = EcoTextoSecundario, style = MaterialTheme.typography.bodyLarge)
    }
}

/** Encabezado de sección tipo "MEJORES PRODUCTOS »" con ícono a la izquierda y chevron doble a la derecha. */
@Composable
fun TituloSeccionConFlechaEco(
    icono: ImageVector,
    titulo: String,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icono, contentDescription = null, tint = EcoTextoPrimario, modifier = Modifier.size(20.dp))
        Text(
            titulo.uppercase(),
            color = EcoTextoPrimario,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f).padding(start = 10.dp)
        )
        Text("»", color = EcoTextoPrimario, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
    }
}

/** Un ítem cuadrado del grid del Menú (Ajustes, Cliente, PMF, Descuento...). */
data class ItemMenuEco(
    val id: String,
    val icono: ImageVector,
    val titulo: String
)

/** Grid de 2 columnas para la pantalla de Menú, con ítems cuadrados oscuros + label debajo. */
@Composable
fun GridMenuEco(
    items: List<ItemMenuEco>,
    onClick: (ItemMenuEco) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(20.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        items(items, key = { it.id }) { item ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(18.dp))
                        .background(EcoInput.copy(alpha = 0.35f))
                        .clickable { onClick(item) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(item.icono, contentDescription = item.titulo, tint = EcoTextoPrimario, modifier = Modifier.size(30.dp))
                }
                Text(
                    item.titulo,
                    color = EcoTextoPrimario,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
    }
}
