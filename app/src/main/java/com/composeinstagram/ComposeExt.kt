package com.composeinstagram

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


fun Modifier.testBorder(
    width: Dp = 2.dp,
    color: Color = Color.Red,
    shape: Shape = RectangleShape
): Modifier {
    return border(width, color, shape)
}

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@Composable
fun LikeButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    var likeButtonResourceId by rememberSaveable {
        mutableStateOf(R.drawable.empty_heart)
    }
    val likeButtonScaleValue = remember { Animatable(1f) }
    LaunchedEffect(key1 = likeButtonResourceId) {
        likeButtonScaleValue.animateTo(1f, animationSpec = keyframes {
            durationMillis = 300
            1.0f at 0 with LinearOutSlowInEasing
            0.7f at 15 with FastOutLinearInEasing
            0.5f at 100
            1.0f at 300
        })
    }
    Image(
        modifier = modifier
            .scale(likeButtonScaleValue.value)
            .noRippleClickable {
                onClick()
                likeButtonResourceId =
                    if (likeButtonResourceId == R.drawable.red_heart)
                        R.drawable.empty_heart else R.drawable.red_heart
            },
        painter = painterResource(id = likeButtonResourceId),
        colorFilter = if (likeButtonResourceId == R.drawable.empty_heart) ColorFilter.tint(
            MaterialTheme.colors.onBackground
        ) else null,
        contentDescription = "Like button"
    )
}