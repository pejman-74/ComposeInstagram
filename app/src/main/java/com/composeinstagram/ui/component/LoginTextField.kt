package com.composeinstagram.ui.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.composeinstagram.ui.theme.textFieldBackground
import com.composeinstagram.ui.theme.textFieldBorder
import com.composeinstagram.ui.theme.textFieldHint


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginTextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (string: String) -> Unit
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onBackground),
        keyboardActions =keyboardActions,
        keyboardOptions = keyboardOptions,
        cursorBrush = SolidColor(MaterialTheme.colors.onBackground),
        visualTransformation = visualTransformation,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(1.dp, MaterialTheme.colors.textFieldBorder, MaterialTheme.shapes.medium)
            .background(
                MaterialTheme.colors.textFieldBackground,
                MaterialTheme.shapes.medium
            ),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (value == "") {
                    Text(
                        modifier = Modifier.padding(start = 1.dp),
                        text = hint,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.textFieldHint
                    )
                }
                innerTextField()
            }
        })
}
