package com.mario.appmario.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.mario.appmario.NavRoutes
import com.mario.appmario.viewmodel.ContactsViewModel

@Composable
fun Call(
    navController: NavHostController,
    viewModel: ContactsViewModel
) {
    val context = LocalContext.current
    var phoneNumber by remember { mutableStateOf("") }

    // Launcher para pedir permiso CALL_PHONE
    val callPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted && phoneNumber.isNotEmpty()) {
            val intent = Intent(
                Intent.ACTION_CALL,
                Uri.parse("tel:$phoneNumber")
            )
            context.startActivity(intent)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(1f))

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
            Spacer(modifier = Modifier.height(16.dp))
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

            // 📞 Llamar DIRECTAMENTE
            FloatingActionButton(
                onClick = {
                    if (phoneNumber.isEmpty()) return@FloatingActionButton

                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CALL_PHONE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        val intent = Intent(
                            Intent.ACTION_CALL,
                            Uri.parse("tel:$phoneNumber")
                        )
                        context.startActivity(intent)
                    } else {
                        callPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
                    }
                },
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

        Spacer(modifier = Modifier.weight(1f))
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
        modifier = Modifier.size(100.dp)
    ) {
        Text(
            text = symbol,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

