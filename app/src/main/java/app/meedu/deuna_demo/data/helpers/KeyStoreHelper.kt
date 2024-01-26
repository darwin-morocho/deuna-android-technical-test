package app.meedu.deuna_demo.data.helpers

import android.content.*
import app.meedu.deuna_demo.data.models.*
import com.google.gson.Gson
import java.lang.Exception

/**
 * @property prefs must be an EncryptedSharedPreferences
 */
class KeyStoreHelper(private val prefs: SharedPreferences, private val gson: Gson) {
  fun save(key: String, value: String) {
    prefs.edit().putString(key, value).apply()
  }

  fun read(key: String): String? {
    return prefs.getString(key, null)
  }

  fun <T> saveInstanceOf(key: String, instance: T) {
    println(gson.toJson(instance))
    prefs.edit().putString(key, gson.toJson(instance)).apply()
  }


  fun <T> readInstanceOf(key: String, classOfT: Class<T>): T? {
    try {
      val pref = prefs.getString(key, null) ?: return null
      return gson.fromJson(pref, classOfT)
    } catch (e: Exception) {
      return null
    }
  }

  fun delete(key: String) {
    prefs.edit().remove(key).apply()
  }
}