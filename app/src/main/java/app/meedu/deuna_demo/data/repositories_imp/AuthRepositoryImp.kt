package app.meedu.deuna_demo.data.repositories_imp

import app.meedu.deuna_demo.core.*
import app.meedu.deuna_demo.data.helpers.*
import app.meedu.deuna_demo.data.models.*
import app.meedu.deuna_demo.domain.repositories.*
import com.google.gson.*
import java.lang.Exception

class AuthRepositoryImp(
  private val keyStore: KeyStoreHelper,
  private val httpClient: HttpClient,
  private val apiKey: String,
) : AuthRepository {


  private val session: Session? get() = keyStore.readInstanceOf("session", Session::class.java)


  override val email: String? get() = session?.email
  override val userToken: String? get() = session?.token

  override suspend fun signUpWithEmail(
    email: String,
    firstName: String,
    lastName: String,
    phone: String,
  ): Either<Failure, Success> {
    val result = httpClient.send(path = "/users/register", method = HttpMethod.POST, body = mapOf(
      "email" to email, "first_name" to firstName, "last_name" to lastName, "phone" to phone
    ), headers = mutableMapOf("X-API-KEY" to apiKey), parser = {
      Session(
        email = email, token = it.getString("token"), userId = it.getString("user_id")
      )
    })

    return when (result) {
      is HttpResult.Success -> {
        keyStore.saveInstanceOf("session", result.data)
        Either.Right(Success())
      }

      else -> Either.Left(Failure())
    }
  }

  override fun signOut() {
    keyStore.delete("session")
  }
}