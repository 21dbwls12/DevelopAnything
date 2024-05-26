package com.example.developanything.navigation

sealed class NavScreen(val route: String) {
    data object Certification : NavScreen("인증")
}