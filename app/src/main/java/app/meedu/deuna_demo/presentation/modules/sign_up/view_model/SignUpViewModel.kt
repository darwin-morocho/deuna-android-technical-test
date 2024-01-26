package app.meedu.deuna_demo.presentation.modules.sign_up.view_model

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import app.meedu.deuna_demo.core.*
import app.meedu.deuna_demo.domain.repositories.*
import app.meedu.deuna_demo.presentation.shared.view_models.*
import kotlinx.coroutines.*

val LocalSignUpViewModel = compositionLocalOf<SignUpViewModel> {
  error("SignInViewModel not found")
}

class SignUpViewModel(
  private val authRepository: AuthRepository,
  private val sessionViewModel: SessionViewModel,
) : ViewModel() {

  private val _state = mutableStateOf(SignUpState())
  val state: SignUpState get() = _state.value

  private fun setState(transform: (SignUpState) -> SignUpState) {
    _state.value = transform(state)
  }


  fun onEmailChanged(text: String) {
    setState {
      it.copy(email = text.lowercase().trim())
    }
  }

  fun onFirstNameChanged(text: String) {
    setState {
      it.copy(firstName = text.trim())
    }
  }

  fun onLastNameChanged(text: String) {
    setState {
      it.copy(lastName = text.trim())
    }
  }

  fun onPhoneChanged(text: String) {
    setState {
      it.copy(phone = text.trim())
    }
  }

  fun signIn(onResult: (Either<Failure, Success>) -> Unit) {
    setState {
      it.copy(fetching = true)
    }

    viewModelScope.launch(Dispatchers.IO) {
      val result = authRepository.signUpWithEmail(
        email = state.email, firstName = state.firstName, lastName = state.lastName, phone = state.phone
      )

      withContext(Dispatchers.Main) {
        setState {
          it.copy(fetching = false)
        }

        when (result) {
          is Either.Right -> sessionViewModel.saveSession(state.email)
          else -> {}
        }
        onResult(result)
      }

    }
  }
}