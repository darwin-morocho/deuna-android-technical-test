package app.meedu.deuna_demo.presentation

import androidx.compose.runtime.*
import app.meedu.deuna_demo.domain.repositories.*

val LocalAuthRepository = compositionLocalOf<AuthRepository> {
  error("AuthRepository not found")
}


val LocalMerchantsRepository= compositionLocalOf<MerchantsRepository> {
  error("MerchantsRepository not found")
}
