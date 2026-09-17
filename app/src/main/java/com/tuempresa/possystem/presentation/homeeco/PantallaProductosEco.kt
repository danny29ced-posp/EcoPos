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
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tuempresa.possystem.POSApplication
import com.tuempresa.possystem.data.local.entity.ProductoEntity
import com.tuempresa.possystem.presentation.inventario.InventarioViewModel
import com.tuempresa.possystem.presentation.inventario.fabricaSimple
import com.tuempresa.possystem.ui.componentes.CampoBusquedaEco
import com.tuempresa.possystem.ui.componentes.EstadoVacioEco
import com.tuempresa.possystem.ui.componentes.FabAgregarEco
import com.tuempresa.possystem.ui.theme.EcoFondoHeader
import com.tuempresa.possystem.ui.theme.EcoSuperficie
import com.tuempresa.possystem.ui.theme.EcoTextoPrimario
import com.tuempresa.possystem.ui.theme.EcoTextoSecundario
import com.tuempresa.possystem.ui.theme.EcoVerde

/**
 * Lista de productos rediseñada estilo referencia ("Producto (N)" + búsqueda
 * + empty state con caja 3D + FAB azul). Reutiliza InventarioViewModel real:
 * no se inventan productos ni contadores, se filtra sobre `productos` real.
 */
@Composable
fun PantallaProductosEco(
    app: POSApplication,
    onAgregarProducto: () -> Unit,
    onAbrirProducto: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: InventarioViewModel = viewModel(factory = fabricaSimple { InventarioViewModel(app) })
    val productos by viewModel.productos.collectAsState()
    var textoBusqueda by remember { mutableStateOf("") }

    val productosFiltrados = remember(productos, textoBusqueda) {
        if (textoBusqueda.isBlank()) {
            productos
        } else {
            val q = textoBusqueda.trim().lowercase()
            productos.filter {
                it.nombre.lowercase().contains(q) ||
                    it.sku.lowercase().contains(q) ||
                    (it.codigoBarras?.lowercase()?.contains(q) == true)
            }
        }
    }

    androidx.compose.foundation.layout.Box(modifier = modifier.fillMaxSize()) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(EcoFondoHeader)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                "Producto (${productos.size})",
                color = EcoTextoPrimario,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
            CampoBusquedaEco(
                valor = textoBusqueda,
                onValorCambia = { textoBusqueda = it },
                placeholder = "Nombre / SKU / Código de barras",
                modifier = Modifier.padding(top = 14.dp)
            )
        }

        if (productosFiltrados.isEmpty()) {
            EstadoVacioEco(
                icono = Icons.Filled.Inventory2,
                texto = if (productos.isEmpty()) "No has agregado ningún producto" else "Ningún producto coincide con tu búsqueda",
                modifier = Modifier.fillMaxSize().padding(bottom = 80.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(productosFiltrados, key = { it.id }) { producto ->
                    TarjetaProductoEco(producto = producto, onClick = { onAbrirProducto(producto.id) })
                }
            }
        }
    }
    com.tuempresa.possystem.ui.componentes.FabAgregarEco(
        onClick = onAgregarProducto,
        modifier = Modifier
            .align(androidx.compose.ui.Alignment.BottomEnd)
            .padding(20.dp)
    )
    }
}

@Composable
private fun TarjetaProductoEco(producto: ProductoEntity, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(EcoSuperficie)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(producto.nombre, color = EcoTextoPrimario, fontWeight = FontWeight.Bold)
            Text(
                "S/ ${"%.2f".format(producto.precioVenta)} · SKU ${producto.sku} · Stock ${producto.stockActual}",
                color = EcoTextoSecundario,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
