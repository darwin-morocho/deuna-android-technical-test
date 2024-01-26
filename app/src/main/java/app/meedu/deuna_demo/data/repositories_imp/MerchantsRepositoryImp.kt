package app.meedu.deuna_demo.data.repositories_imp

import app.meedu.deuna_demo.core.*
import app.meedu.deuna_demo.data.helpers.*
import app.meedu.deuna_demo.data.models.*
import app.meedu.deuna_demo.domain.enums.*
import app.meedu.deuna_demo.domain.repositories.*
import java.util.Date
import kotlin.math.*
import kotlin.time.Duration.Companion.milliseconds

class MerchantsRepositoryImp(
  private val httpClient: HttpClient,
  private val apiKey: String,
  private val keyStore: KeyStoreHelper,
) : MerchantsRepository {

  private val session: Session? get() = keyStore.readInstanceOf("session", Session::class.java)


  override suspend fun getOrderToken(
    productId: String,
    productName: String,
    currency: Currency,
    price: Double,
  ): Either<Failure, String> {

    val priceInCents = (price * 1000).roundToInt()
    val taxesInCents = ((price * 0.12) * 1000).roundToInt()
    val totalInCents = priceInCents + taxesInCents

    val result = httpClient.send(
      path = "/merchants/orders",
      method = HttpMethod.POST,
      body = mapOf(
        "order_type" to "PAYMENT_LINK",
        "order" to mapOf(
          "order_id" to Date().time.milliseconds.toString(),
          "items" to listOf(
            mapOf(
              "unit_price" to mapOf(
                "currency_symbol" to "$",
                "currency" to currency.name,
                "amount" to priceInCents
              ),
              "tax_amount" to mapOf(
                "currency_symbol" to "$",
                "currency" to currency.name,
                "amount" to taxesInCents
              ),
              "id" to productId,
              "name" to productName,
              "taxable" to true,
            )
          ),
          "currency" to currency.name,
          "store_code" to "all",
          "sub_total" to priceInCents,
          "total_tax_amount" to taxesInCents,
          "total_amount" to totalInCents,
          "billing_address" to mapOf(
            "id" to 1896,
            "user_id" to session?.userId,
            "email" to session?.email
          )
        )
      ),
      headers = mutableMapOf("X-API-KEY" to apiKey),
      parser = {
        it.getString("token")
      }
    )
    return when (result) {
      is HttpResult.Success -> {
        Either.Right(result.data)
      }

      else -> Either.Left(Failure())
    }
  }
}