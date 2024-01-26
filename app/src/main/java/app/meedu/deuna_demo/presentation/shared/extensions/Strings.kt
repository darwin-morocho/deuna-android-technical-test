package app.meedu.deuna_demo.presentation.shared.extensions

fun String.isValidEmail(): Boolean {
  val emailRegex = Regex("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
  return emailRegex.matches(this)
}