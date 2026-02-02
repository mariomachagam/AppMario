package com.mario.appmario.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.mario.appmario.model.Contact
import com.mario.appmario.model.defaultContacts

class ContactsViewModel : ViewModel() {

    // Lista de contactos que se muestra en la app
    val contacts = mutableStateListOf<Contact>().apply {
        addAll(defaultContacts) // Añadimos los contactos por defecto
    }

    // Función para añadir un contacto nuevo
    fun addContact(contact: Contact) {
        contacts.add(contact)
    }

    // Función opcional para eliminar un contacto
    fun removeContact(contact: Contact) {
        contacts.remove(contact)
    }
}