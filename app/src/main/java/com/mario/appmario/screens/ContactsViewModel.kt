package com.mario.appmario.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.mario.appmario.model.Contact
import com.mario.appmario.model.defaultContacts

class ContactsViewModel : ViewModel() {

    var contacts: SnapshotStateList<Contact> = mutableStateListOf()
        private set

    init {
        contacts.addAll(defaultContacts)
    }

    fun addContact(contact: Contact) {
        contacts.add(contact)
    }

    fun removeContact(contact: Contact) {
        contacts.remove(contact)
    }

    fun updateContact(oldContact: Contact, newContact: Contact) {
        val index = contacts.indexOf(oldContact)
        if (index != -1) contacts[index] = newContact
    }
}
