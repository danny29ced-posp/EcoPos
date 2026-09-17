package com.tuempresa.possystem.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Paleta "Eco" — inspirada en la interfaz de referencia (fondo negro puro,
 * acentos verde menta y azul-violeta). Se centraliza aquí para que todas las
 * pantallas nuevas usen los mismos tonos en vez de declarar Color(0x...) sueltos.
 *
 * Las pantallas antiguas (paleta "boutique" cálida: FondoCarbon/AcentoTerracota)
 * se pueden migrar poco a poco a esta paleta sin romper nada, ya que ambas
 * conviven como simples objetos de color.
 */

// Fondo
val EcoFondo = Color(0xFF000000)          // negro puro, fondo de toda la app
val EcoFondoHeader = Color(0xFF141414)    // header/appbar ligeramente elevado
val EcoSuperficie = Color(0xFF1C1C1C)     // tarjetas grandes (resumen, bloques)
val EcoInput = Color(0xFF474747)          // fondo de inputs y chips inactivos
val EcoInputBorde = Color(0xFF5C5C5C)     // borde sutil de inputs sin foco
val EcoDivisor = Color(0xFF2E2E2E)

// Marca / acentos
val EcoVerde = Color(0xFF8FE0A8)          // verde menta: logo, botón primario, tab activo
val EcoVerdeFuerte = Color(0xFF7ED08F)
val EcoVerdeTexto = Color(0xFF1E3D28)     // texto oscuro sobre botones verdes
val EcoAzul = Color(0xFF7C89C9)           // azul-violeta: FAB, tarjetas destacadas, switches
val EcoAzulTenue = Color(0xFF4D5578)      // bloque "carros activos" / superficies azuladas

// Semánticos
val EcoRojo = Color(0xFFF08C8C)           // Gastos
val EcoVerdeIngreso = Color(0xFFA6E894)   // Ingresos
val EcoCian = Color(0xFF5FC9D8)           // cifras de ingresos/lucro en reportes
val EcoAmbarFondo = Color(0xFF4A3220)     // warning box (advertencias / "necesita selección")
val EcoAmbarTexto = Color(0xFFE9B48C)
val EcoVerdeInfoFondo = Color(0xFF16321F) // info box ("se imprimirá en el recibo")
val EcoVerdeInfoTexto = Color(0xFFB9E3C6)

// Texto
val EcoTextoPrimario = Color(0xFFFFFFFF)
val EcoTextoSecundario = Color(0xFFA3A3A3)
val EcoTextoTerciario = Color(0xFF707070)
val EcoTextoSobreVerde = Color(0xFF163B22)
val EcoTextoSobreAzul = Color(0xFFFFFFFF)
