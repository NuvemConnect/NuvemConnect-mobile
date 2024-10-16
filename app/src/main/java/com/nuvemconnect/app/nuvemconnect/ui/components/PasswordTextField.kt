package com.nuvemconnect.app.nuvemconnect.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nuvemconnect.app.nuvemconnect.R
import com.nuvemconnect.app.nuvemconnect.model.error.PasswordErrorType
import com.nuvemconnect.app.nuvemconnect.ui.theme.dmSansFamily
import com.nuvemconnect.app.nuvemconnect.ui.theme.error100
import com.nuvemconnect.app.nuvemconnect.ui.theme.poppinsFontFamily
import com.nuvemconnect.app.nuvemconnect.ui.theme.white_two

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    value: String,
    placeholder: String,
    validate: (String) -> PasswordErrorType,
    isUserInteracted: Boolean
) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    val error = if (isUserInteracted) validate(value) else PasswordErrorType.None

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Spacer(modifier = modifier.height(5.dp))
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.length <= 40) onValueChange(newValue)
            },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_lock_outline),
                    contentDescription = "Icone de senha"
                )
            },
            trailingIcon = {
                val image =
                    if (passwordVisibility) painterResource(id = R.drawable.outline_visibility_24) else painterResource(
                        id = R.drawable.outline_visibility_off_24
                    )
                val description = if (passwordVisibility) "Hide password" else "Show password"


                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(painter = image, contentDescription = description)
                }
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            placeholder = {
                Text(
                    text = placeholder,
                    fontFamily = dmSansFamily,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1
                )
            },
            modifier = modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(8),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = white_two,
                focusedBorderColor = white_two,
                focusedTextColor = Color.Black,
                unfocusedContainerColor = white_two,
            ),
            isError = error != PasswordErrorType.None
        )
        if (error != PasswordErrorType.None) {
            Text(
                text = error.message,
                color = error100,
                fontSize = 12.sp,
                fontFamily = poppinsFontFamily
            )
        }
    }
}


@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun CustomTextFieldPreview() {
    val value by rememberSaveable {
        mutableStateOf("")
    }
    PasswordTextField(
        onValueChange = {},
        value = value,
        placeholder = "Descrição do texto",
        validate = { PasswordErrorType.rulePassword },
        isUserInteracted = false
    )

}