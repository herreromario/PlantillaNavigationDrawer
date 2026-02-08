package com.example.examenandroidmarioherrero.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.examenandroidmarioherrero.R
import com.example.examenandroidmarioherrero.modelos.drawemenu.DrawerMenu
import com.example.examenandroidmarioherrero.ui.screens.dinamicas.PantallaInicio
import com.example.examenandroidmarioherrero.ui.screens.dinamicas.PantallaMarcarMeGusta
import com.example.examenandroidmarioherrero.ui.screens.dinamicas.PantallaProductos
import com.example.examenandroidmarioherrero.ui.screens.dinamicas.PantallaInsertar
import com.example.examenandroidmarioherrero.ui.screens.dinamicas.PantllaProductosMeGusta
import com.example.examenandroidmarioherrero.ui.viewmodel.bd.BDViewModel
import com.example.examenandroidmarioherrero.ui.viewmodel.jsonserver.JSViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class Pantallas(@StringRes val titulo: Int){
    Inicio(R.string.inicio),
    Insertar(R.string.insertar_producto),
    Actualizar(R.string.actualizar_producto),
    Productos(R.string.productos),
    ProductosMeGusta(R.string.productos_favoritos),
    MarcarMeGusta(R.string.marcar_producto_favorito)
}

val menu = arrayOf(
    DrawerMenu(Icons.Filled.Info, Pantallas.Inicio.titulo, Pantallas.Inicio.name),
    DrawerMenu(Icons.Filled.Info, Pantallas.Productos.titulo, Pantallas.Productos.name),
    DrawerMenu(Icons.Filled.Info, Pantallas.ProductosMeGusta.titulo, Pantallas.ProductosMeGusta.name),
)

@Composable
fun ExamenNavigation(
    bdViewModel: BDViewModel = viewModel(factory = BDViewModel.Factory),
    jsViewModel: JSViewModel = viewModel(factory = JSViewModel.Factory),
    navController: NavHostController = rememberNavController(),
    coroutine: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {
    val pilaRetroceso by navController.currentBackStackEntryAsState()

    val pantallaActual = Pantallas.valueOf(
        pilaRetroceso?.destination?.route ?: Pantallas.Inicio.name)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    menu = menu,
                    pantallaActual = pantallaActual
                ) { ruta ->
                    coroutine.launch {
                        drawerState.close()
                    }
                    navController.navigate(ruta)
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    pantallaActual = pantallaActual,
                    puedeNavegarAtras = navController.previousBackStackEntry != null,
                    onNavegarAtras = { navController.navigateUp() },
                    drawerState = drawerState
                )
            },

            floatingActionButton = {
                if (pantallaActual == Pantallas.Productos) {

                    Column(
                        horizontalAlignment = Alignment.End
                    ) {

                        FloatingActionButton(
                            onClick = { navController.navigate(Pantallas.MarcarMeGusta.name) }
                        ) {
                            Icon(Icons.Default.Favorite,
                                contentDescription = stringResource(R.string.marcar_producto_favorito))
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        FloatingActionButton(
                            onClick = { navController.navigate(Pantallas.Insertar.name) }
                        ) {
                            Icon(Icons.Default.Add,
                                contentDescription = stringResource(R.string.anadir_producto))
                        }
                    }
                }
            }


        ) { innerPadding ->

            val bdUIState = bdViewModel.bdUIState
            val jsUIState = jsViewModel.jsUIState

            NavHost(
                navController = navController,
                startDestination = Pantallas.Inicio.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = Pantallas.Inicio.name) { PantallaInicio(
                    modifier = Modifier.fillMaxSize(),
                    jsuiState = jsUIState,
                    bduiState = bdUIState,
                    onListarProductos = { navController.navigate(Pantallas.Productos.name) },
                    onListarProductosMeGusta = { navController.navigate(Pantallas.ProductosMeGusta.name) }
                ) }

                composable(route = Pantallas.Insertar.name) { PantallaInsertar(
                    modifier = Modifier.fillMaxSize(),
                    onInsertarProducto = {
                        jsViewModel.insertarProducto(it)
                        jsViewModel.obtenerTodosProductos()
                        navController.navigate(Pantallas.Inicio.name)
                    }
                ) }

                composable(route = Pantallas.Productos.name) { PantallaProductos(
                    modifier = Modifier.fillMaxSize(),
                    jsuiState = jsUIState,
                    productoPulsado = jsViewModel.productoPulsado,
                    onDesplegarProducto = { jsViewModel.actualizarProductoPulsado(producto = it)},
                    onEditarProducto = { navController.navigate(Pantallas.Actualizar.name)},
                    onEliminarProducto = { jsViewModel.eliminarProducto(it.id) },
                    onActualizarListaProductos = { jsViewModel.obtenerTodosProductos() }
                ) }

                composable(route = Pantallas.ProductosMeGusta.name) { PantllaProductosMeGusta(modifier = Modifier.fillMaxSize()) }
                composable(route = Pantallas.MarcarMeGusta.name) { PantallaMarcarMeGusta(modifier = Modifier.fillMaxSize()) }
            }
        }
    }
}

@Composable
fun DrawerContent(
    menu: Array<DrawerMenu>,
    pantallaActual: Pantallas,
    onMenuClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(150.dp),
                imageVector = Icons.Filled.AccountCircle,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        menu.forEach {
            NavigationDrawerItem(
                label = { Text(text = stringResource(id = it.titulo)) },
                icon = { Icon(imageVector = it.icono, contentDescription = null) },
                selected = it.titulo == pantallaActual.titulo,
                onClick = {
                    onMenuClick(it.ruta)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    pantallaActual: Pantallas,
    puedeNavegarAtras: Boolean,
    onNavegarAtras: () -> Unit,
    drawerState: DrawerState?,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    TopAppBar(
        title = { Text(text = stringResource(id = pantallaActual.titulo)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {

            when {

                puedeNavegarAtras &&
                        (pantallaActual == Pantallas.Insertar ||
                                pantallaActual == Pantallas.MarcarMeGusta) -> {

                    IconButton(onClick = onNavegarAtras) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.atras)
                        )
                    }
                }

                drawerState != null -> {

                    IconButton(
                        onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }
                    ) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = stringResource(R.string.atras)
                        )
                    }
                }
            }
        }


    )
}