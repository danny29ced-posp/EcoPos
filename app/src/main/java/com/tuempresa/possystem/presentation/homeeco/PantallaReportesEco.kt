package com.tuempresa.possystem.presentation.homeeco

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tuempresa.possystem.POSApplication
import com.tuempresa.possystem.presentation.inventario.fabricaSimple
import com.tuempresa.possystem.presentation.reportes.ReportesViewModel
import com.tuempresa.possystem.ui.componentes.BloqueSinDatosEco
import com.tuempresa.possystem.ui.componentes.FilaResumenEco
import com.tuempresa.possystem.ui.componentes.TarjetaResumenEco
import com.tuempresa.possystem.ui.componentes.TituloSeccionConFlechaEco
import com.tuempresa.possystem.ui.theme.EcoCian
import com.tuempresa.possystem.ui.theme.EcoFondoHeader
import com.tuempresa.possystem.ui.theme.EcoRojo
import com.tuempresa.possystem.ui.theme.EcoTextoPrimario
import com.tuempresa.possystem.ui.theme.EcoTextoSecundario
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Reportes del día rediseñado con la tarjeta de resumen (Transacción,
 * Ingresos, Gastos, Lucro) y bloques "Mejores productos" / "categorías",
 * usando los datos reales de ReportesViewModel (resumenTurno, masVendidosHoy).
 * No hay concepto de "Pendiente" ni "Gastos" en el dominio actual, así que
 * esas filas se muestran en 0 en vez de simular datos.
 */
@Composable
fun PantallaReportesEco(app: POSApplication, modifier: Modifier = Modifier) {
    val viewModel: ReportesViewModel = viewModel(factory = fabricaSimple { ReportesViewModel(app) })
    val resumen by viewModel.resumenTurno.collectAsState()
    val masVendidos by viewModel.masVendidosHoy.collectAsState()

    val formatoFecha = remember(Unit) { SimpleDateFormat("d MMM. yyyy", Locale("es", "PE")) }

    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Text("Hoy", color = EcoTextoPrimario, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineSmall)
            Text(
                formatoFecha.format(Date()),
                color = EcoTextoSecundario,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 20.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                TarjetaResumenEco(
                    filas = listOf(
                        FilaResumenEco("Transacción", "${resumen?.numeroTransacciones ?: 0}"),
                        FilaResumenEco("Ingresos", "S/ ${"%.2f".format(resumen?.totalVentas ?: 0.0)}", colorValor = EcoCian),
                        FilaResumenEco("Descuentos", "S/ ${"%.2f".format(resumen?.totalDescuentos ?: 0.0)}", colorValor = EcoRojo),
                        FilaResumenEco("Impuestos", "S/ ${"%.2f".format(resumen?.totalImpuestos ?: 0.0)}")
                    )
                )
            }
            item {
                Column {
                    TituloSeccionConFlechaEco(icono = Icons.Filled.Inventory2, titulo = "Mejores productos")
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(12.dp))
                    if (masVendidos.isEmpty()) {
                        BloqueSinDatosEco()
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            masVendidos.take(5).forEach { producto ->
                                Text(
                                    "${producto.nombreProducto} · ${producto.unidadesVendidas} und · S/ ${"%.2f".format(producto.totalVendido)}",
                                    color = EcoTextoPrimario,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
            item {
                Column {
                    TituloSeccionConFlechaEco(icono = Icons.Filled.Category, titulo = "Mejores categorías")
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(12.dp))
                    BloqueSinDatosEco()
                }
            }
        }
    }
}
