package com.tuempresa.possystem.presentation.homeeco

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tuempresa.possystem.POSApplication
import com.tuempresa.possystem.data.local.entity.EstadoVenta
import com.tuempresa.possystem.data.local.entity.VentaEntity
import com.tuempresa.possystem.presentation.inventario.fabricaSimple
import com.tuempresa.possystem.presentation.venta.MisVentasViewModel
import com.tuempresa.possystem.ui.componentes.CampoBusquedaEco
import com.tuempresa.possystem.ui.componentes.EstadoVacioEco
import com.tuempresa.possystem.ui.theme.EcoFondoHeader
import com.tuempresa.possystem.ui.theme.EcoRojo
import com.tuempresa.possystem.ui.theme.EcoSuperficie
import com.tuempresa.possystem.ui.theme.EcoTextoPrimario
import com.tuempresa.possystem.ui.theme.EcoTextoSecundario
import com.tuempresa.possystem.ui.theme.EcoVerdeIngreso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Transacciones de hoy, rediseñada estilo referencia ("Hoy · Transacción &
 * Gastos" + búsqueda + lista o estado vacío "Sin transacción"). Usa
 * MisVentasViewModel.ventasDeHoy real; la búsqueda filtra por folio.
 */
@Composable
fun PantallaTransaccionesEco(
    app: POSApplication,
    onAbrirVenta: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: MisVentasViewModel = viewModel(factory = fabricaSimple { MisVentasViewModel(app) })
    val ventas by viewModel.ventasDeHoy.collectAsState()
    var textoBusqueda by remember { mutableStateOf("") }

    val ventasFiltradas = remember(ventas, textoBusqueda) {
        if (textoBusqueda.isBlank()) {
            ventas
        } else {
            val q = textoBusqueda.trim().lowercase()
            ventas.filter { (it.folio?.toString() ?: it.id).lowercase().contains(q) }
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(EcoFondoHeader)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text("Hoy", color = EcoTextoPrimario, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineSmall)
            Text(
                "Transacción & Gastos",
                color = EcoTextoSecundario,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
            )
            CampoBusquedaEco(
                valor = textoBusqueda,
                onValorCambia = { textoBusqueda = it },
                placeholder = "Factura / SKU / Notas"
            )
        }

        if (ventasFiltradas.isEmpty()) {
            EstadoVacioEco(
                icono = Icons.Filled.EditNote,
                texto = if (ventas.isEmpty()) "Sin transacción" else "Ninguna transacción coincide con tu búsqueda",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(ventasFiltradas, key = { it.id }) { venta ->
                    TarjetaVentaEco(venta = venta, onClick = { onAbrirVenta(venta.id) })
                }
            }
        }
    }
}

@Composable
private fun TarjetaVentaEco(venta: VentaEntity, onClick: () -> Unit) {
    val formatoHora = remember(Unit) { SimpleDateFormat("HH:mm", Locale.getDefault()) }
    val anulada = venta.estado == EstadoVenta.ANULADA

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(EcoSuperficie)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                "Folio ${venta.folio ?: "—"}",
                color = EcoTextoPrimario,
                fontWeight = FontWeight.Bold
            )
            Text(
                "${formatoHora.format(Date(venta.fecha))} · ${venta.metodoPago}",
                color = EcoTextoSecundario,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
        Text(
            "S/ ${"%.2f".format(venta.total)}",
            color = if (anulada) EcoRojo else EcoVerdeIngreso,
            fontWeight = FontWeight.Bold
        )
    }
}
