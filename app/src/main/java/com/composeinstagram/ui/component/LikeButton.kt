package com.composeinstagram.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.composeinstagram.R
import com.composeinstagram.noRippleClickable

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