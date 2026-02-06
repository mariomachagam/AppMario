package com.mario.appmario.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

// Modelo de mensaje
data class Message(val text: String, val isSentByUser: Boolean)

@Composable
fun Mensajes(navController: NavHostController, contactName: String) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    // Lista de mensajes
    var messages by remember {
        mutableStateOf(
            listOf(
                Message("Hola, ¿qué tal?", false),
                Message("Bien, gracias 😊", true)
            )
        )
    }

    var inputText by remember { mutableStateOf("") }

    val listState = rememberLazyListState()

    // Abrir teclado y scroll al último mensaje al iniciar
    LaunchedEffect(Unit) {
        keyboardController?.show()
        if (messages.isNotEmpty()) {
            scope.launch { listState.scrollToItem(messages.size - 1) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // Nombre del contacto en la parte superior
        Text(
            text = "Conversación con $contactName",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        // Lista de mensajes
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(messages) { msg ->
                MessageRow(msg)
            }
        }

        // Fila de entrada de texto
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Escribe un mensaje...") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (inputText.isNotBlank()) {
                            messages = messages + Message(inputText, true)
                            inputText = ""
                            scope.launch { listState.scrollToItem(messages.size - 1) }
                        }
                    }
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (inputText.isNotBlank()) {
                        messages = messages + Message(inputText, true)
                        inputText = ""
                        scope.launch { listState.scrollToItem(messages.size - 1) }
                    }
                }
            ) {
                Text("Enviar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón grande para volver
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Volver")
        }
    }
}

@Composable
fun MessageRow(msg: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = if (msg.isSentByUser) Arrangement.End else Arrangement.Start
    ) {
        Text(
            text = msg.text,
            color = if (msg.isSentByUser) Color.White else Color.Black,
            modifier = Modifier
                .background(
                    color = if (msg.isSentByUser) MaterialTheme.colorScheme.primary else Color.LightGray,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        )
    }
}

