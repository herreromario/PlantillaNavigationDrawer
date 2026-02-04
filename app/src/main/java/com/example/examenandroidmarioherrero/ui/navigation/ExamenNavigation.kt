package com.example.examenandroidmarioherrero.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.examenandroidmarioherrero.R
import com.example.examenandroidmarioherrero.modelos.drawemenu.DrawerMenu
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
    DrawerMenu(Icons.Filled.Info, Pantallas.Insertar.titulo, Pantallas.Insertar.name),
    DrawerMenu(Icons.Filled.Info, Pantallas.Productos.titulo, Pantallas.Productos.name),
    DrawerMenu(Icons.Filled.Info, Pantallas.ProductosMeGusta.titulo, Pantallas.ProductosMeGusta.name),
    DrawerMenu(Icons.Filled.Info, Pantallas.MarcarMeGusta.titulo, Pantallas.MarcarMeGusta.name)
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
                    onNavegarAtras = { navController.navigateUp() }
                )
            },

            floatingActionButton = {
                if(pantallaActual == Pantallas.Productos){
                    FloatingActionButton(
                        onClick = { navController.navigate(Pantallas.Insertar.name) }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = stringResource(R.string.anadir_producto))
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
                composable(route = Pantallas.Inicio.name) { }
                composable(route = Pantallas.Insertar.name) { }
                composable(route = Pantallas.Productos.name) { }
                composable(route = Pantallas.ProductosMeGusta.name) { }
                composable(route = Pantallas.MarcarMeGusta.name) { }
            }
        }
    }
}

@Composable
fun DrawerContent(menu: Array<DrawerMenu>, pantallaActual: Pantallas, onMenuClick: (String) -> Unit) {
    TODO("Not yet implemented")
}

@Composable
fun AppTopBar(
    pantallaActual: Pantallas,
    puedeNavegarAtras: Boolean,
    onNavegarAtras: () -> Boolean
) {
    TODO("Not yet implemented")
}