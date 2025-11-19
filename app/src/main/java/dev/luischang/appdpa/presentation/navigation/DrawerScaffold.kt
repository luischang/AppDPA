package dev.luischang.appdpa.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerScaffold(navController: NavController, content: @Composable () -> Unit){
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
    drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(16.dp))
                Text("Menú Principal", modifier = Modifier.padding(16.dp))
                HorizontalDivider()

                NavigationDrawerItem(
                    label =  { Text("Inicio")},
                    selected = false,
                    onClick = {
                        navController.navigate("home")
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    label =  { Text("Permisos")},
                    selected = false,
                    onClick = {
                        navController.navigate("permissions")
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    label =  { Text("Favoritos")},
                    selected = false,
                    onClick = {
                        navController.navigate("favorites")
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    label =  { Text("Api Football")},
                    selected = false,
                    onClick = {
                        navController.navigate("football")
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    label =  { Text("Gemini chat")},
                    selected = false,
                    onClick = {
                        navController.navigate("chat")
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }

    ) {
        Scaffold (
            topBar = {
                TopAppBar(
                    title = {Text("App DPA ")},
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(modifier= Modifier.padding(paddingValues)) {
                content()
            }
        }
    }
}