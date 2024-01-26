package app.meedu.deuna_demo.data.helpers


import app.meedu.deuna_demo.BuildConfig
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.*
import java.net.*

class HttpClient(
  private val okHttpClient: OkHttpClient,
  private val baseUrl: String,
  private val gson: Gson,
) {
  fun <T> send(
    path: String,
    method: HttpMethod,
    queryParameters: Map<String, String> = mapOf(),
    headers: Map<String, String> = mapOf(),
    body: Map<String, Any> = mapOf(),
    parser: (json: JSONObject) -> T,
  ): HttpResult<T> {
    try {

      val url = when {
        path.startsWith("http://") || path.startsWith("https://") -> path
        else -> baseUrl + (if (path.startsWith("/")) path else "/$path")
      } + if (queryParameters.isNotEmpty()) {
        "?" + queryParameters.map { "${it.key}=${URLEncoder.encode(it.value, "UTF-8")}" }
          .joinToString("&")
      } else {
        ""
      }
      val requestBuilder = Request.Builder().url(url)

      headers.forEach { (key, value) ->
        requestBuilder.addHeader(key, value)
      }

      when (method) {
        HttpMethod.GET -> requestBuilder.method(HttpMethod.GET.name, null)
        else -> {
          val json = gson.toJson(body)
          requestBuilder.method(
            method.name, json.toRequestBody(
              "application/json; charset=utf-8".toMediaType(),
            )
          )
        }
      }
      val response = okHttpClient.newCall(requestBuilder.build()).execute()
      val responseBody = response.body?.bytes()?.decodeToString()


      printDebugInfo(path, response.code, responseBody)

      if (response.isSuccessful) {
        return HttpResult.Success(
          response.code,
          parser(
            JSONObject(
              responseBody ?: "{}"
            )
          ),
        )
      }



      return HttpResult.Failure(
        response.code, response.message,
      )
    } catch (e: Exception) {
      println(e)
      return HttpResult.Failure(
        0, e
      )
    }
  }


  private fun printDebugInfo(path: String, statusCode: Int, responseBody: String?) {
    if (BuildConfig.DEBUG) {
      println(responseBody)
    }

    if (BuildConfig.DEBUG && statusCode != 0) {
      println("❌ $path $statusCode")
    }
  }
}

enum class HttpMethod {
  GET, POST, PATCH, PUT, DELETE,
}

sealed class HttpResult<T>(open val statusCode: Int) {
  class Success<T>(override val statusCode: Int, val data: T) : HttpResult<T>(statusCode)
  class Failure<T>(override val statusCode: Int, val errorData: Any) : HttpResult<T>(statusCode)
}