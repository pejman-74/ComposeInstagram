package com.composeinstagram.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val Colors.textFieldBackground: Color
    @Composable
    get() = if (isLight) Color(0xFFFAFAFA) else Color(0xFF424242)

val Colors.textFieldBorder: Color
    @Composable
    get() = if (isLight) Color(0xFFC7C7C7) else textFieldBackground

val Colors.textFieldHint: Color
    @Composable
    get() = if (isLight) Color(0xFFAAAAAA) else Color(0xFF919191)

val Colors.loginButtonBackground: Color
    @Composable
    get() = if (isLight) Color(0xFF3897F0) else Color(0xFF3897F0)

val Colors.loginButtonTextColor: Color
    @Composable
    get() = if (isLight) Color(0xFFFFFFFF) else Color(0xFFFFFFFF)

val Colors.loginButtonDisabledBackground: Color
    @Composable
    get() = if (isLight) Color(0xF395C4EF) else Color(0xF302284D)

val Colors.loginButtonDisabledTextColor: Color
    @Composable
    get() = if (isLight) Color(0xF3DCEEFF) else Color(0xF3255581)


val storyItemBorderGradiantRed = Color(0xFFD72367)

val storyItemBorderGradiantOrange = Color(0xFFF7AF5E)