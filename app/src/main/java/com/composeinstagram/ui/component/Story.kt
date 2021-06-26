package com.composeinstagram.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import com.composeinstagram.R
import com.composeinstagram.ui.theme.storyItemBorderGradiantOrange
import com.composeinstagram.ui.theme.storyItemBorderGradiantRed
import com.google.accompanist.coil.rememberCoilPainter


@Composable
fun StoryItem(
    modifier: Modifier = Modifier,
    imgUrl: String,
    name: String
) {
    val painter = rememberCoilPainter(
        request = ImageRequest.Builder(LocalContext.current).data(imgUrl).build()
    )
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painter,
            contentDescription = "Story Item",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(1f, false)
                .aspectRatio(1f)
                .border(
                    2.dp,
                    Brush.verticalGradient(
                        listOf(
                            storyItemBorderGradiantRed,
                            storyItemBorderGradiantOrange
                        )
                    ), CircleShape
                )
                .padding(4.dp)
                .clip(CircleShape)

        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.caption,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

    }

}

@Composable
fun UserStory(modifier: Modifier = Modifier, imgUrl: String) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        Box(modifier = Modifier.weight(1f, false)) {
            Image(
                painter = rememberCoilPainter(
                    request = ImageRequest.Builder(LocalContext.current).data(imgUrl).build()
                ),
                contentDescription = "User Story",
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clip(CircleShape)
            )
            Image(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .border(2.dp, MaterialTheme.colors.background, CircleShape)
                    .size(25.dp),
                painter = painterResource(id = R.drawable.add),
                contentDescription = "Add Story Icon"
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.your_story),
            style = MaterialTheme.typography.caption,
        )

    }
}
