package com.example.myapplication

import android.R.string
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.LoginScreen
import com.example.myapplication.screens.SignUpScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

sealed class DestinationScreen(val route: String) {
    object Signup : DestinationScreen("signup")
    object Login : DestinationScreen("login")
    object Profile : DestinationScreen("profile")
    object ChatList : DestinationScreen("chatList")
    object SingleChat : DestinationScreen("SingleChat/{chatId}") {
        fun createRoute(chatId: String) = "SingleChat/$chatId"
    }
    object StatusList : DestinationScreen("StatusList")
    object SingleStatus : DestinationScreen("SingleStatus/{UserId}") {
        fun createRoute(userId: String) = "SingleStatus/$userId"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "onCreate called")
        super.onCreate(savedInstanceState)
        setContent {
            Log.d("MainActivity", "Setting content")
            MyApplicationTheme {
                ChatAppNavigation()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Log.d("MainActivity", "Surface created")
                }
            }
        }
    }
}


    @Composable
    fun ChatAppNavigation() {
        val navController = rememberNavController()
        Log.d("MainActivity", "NavController created")
        var vm = hiltViewModel<LCViewModel>()
        Log.d("MainActivity", "ViewModel created")
        NavHost(navController = navController, startDestination = DestinationScreen.Signup.route) {

            composable(DestinationScreen.Signup.route) {
                SignUpScreen(navController, vm)
            }

            composable(DestinationScreen.Login.route) {
                LoginScreen(navController, vm)
            }

        }


    }







