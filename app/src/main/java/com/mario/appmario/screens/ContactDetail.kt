package com.mario.appmario.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mario.appmario.model.Contact
import com.mario.appmario.viewmodel.ContactsViewModel

@Composable
fun ContactDetail(
    contact: Contact,
    viewModel: ContactsViewModel,
    navController: NavHostController,
    onMessageClick: (contactName: String) -> Unit = {}
) {
    // Estados editables
    var birthday by remember { mutableStateOf(contact.birthday) }
    var address by remember { mutableStateOf(contact.address) }
    var notes by remember { mutableStateOf(contact.notes) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Foto del contacto
        Image(
            painter = painterResource(id = contact.photoResId),
            contentDescription = contact.name,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(contact.name, style = MaterialTheme.typography.headlineMedium)
        Text("Teléfono: ${contact.phone}", style = MaterialTheme.typography.bodyLarge)
        Text("Email: ${contact.mail}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(24.dp))

        // Campos editables
        OutlinedTextField(
            value = birthday,
            onValueChange = { birthday = it },
            label = { Text("Cumpleaños") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Domicilio") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notas") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón “Guardar”
        Button(
            onClick = {
                val updatedContact = contact.copy(
                    birthday = birthday,
                    address = address,
                    notes = notes
                )
                viewModel.updateContact(contact, updatedContact)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón “Volver”
        OutlinedButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🔹 NUEVOS BOTONES DE LLAMAR Y MENSAJE (debajo de Volver)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Botón Llamar (verde)
            Button(
                onClick = {
                    // Aquí puedes agregar la lógica de llamada si quieres
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // verde
                modifier = Modifier.weight(1f)
            ) {
                Text("Llamar", color = Color.White)
            }

            // Botón Mensaje (azul)
            Button(
                onClick = { onMessageClick(contact.name) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)), // azul
                modifier = Modifier.weight(1f)
            ) {
                Text("Mensaje", color = Color.White)
            }
        }
    }
}
