package com.tuempresa.possystem.presentation.homeeco

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tuempresa.possystem.POSApplication
import com.tuempresa.possystem.data.local.entity.CategoriaEntity
import com.tuempresa.possystem.presentation.inventario.ColorEnCaptura
import com.tuempresa.possystem.presentation.inventario.EstadoGuardado
import com.tuempresa.possystem.presentation.inventario.NuevoProductoViewModel
import com.tuempresa.possystem.presentation.inventario.PantallaAgregarColor
import com.tuempresa.possystem.presentation.inventario.TALLAS_DISPONIBLES
import com.tuempresa.possystem.presentation.inventario.TallaEnCaptura
import com.tuempresa.possystem.presentation.inventario.fabricaSimple
import com.tuempresa.possystem.presentation.venta.EscanerCodigoBarras
import com.tuempresa.possystem.presentation.venta.ModoEscaneo
import com.tuempresa.possystem.presentation.venta.tienePermisoCamara
import com.tuempresa.possystem.ui.componentes.BotonPrimarioEco
import com.tuempresa.possystem.ui.componentes.CampoMonedaEco
import com.tuempresa.possystem.ui.componentes.CampoTextoEco
import com.tuempresa.possystem.ui.componentes.HeaderEco
import com.tuempresa.possystem.ui.componentes.TabEco
import com.tuempresa.possystem.ui.componentes.TabsSegmentadosEco
import com.tuempresa.possystem.ui.theme.EcoAzul
import com.tuempresa.possystem.ui.theme.EcoFondo
import com.tuempresa.possystem.ui.theme.EcoInput
import com.tuempresa.possystem.ui.theme.EcoRojo
import com.tuempresa.possystem.ui.theme.EcoSuperficie
import com.tuempresa.possystem.ui.theme.EcoTextoPrimario
import com.tuempresa.possystem.ui.theme.EcoTextoSecundario
import com.tuempresa.possystem.ui.theme.EcoVerde
import com.tuempresa.possystem.ui.theme.EcoVerdeTexto

/**
 * "Añadir Producto" con la piel visual EcoPos (header ✕ + tabs INFO / PRECIO
 * / OPCIONES) pero sobre la lógica real del negocio de ropa: reutiliza
 * NuevoProductoViewModel sin cambios. La estructura del formulario original
 * de la referencia (un precio, un stock) no existe en este dominio — aquí
 * "PRECIO" son tallas con escalones, y "OPCIONES" son colores con stock por
 * talla, que es el equivalente real más cercano al concepto de "opciones"
 * de la referencia (variantes que afectan el producto final).
 */
@Composable
fun PantallaNuevoProductoEco(app: POSApplication, onVolver: () -> Unit, onGuardado: () -> Unit) {
    val viewModel: NuevoProductoViewModel = viewModel(factory = fabricaSimple { NuevoProductoViewModel(app) })

    val nombre by viewModel.nombre.collectAsState()
    val descripcion by viewModel.descripcion.collectAsState()
    val codigoBarras by viewModel.codigoBarras.collectAsState()
    val categorias by viewModel.categorias.collectAsState()
    val categoriaId by viewModel.categoriaId.collectAsState()
    val precioCompraTexto by viewModel.precioCompraTexto.collectAsState()
    val tallas by viewModel.tallas.collectAsState()
    val colores by viewModel.colores.collectAsState()
    val estadoGuardado by viewModel.estadoGuardado.collectAsState()

    var tabActiva by remember { mutableStateOf("info") }
    var mostrandoEscaner by remember { mutableStateOf(false) }
    var mostrandoAgregarColor by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val lanzadorPermiso = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido -> if (concedido) mostrandoEscaner = true }

    if (estadoGuardado is EstadoGuardado.Exitoso) {
        onGuardado()
        return
    }

    when {
        mostrandoEscaner -> {
            Box(modifier = Modifier.fillMaxSize()) {
                EscanerCodigoBarras(
                    modifier = Modifier.fillMaxSize(),
                    modo = ModoEscaneo.CODIGO_BARRAS,
                    onCodigoDetectado = { codigo ->
                        mostrandoEscaner = false
                        viewModel.actualizarCodigoBarras(codigo)
                    }
                )
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Cerrar",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(24.dp)
                        .clickable { mostrandoEscaner = false }
                )
            }
        }
        mostrandoAgregarColor -> {
            PantallaAgregarColor(
                tallasMarcadas = viewModel.tallasMarcadasOrdenadas(),
                onGuardar = { color, stockPorTalla ->
                    viewModel.agregarColor(color, stockPorTalla)
                    mostrandoAgregarColor = false
                },
                onCancelar = { mostrandoAgregarColor = false }
            )
        }
        else -> {
            Surface(modifier = Modifier.fillMaxSize().imePadding(), color = EcoFondo) {
                Column(modifier = Modifier.fillMaxSize()) {
                    HeaderEco(titulo = "Añadir Producto", usarCerrarEnVezDeAtras = true, onNavegarAtras = onVolver)
                    TabsSegmentadosEco(
                        tabs = listOf(
                            TabEco("info", "Info"),
                            TabEco("precio", "Precio"),
                            TabEco("opciones", "Opciones")
                        ),
                        seleccionadoId = tabActiva,
                        onSeleccionar = { tabActiva = it }
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 20.dp)
                    ) {
                        when (tabActiva) {
                            "info" -> TabInfoProducto(
                                nombre = nombre,
                                onNombreCambiado = viewModel::actualizarNombre,
                                descripcion = descripcion,
                                onDescripcionCambiada = viewModel::actualizarDescripcion,
                                codigoBarras = codigoBarras,
                                onCodigoBarrasCambiado = viewModel::actualizarCodigoBarras,
                                onEscanear = {
                                    if (tienePermisoCamara(context)) mostrandoEscaner = true
                                    else lanzadorPermiso.launch(Manifest.permission.CAMERA)
                                },
                                categorias = categorias,
                                categoriaId = categoriaId,
                                onCategoriaSeleccionada = viewModel::seleccionarCategoria
                            )
                            "precio" -> TabPrecioProducto(
                                precioCompraTexto = precioCompraTexto,
                                onPrecioCompraCambiado = viewModel::actualizarPrecioCompra,
                                tallas = tallas,
                                onAlternarTalla = viewModel::alternarTalla,
                                onPrecioCambiado = viewModel::actualizarPrecioEscalon
                            )
                            "opciones" -> TabOpcionesProducto(
                                colores = colores,
                                tallasDisponibles = viewModel.tallasMarcadasOrdenadas().map { it.talla },
                                onAgregarColor = { mostrandoAgregarColor = true },
                                onQuitarColor = viewModel::quitarColor
                            )
                        }

                        if (estadoGuardado is EstadoGuardado.Error) {
                            Text(
                                text = (estadoGuardado as EstadoGuardado.Error).mensaje,
                                color = EcoRojo,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 12.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    BotonPrimarioEco(
                        texto = if (estadoGuardado is EstadoGuardado.Guardando) "Guardando…" else "Guardar",
                        onClick = { viewModel.guardarProducto() },
                        habilitado = estadoGuardado !is EstadoGuardado.Guardando,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TabInfoProducto(
    nombre: String,
    onNombreCambiado: (String) -> Unit,
    descripcion: String,
    onDescripcionCambiada: (String) -> Unit,
    codigoBarras: String,
    onCodigoBarrasCambiado: (String) -> Unit,
    onEscanear: () -> Unit,
    categorias: List<CategoriaEntity>,
    categoriaId: String?,
    onCategoriaSeleccionada: (String?) -> Unit
) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        EtiquetaEco("Nombre del producto")
        CampoTextoEco(valor = nombre, onValorCambia = onNombreCambiado, placeholder = "Ej. Vestido floral")

        Spacer(modifier = Modifier.height(18.dp))
        EtiquetaEco("Descripción (opcional)")
        CampoTextoEco(
            valor = descripcion,
            onValorCambia = onDescripcionCambiada,
            placeholder = "Tipo de tela, corte, detalles...",
            lineasMax = 3
        )

        Spacer(modifier = Modifier.height(18.dp))
        EtiquetaEco("Categoría")
        if (categorias.isEmpty()) {
            Text("Sin categorías creadas todavía", color = EcoTextoSecundario, style = MaterialTheme.typography.bodyMedium)
        } else {
            androidx.compose.foundation.layout.FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categorias.forEach { categoria ->
                    val seleccionada = categoriaId == categoria.id
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (seleccionada) EcoVerde else EcoInput)
                            .clickable { onCategoriaSeleccionada(if (seleccionada) null else categoria.id) }
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Text(
                            categoria.nombre,
                            color = if (seleccionada) EcoVerdeTexto else EcoTextoPrimario,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))
        EtiquetaEco("Código de barras")
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.weight(1f)) {
                CampoTextoEco(valor = codigoBarras, onValorCambia = onCodigoBarrasCambiado, placeholder = "Escanea o escribe el código")
            }
            Box(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(EcoAzul)
                    .clickable(onClick = onEscanear),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.PhotoCamera, contentDescription = "Escanear", tint = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TabPrecioProducto(
    precioCompraTexto: String,
    onPrecioCompraCambiado: (String) -> Unit,
    tallas: Map<String, TallaEnCaptura>,
    onAlternarTalla: (String, Boolean) -> Unit,
    onPrecioCambiado: (String, String, String) -> Unit
) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        EtiquetaEco("Precio de compra")
        CampoMonedaEco(valor = precioCompraTexto, onValorCambia = onPrecioCompraCambiado)

        Spacer(modifier = Modifier.height(24.dp))
        Text("Tallas y precios", color = EcoTextoPrimario, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        Text(
            "Marca las tallas del producto y define su precio — es el mismo para todos los colores",
            color = EcoTextoSecundario,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp, bottom = 10.dp)
        )

        TALLAS_DISPONIBLES.forEach { talla ->
            val capturaActual = tallas[talla]
            val marcada = capturaActual != null

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (marcada) EcoSuperficie else Color.Transparent)
                    .padding(if (marcada) 14.dp else 0.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = marcada,
                        onCheckedChange = { activo -> onAlternarTalla(talla, activo) },
                        colors = CheckboxDefaults.colors(checkedColor = EcoVerde, uncheckedColor = EcoTextoSecundario)
                    )
                    Text("Talla $talla", color = EcoTextoPrimario, fontWeight = FontWeight.Bold)
                }

                if (capturaActual != null) {
                    Text(
                        "Precios por cantidad",
                        color = EcoTextoSecundario,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 10.dp, start = 40.dp, bottom = 6.dp)
                    )
                    FlowRow(
                        modifier = Modifier.fillMaxWidth().padding(start = 40.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        capturaActual.escalones.forEach { escalon ->
                            Column(modifier = Modifier.width(96.dp)) {
                                Text(
                                    "${escalon.etiqueta} (≥${escalon.cantidadMinima})",
                                    color = EcoTextoSecundario,
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Box(modifier = Modifier.padding(top = 4.dp)) {
                                    CampoMonedaEco(
                                        valor = escalon.precioTexto,
                                        onValorCambia = { nuevoPrecio -> onPrecioCambiado(talla, escalon.etiqueta, nuevoPrecio) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TabOpcionesProducto(
    colores: List<ColorEnCaptura>,
    tallasDisponibles: List<String>,
    onAgregarColor: () -> Unit,
    onQuitarColor: (String) -> Unit
) {
    val puedeAgregar = tallasDisponibles.isNotEmpty()

    Column(modifier = Modifier.padding(top = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(if (puedeAgregar) EcoAzul else EcoInput)
                .clickable(enabled = puedeAgregar, onClick = onAgregarColor)
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Add, contentDescription = null, tint = Color.White)
            }
            Column(modifier = Modifier.padding(start = 14.dp)) {
                Text("Nueva opción de color", color = Color.White, fontWeight = FontWeight.Bold)
                Text(
                    if (puedeAgregar) "Agregue un color con su stock por talla" else "Primero marca al menos una talla en la pestaña Precio",
                    color = Color.White.copy(alpha = 0.85f),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        if (colores.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "SELECCIÓN (${colores.size})",
                color = EcoTextoPrimario,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            colores.forEach { color ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(EcoSuperficie)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(color.color, color = EcoTextoPrimario, fontWeight = FontWeight.Bold)
                        Text(
                            color.stockPorTalla.entries.joinToString(" · ") { (talla, stock) -> "T$talla: ${stock.stockTexto.ifBlank { "0" }}" },
                            color = EcoTextoSecundario,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "Quitar",
                        tint = EcoTextoSecundario,
                        modifier = Modifier.clickable { onQuitarColor(color.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun EtiquetaEco(texto: String) {
    Text(
        texto,
        color = EcoTextoPrimario,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}
