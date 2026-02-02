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
import com.mario.appmario.R
import com.mario.appmario.model.Contact
import com.mario.appmario.viewmodel.ContactsViewModel

@Composable
fun Add(
    viewModel: ContactsViewModel,
    initialPhone: String = "",
    onContactSaved: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf(initialPhone) }
    var mail by remember { mutableStateOf("") }
    var selectedPhoto by remember { mutableStateOf<Int?>(null) }
    var expanded by remember { mutableStateOf(false) }

    val photos = listOf(
        R.drawable.img,
        R.drawable.img,
        R.drawable.img,
        R.drawable.img
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Añadir contacto",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // 🔝 Imagen seleccionada arriba y centrada
        selectedPhoto?.let { photo ->
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = photo),
                contentDescription = "Imagen seleccionada",
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = mail,
            onValueChange = { mail = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Selector de imagen
        Box {
            Button(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = selectedPhoto?.let { "Cambiar imagen" }
                        ?: "Seleccionar imagen"
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                photos.forEachIndexed { index, photo ->
                    DropdownMenuItem(
                        text = { Text("Foto ${index + 1}") },
                        onClick = {
                            selectedPhoto = photo
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (name.isNotBlank() && phone.isNotBlank() && mail.isNotBlank()) {
                    viewModel.addContact(
                        Contact(
                            name = name,
                            phone = phone,
                            mail = mail,
                            photoResId = selectedPhoto ?: R.drawable.img
                        )
                    )
                    onContactSaved()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar contacto")
        }
    }
}
