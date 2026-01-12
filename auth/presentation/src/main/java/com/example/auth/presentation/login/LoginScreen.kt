package com.example.auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.auth.presentation.R
import com.example.core.presentation.designsystem.EmailIcon
import com.example.core.presentation.designsystem.Poppins
import com.example.core.presentation.designsystem.RuniqueTheme
import com.example.core.presentation.designsystem.components.GradientBackground
import com.example.core.presentation.designsystem.components.RuniqueActionButton
import com.example.core.presentation.designsystem.components.RuniquePasswordTextField
import com.example.core.presentation.designsystem.components.RuniqueTextField
import com.example.core.presentation.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginRoot(
    onSignUpClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    loginViewModel: LoginViewModel = koinViewModel()
) {
    val state by loginViewModel.state.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    ObserveAsEvents(loginViewModel.events) { events ->
        when (events) {
            is LoginEvents.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    events.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }

            LoginEvents.LoginSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    R.string.login_successful,
                    Toast.LENGTH_LONG
                ).show()
                onLoginSuccess()
            }

            LoginEvents.Register -> {
                keyboardController?.hide()
                onSignUpClick()
            }
        }
    }
    LoginScreen(
        state = state,
        onAction = loginViewModel::onActions
    )
}

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginActions) -> Unit
) {
    GradientBackground(
        hasToolbar = false
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = stringResource(R.string.hi_there) + "!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = stringResource(R.string.runique_welcome_text),
                fontFamily = Poppins,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(
                modifier = Modifier.height(20.dp)
            )
            RuniqueTextField(
                value = state.email,
                onValueChanged = {
                    onAction(LoginActions.UpdateEmailValue(it))
                },
                startIcon = EmailIcon,
                endIcon = null,
                hint = stringResource(id = R.string.example_email),
                title = stringResource(id = R.string.email),
                modifier = Modifier.fillMaxWidth(),
                additionalInfo = null,
                keyboardType = KeyboardType.Email
            )
            RuniquePasswordTextField(
                value = state.password,
                onValueChanged = {
                    onAction(LoginActions.UpdatePasswordValue(it))
                },
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(LoginActions.OnTogglePasswordVisibility)
                },
                hint = stringResource(id = R.string.password),
                title = stringResource(id = R.string.password),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
            RuniqueActionButton(
                text = stringResource(id = R.string.register),
                isLoading = state.isLoggingIn,
                enabled = state.canLogIn,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(LoginActions.OnLoginClick)
                }
            )
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    append(stringResource(R.string.dont_have_an_account) + " ")
                }
                val link = LinkAnnotation.Clickable(
                    tag = "login",
                    styles = TextLinkStyles(
                        style = SpanStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                ) {
                    onAction(LoginActions.OnRegisterClick)
                }
                withLink(link = link) {
                    append(stringResource(R.string.sign_up))
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                BasicText(
                    text = annotatedString,
                )
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    RuniqueTheme {
        LoginScreen(
            LoginState(),
            onAction = {}
        )
    }
}