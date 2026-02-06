package com.mario.appmario.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.mario.appmario.viewmodel.ContactsViewModel
import androidx.compose.ui.res.painterResource

@Composable
fun Favoritos(
    viewModel: ContactsViewModel,
    navController: NavHostController
) {
    val favorites = viewModel.favoriteContacts

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "❤️ Favoritos",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (favorites.isEmpty()) {
            Text(
                text = "No tienes contactos favoritos aún",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Box(modifier = Modifier.fillMaxSize()) {

                // Definimos offsets fijos para que no se pisen
                val offsets = listOf(
                    0.dp to 100.dp,
                    150.dp to 100.dp,
                    0.dp to 250.dp,
                    150.dp to 250.dp,
                    75.dp to 400.dp
                )

                favorites.forEachIndexed { index, contact ->
                    val (xOffset, yOffset) = offsets.getOrElse(index) { 0.dp to (index * 150).dp }

                    Box(
                        modifier = Modifier
                            .offset(x = xOffset, y = yOffset)
                            .size(120.dp)
                            .clickable {
                                navController.navigate("contactDetail/${contact.phone}")
                            },
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Image(
                            painter = painterResource(id = contact.photoResId),
                            contentDescription = contact.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                        )

                        Text(
                            text = contact.name,
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.5f))
                                .padding(4.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
