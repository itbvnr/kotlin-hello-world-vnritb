package cat.itb.m78.exercices


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cat.itb.m78.exercices.theme.AppTheme
import kotlinx.serialization.Serializable

object PantallesDesti { //Singleton class
    @Serializable
    data object Pantalla10
    @Serializable
    data object Pantalla20
    @Serializable
    data class Pantalla30(val missatge: String)
}

@Composable
fun LibNavExempleNavegacio() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = PantallesDesti.Pantalla10) {/*NavGraphBuilder.() Receiver: Ús implícit*/
        composable<PantallesDesti.Pantalla10> {//composable és un mètode de NavGraphBuilder
            Screen10(
                navigateToScreen2 = { navController.navigate(PantallesDesti.Pantalla20) },
            )
        }
        composable<PantallesDesti.Pantalla20> {
            Screen20 { navController.navigate(PantallesDesti.Pantalla30(it)) }  //Pas de valors entre pantalles
        }
        composable<PantallesDesti.Pantalla30> { backStack ->
            val message = backStack.toRoute<PantallesDesti.Pantalla30>().missatge
            Screen30(message)
        }
    }
}

@Composable
fun Screen10(navigateToScreen2: ()-> Unit){
    Column(){
        //Text("Your app goes here!", Modifier.align(Alignment.Center))
        Spacer(Modifier.height(Dp(50f)))

        Button(onClick = navigateToScreen2){
            Text("Go To screen2")
        }
    }
}

@Composable
fun Screen20(navigateToScreen3: (String)-> Unit){
    Column(){
        //Text("Your app goes here!", Modifier.align(Alignment.Center))
        Spacer(Modifier.height(Dp(50f)))

        var text by remember{ mutableStateOf("") }
        TextField(text, onValueChange = {text = it})  //Aquí li passa el valor: quan canvia el text
        Button(onClick = {navigateToScreen3(text)}){
            Text("Go to Screen 3")
        }
    }
}

@Composable
fun Screen30(message: String) {
    Column(){
        //Text("Your app goes here!", Modifier.align(Alignment.Center))
        Spacer(Modifier.height(Dp(50f)))

        Text("Pantalla 3")
        Text(text = "Text rebut: $message")
    }
}



@Composable
internal fun App() = AppTheme {
    Scaffold  (
        topBar = { Text("Navegació per pantalles")
        }
    ){
        LibNavExempleNavegacio()
    }
}