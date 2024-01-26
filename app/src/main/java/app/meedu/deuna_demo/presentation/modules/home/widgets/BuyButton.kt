package app.meedu.deuna_demo.presentation.modules.home.widgets

import android.net.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.*
import app.meedu.deuna_demo.BuildConfig
import app.meedu.deuna_demo.core.*
import app.meedu.deuna_demo.domain.enums.*
import app.meedu.deuna_demo.presentation.*
import app.meedu.deuna_demo.presentation.shared.widgets.*
import com.deuna.maven.*
import com.deuna.maven.checkout.*
import com.deuna.maven.checkout.domain.*
import com.deuna.maven.element.domain.*
import kotlinx.coroutines.*
import java.lang.Exception


@Composable
fun BuyButton() {

  val context = LocalContext.current
  val merchantsRepository = LocalMerchantsRepository.current
  val navController = LocalNavController.current
  var navigateToThankYou by remember { mutableStateOf(false) }

  val coroutineScope = rememberCoroutineScope()

  fun onClick() {

    coroutineScope.launch(Dispatchers.IO) {
      val result = merchantsRepository.getOrderToken(
        productId = "FakeID",
        productName = "Fake Product",
        currency = Currency.MXN,
        price = 1000.0,
      )

      when (result) {
        is Either.Right -> {
          val callbacks = Callbacks().apply {
            onSuccess = {
              DeUnaSdk.closeCheckout()
              navigateToThankYou = true
            }
            onError = {
              println(it?.message)
              DeUnaSdk.closeCheckout()
            }
            onClose = {
              DeUnaSdk.closeCheckout()
            }
          }

          DeUnaSdk.config(
            apiKey = BuildConfig.API_KEY,
            environment = Environment.STAGING,
            callbacks = callbacks,
            context = context
          )
          DeUnaSdk.initCheckout(orderToken = result.value)
        }

        is Either.Left -> {}
      }

    }


  }


  MaterialButton(
    onClick = { onClick() },
    text = "Comprar"
  )

  // Use LaunchedEffect to trigger navigation when navigateToThankYou changes
  LaunchedEffect(navigateToThankYou) {
    if (navigateToThankYou) {
      navController.navigate("/thank-you/${Uri.encode("Compra realizada con éxito")}")
    }
  }


}