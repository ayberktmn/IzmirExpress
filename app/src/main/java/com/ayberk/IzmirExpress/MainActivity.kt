package com.ayberk.IzmirExpress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ayberk.IzmirExpress.navigation.Navigation
import com.ayberk.IzmirExpress.ui.theme.IzmirIlkYardimTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IzmirIlkYardimTheme {
              Navigation()
            }
        }
    }
}

