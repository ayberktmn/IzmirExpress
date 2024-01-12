package com.ayberk.IzmirExpress.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ayberk.IzmirExpress.ActivityDetailsScreen
import com.ayberk.IzmirExpress.ActivityScreen
import com.ayberk.IzmirExpress.ActivitysItem
import com.ayberk.IzmirExpress.Anasayfa
import com.ayberk.IzmirExpress.EmergencyassemblyareaScreen
import com.ayberk.IzmirExpress.Muzeler
import com.ayberk.IzmirExpress.Pharmacy
import com.ayberk.IzmirExpress.SplashScreen
import com.ayberk.IzmirExpress.WaterProblemScreen
import com.ayberk.IzmirExpress.model.Activitys
import com.ayberk.IzmirExpress.model.ActivitysItem

@RequiresApi(Build.VERSION_CODES.O)
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
        composable("pharmacy"){
            Pharmacy(navHostController)
        }
        composable("waterproblem"){
            WaterProblemScreen(navHostController)
        }
        composable("emergencyassembly"){
            EmergencyassemblyareaScreen(navHostController)
        }
        composable("activity"){
            ActivityScreen(navHostController)
        }
        composable("activitydetails/{Id}",
            arguments = listOf(
                navArgument("Id") {
                    type = NavType.IntType
                }
            )
        ) {
            val Id = remember {
                it.arguments?.getInt("Id") ?: 0
            }
            ActivityDetailsScreen(
                Id = Id,
                navHostController = navHostController
            )
        }
    }
}