package com.example.weightdojo.screens.main

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weightdojo.MyApp
import com.example.weightdojo.database.models.Config
import com.example.weightdojo.screens.charts.ChartScreen
import com.example.weightdojo.screens.home.Home
import com.example.weightdojo.screens.lock.Lock
import com.example.weightdojo.screens.lockfirsttime.LockFirstTime
import com.example.weightdojo.utils.VMFactory
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.navigation.NavController

@Composable
fun MainScreen(
    navHostController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel = viewModel(
        factory = VMFactory.build {
            MainViewModel(MyApp.appModule.database, MyApp.appModule.configSessionCache)
        }
    ),
    config: Config? = mainViewModel.state.config,
) {
    if (mainViewModel.state.startDestination == null) return

    val currentRoute = remember { mutableStateOf(Screens.Home.name) }

    fun navigateTo(screen: Screens) {
        when (screen) {
            Screens.Home -> navHostController.navigate(Screens.Home.name)
            Screens.Charts -> navHostController.navigate(Screens.Charts.name)
            Screens.Settings -> navHostController.navigate(Screens.Settings.name)
            else -> {
                throw Error("Lock screens aren't intended to be navigable")
            }
        }

        currentRoute.value = screen.name
    }

    val context = LocalContext.current as FragmentActivity

    Scaffold(
        containerColor = MaterialTheme.colors.secondary,
        bottomBar = {
            BottomBarNav(
                navigateTo = ::navigateTo,
                currentScreen = currentRoute.value
            )
        }
    ) {

        NavHost(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            navController = navHostController,
//            startDestination = Screens.Home.name
            startDestination = mainViewModel.state.startDestination?.name ?: Screens.Lock.name
        ) {
            composable(route = Screens.LockFirstTime.name) {
                LockFirstTime(
                    onSubmitRedirect = {
                        mainViewModel.viewModelScope.launch {
//                        mainViewModel.setAuthenticated(true)
                            navHostController.navigate(Screens.Home.name)
                        }
                    },
                    context = context
                )
            }
            composable(route = Screens.Lock.name) {
                Lock(
                    config = config!!,
                    context = context,
                    redirectToHome = {
                        mainViewModel.viewModelScope.launch {
                            mainViewModel.setAuthenticated(true)
                            navHostController.navigate(Screens.Home.name)
                        }
                    },
                    isAuthenticated = mainViewModel.state.authenticated,
                )
            }
            composable(route = Screens.Home.name) {
                Home(
                    navigateTo = ::navigateTo
                )
            }
            composable(route = Screens.Charts.name) {
                ChartScreen()
            }
            composable(route = Screens.Settings.name) {
                Text(text = "Settings")
            }
        }
    }

}