package app.meedu.deuna_demo.presentation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.*
import androidx.navigation.*
import androidx.navigation.compose.*
import androidx.security.crypto.*
import app.meedu.deuna_demo.data.helpers.*
import app.meedu.deuna_demo.data.repositories_imp.*
import app.meedu.deuna_demo.presentation.shared.view_models.*

import androidx.security.crypto.MasterKeys
import app.meedu.deuna_demo.*
import app.meedu.deuna_demo.R
import app.meedu.deuna_demo.domain.repositories.*
import com.google.gson.Gson
import okhttp3.*


val LocalNavController = compositionLocalOf<NavHostController> {
  error("NavHostController not found")
}


@Composable
fun Navigator(content: @Composable () -> Unit) {
  val httpClient = HttpClient(
    okHttpClient = OkHttpClient(),
    baseUrl = "https://api.stg.deuna.io",
    gson = Gson()
  )

  val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

  val securePreferences = EncryptedSharedPreferences.create(
    "secure_preferences",
    masterKeyAlias,
    LocalContext.current,
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
  )

  val keyStore = KeyStoreHelper(
    prefs = securePreferences, gson = Gson()
  )

  val authRepository = AuthRepositoryImp(
    keyStore = keyStore,
    httpClient = httpClient,
    apiKey = BuildConfig.PRIVATE_API_KEY
  )

  CompositionLocalProvider(
    LocalNavController provides rememberNavController(),
    LocalAuthRepository provides authRepository,
    LocalMerchantsRepository provides MerchantsRepositoryImp(
      httpClient = httpClient,
      apiKey = BuildConfig.PRIVATE_API_KEY,
      keyStore = keyStore
    ),
    LocalSessionViewModel provides SessionViewModel(
      authRepository
    ),
  ) {
    content()
  }
}

