package app.meedu.deuna_demo.presentation.modules.home

import android.annotation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import app.meedu.deuna_demo.presentation.*
import app.meedu.deuna_demo.presentation.modules.home.widgets.*
import app.meedu.deuna_demo.presentation.shared.view_models.*
import app.meedu.deuna_demo.presentation.shared.widgets.*
import com.google.android.material.button.MaterialButton


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen() {


  Scaffold(
    topBar = {
      val sessionViewModel = LocalSessionViewModel.current
      val navController = LocalNavController.current
      TopAppBar(
        title = {
          Text(text = "Home")
        },
        actions = {
          IconButton(
            onClick = {
              sessionViewModel.signOut()
              navController.navigate(
                Screens.SignUp.route
              ) {
                popUpTo(0)
              }
            }
          ) {
            Text(text = "Salir")
          }
        }
      )
    }
  ) {

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(
          horizontal = 20.dp
        ),
      verticalArrangement = Arrangement.Bottom
    ) {

      SaveCardButton()

      Box(modifier = Modifier.height(15.dp))

      BuyButton()

      Box(modifier = Modifier.height(20.dp))
    }
  }
}

@Preview
@Composable
private fun Preview() {
  Navigator {
    HomeScreen()
  }
}