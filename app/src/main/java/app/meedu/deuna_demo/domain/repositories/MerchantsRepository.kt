package app.meedu.deuna_demo.domain.repositories

import app.meedu.deuna_demo.core.*
import app.meedu.deuna_demo.domain.enums.*

interface MerchantsRepository {
  suspend fun getOrderToken(
    productId: String,
    productName: String,
    currency: Currency,
    price: Double,
  ): Either<Failure, String>
}