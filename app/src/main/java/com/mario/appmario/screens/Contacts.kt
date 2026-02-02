package com.mario.appmario.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun Contacts(viewModel: ContactsViewModel, navController: NavHostController) {
    Column {
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Lista de contactos",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Pulsa en un contacto para ver más info",
            style = MaterialTheme.typography.bodySmall,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(viewModel.contacts) { contact ->
                ContactItem(contact, navController)
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable {
                // Navegamos pasando el teléfono como parámetro
                navController.navigate("contactDetail/${contact.phone}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(contact.name, style = MaterialTheme.typography.titleMedium)
                Text(contact.phone, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(contact.mail, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Image(
                painter = painterResource(id = contact.photoResId),
                contentDescription = contact.name,
                modifier = Modifier.size(50.dp).clip(CircleShape)
            )
        }
    }
}
