package app.meedu.deuna_demo.presentation.modules.sign_up

import android.annotation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.*
import app.meedu.deuna_demo.core.*
import app.meedu.deuna_demo.presentation.*
import app.meedu.deuna_demo.presentation.modules.sign_up.view_model.*
import app.meedu.deuna_demo.presentation.shared.extensions.*
import app.meedu.deuna_demo.presentation.shared.view_models.*
import app.meedu.deuna_demo.presentation.shared.widgets.*
import kotlinx.coroutines.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignUpScreen() {
  val sessionViewModel = LocalSessionViewModel.current
  val authRepository = LocalAuthRepository.current

  CompositionLocalProvider(
    LocalSignUpViewModel provides SignUpViewModel(
      sessionViewModel = sessionViewModel,
      authRepository = authRepository,
    )
  ) {

    val navController = LocalNavController.current
    val signInViewModel = LocalSignUpViewModel.current
    val state = signInViewModel.state

    val errorMessage = remember { mutableStateOf<String?>(null) }


    fun validateForm(): Boolean {
      if (!state.email.isValidEmail()) {
        return false
      }

      return !(state.firstName.isEmpty() || state.lastName.isEmpty() || state.phone.isEmpty())

    }


    Box(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    ) {
      Scaffold {
        Column(
          modifier = Modifier
            .padding(
              horizontal = 20.dp, vertical = 30.dp
            ),
          verticalArrangement = Arrangement.Bottom,
        ) {

          OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.email,
            keyboardOptions = KeyboardOptions(
              keyboardType = KeyboardType.Email
            ),
            onValueChange = {
              signInViewModel.onEmailChanged(it)
            },
            label = {
              Text(text = "Email")
            },
          )

          Box(modifier = Modifier.height(20.dp))

          OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.firstName,
            keyboardOptions = KeyboardOptions(
              keyboardType = KeyboardType.Text
            ),
            onValueChange = {
              signInViewModel.onFirstNameChanged(it)
            },
            label = {
              Text(text = "Nombre")
            },
          )

          Box(modifier = Modifier.height(20.dp))

          OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.lastName,
            keyboardOptions = KeyboardOptions(
              keyboardType = KeyboardType.Text
            ),
            onValueChange = {
              signInViewModel.onLastNameChanged(it)
            },
            label = {
              Text(text = "Apellido")
            },
          )

          Box(modifier = Modifier.height(20.dp))

          OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.phone,
            keyboardOptions = KeyboardOptions(
              keyboardType = KeyboardType.Phone
            ),
            onValueChange = {
              signInViewModel.onPhoneChanged(it)
            },
            label = {
              Text(text = "Celular")
            },
          )

          Box(modifier = Modifier.height(60.dp))

          MaterialButton(
            enabled = validateForm() && !state.fetching,
            onClick = {
              signInViewModel.signIn {
                when (it) {
                  is Either.Right -> {
                    navController.navigate(Screens.Home.route)
                  }
                  else -> {
                    errorMessage.value = "No se pudo procesar la solicitud"
                  }
                }
              }
            },
            text = "Continuar",
          )
        }
      }
    }


    if (errorMessage.value != null) {
      AlertDialog(
        title = {
          Text(text = "Error")
        },
        text = {
          Text(text = errorMessage.value!!)
        },
        onDismissRequest = {
          errorMessage.value = null
        },
        confirmButton = {

        })
    }
  }
}

@Preview
@Composable
private fun Preview() {
  Navigator {
    SignUpScreen()
  }
}