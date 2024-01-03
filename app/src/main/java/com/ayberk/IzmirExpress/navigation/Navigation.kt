package com.ayberk.IzmirExpress.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ayberk.IzmirExpress.Anasayfa
import com.ayberk.IzmirExpress.Muzeler
import com.ayberk.IzmirExpress.SplashScreen

@Composable
fun Navigation() {

    val navHostController = rememberNavController()

    NavHost(navHostController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navHostController)
        }
        composable("anasayfa"){
            Anasayfa(navHostController)
        }
        composable("muzeler"){
            Muzeler(navHostController)
        }
    }
}