package com.example.utils.presentation.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import com.example.resourse.GrayColor
import com.example.resourse.body2_Reg14

@Composable
fun TextFieldOutlined(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    label: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = body2_Reg14.copy(color = MaterialTheme.colorScheme.onBackground),
    isError: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done
    )
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf(value) }

    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            text = it
            onValueChange(text)
        },
        placeholder = {
            placeholder?.let {
                Text(
                    text = it,
                    color = GrayColor
                )
            }
        },
        label = {
            label?.let {
                Text(
                    text = it
                )
            }
        },
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        isError = isError,
        leadingIcon = leadingIcon,
        trailingIcon = {
            if (value.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            text = ""
                            onValueChange(text)
                        }
                )
            }
        },
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onAny = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
    )

}