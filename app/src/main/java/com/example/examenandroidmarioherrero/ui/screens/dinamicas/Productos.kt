package com.example.examenandroidmarioherrero.ui.screens.dinamicas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.examenandroidmarioherrero.R
import com.example.examenandroidmarioherrero.modelos.jsonserver.Producto
import com.example.examenandroidmarioherrero.ui.screens.estaticas.PantallaCargando
import com.example.examenandroidmarioherrero.ui.screens.estaticas.PantallaError
import com.example.examenandroidmarioherrero.ui.theme.ExamenAndroidMarioHerreroTheme
import com.example.examenandroidmarioherrero.ui.viewmodel.jsonserver.JSUIState

@Composable
fun PantallaProductos(
    modifier: Modifier = Modifier,
    jsuiState: JSUIState,
    productoPulsado: Producto,
    onDesplegarProducto: (Producto) -> Unit,
    onEditarProducto: (Producto) -> Unit,
    onEliminarProducto: (Producto) -> Unit,
    onActualizarListaProductos: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TituloPagina(stringResource(R.string.lista_de_productos_disponibles))

        when (jsuiState) {
            is JSUIState.Cargando -> PantallaCargando(modifier)
            is JSUIState.Error -> PantallaError(modifier)

            is JSUIState.ObtenerTodosProductosExito -> PantallaProductosExito(
                listaProductos = jsuiState.listaProductos,
                productoPulsado = productoPulsado,
                onDesplegarProducto = onDesplegarProducto,
                onEditarProducto = onEditarProducto,
                onEliminarProducto = onEliminarProducto,
                onActualizarListaProductos = onActualizarListaProductos
            )

            else -> {}
        }
    }
}

@Composable
fun PantallaProductosExito(
    modifier: Modifier = Modifier,
    listaProductos: List<Producto>,
    productoPulsado: Producto,
    onDesplegarProducto: (Producto) -> Unit,
    onEditarProducto: (Producto) -> Unit,
    onEliminarProducto: (Producto) -> Unit,
    onActualizarListaProductos: () -> Unit
){

    LazyColumn() {
        items(listaProductos) { producto ->
            TarjetaProducto(
                producto = producto,
                productoPulsado = productoPulsado,
                onDesplegarProducto = onDesplegarProducto,
                onEditarProducto = onEditarProducto,
                onEliminarProducto = onEliminarProducto,
                onActualizarListaProductos = onActualizarListaProductos
            )
        }
    }
}

@Composable
fun TarjetaProducto(
    producto: Producto,
    productoPulsado: Producto,
    onDesplegarProducto: (Producto) -> Unit,
    onEditarProducto: (Producto) -> Unit,
    onEliminarProducto: (Producto) -> Unit,
    onActualizarListaProductos: () -> Unit
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(6.dp)
        .clickable(
            onClick = { onDesplegarProducto(producto) }
        ),
    ) {
        Column(modifier = Modifier
            .padding(6.dp)) {
            Text(text = producto.nombre, fontWeight = FontWeight.Bold)
            Text(text = "${producto.precio} €")
            Text(text = "Unidades disponibles: ${producto.cantidad}")

            if(producto == productoPulsado){
                HorizontalDivider(modifier = Modifier.height(2.dp) .padding(vertical =  4.dp))

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                ) {
                    Text("Fecha caducidad: ${producto.fechaCaducidad}")
                    Text("Hora caducidad: ${producto.horaCaducidad}")

                    Row(
                        modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally)) {

                        Button(
                            onClick = { onEditarProducto(productoPulsado) }
                        ) {
                            Text("Editar")
                        }

                        Button(
                            onClick = { onEliminarProducto(productoPulsado)
                                      onActualizarListaProductos()},

                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text("Eliminar")
                        }

                    }
                }
            }
        }
    }
}