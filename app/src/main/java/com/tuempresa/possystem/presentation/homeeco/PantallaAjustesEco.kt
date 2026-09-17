package com.tuempresa.possystem.presentation.homeeco

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Unarchive
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tuempresa.possystem.ui.componentes.HeaderEco
import com.tuempresa.possystem.ui.componentes.SwitchEco
import com.tuempresa.possystem.ui.theme.EcoTextoPrimario
import com.tuempresa.possystem.ui.theme.EcoTextoSecundario

private sealed class FilaAjuste {
    abstract val icono: ImageVector
    abstract val titulo: String

    data class Valor(override val icono: ImageVector, override val titulo: String, val valor: String) : FilaAjuste()
    data class Interruptor(override val icono: ImageVector, override val titulo: String, val activo: Boolean, val onCambio: (Boolean) -> Unit) : FilaAjuste()
    data class Navegable(override val icono: ImageVector, override val titulo: String, val onClick: () -> Unit) : FilaAjuste()
}

/**
 * Pantalla de Ajustes generales del POS, estilo referencia: secciones en
 * mayúsculas (GENERAL, CARRO, TRANSACCIÓN) con filas de ícono + label +
 * valor/switch/chevron. Los valores mostrados (USD/moneda, decimales, orden
 * del carrito) son de UI local por ahora: no hay todavía un
 * ConfiguracionAppViewModel persistido para estas preferencias específicas,
 * a diferencia de PantallaAjustesBoleta/Impresora que sí son reales y persistidas.
 */
@Composable
fun PantallaAjustesEco(onVolver: () -> Unit, modifier: Modifier = Modifier) {
    var mostrarDecimales by remember { mutableStateOf(true) }
    var escaneoConSonido by remember { mutableStateOf(true) }
    var ocultarAnuladas by remember { mutableStateOf(true) }
    var anuladaRepone by remember { mutableStateOf(true) }

    Column(modifier = modifier.fillMaxSize()) {
        HeaderEco(titulo = "Ajustes", onNavegarAtras = onVolver)

        LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
            item { TituloSeccionAjuste("GENERAL") }
            item { FilaAjusteValor(Icons.Filled.AttachMoney, "Divisa", "PEN") }
            item {
                FilaAjusteSwitch(Icons.Filled.Numbers, "Mostrar puntos decimales", mostrarDecimales) {
                    mostrarDecimales = it
                }
            }
            item { FilaAjusteValor(Icons.Filled.Palette, "Tema", "Oscuro") }

            item { TituloSeccionAjuste("CARRO") }
            item { FilaAjusteValor(Icons.Filled.Sort, "Ordenar", "Nuevo producto en la parte superior") }
            item {
                FilaAjusteSwitch(Icons.Filled.QrCodeScanner, "Escaneo de sonido", escaneoConSonido) {
                    escaneoConSonido = it
                }
            }

            item { TituloSeccionAjuste("TRANSACCIÓN") }
            item { FilaAjusteNavegable(Icons.Filled.Receipt, "Recibo", onClick = {}) }
            item { FilaAjusteNavegable(Icons.Filled.Calculate, "Cálculo de tarifas", onClick = {}) }
            item { FilaAjusteValor(Icons.Filled.ConfirmationNumber, "Número de Cola", "APAGADO") }
            item { FilaAjusteNavegable(Icons.Filled.Tag, "Número de factura", onClick = {}) }
            item {
                FilaAjusteSwitch(Icons.Filled.VisibilityOff, "Ocultar transacción anulada", ocultarAnuladas) {
                    ocultarAnuladas = it
                }
            }
            item {
                FilaAjusteSwitch(Icons.Filled.Unarchive, "Anulada repondrá stock", anuladaRepone) {
                    anuladaRepone = it
                }
            }
        }
    }
}

@Composable
private fun TituloSeccionAjuste(texto: String) {
    Text(
        texto,
        color = EcoTextoPrimario,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
    )
}

@Composable
private fun FilaAjusteValor(icono: ImageVector, titulo: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icono, contentDescription = null, tint = EcoTextoPrimario, modifier = Modifier.padding(end = 16.dp))
        Text(titulo, color = EcoTextoPrimario, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        Text(valor, color = EcoTextoSecundario, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun FilaAjusteSwitch(icono: ImageVector, titulo: String, activo: Boolean, onCambio: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icono, contentDescription = null, tint = EcoTextoPrimario, modifier = Modifier.padding(end = 16.dp))
        Text(titulo, color = EcoTextoPrimario, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        SwitchEco(checked = activo, onCheckedChange = onCambio)
    }
}

@Composable
private fun FilaAjusteNavegable(icono: ImageVector, titulo: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icono, contentDescription = null, tint = EcoTextoPrimario, modifier = Modifier.padding(end = 16.dp))
        Text(titulo, color = EcoTextoPrimario, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = EcoTextoSecundario)
    }
}
