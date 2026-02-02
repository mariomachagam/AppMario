package com.mario.appmario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mario.appmario.screens.*
import com.mario.appmario.ui.theme.AppMarioTheme
import com.mario.appmario.viewmodel.ContactsViewModel
import com.mario.appmario.model.Contact

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppMarioTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppMarioTheme {
        MainScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val contactsViewModel: ContactsViewModel = viewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(" Aplicación de teléfono \n ")
                        Text("\nMario Machado Gámez", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            )
        },
        content = { padding ->
            Column(Modifier.padding(padding)) {
                NavigationHost(navController = navController, viewModel = contactsViewModel)
            }
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    )
}

@Composable
fun NavigationHost(navController: NavHostController, viewModel: ContactsViewModel) {
    NavHost(navController = navController, startDestination = NavRoutes.Contacts.route) {
        composable(NavRoutes.Contacts.route) {
            Contacts(viewModel = viewModel, navController = navController)
        }

        // Ruta con parámetro para detalle
        composable(
            route = "contactDetail/{phone}",
            arguments = listOf(navArgument("phone") { type = NavType.StringType })
        ) { backStackEntry ->
            val phone = backStackEntry.arguments?.getString("phone")
            val contact = viewModel.contacts.find { it.phone == phone }
            contact?.let {
                ContactDetail(
                    contact = it,
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }


        composable(NavRoutes.Call.route) {
            Call(navController = navController, viewModel = viewModel)
        }

        composable(
            route = NavRoutes.Add.route + "?phone={phone}",
            arguments = listOf(navArgument("phone") { type = NavType.StringType; defaultValue = "" })
        ) { backStackEntry ->
            val phoneArg = backStackEntry.arguments?.getString("phone") ?: ""
            Add(viewModel = viewModel, initialPhone = phoneArg, onContactSaved = { navController.popBackStack() })
        }

        composable(NavRoutes.Mail.route) {
            Mail()
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
