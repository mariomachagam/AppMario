package com.mario.appmario

import android.os.Bundle
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mario.appmario.screens.Call
import com.mario.appmario.screens.Contacts
import com.mario.appmario.screens.Add
import com.mario.appmario.screens.Mail
import com.mario.appmario.ui.theme.AppMarioTheme
import androidx.navigation.compose.composable
import com.mario.appmario.screens.Add
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mario.appmario.viewmodel.ContactsViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument

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

    // Aquí usamos tu ViewModel
    val contactsViewModel: ContactsViewModel = viewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,

                    ) {
                        Text(" Aplicación de teléfono \n ")
                        Text(
                            text = "\nMario Machado Gámez",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        },
        content = { padding ->
            Column(Modifier.padding(padding)) {
                NavigationHost(
                    navController = navController,
                    viewModel = contactsViewModel // PASAMOS EL VIEWMODEL
                )
            }
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    )
}


@Composable
fun NavigationHost(
    navController: NavHostController,
    viewModel: ContactsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Call.route
    ) {

        composable(NavRoutes.Call.route) {
            Call(navController = navController, viewModel = viewModel)
        }

        composable(NavRoutes.Contacts.route) {
            Contacts(viewModel)
        }

        // Aquí definimos la ruta con parámetro opcional 'phone'
        composable(
            route = NavRoutes.Add.route + "?phone={phone}",
            arguments = listOf(navArgument("phone") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) { backStackEntry ->
            val phoneArg = backStackEntry.arguments?.getString("phone") ?: ""
            Add(
                viewModel = viewModel,
                initialPhone = phoneArg, // <-- esto prellena el campo teléfono
                onContactSaved = { navController.popBackStack() }
            )
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
                        popUpTo(navController.graph.findStartDestination().id)
                        {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = navItem.image,
                        contentDescription = navItem.title
                    )
                },
                label = {
                    Text(text = navItem.title)
                },
            )
        }
    }
}
