package com.tuempresa.possystem.presentation.homeeco

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.ImportExport
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tuempresa.possystem.ui.componentes.GridMenuEco
import com.tuempresa.possystem.ui.componentes.ItemMenuEco
import com.tuempresa.possystem.ui.theme.EcoSuperficie
import com.tuempresa.possystem.ui.theme.EcoTextoPrimario
import com.tuempresa.possystem.ui.theme.EcoTextoSecundario

/**
 * Menú en grid 2 columnas, estilo referencia. Cada ítem navega a una ruta
 * que ya existe en el proyecto (ajustes_boleta, ajustes_impresora, usuarios,
 * inventario_consulta...); "PMF", "Descuento", "Unidad", "Copia de
 * seguridad", "Importar Productos" y "Contacto" no tienen todavía pantalla
 * propia en el proyecto, así que se marcan como "proximamente/<id>" igual
 * que el resto de secciones no implementadas en GrafoNavegacionPrincipal.
 */
@Composable
fun PantallaMenuEco(
    onNavegar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var bannerVisible by remember { mutableStateOf(true) }

    val items = listOf(
        ItemMenuEco("ajustes", Icons.Filled.Settings, "Ajustes"),
        ItemMenuEco("usuarios", Icons.Filled.People, "Cliente"),
        ItemMenuEco("pmf", Icons.Filled.Help, "PMF"),
        ItemMenuEco("descuento", Icons.Filled.Sell, "Descuento"),
        ItemMenuEco("unidad", Icons.Filled.Straighten, "Unidad"),
        ItemMenuEco("etiqueta_pago", Icons.Filled.CreditCard, "Etiqueta de Pago"),
        ItemMenuEco("backup", Icons.Filled.CloudUpload, "Copia de seguridad"),
        ItemMenuEco("importar_productos", Icons.Filled.ImportExport, "Importar Productos"),
        ItemMenuEco("ajustes_impresora", Icons.Filled.Print, "Impresora"),
        ItemMenuEco("politica_privacidad", Icons.Filled.Shield, "Política de Privacidad")
    )

    Column(modifier = modifier.fillMaxSize()) {
        if (bannerVisible) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(EcoSuperficie)
                    .padding(18.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Novedades", color = EcoTextoPrimario, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                    Text(
                        "Explora las últimas funciones agregadas a tu POS.",
                        color = EcoTextoSecundario,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Cerrar",
                    tint = EcoTextoSecundario,
                    modifier = Modifier.clickable { bannerVisible = false }
                )
            }
        }

        GridMenuEco(
            items = items,
            onClick = { item ->
                when (item.id) {
                    "ajustes" -> onNavegar("ajustes_eco")
                    "usuarios" -> onNavegar("usuarios")
                    "ajustes_impresora" -> onNavegar("ajustes_impresora")
                    else -> onNavegar("proximamente/${item.id}")
                }
            }
        )
    }
}
