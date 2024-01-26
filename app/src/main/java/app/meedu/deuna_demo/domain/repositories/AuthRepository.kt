package app.meedu.deuna_demo.domain.repositories

import app.meedu.deuna_demo.core.*

interface AuthRepository {

  val email: String?

  val userToken: String?

  suspend fun signUpWithEmail(
    email: String,
    firstName: String,
    lastName: String,
    phone: String,
  ): Either<Failure, Success>

  fun signOut()

}