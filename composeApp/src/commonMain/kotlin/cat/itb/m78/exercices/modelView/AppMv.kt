package cat.itb.m78.exercices.modelView


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.theme.AppTheme

private class MyViwModel : ViewModel() {
    val local = mutableStateOf<Int>(0)
    val visit = mutableStateOf<Int>(0)


    fun golLocal() {
        local.value++
    }

    fun golVisit() {
        visit.value++
    }
}

@Composable
internal fun AppMv() = AppTheme {
    val viewModel = viewModel { MyViwModel() }

    Row() {
        Column {
            Text(viewModel.local.value.toString())
            Button({viewModel.golLocal()}) {
                Text("Local")
            }
        }
        Column {
            Text(viewModel.visit.value.toString())
            Button({viewModel.golVisit()}) {
                Text("Vis")
            }

        }

    }
}
