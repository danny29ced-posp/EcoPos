package com.tuempresa.possystem.presentation.homeeco

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.tuempresa.possystem.POSApplication
import com.tuempresa.possystem.ui.componentes.BarraNavegacionEco
import com.tuempresa.possystem.ui.componentes.ItemBarraEco
import com.tuempresa.possystem.ui.theme.EcoFondo

private val TABS = listOf(
    ItemBarraEco("caja", Icons.Filled.PointOfSale, "Caja"),
    ItemBarraEco("productos", Icons.Filled.Inventory2, "Productos"),
    ItemBarraEco("transacciones", Icons.Filled.Receipt, "Transacciones"),
    ItemBarraEco("reportes", Icons.Filled.Assessment, "Reportes"),
    ItemBarraEco("menu", Icons.Filled.Menu, "Menú")
)

/**
 * Contenedor con la barra inferior de 5 pestañas (Caja, Productos,
 * Transacciones, Reportes, Menú), igual a la referencia. Vive como pantalla
 * adicional en el grafo de navegación ("caja_principal"), sin reemplazar
 * PantallaHomeAdmin/PantallaHomeVendedor existentes.
 */
@Composable
fun PantallaPrincipalEco(
    app: POSApplication,
    onIrANuevaVenta: () -> Unit,
    onAbrirCarritoActivo: () -> Unit,
    onAgregarProducto: () -> Unit,
    onAbrirProducto: (String) -> Unit,
    onAbrirVenta: (String) -> Unit,
    onNavegarDesdeMenu: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var tabActiva by remember { mutableStateOf("caja") }

    Surface(modifier = modifier.fillMaxSize(), color = EcoFondo) {
        androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxSize()) {
            androidx.compose.foundation.layout.Box(modifier = Modifier.weight(1f)) {
                when (tabActiva) {
                    "caja" -> PantallaCajaEco(
                        app = app,
                        onIrANuevaVenta = onIrANuevaVenta,
                        onAbrirCarritoActivo = onAbrirCarritoActivo
                    )
                    "productos" -> PantallaProductosEco(
                        app = app,
                        onAgregarProducto = onAgregarProducto,
                        onAbrirProducto = onAbrirProducto
                    )
                    "transacciones" -> PantallaTransaccionesEco(app = app, onAbrirVenta = onAbrirVenta)
                    "reportes" -> PantallaReportesEco(app = app)
                    "menu" -> PantallaMenuEco(onNavegar = onNavegarDesdeMenu)
                }
            }
            BarraNavegacionEco(
                items = TABS,
                seleccionadoId = tabActiva,
                onSeleccionar = { tabActiva = it }
            )
        }
    }
}
