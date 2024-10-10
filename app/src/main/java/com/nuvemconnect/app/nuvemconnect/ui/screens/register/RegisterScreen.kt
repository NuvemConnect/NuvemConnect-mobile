package com.nuvemconnect.app.nuvemconnect.ui.screens.register

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nuvemconnect.app.nuvemconnect.R
import com.nuvemconnect.app.nuvemconnect.model.error.NameErrorType
import com.nuvemconnect.app.nuvemconnect.navigation.graph.auth.screens.navigateToVerificationLink
import com.nuvemconnect.app.nuvemconnect.navigation.navigateBack
import com.nuvemconnect.app.nuvemconnect.ui.components.CustomButton
import com.nuvemconnect.app.nuvemconnect.ui.components.CustomTextField
import com.nuvemconnect.app.nuvemconnect.ui.components.PasswordTextField
import com.nuvemconnect.app.nuvemconnect.ui.components.TopBar
import com.nuvemconnect.app.nuvemconnect.ui.screens.login.validateEmail
import com.nuvemconnect.app.nuvemconnect.ui.screens.login.validatePassword
import com.nuvemconnect.app.nuvemconnect.ui.theme.dmSansFamily
import com.nuvemconnect.app.nuvemconnect.ui.theme.primary100
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = koinViewModel(),
) {
    val modifier: Modifier = Modifier
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val uiStateWithRemember = remember { viewModel.uiState }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.padding(top = 36.dp, start = 16.dp, end = 16.dp),
    ) {
        TopBar(
            headingTitle = stringResource(R.string.registre_se),
            subtitleText = stringResource(R.string.insira_suas_informacoes_pessoais),
            navController = navController,
            onBackClick = { navController.navigateBack() },
        )

        Column(modifier = modifier.verticalScroll(state = scrollState)) {
            Spacer(modifier = modifier.height(29.dp))
            CustomTextField(
                onValueChange = { newEmail ->
                    viewModel.onEmailChange(newEmail)
                },
                value = uiState.value.email,
                leadingIcon = painterResource(id = R.drawable.baseline_mail_outline_24),
                placeholder = stringResource(id = R.string.email),
                validate = {
                    validateEmail(uiState.value.email)
                },
                isUserInteracted = uiState.value.isUserInteracted,
            )
            Spacer(modifier = modifier.height(17.dp))
            CustomTextField(
                onValueChange = { newName ->
                    viewModel.onName(newName)
                },
                leadingIcon = painterResource(id = R.drawable.baseline_user_01),
                value = uiState.value.name,
                placeholder = stringResource(R.string.nome_do_usuario),
                validate = { validateEmail(uiState.value.name) }, // TODO: ajuda para a validação do nome e não do tipo Email
                isUserInteracted = false,
            )
            Spacer(modifier = modifier.height(17.dp))
            PasswordTextField(
                onValueChange = { newPassword ->
                    viewModel.onPasswordChange(newPassword)
                },
                value = uiState.value.password,
                placeholder = stringResource(id = R.string.digite_sua_senha),
                validate = { password ->
                    validatePassword(password)
                },
                isUserInteracted = uiState.value.isUserInteracted,
            )
            Spacer(modifier = modifier.height(17.dp))
            PasswordTextField(
                onValueChange = { newConfirmPassword ->
                    viewModel.onConfirmPassword(newConfirmPassword)
                },
                value = uiState.value.confirmPassword,
                placeholder = stringResource(id = R.string.confirme_sua_senha),
                validate = { confirmedPassword ->
                    validatePassword(confirmedPassword)
                },
                isUserInteracted = uiState.value.isUserInteracted,
            )
            Spacer(modifier = modifier.height(35.dp))
            CustomButton(
                onClick = {
                    val response = viewModel.onRegisterClick()
                    scope.launch {
                        uiStateWithRemember.collect { state ->
                            if (state.onError != null)
                                {
                                    delay(1000)
                                    Toast.makeText(context, state.onError, Toast.LENGTH_LONG).show()
                                    viewModel.dimissError()
                                }

                            if (state.onSucess != null)
                                {
                                    Toast.makeText(context, state.onSucess, Toast.LENGTH_LONG).show()
                                    delay(1000)
                                    navController.navigateToVerificationLink()
                                    viewModel.dimissSucess()
                                }
                        }
                    }
                },
                text = stringResource(R.string.registrar),
                backgroundColor = primary100,
                fontSize = 18.sp,
                fontFamily = dmSansFamily,
            )

            Spacer(modifier = modifier.height(30.dp))
        }
    }
}

fun validatedName(name: String): NameErrorType =
    when {
        name.isEmpty() -> NameErrorType.Empty
        else -> NameErrorType.None
    }

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(
        navController = rememberNavController(),
        viewModel = RegisterViewModel(),
    )
}
