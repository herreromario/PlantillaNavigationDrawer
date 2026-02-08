package com.example.examenandroidmarioherrero.ui.screens.dinamicas

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.examenandroidmarioherrero.R
import com.example.examenandroidmarioherrero.modelos.jsonserver.Producto
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInsertar(
    modifier: Modifier = Modifier,
    onInsertarProducto: (Producto) -> Unit
){
    TituloPagina(stringResource(R.string.insertar_nuevo_producto))

    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    var fechaCaducidadElegida: Long? by remember { mutableStateOf(null) }
    var horaCaducidadElegida: TimePickerState? by remember { mutableStateOf(null) }

    var fechaCaducidad by remember { mutableStateOf("") }
    var horaCaducidad by remember { mutableStateOf("") }

    var nombreError = nombre.isBlank() && nombre.isNotEmpty()
    var precioError = precio.isBlank() && precio.isNotEmpty()
    var cantidadError = cantidad.isBlank() && cantidad.isNotEmpty()
    var fechaError = fechaCaducidad.isBlank()
    var horaError = horaCaducidad.isBlank()

    var botonFechaPulsado by remember { mutableStateOf(false) }
    var botonHoraPulsado by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically)
    ) {
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            placeholder = { Text(stringResource(R.string.nombre_del_producto))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            isError = nombreError,
            supportingText = {
                if(nombreError){
                    Text(stringResource(R.string.campo_obligatorio))
                }
            }
        )

        TextField(
            value = precio,
            onValueChange = { if(it.isDigitsOnly()) precio = it },
            placeholder = { Text(stringResource(R.string.precio_0_0))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = precioError,
            supportingText = {
                if(precioError){
                    Text(stringResource(R.string.campo_obligatorio))
                }
            }
        )

        TextField(
            value = cantidad,
            onValueChange = { if(it.isDigitsOnly()) cantidad = it },
            placeholder = { Text(stringResource(R.string.cantidad_0))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = cantidadError,
            supportingText = {
                if(cantidadError){
                    Text(stringResource(R.string.campo_obligatorio))
                }
            }
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.fecha_de_caducidad))

            Button(
                modifier = Modifier.padding(horizontal = 8.dp),
                onClick = { botonFechaPulsado = true }
            ) {
                Text(stringResource(R.string.seleccionar_fecha))
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.hora_de_caducidad))

            Button(
                modifier = Modifier
                    .padding(horizontal = 8.dp),

                onClick = { botonHoraPulsado = true }
            ) {
                Text(stringResource(R.string.seleccionar_hora))
            }
        }

        if(botonFechaPulsado){
            DatePickerMostrado(
                onConfirm = { fecha ->
                    fechaCaducidadElegida = fecha
                    botonFechaPulsado = false
                },
                onDismiss = { botonFechaPulsado = false })
        }

        if(botonHoraPulsado){
            TimePickerMostrado(
                onConfirm = { hora ->
                    horaCaducidadElegida = hora
                    botonHoraPulsado = false
                },
                onDismiss = { botonHoraPulsado = false }
            )
        }

        if (fechaCaducidadElegida != null){
            val date = Date(fechaCaducidadElegida!!)
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)

            fechaCaducidad = formattedDate
        }

        if(horaCaducidadElegida != null){
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, horaCaducidadElegida!!.hour)
            cal.set(Calendar.MINUTE, horaCaducidadElegida!!.minute)

            val hora = cal.get(Calendar.HOUR_OF_DAY)
            val minuto = cal.get(Calendar.MINUTE)

            horaCaducidad = String.format("%02d:%02d", hora, minuto)
        }

        var producto = Producto(
            id = "",
            nombre = nombre,
            precio = precio,
            cantidad = cantidad,
            fechaCaducidad = fechaCaducidad,
            horaCaducidad = horaCaducidad)

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onInsertarProducto(producto)},
                enabled = !nombreError && !precioError && !cantidadError && !fechaError && !horaError
            ) {
                Text(stringResource(R.string.insertar_producto))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerMostrado(
    onConfirm: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirm(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(stringResource(R.string.aceptar))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancelar))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerMostrado(
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit
) {
    val horaActual = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = horaActual.get(Calendar.HOUR_OF_DAY),
        initialMinute = horaActual.get(Calendar.MINUTE),
        is24Hour = true
    )
    var mostrarDialogo by remember { mutableStateOf(true) }

    if (mostrarDialogo) {
        AlertDialog(
            text = {
                Column {
                    TimeInput(state = timePickerState)
                    //TimePicker(state = timePickerState)
                }
            },
            onDismissRequest = {
                onDismiss()
                mostrarDialogo = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm(timePickerState)
                        mostrarDialogo = false
                    }
                ) {
                    Text(text = stringResource(R.string.aceptar))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                        mostrarDialogo = false
                    }
                ) {
                    Text(text = stringResource(R.string.cancelar))
                }
            }
        )
    }
}