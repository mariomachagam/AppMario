package com.mario.appmario.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import com.mario.appmario.NavRoutes
import com.mario.appmario.model.Contact
import com.mario.appmario.viewmodel.ContactsViewModel

@Composable
fun Contacts(
    viewModel: ContactsViewModel,
    navController: NavHostController
) {
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

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.contacts) { contact ->
                ContactItem(
                    contact = contact,
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun ContactItem(
    contact: Contact,
    navController: NavHostController,
    viewModel: ContactsViewModel
) {

    val isFavorite = viewModel.isFavorite(contact)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable {
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

            // INFO DEL CONTACTO
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = contact.phone,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = contact.mail,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // MENSAJE + FAVORITO + FOTO
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                // 📨 Botón de mensaje (izquierda del corazón)
                IconButton(
                    onClick = {
                        val encodedName = Uri.encode(contact.name) // codifica espacios y caracteres especiales
                        navController.navigate("mail/$encodedName")
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Mensaje",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                }



                Spacer(modifier = Modifier.width(8.dp))

                // ❤️ Botón de favorito
                IconButton(
                    onClick = {
                        viewModel.toggleFavorite(contact)
                    },
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite)
                            Icons.Filled.Favorite
                        else
                            Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorito",
                        tint = if (isFavorite)
                            MaterialTheme.colorScheme.error
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // FOTO DEL CONTACTO
                Image(
                    painter = painterResource(id = contact.photoResId),
                    contentDescription = contact.name,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            }

        }
        }
    }


