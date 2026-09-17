package com.tuempresa.possystem.ui.componentes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tuempresa.possystem.ui.theme.EcoFondo

/**
 * Estructura base para las pantallas principales con barra inferior
 * (Caja, Productos, Transacciones, Reportes). El [contenido] ocupa todo el
 * espacio disponible arriba de la barra; [fab] se dibuja flotando sobre
 * la esquina inferior derecha del contenido, igual que en Productos.
 */
@Composable
fun PantallaConBarraEco(
    barraInferior: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    fab: (@Composable () -> Unit)? = null,
    contenido: @Composable () -> Unit
) {
    Surface(modifier = modifier.fillMaxSize(), color = EcoFondo) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                contenido()
                if (fab != null) {
                    Box(
                        modifier = Modifier
                            .align(androidx.compose.ui.Alignment.BottomEnd)
                            .padding(20.dp),
                    ) {
                        fab()
                    }
                }
            }
            barraInferior()
        }
    }
}
