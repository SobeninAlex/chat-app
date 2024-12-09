package com.example.utils.presentation.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.resourse.GrayColor
import com.example.resourse.R
import com.example.resourse.body2_Reg14

@Composable
fun PasswordFieldOutlined(
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
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done
    )
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var visualTransformation by remember {
        mutableStateOf<VisualTransformation>(PasswordVisualTransformation())
    }
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
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (visualTransformation == VisualTransformation.None) {
                        Icon(
                            painter = painterResource(R.drawable.ic_eye_close),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .clickable { visualTransformation = PasswordVisualTransformation() }
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_eye_open),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .clickable { visualTransformation = VisualTransformation.None }
                        )
                    }

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