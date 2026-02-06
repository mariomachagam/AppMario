package com.mario.appmario

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Telefono",
            image = Icons.Filled.Call,
            route = "call"
        ),
        BarItem(
            title = "Contacts",
            image = Icons.Filled.Face,
            route = "contacts"
        ),
        BarItem(
            title = "Add",
            image = Icons.Filled.Add,
            route = "add"
        ),
        BarItem(
            title = "Favotitos",
            image = Icons.Filled.FavoriteBorder,
            route = "favoritos"
        ),

    )

}


