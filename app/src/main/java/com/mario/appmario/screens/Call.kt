package com.mario.appmario.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mario.appmario.NavRoutes
import com.mario.appmario.viewmodel.ContactsViewModel

@Composable
fun Call(
    navController: NavHostController,
    viewModel: ContactsViewModel
) {
    var phoneNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(1f)) // espacio superior

        // Número marcado
        Text(
            text = phoneNumber.ifEmpty { "Introduce un número" },
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Teclado
        val keys = listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf("*", "0", "#")
        )

        keys.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { key ->
                    DialButton(symbol = key) {
                        phoneNumber += key
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botones de acción
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Borrar
            FloatingActionButton(
                onClick = {
                    if (phoneNumber.isNotEmpty()) {
                        phoneNumber = phoneNumber.dropLast(1)
                    }
                },
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Icon(Icons.Filled.Backspace, contentDescription = "Borrar")
            }

            // Llamar
            FloatingActionButton(
                onClick = { /* llamada real */ },
                containerColor = Color(0xFF4CAF50)
            ) {
                Icon(
                    Icons.Filled.Call,
                    contentDescription = "Llamar",
                    tint = Color.White
                )
            }

            // Añadir contacto
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        NavRoutes.Add.route + "?phone=$phoneNumber"
                    )
                },
                containerColor = Color(0xFF2196F3)
            ) {
                Text("+")
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // espacio inferior
    }
}

@Composable
fun DialButton(
    symbol: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier.size(80.dp)
    ) {
        Text(
            text = symbol,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
