package com.example.auth.presentation.sign_up

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.navigation.HomeGraph
import com.example.navigation.LocalNavController
import com.example.resourse.AccentColor
import com.example.resourse.R
import com.example.resourse.body1_Reg16
import com.example.utils.event.LaunchNextScreenController
import com.example.utils.event.ObserveAsEvent
import com.example.utils.presentation.compose.ApplyButton
import com.example.utils.presentation.compose.DotsLoadingIndicator
import com.example.utils.presentation.compose.PasswordFieldOutlined
import com.example.utils.presentation.compose.SimpleTextFieldOutlined
import com.example.utils.presentation.noRippleClickable

@Composable
fun SignUpScreen(email: String) {
    val viewModel = hiltViewModel<SignUpViewModel, SignUpViewModel.Factory> { factory ->
        factory.create(email)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SignUpContent(
        uiState = uiState,
        event = viewModel::onEvent
    )
}

@Composable
private fun SignUpContent(
    uiState: SignUpUiState,
    event: (SignUpEvent) -> Unit
) {
    val navController = LocalNavController.current
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    ObserveAsEvent(LaunchNextScreenController.event) {
        navController.navigate(HomeGraph.HomeRoute) {
            popUpTo(currentBackStackEntry?.destination?.route!!) {
                inclusive = true
            }
        }
    }

    if (uiState.loading) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            DotsLoadingIndicator(dotsColor = AccentColor)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .noRippleClickable {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                },
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(R.drawable.ic_telegram),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(68.dp)
                )

                Spacer(modifier = Modifier.height(48.dp))

                SimpleTextFieldOutlined(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.fullName,
                    onValueChange = { event(SignUpEvent.ChangeFullName(it)) },
                    placeholder = stringResource(R.string.placeholder_full_name),
                    label = "Full name",
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                SimpleTextFieldOutlined(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.email,
                    onValueChange = { event(SignUpEvent.ChangeEmail(it)) },
                    placeholder = stringResource(R.string.placeholder_email),
                    label = "Email",
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                PasswordFieldOutlined(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.password,
                    onValueChange = { event(SignUpEvent.ChangePassword(it)) },
                    placeholder = stringResource(R.string.placeholder_password),
                    label = "Password"
                )

                Spacer(modifier = Modifier.height(12.dp))

                PasswordFieldOutlined(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.password,
                    isError = uiState.errorConfirm,
                    onValueChange = { event(SignUpEvent.ChangeConfirmPassword(it)) },
                    placeholder = stringResource(R.string.placeholder_password),
                    label = "Confirm password"
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Text(
                        text = stringResource(R.string.already_account),
                        style = body1_Reg16
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                ApplyButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.sign_up),
                    enabled = uiState.enabledSignUpButton,
                    onClick = { event(SignUpEvent.OnSignUpClicked) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun SignUpContentPreview() {
    SignUpContent(
        uiState = SignUpUiState.FAKE,
        event = {}
    )
}