package com.composeinstagram.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.composeinstagram.R
import com.composeinstagram.TAG
import com.composeinstagram.ui.component.ImagePost
import com.composeinstagram.ui.component.StoryItem
import com.composeinstagram.ui.component.UserStory
import com.composeinstagram.viewmodel.MainViewModel
import com.composeinstagram.wrapper.ActionState
import com.github.instagram4j.instagram4j.models.media.timeline.TimelineCarouselMedia
import com.github.instagram4j.instagram4j.models.media.timeline.TimelineImageMedia
import com.github.instagram4j.instagram4j.models.media.timeline.TimelineVideoMedia

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    SideEffect {
        mainViewModel.feedStory()
    }

    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.instagram_logo)),
                contentDescription = stringResource(R.string.cd_instagram_logo),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                modifier = Modifier.size(height = 50.dp, width = 110.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.send),
                contentDescription = stringResource(R.string.cd_instagram_logo),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                modifier = Modifier.size(30.dp)
            )
        }
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),

            ) {

            item {
                val storyScrollableState = rememberScrollState()
                Row(
                    modifier = Modifier
                        .height(90.dp)
                        .fillMaxWidth()
                        .horizontalScroll(storyScrollableState),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserStory(
                        modifier = Modifier.width(90.dp),
                        imgUrl = mainViewModel.currentIGClient.selfProfile.profile_pic_url
                    )
                    when (val state = mainViewModel.feedStory) {
                        is ActionState.Fail -> {
                            Log.e(TAG, "MainScreen: ", state.message)
                        }
                        is ActionState.Loading ->
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 1.dp,
                                color = MaterialTheme.colors.onBackground
                            )

                        is ActionState.Success -> {
                            state.value.let { reels ->
                                reels.forEach { reel ->
                                    StoryItem(
                                        modifier = Modifier.width(100.dp),
                                        imgUrl = reel.user.profile_pic_url,
                                        name = reel.user.username
                                    )
                                }
                            }

                        }
                    }
                }

            }
            item {
                Divider(
                    modifier = Modifier.padding(top = 8.dp),
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.1f)
                )
            }

            item {
                mainViewModel.feedPost.feed_items?.forEach {
                    when (it) {
                        is TimelineImageMedia -> {
                            ImagePost(
                                imageMedia = it,
                                onLikePress = { },
                                onCommentPress = { }) {
                            }
                        }
                        is TimelineVideoMedia -> {
                        }
                        is TimelineCarouselMedia -> {
                        }
                    }
                }

            }
        }


    }
}



