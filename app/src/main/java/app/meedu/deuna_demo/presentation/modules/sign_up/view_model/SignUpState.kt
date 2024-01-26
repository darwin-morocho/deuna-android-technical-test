package app.meedu.deuna_demo.presentation.modules.sign_up.view_model

class SignUpState(
  val email: String = "unique_email@test.com",
  val firstName: String = "Test",
  val lastName: String = "User",
  val phone: String = "098787765",
  val fetching: Boolean = false,
) {

  fun copy(
    email: String? = null,
    firstName: String? = null,
    lastName: String? = null,
    phone: String? = null,
    fetching: Boolean? = null,
  ): SignUpState {
    return SignUpState(
      email = email ?: this.email,
      firstName = firstName ?: this.firstName,
      lastName = lastName ?: this.lastName,
      phone = phone ?: this.phone,
      fetching = fetching ?: this.fetching
    )
  }
}