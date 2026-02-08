package com.example.examenandroidmarioherrero.ui.screens.dinamicas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.examenandroidmarioherrero.R
import com.example.examenandroidmarioherrero.modelos.bd.ProductoMeGusta
import com.example.examenandroidmarioherrero.modelos.jsonserver.Producto
import com.example.examenandroidmarioherrero.ui.screens.estaticas.PantallaCargando
import com.example.examenandroidmarioherrero.ui.screens.estaticas.PantallaError
import com.example.examenandroidmarioherrero.ui.viewmodel.bd.BDUIState
import com.example.examenandroidmarioherrero.ui.viewmodel.jsonserver.JSUIState

@Composable
fun PantallaInicio(
    modifier: Modifier,
    jsuiState: JSUIState,
    bduiState: BDUIState,
    onListarProductos: () -> Unit,
    onListarProductosMeGusta: () -> Unit
){
    when {
        jsuiState is JSUIState.Cargando || bduiState is BDUIState.Cargando ->
            PantallaCargando(modifier)

        jsuiState is JSUIState.Error || bduiState is BDUIState.Error ->
            PantallaError(modifier)

        jsuiState is JSUIState.ObtenerTodosProductosExito &&
                bduiState is BDUIState.ObtenerTodosProductosMeGustaExito ->
                    PantallaInicioExito(modifier = modifier,
                        listaProductos = jsuiState.listaProductos,
                        listaProductosMeGusta = bduiState.listaProductosMeGusta,
                        onListarProductos = onListarProductos,
                        onListarProductosMeGusta = onListarProductosMeGusta)

    }
}

@Composable
fun PantallaInicioExito(
    modifier: Modifier,
    listaProductos: List<Producto>,
    listaProductosMeGusta: List<ProductoMeGusta>,
    onListarProductos: () -> Unit,
    onListarProductosMeGusta: () -> Unit
){
    TituloPagina(stringResource(R.string.pagina_de_inicio))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Lista de productos disponibles: ${listaProductos.size}"
        )

        Text(
            text = "Lista de productos favoritos: ${listaProductosMeGusta.size}"
        )

        BotonesListas(
            onListarProductos = onListarProductos,
            onListarProductosMeGusta = onListarProductosMeGusta
        )
    }
}

@Composable
fun BotonesListas(
    onListarProductos: () -> Unit,
    onListarProductosMeGusta: () -> Unit
){
    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically)) {
        Button(
            onClick = { onListarProductos() }
        ) {
            Text("Lista de productos")
        }

        Button(
            onClick = { onListarProductosMeGusta() }
        ) {
            Text("Lista de favoritos")
        }
    }
}

@Composable
fun TituloPagina(
    texto: String,
) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}