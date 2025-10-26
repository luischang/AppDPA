package dev.luischang.appdpa.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.luischang.appdpa.presentation.apifootball.ApiFootballScreen
import dev.luischang.appdpa.presentation.auth.LoginScreen
import dev.luischang.appdpa.presentation.auth.RegisterScreen
import dev.luischang.appdpa.presentation.home.HomeScreen
import dev.luischang.appdpa.presentation.permissions.GalleryPermissionScreen

@Composable
fun AppNavGraph(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home")
    {
        composable("register") { RegisterScreen(navController) }
        composable("login") { LoginScreen(navController) }

        composable("home") {
            DrawerScaffold(navController) {
                HomeScreen()
            }
        }
        composable ("permissions") {
            DrawerScaffold(navController) {
                GalleryPermissionScreen()
            }
        }
        composable ("favorites") {
            DrawerScaffold(navController) {
                Text("Proximamente pantalla favoritos.....")
            }
        }
        composable ("football") {
            DrawerScaffold(navController) {
                ApiFootballScreen()
            }
        }
    }
}