package com.mario.appmario.model

import androidx.annotation.DrawableRes
import com.mario.appmario.R

data class Contact(
    val name: String,
    val phone: String,
    val mail:String,
    @DrawableRes val photoResId: Int
)

// Lista de contactos por defecto
val defaultContacts = listOf(
    Contact("Ana López", "611 222 333","ana@gmail.com",R.drawable.img),
    Contact("Carlos Pérez", "622 333 444","carlos@gmail.com",R.drawable.img),
    Contact("Laura Martín", "633 444 555","laura@gmail.com",R.drawable.img),
    Contact("David Ruiz", "644 555 666","david@gmail.com",R.drawable.img)
)
