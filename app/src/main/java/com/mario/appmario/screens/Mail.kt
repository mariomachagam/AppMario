package com.mario.appmario.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

data class MailItem(
    val sender: String,
    val subject: String,
    val time: String
)

@Composable
fun Mail() {

    val mails = listOf(
        MailItem("Ana", "¿Quedamos luego?", "5 min"),
        MailItem("Carlos", "Archivo del proyecto", "32 min"),
        MailItem("Equipo Programación", "Revisión del sprint", "Ayer"),
        MailItem("Apple", "Actualización de tu cuenta", "Domingo")
    )

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "Bandeja de entrada",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn {
                items(mails) { mail ->
                    MailRow(mail)
                    Divider()
                }
            }
        }

        // ✍️ Botón nuevo mensaje
        FloatingActionButton(
            onClick = {
                // navegar a pantalla "NuevoMensaje"
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Nuevo mensaje"
            )
        }
    }
}

@Composable
fun MailRow(mail: MailItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* abrir correo */ }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Email,
            contentDescription = "Mail",
            modifier = Modifier
                .size(40.dp)
                .padding(end = 12.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = mail.sender, style = MaterialTheme.typography.bodyLarge)
            Text(text = mail.subject, style = MaterialTheme.typography.bodyMedium)
        }

        Text(
            text = mail.time,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
