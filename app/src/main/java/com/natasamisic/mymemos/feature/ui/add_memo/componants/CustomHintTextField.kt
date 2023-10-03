package com.natasamisic.mymemos.feature.ui.add_memo.componants

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun CustomHintTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit
) {
    Box(modifier = modifier) {
        if (text.isEmpty() && isHintVisible) {
            HintText(hint = hint, textStyle = textStyle)
        }
        ActualTextField(
            text = text,
            onValueChange = onValueChange,
            textStyle = textStyle,
            singleLine = singleLine,
            onFocusChange = onFocusChange
        )
    }
}

@Composable
fun HintText(hint: String, textStyle: TextStyle) {
    Text(text = hint, style = textStyle, color = Color.DarkGray)
}

@Composable
fun ActualTextField(
    text: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
    singleLine: Boolean,
    onFocusChange: (FocusState) -> Unit
) {
    BasicTextField(
        value = text,
        onValueChange = onValueChange,
        singleLine = singleLine,
        textStyle = textStyle,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                onFocusChange(it)
            }
    )
}
