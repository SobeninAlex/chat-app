package com.example.auth.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.resourse.GrayColor
import com.example.resourse.R
import com.example.utils.presentation.compose.ApplyButton
import com.example.utils.presentation.compose.LottieSimpleAnimation
import com.example.utils.presentation.compose.TextFieldOutlined
import com.example.utils.presentation.noRippleClickable

@Composable
fun SignInScreen() {
    val viewMode = hiltViewModel<SignInViewModel>()
    val uiState by viewMode.uiState.collectAsStateWithLifecycle()

    SignInContent(
        uiState = uiState,
        event = viewMode::onEvent
    )
}

@Composable
private fun SignInContent(
    uiState: SignInUiState,
    event: (SignInEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .noRippleClickable {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                },
        ) {
            LottieSimpleAnimation(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .align(Alignment.TopCenter),
                animation = R.raw.anim_1,
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Center)
            ) {
                TextFieldOutlined(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.email,
                    onValueChange = { event(SignInEvent.ChangeEmail(it)) },
                    placeholder = stringResource(R.string.placeholder_email),
                    label = "Email",
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { event(SignInEvent.ChangeEmail("")) }
                        )
                    }
                )

                var visualTransformation by remember {
                    mutableStateOf(VisualTransformation.None)
                }

                TextFieldOutlined(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.password,
                    onValueChange = { event(SignInEvent.ChangePassword(it)) },
                    placeholder = stringResource(R.string.placeholder_password),
                    label = "Password",
                    visualTransformation = visualTransformation,
                    trailingIcon = {
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

                    }
                )
            }

            ApplyButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
                    .align(Alignment.BottomCenter),
                text = stringResource(R.string.sign_in),
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun SignInScreenPreview() {
    SignInContent(
        uiState = SignInUiState.FAKE,
        event = {}
    )
}