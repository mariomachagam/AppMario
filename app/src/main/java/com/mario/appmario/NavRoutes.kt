package com.mario.appmario
sealed class NavRoutes(val route: String) {
    object Call : NavRoutes("call")
    object Contacts : NavRoutes("contacts")
    object Add : NavRoutes("add") // ruta base
    object Mail : NavRoutes("mail")
}
