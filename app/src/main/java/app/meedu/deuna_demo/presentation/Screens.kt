package app.meedu.deuna_demo.presentation

sealed class Screens(val route: String) {
  object Splash : Screens(route = "/splash")
  object SignUp : Screens(route = "/sign-up")
  object Home : Screens(route = "/home")
  object ThankYou : Screens(route = "/thank-you/{message}")
}