package com.mario.appmario

sealed class NavRoutes(val route: String) {
    object Call : NavRoutes("call")
    object Contacts : NavRoutes("contacts")
    object Add : NavRoutes("add")
    object Favoritos : NavRoutes("favoritos")
    object Mail : NavRoutes("mail/{contactName}")
    // pantalla de mensajes

}
