package app.meedu.deuna_demo.presentation.shared.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*

@Composable

fun MaterialButton(onClick: () -> Unit, text: String, enabled: Boolean = true) {
  Button(
    enabled = enabled,
    modifier = Modifier.fillMaxWidth().height(50.dp),
    onClick = onClick
  ) {
    Text(text = text)
  }
}