package app.meedu.deuna_demo

import android.os.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.*
import androidx.navigation.compose.*
import app.meedu.deuna_demo.presentation.*
import app.meedu.deuna_demo.presentation.modules.home.*
import app.meedu.deuna_demo.presentation.modules.sign_up.*
import app.meedu.deuna_demo.presentation.modules.splash.*
import app.meedu.deuna_demo.presentation.modules.thank_you.*

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Navigator {
        NavHost(
          navController = LocalNavController.current,
          startDestination = Screens.Splash.route
        ) {
          composable(Screens.Splash.route) {
            SplashScreen()
          }
          composable(Screens.SignUp.route) {
            SignUpScreen()
          }
          composable(Screens.Home.route) {
            HomeScreen()
          }
          composable(Screens.ThankYou.route) {
            ThankYouPage(message = it.arguments?.getString("message") ?: "Thank You")
          }
        }
      }
    }
  }
}