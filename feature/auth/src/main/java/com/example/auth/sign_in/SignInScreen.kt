package com.example.auth.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.navigation.AuthGraph
import com.example.navigation.LocalNavController
import com.example.resourse.R
import com.example.resourse.body1_Reg16
import com.example.utils.event.EmailController
import com.example.utils.event.ObserveAsEvent
import com.example.utils.presentation.compose.ApplyButton
import com.example.utils.presentation.compose.LottieSimpleAnimation
import com.example.utils.presentation.compose.PasswordFieldOutlined
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
    val navController = LocalNavController.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    ObserveAsEvent(EmailController.event) {
        event(SignInEvent.ChangeEmail(it))
    }

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
                    singleLine = true
                )

                PasswordFieldOutlined(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.password,
                    onValueChange = { event(SignInEvent.ChangePassword(it)) },
                    placeholder = stringResource(R.string.placeholder_password),
                    label = "Password"
                )

                TextButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { navController.navigate(AuthGraph.SignUpRoute(uiState.email)) }
                ) {
                    Text(
                        text = stringResource(R.string.not_account),
                        style = body1_Reg16
                    )
                }
            }

            ApplyButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
                    .align(Alignment.BottomCenter),
                text = stringResource(R.string.sign_in),
                onClick = {
//                    navController.navigate(HomeGraph.HomeRoute) {
//                        popUpTo(AuthGraph.SignInRoute) {
//                            inclusive = true
//                        }
//                    }
                }
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