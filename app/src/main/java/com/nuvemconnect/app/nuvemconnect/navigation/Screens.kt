package com.nuvemconnect.app.nuvemconnect.navigation

sealed class Screens(val route: String) {
    data object Login: Screens("login")
    data object Register: Screens("register")
    data object Home: Screens("home")
    data object Splash: Screens("splash")
    data object Welcome: Screens("welcome")
}

