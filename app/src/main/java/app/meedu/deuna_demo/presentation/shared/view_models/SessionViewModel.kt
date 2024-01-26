package app.meedu.deuna_demo.presentation.shared.view_models

import androidx.compose.runtime.*
import androidx.lifecycle.*
import app.meedu.deuna_demo.domain.repositories.*

val LocalSessionViewModel = compositionLocalOf<SessionViewModel> {
  error("SessionViewModel not found")
}


class SessionViewModel(private val authRepository: AuthRepository) : ViewModel() {
  private val _state: MutableState<String?> = mutableStateOf(authRepository.email)

  val userEmail: String? get() = _state.value


  fun saveSession(email: String) {
    _state.value = email
  }

  fun signOut() {
    authRepository.signOut()
  }


}