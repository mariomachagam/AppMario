package com.mario.appmario.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mario.appmario.model.Contact
import com.mario.appmario.viewmodel.ContactsViewModel

@Composable
fun ContactDetail(
    contact: Contact,
    viewModel: ContactsViewModel,
    navController: NavHostController
) {
    // ——————————————————————————
    // Estados editables: inicializados con los datos actuales del contacto
    var birthday by remember { mutableStateOf(contact.birthday) }
    var address by remember { mutableStateOf(contact.address) }
    var notes by remember { mutableStateOf(contact.notes) }
    // ——————————————————————————

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Aquí continúa tu UI: título, foto, nombre, campos editables, botón guardar…
        Text(
            text = "Información de contacto",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = contact.photoResId),
            contentDescription = contact.name,
            modifier = Modifier.size(120.dp).clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(contact.name, style = MaterialTheme.typography.headlineMedium)
        Text("Teléfono: ${contact.phone}", style = MaterialTheme.typography.bodyLarge)
        Text("Email: ${contact.mail}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(24.dp))

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



    }
}
