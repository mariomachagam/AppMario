package com.mario.appmario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.mario.appmario.screens.*
import com.mario.appmario.ui.theme.AppMarioTheme
import com.mario.appmario.viewmodel.ContactsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppMarioTheme {
                MainScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    AppMarioTheme {
        MainScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val contactsViewModel: ContactsViewModel = viewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("EasyPhone")
                        Text(
                            text = "By mariomachagam",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {
            NavigationHost(navController, contactsViewModel)
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, viewModel: ContactsViewModel) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Contacts.route
    ) {

        // Pantalla principal de contactos
        composable(NavRoutes.Contacts.route) {
            Contacts(viewModel = viewModel, navController = navController)
        }

        // Detalle de contacto
        composable(
            route = "contactDetail/{phone}",
            arguments = listOf(navArgument("phone") { type = NavType.StringType })
        ) { backStackEntry ->
            val phone = backStackEntry.arguments?.getString("phone")
            val contact = viewModel.contacts.find { it.phone == phone }
            contact?.let {
                ContactDetail(contact = it, viewModel = viewModel, navController = navController)
            }
        }

        // Pantalla añadir contacto
        composable(
            route = NavRoutes.Add.route + "?phone={phone}",
            arguments = listOf(navArgument("phone") { type = NavType.StringType; defaultValue = "" })
        ) { backStackEntry ->
            val phoneArg = backStackEntry.arguments?.getString("phone") ?: ""
            Add(viewModel = viewModel, initialPhone = phoneArg) {
                navController.popBackStack()
            }
        }

        // Pantalla favoritos
        composable(NavRoutes.Favoritos.route) {
            Favoritos(viewModel = viewModel, navController = navController)
        }

        // Pantalla de mensajes con parámetro de contacto
        composable(
            route = "mail/{contactName}",
            arguments = listOf(navArgument("contactName") { type = NavType.StringType })
        ) { backStackEntry ->
            val contactName = backStackEntry.arguments?.getString("contactName") ?: ""
            Mensajes(navController = navController, contactName = contactName)
        }

        // Pantalla llamadas
        composable(NavRoutes.Call.route) {
            Call(navController = navController, viewModel = viewModel)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        NavBarItems.BarItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(imageVector = navItem.image, contentDescription = navItem.title) },
                label = { Text(navItem.title) }
            )
        }
    }
}
