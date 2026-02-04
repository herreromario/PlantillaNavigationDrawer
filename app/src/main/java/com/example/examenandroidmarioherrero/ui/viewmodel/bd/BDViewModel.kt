package com.example.examenandroidmarioherrero.ui.viewmodel.bd

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.examenandroidmarioherrero.aplicacion.bd.BDAplicacion
import com.example.examenandroidmarioherrero.datos.bd.repositorios.ProductoMeGustaRepository
import com.example.examenandroidmarioherrero.modelos.bd.ProductoMeGusta
import kotlinx.coroutines.launch

sealed interface BDUIState {
    data class ObtenerTodosProductosMeGustaExito(val listaProductosMeGusta: List<ProductoMeGusta>): BDUIState
    data class ObtenerProductoMeGustaExito(val productoMeGusta: ProductoMeGusta): BDUIState

    object InsertarProductoMeGustaExito: BDUIState
    object ActualizarProductoMeGustaExito: BDUIState
    object EliminarProductoMeGustaExito: BDUIState
    object Error: BDUIState
    object Cargando: BDUIState
}

class BDViewModel(private val productoMeGustaRepository: ProductoMeGustaRepository) : ViewModel() {
    var bdUIState: BDUIState by mutableStateOf(BDUIState.Cargando)
        private set

    @OptIn(ExperimentalMaterial3Api::class)
    var productoMeGustaPulsado: ProductoMeGusta by mutableStateOf(ProductoMeGusta(0, "", 0.0, 0, null, null))

    init {
        obtenerTodosProductosMeGusta()
    }

    fun obtenerTodosProductosMeGusta() {
        viewModelScope.launch {
            bdUIState = try {
                val listaProductosMeGusta = productoMeGustaRepository.obtenerTodosProductosMeGusta()
                BDUIState.ObtenerTodosProductosMeGustaExito(listaProductosMeGusta)
            } catch (e: Exception) {
                BDUIState.Error
            }
        }
    }

    fun obtenerProductoMeGusta(id: Int) {
        viewModelScope.launch {
            bdUIState = try {
                val productoMeGusta = productoMeGustaRepository.obtenerProductoMeGusta(id)
                BDUIState.ObtenerProductoMeGustaExito(productoMeGusta)
            } catch (e: Exception) {
                BDUIState.Error
            }
        }
    }

    fun insertarProductoMeGusta(productoMeGusta: ProductoMeGusta) {
        viewModelScope.launch {
            bdUIState = try {
                productoMeGustaRepository.insertarProductoMeGusta(productoMeGusta)
                BDUIState.InsertarProductoMeGustaExito
            } catch (e: Exception) {
                BDUIState.Error
            }
        }
    }

    fun actualizarProductoMeGusta(productoMeGusta: ProductoMeGusta) {
        viewModelScope.launch {
            bdUIState = try {
                productoMeGustaRepository.actualizarProductoMeGusta(productoMeGusta)
                BDUIState.InsertarProductoMeGustaExito
            } catch (e: Exception) {
                BDUIState.Error
            }
        }
    }

    fun eliminarProductoMeGusta(productoMeGusta: ProductoMeGusta) {
        viewModelScope.launch {
            bdUIState = try {
                productoMeGustaRepository.eliminarProductoMeGusta(productoMeGusta)
                BDUIState.EliminarProductoMeGustaExito
            } catch (e: Exception) {
                BDUIState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as BDAplicacion)
                val productoMeGustaRepository = aplicacion.contenedor.productoMeGustaRepository
                BDViewModel(productoMeGustaRepository)
            }
        }
    }
}