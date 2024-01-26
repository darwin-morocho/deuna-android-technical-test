package app.meedu.deuna_demo.presentation.modules.thank_you

import android.annotation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import app.meedu.deuna_demo.presentation.*
import app.meedu.deuna_demo.presentation.shared.widgets.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable

fun ThankYouPage(message: String) {

  val navController = LocalNavController.current

  Scaffold {
    Column(

      modifier = Modifier
        .fillMaxSize()
        .padding(30.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(text = message)
      Box(modifier = Modifier.height(20.dp))
      MaterialButton(
        onClick = {
          navController.popBackStack()
        },
        text = "Aceptar"
      )
    }
  }

}


@Preview
@Composable
private fun Preview() {
  Navigator {
    ThankYouPage(message = "Compra realizada con exito")
  }
}