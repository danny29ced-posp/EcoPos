package com.tuempresa.possystem.presentation.homeeco

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tuempresa.possystem.POSApplication
import com.tuempresa.possystem.presentation.inventario.fabricaSimple
import com.tuempresa.possystem.presentation.venta.LineaCarrito
import com.tuempresa.possystem.presentation.venta.VentaViewModel
import com.tuempresa.possystem.ui.theme.EcoAzulTenue
import com.tuempresa.possystem.ui.theme.EcoFondo
import com.tuempresa.possystem.ui.theme.EcoFondoHeader
import com.tuempresa.possystem.ui.theme.EcoRojo
import com.tuempresa.possystem.ui.theme.EcoTextoPrimario
import com.tuempresa.possystem.ui.theme.EcoVerdeIngreso
import com.tuempresa.possystem.ui.theme.EcoVerdeTexto

/**
 * Pantalla "Caja" — home operativo del día a día, equivalente a la pantalla
 * principal de la referencia (Gastos / Ingresos / Nuevo orden / Carros activos).
 *
 * "Gastos" queda solo visual por ahora: el proyecto no maneja gasto de caja
 * como concepto propio todavía (a diferencia de Ingresos, que sí mapea 1:1
 * a iniciar una venta con VentaViewModel/PantallaVenta ya existentes).
 */
@Composable
fun PantallaCajaEco(
    app: POSApplication,
    onIrANuevaVenta: () -> Unit,
    onAbrirCarritoActivo: () -> Unit,
    modifier: Modifier = Modifier
) {
    val ventaViewModel: VentaViewModel = viewModel(factory = fabricaSimple { VentaViewModel(app) })
    val carrito by ventaViewModel.carrito.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(EcoFondoHeader)
                .padding(20.dp)
        ) {
            Text(
                "ECO POS",
                color = EcoTextoPrimario,
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TarjetaAccionCaja(
                titulo = "Gastos",
                icono = Icons.Filled.Receipt,
                colorFondo = EcoRojo,
                colorTexto = EcoVerdeTexto,
                onClick = { /* Pendiente: el proyecto aún no tiene módulo de gastos de caja. */ },
                modifier = Modifier.weight(1f)
            )
            TarjetaAccionCaja(
                titulo = "Ingresos",
                icono = Icons.Filled.AddShoppingCart,
                colorFondo = EcoVerdeIngreso,
                colorTexto = EcoVerdeTexto,
                onClick = onIrANuevaVenta,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(EcoAzulTenue)
                .clickable(onClick = onIrANuevaVenta)
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.AddShoppingCart, contentDescription = null, tint = EcoTextoPrimario, modifier = Modifier.size(28.dp))
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text("Nuevo orden", color = EcoTextoPrimario, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text(
                    "Crear nueva transacción con productos",
                    color = EcoTextoPrimario.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
                .background(EcoAzulTenue.copy(alpha = 0.6f))
                .padding(20.dp)
        ) {
            Text(
                "CARROS ACTIVOS",
                color = EcoTextoPrimario.copy(alpha = 0.7f),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            if (carrito.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Filled.ShoppingBag, contentDescription = null, tint = EcoTextoPrimario, modifier = Modifier.size(48.dp))
                    Text(
                        "No tienes carrito activo",
                        color = EcoTextoPrimario,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(carrito, key = { it.id }) { linea ->
                        TarjetaCarritoActivo(linea = linea, onClick = onAbrirCarritoActivo)
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaAccionCaja(
    titulo: String,
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    colorFondo: androidx.compose.ui.graphics.Color,
    colorTexto: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(colorFondo)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icono, contentDescription = null, tint = colorTexto, modifier = Modifier.size(20.dp))
        Text(
            titulo,
            color = colorTexto,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

@Composable
private fun TarjetaCarritoActivo(linea: LineaCarrito, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(EcoFondo.copy(alpha = 0.3f))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(linea.producto.nombre, color = EcoTextoPrimario, fontWeight = FontWeight.Bold)
            Text(
                "${linea.cantidad} × S/ ${"%.2f".format(linea.precioUnitario)} · ${linea.etiquetaEscalon}",
                color = EcoTextoPrimario.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
        Text(
            "S/ ${"%.2f".format(linea.subtotal)}",
            color = EcoTextoPrimario,
            fontWeight = FontWeight.Bold
        )
    }
}
