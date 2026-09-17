package com.tuempresa.possystem.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.tuempresa.possystem.ui.theme.EcoInput
import com.tuempresa.possystem.ui.theme.EcoTextoPrimario
import com.tuempresa.possystem.ui.theme.EcoTextoSecundario
import com.tuempresa.possystem.ui.theme.EcoVerde

/**
 * Input estilo "píldora" (esquinas muy redondeadas) como en Nombre de la
 * tienda / Nombre del producto / Nombre-SKU-Código de barras de la referencia.
 * Sin foco: fondo gris, sin borde visible. Con foco: borde verde de 1.5dp.
 */
@Composable
fun CampoTextoEco(
    valor: String,
    onValorCambia: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    iconoInicial: ImageVector? = null,
    sufijo: (@Composable () -> Unit)? = null,
    soloLectura: Boolean = false,
    teclado: KeyboardType = KeyboardType.Text,
    lineasMax: Int = 1,
    transformacionVisual: VisualTransformation = VisualTransformation.None
) {
    val interaccion = remember { MutableInteractionSource() }
    val tieneFoco by interaccion.collectIsFocusedAsState()
    val forma = RoundedCornerShape(28.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(forma)
            .background(EcoInput, forma)
            .border(1.5.dp, if (tieneFoco) EcoVerde else EcoInput, forma)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (iconoInicial != null) {
            Icon(
                imageVector = iconoInicial,
                contentDescription = null,
                tint = EcoTextoSecundario,
                modifier = Modifier.padding(end = 12.dp)
            )
        }
        Box(modifier = Modifier.weight(1f)) {
            if (valor.isEmpty() && placeholder.isNotEmpty()) {
                Text(placeholder, color = EcoTextoSecundario, style = MaterialTheme.typography.bodyLarge)
            }
            BasicTextField(
                value = valor,
                onValueChange = onValorCambia,
                readOnly = soloLectura,
                textStyle = TextStyle(
                    color = EcoTextoPrimario,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                ),
                cursorBrush = SolidColor(EcoVerde),
                interactionSource = interaccion,
                keyboardOptions = KeyboardOptions(keyboardType = teclado),
                maxLines = lineasMax,
                visualTransformation = transformacionVisual,
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (sufijo != null) {
            sufijo()
        }
    }
}

/** Igual que [CampoTextoEco] pero con el símbolo de moneda como prefijo fijo (ej. Precio de venta, Costo). */
@Composable
fun CampoMonedaEco(
    valor: String,
    onValorCambia: (String) -> Unit,
    modifier: Modifier = Modifier,
    simboloMoneda: String = "S/",
    resaltarBorde: Boolean = false
) {
    val interaccion = remember { MutableInteractionSource() }
    val tieneFoco by interaccion.collectIsFocusedAsState()
    val forma = RoundedCornerShape(28.dp)
    val mostrarBorde = tieneFoco || resaltarBorde

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(forma)
            .background(EcoInput, forma)
            .border(1.5.dp, if (mostrarBorde) EcoVerde else EcoInput, forma)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            simboloMoneda,
            color = EcoTextoPrimario,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(end = 10.dp)
        )
        Box(modifier = Modifier.weight(1f)) {
            if (valor.isEmpty()) {
                Text("0", color = EcoTextoSecundario, style = MaterialTheme.typography.bodyLarge)
            }
            BasicTextField(
                value = valor,
                onValueChange = onValorCambia,
                textStyle = TextStyle(color = EcoTextoPrimario, fontSize = MaterialTheme.typography.bodyLarge.fontSize),
                cursorBrush = SolidColor(EcoVerde),
                interactionSource = interaccion,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/** Campo de búsqueda con lupa, usado en encabezados de listas (Productos, Transacciones). */
@Composable
fun CampoBusquedaEco(
    valor: String,
    onValorCambia: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    CampoTextoEco(
        valor = valor,
        onValorCambia = onValorCambia,
        placeholder = placeholder,
        iconoInicial = Icons.Filled.Search,
        modifier = modifier
    )
}

/** Etiqueta de sección tipo "Nombre de la tienda", encima de un campo. */
@Composable
fun EtiquetaCampoEco(texto: String, modifier: Modifier = Modifier) {
    Text(
        text = texto,
        color = EcoTextoPrimario,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier.padding(bottom = 8.dp)
    )
}
