package com.mario.appmario.model

import androidx.annotation.DrawableRes
import com.mario.appmario.R

data class Contact(
    val name: String,
    val phone: String,
    val mail: String,
    @DrawableRes val photoResId: Int,
    var birthday: String = "",
    var address: String = "",
    var notes: String = ""
)

// Lista de contactos por defecto
val defaultContacts = listOf(
    Contact("Ana López", "611222333", "ana@gmail.com", R.drawable.img),
    Contact("Carlos Pérez", "622333444", "carlos@gmail.com", R.drawable.img),
    Contact("Laura Martín", "633444555", "laura@gmail.com", R.drawable.img),
    Contact("David Ruiz", "644555666", "david@gmail.com", R.drawable.img)
)
