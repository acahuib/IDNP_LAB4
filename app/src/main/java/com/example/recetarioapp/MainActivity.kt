package com.example.recetarioapp

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import com.example.recetarioapp.ui.theme.RecetarioAppTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecetarioAppTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "pantalla1") {
        composable("pantalla1") {
            Pantalla1(onNavigate = { texto ->
                // Encode para manejar espacios y caracteres especiales en el texto
                navController.navigate("pantalla2/${Uri.encode(texto)}")
            })
        }

        composable(
            route = "pantalla2/{texto}",
            arguments = listOf(navArgument("texto") { type = NavType.StringType })
        ) { backStackEntry ->
            val texto = backStackEntry.arguments?.getString("texto") ?: "Sin texto"
            Pantalla2(texto)
        }
    }
}

@Composable
fun Pantalla1(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Pantalla 1", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onNavigate("Hola desde Pantalla 1") }) {
            Text("Ir a Pantalla 2")
        }
    }
}

@Composable
fun Pantalla2(texto: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Pantalla 2", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = texto, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantalla1() {
    RecetarioAppTheme {
        Pantalla1 {}
    }
}
