package app.meedu.deuna_demo.presentation.modules.splash

import android.annotation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import app.meedu.deuna_demo.presentation.*
import app.meedu.deuna_demo.presentation.shared.view_models.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SplashScreen() {

  val navController = LocalNavController.current
  val userEmail = LocalSessionViewModel.current.userEmail

  LaunchedEffect(key1 = "splash", block = {
    navController.navigate(if (userEmail != null) Screens.Home.route else Screens.SignUp.route)
  })

  Scaffold {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      CircularProgressIndicator()
    }
  }
}

@Preview
@Composable
private fun Preview() {
  Navigator {
    SplashScreen()
  }
}