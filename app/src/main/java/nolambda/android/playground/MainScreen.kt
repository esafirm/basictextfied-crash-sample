package nolambda.android.playground

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val textFieldState = remember { TextFieldState("Paste here…") }
    var logText by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
    ) {
        BasicTextField(
            state = textFieldState,
            lineLimits = TextFieldLineLimits.SingleLine,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .height(56.dp)
        )

        val scope = rememberCoroutineScope()

        Button(
            onClick = {
                scope.launch {
                    logText = "Generated string with length 20_000"

                    textFieldState.clearText()
                    textFieldState.edit {
                        append(generateString(20_000))
                    }
                    focusRequester.requestFocus()

                    delay(2_000)

                    logText = "Generated string with length 40_000"

                    textFieldState.clearText()
                    textFieldState.edit {
                        append(generateString(40_000))
                    }
                    focusRequester.requestFocus()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Crash the app")
        }

        Text(
            text = logText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}


private fun generateString(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}
