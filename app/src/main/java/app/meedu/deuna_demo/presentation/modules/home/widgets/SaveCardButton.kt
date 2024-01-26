package app.meedu.deuna_demo.presentation.modules.home.widgets

import android.net.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.*
import app.meedu.deuna_demo.*
import app.meedu.deuna_demo.presentation.*
import app.meedu.deuna_demo.presentation.shared.widgets.*
import com.deuna.maven.*
import com.deuna.maven.checkout.*
import com.deuna.maven.checkout.domain.*
import com.deuna.maven.element.domain.*


@Composable
fun SaveCardButton() {

  val context = LocalContext.current
  val authRepository = LocalAuthRepository.current
  val navController = LocalNavController.current
  var navigateToThankYou by remember { mutableStateOf(false) }

  fun onClick() {

    val callbacks = ElementCallbacks().apply {
      onSuccess = {
        DeUnaSdk.closeElements()
        navigateToThankYou = true
      }
      onError = {
        println(it?.message)
        DeUnaSdk.closeElements()
      }
      onClose = {
        DeUnaSdk.closeElements()
      }
    }

    DeUnaSdk.config(
      apiKey = BuildConfig.API_KEY,
      environment = Environment.STAGING,
      elementCallbacks = callbacks,
      context = context,
      closeOnEvents = arrayOf(CheckoutEvents.linkFailed),
      showCloseButton = true
    )

    DeUnaSdk.initElements(
      element = ElementType.VAULT,
      userToken = authRepository.userToken ?: ""
    )


  }


  MaterialButton(
    onClick = { onClick() },
    text = "Guardar tarjeta"
  )

  // Use LaunchedEffect to trigger navigation when navigateToThankYou changes
  LaunchedEffect(navigateToThankYou) {
    if (navigateToThankYou) {
      navController.navigate("/thank-you/${Uri.encode("Tarjeta guardada con éxito")}")
    }
  }

}


