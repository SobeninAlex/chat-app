package com.example.utils.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.resourse.GrayColor50
import com.example.resourse.MainColor
import com.example.resourse.body1_Reg16
import com.example.resourse.body2_Reg14
import com.example.resourse.roundedCornerShape12
import com.example.utils.presentation.noRippleClickable
import com.google.android.material.internal.ViewUtils.RelativePadding

@Preview
@Composable
fun EditTextPreview() {
    SimpleTextField(value = "123", onValueChange = {})
}

@Composable
fun SimpleTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    maxLength: Int = Int.MAX_VALUE,
    placeholder: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = body1_Reg16.copy(color = MaterialTheme.colorScheme.onBackground),
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done
    ),
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(MainColor),
    closeIconVisibility: Boolean = true
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = value,
        onValueChange = {
            if (isValidValue(it, maxLength)) onValueChange(it)
        },
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onAny = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        visualTransformation = visualTransformation,
        decorationBox = { innerTextField ->
            // в многострочном редакторе выравниваем крестик (очистки поля) по верхнему краю
            // и нужно сдвинуть его чуть вниз, чтобы визуально казался по центру
            // когда еще не ввели несколько строк
            val verticalAlignment = if (singleLine) Alignment.CenterVertically else Alignment.Top
            Row(
                verticalAlignment = verticalAlignment,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = textStyle,
                            color = GrayColor50
                        )
                    }
                    innerTextField()
                }
                if (value.isNotEmpty() && closeIconVisibility) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Очистить поле",
                        modifier = modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .clickable { onValueChange("") }
                    )
                }
            }
        },
        modifier = modifier
            .clip(roundedCornerShape12)
            .background(MaterialTheme.colorScheme.outline)
            .padding(12.dp)
    )
}

private fun isValidValue(value: String, maxLength: Int): Boolean {
    return value.length <= maxLength
}
