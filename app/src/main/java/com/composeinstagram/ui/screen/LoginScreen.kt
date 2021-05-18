package com.composeinstagram.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.composeinstagram.R
import com.composeinstagram.ui.component.CustomPasswordVisualTransformation
import com.composeinstagram.ui.component.LoginTextField
import com.composeinstagram.ui.theme.loginButtonBackground
import com.composeinstagram.ui.theme.loginButtonDisabledBackground
import com.composeinstagram.ui.theme.loginButtonDisabledTextColor
import com.composeinstagram.ui.theme.loginButtonTextColor
import com.composeinstagram.viewmodel.LoginScreenViewModel
import com.composeinstagram.wrapper.LoginState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    lViewModel: LoginScreenViewModel = hiltNavGraphViewModel(),
    navToMain: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val loginState by lViewModel.loginState.collectAsState(null)

    val showLastChar by produceState(initialValue = true, key1 = lViewModel.passwordTextState) {
        if (lViewModel.lastPasswordLength < lViewModel.passwordTextState.length) {
            lViewModel.lastPasswordLength = lViewModel.passwordTextState.length
            value = true
            delay(1000)
            value = false
        } else {
            lViewModel.lastPasswordLength = lViewModel.passwordTextState.length
            value = false
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 32.dp)
        ) {

            Image(
                painter = rememberVectorPainter(image = ImageVector.Companion.vectorResource(id = R.drawable.instagram_logo)),
                contentDescription = stringResource(R.string.cd_instagram_logo),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
            )

            LoginTextField(
                modifier = Modifier
                    .testTag("UserNameTextField")
                    .padding(top = 32.dp),
                value = lViewModel.userNameTextState,
                hint = stringResource(R.string.username_textfield_hint),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    imeAction = ImeAction.Next
                ), keyboardActions = KeyboardActions(onNext = {
                    FocusRequester.Default.requestFocus()
                }),
                onValueChange = { lViewModel.userNameTextState = it }
            )

            LoginTextField(
                modifier = Modifier
                    .testTag("PasswordTextField")
                    .padding(top = 16.dp)
                    .focusRequester(FocusRequester.Default),
                value = lViewModel.passwordTextState,
                hint = stringResource(R.string.password_textfield_hint),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    autoCorrect = false,
                    imeAction = ImeAction.Go
                ),
                visualTransformation = CustomPasswordVisualTransformation(showLastChar = showLastChar),
                onValueChange = {
                    lViewModel.passwordTextState = it
                })

            Button(
                modifier = Modifier
                    .testTag("LoginButton")
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    MaterialTheme.colors.loginButtonBackground,
                    MaterialTheme.colors.loginButtonTextColor,
                    MaterialTheme.colors.loginButtonDisabledBackground,
                    MaterialTheme.colors.loginButtonDisabledTextColor
                ),
                enabled = lViewModel.userNameTextState != "" && lViewModel.passwordTextState != "",
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    lViewModel.login(lViewModel.userNameTextState, lViewModel.passwordTextState)
                }

            ) {

                if (loginState == LoginState.Fail) {
                    val string = stringResource(R.string.login_failed)
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = string,
                            duration = SnackbarDuration.Short
                        )
                    }
                }

                if (loginState == LoginState.Success)
                    navToMain()

                if (loginState == LoginState.Loading)
                    CircularProgressIndicator(color = MaterialTheme.colors.loginButtonTextColor)
                else
                    Text(
                        text = stringResource(R.string.logein),
                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
                    )

            }
        }
    }
}



