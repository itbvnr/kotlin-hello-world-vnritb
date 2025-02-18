package cat.itb.m78.exercices


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.theme.AppTheme

private sealed interface Pantalla {
    data object Pantalla1 : Pantalla  //singleton, sense estat, una sola instància
    data object Pantalla2 : Pantalla
    data class Pantalla3 (val missatge: String ) : Pantalla //clases, amb estat, múltiples instàncies
}

private class ViewModelDeNavegacioManual : ViewModel(){
    val pantallaActual = mutableStateOf<Pantalla>(Pantalla.Pantalla1)

    fun anarA (pantalla: Pantalla, titol: String) {
        pantallaActual.value=pantalla
    }
}

@Composable
fun navegacioManual(){
    val viewModel = viewModel {ViewModelDeNavegacioManual()}

    when (val pantallaActual= viewModel.pantallaActual.value){
        Pantalla.Pantalla1 -> Screen1(
            anarOnToqui= { viewModel.anarA (Pantalla.Pantalla2,"Pantalla 2")}
                            )
        is Pantalla.Pantalla2 -> Screen2(
            anarOnToqui= { viewModel.anarA (Pantalla.Pantalla3(" Const Pantalla 3")," Text Pantalla 3")}
                            )
        is Pantalla.Pantalla3 -> Screen3(pantallaActual.missatge)

    }
}

@Composable
fun Screen1(anarOnToqui: () -> Unit) {
    Text("Pantalla 1")
    Button(onClick = anarOnToqui) {
        Text("Go to Screen 2")
    }
}

@Composable
fun Screen2(anarOnToqui: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    Column () {
        Text("Pantalla 2")
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter Text") }
        )
        Button(
            onClick = { anarOnToqui(text) },
        ) {
            Text("Go to Screen 3")
        }
    }
}

@Composable
fun Screen3(message: String) {
    Text("Pantalla 3")
    Text(text = "Text rebut: $message")
}


@Composable
internal fun AppVm() = AppTheme {
    Scaffold  (
        topBar = { Text("Navegació per pantalles")
        }
    ){

        Column(){
            //Text("Your app goes here!", Modifier.align(Alignment.Center))
            Spacer(Modifier.height(Dp(50f)))
            navegacioManual()
        }
    }
}