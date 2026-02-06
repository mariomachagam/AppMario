package com.mario.appmario.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.mario.appmario.model.Contact
import com.mario.appmario.model.defaultContacts

class ContactsViewModel : ViewModel() {

    // Lista principal de contactos
    var contacts: SnapshotStateList<Contact> = mutableStateListOf()
        private set

    // ⭐ Lista de favoritos
    var favoriteContacts: SnapshotStateList<Contact> = mutableStateListOf()
        private set

    init {
        contacts.addAll(defaultContacts)
    }

    fun addContact(contact: Contact) {
        contacts.add(contact)
    }

    fun removeContact(contact: Contact) {
        contacts.remove(contact)
        favoriteContacts.remove(contact) // si estaba en favoritos
    }

    fun updateContact(oldContact: Contact, newContact: Contact) {
        val index = contacts.indexOf(oldContact)
        if (index != -1) {
            contacts[index] = newContact

            // actualizar favorito si lo era
            val favIndex = favoriteContacts.indexOf(oldContact)
            if (favIndex != -1) {
                favoriteContacts[favIndex] = newContact
            }
        }
    }

    // ❤️ Marcar / desmarcar favorito
    fun toggleFavorite(contact: Contact) {
        if (favoriteContacts.contains(contact)) {
            favoriteContacts.remove(contact)
        } else {
            favoriteContacts.add(contact)
        }
    }

    // 🔍 Saber si es favorito
    fun isFavorite(contact: Contact): Boolean {
        return favoriteContacts.contains(contact)
    }
}
