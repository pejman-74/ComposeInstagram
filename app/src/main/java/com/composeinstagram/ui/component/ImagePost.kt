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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.request.ImageRequest
import com.composeinstagram.R
import com.composeinstagram.noRippleClickable
import com.composeinstagram.toCommaNumber
import com.composeinstagram.toInstaTime
import com.composeinstagram.ui.theme.storyItemBorderGradiantOrange
import com.composeinstagram.ui.theme.storyItemBorderGradiantRed
import com.composeinstagram.ui.theme.textFieldHint
import com.github.instagram4j.instagram4j.models.media.timeline.TimelineImageMedia
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun ImagePost(
    imageMedia: TimelineImageMedia,
    onLikePress: () -> Unit,
    onCommentPress: () -> Unit,
    onPreviewCommentLikePress: () -> Unit,
) {

    ConstraintLayout {
        val (avatarRef, usernameRef, moreRef, mediaRef, likeRef,
            commentRef, sendRef, bookmarkRef, likeCountRef,
            captionRef, viewAllNCommentsRef, previewCommentsColumnRef,
            postCreateTimeRef
        ) = createRefs()
        Image(
            modifier = Modifier
                .constrainAs(avatarRef) {
                    start.linkTo(parent.start, 8.dp)
                    top.linkTo(parent.top, 8.dp)
                }
                .size(40.dp)
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
                .clip(CircleShape),
            painter = rememberCoilPainter(
                request = ImageRequest.Builder(LocalContext.current)
                    .data(imageMedia.user.profile_pic_url).build()
            ), contentDescription = "Page profile pic"
        )
        Text(
            modifier = Modifier.constrainAs(usernameRef) {
                start.linkTo(avatarRef.end, 8.dp)
                top.linkTo(avatarRef.top)
                bottom.linkTo(avatarRef.bottom)
            },
            text = imageMedia.user.username,
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
        )
        Image(
            painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.more)),
            contentDescription = "More option post menu",
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
            modifier = Modifier
                .constrainAs(moreRef) {
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(avatarRef.top)
                    bottom.linkTo(avatarRef.bottom)
                }
                .size(25.dp)
        )

        Image(
            modifier = Modifier
                .constrainAs(mediaRef) {
                    start.linkTo(parent.start)
                    top.linkTo(avatarRef.bottom, 8.dp)
                },
            painter = rememberCoilPainter(
                request = ImageRequest.Builder(LocalContext.current)
                    .data(imageMedia.image_versions2.candidates.first().url).build()
            ), contentDescription = "Post content"
        )

        com.composeinstagram.LikeButton(
            modifier = Modifier
                .constrainAs(likeRef) {
                    top.linkTo(mediaRef.bottom, 8.dp)
                    start.linkTo(parent.start, 16.dp)
                }
                .size(25.dp),
            onClick = {
                onLikePress()
            }
        )

        Image(
            modifier = Modifier
                .constrainAs(commentRef) {
                    top.linkTo(mediaRef.bottom, 8.dp)
                    start.linkTo(likeRef.end, 16.dp)
                }
                .size(25.dp)
                .noRippleClickable {
                    onCommentPress()
                },
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
            painter = painterResource(id = R.drawable.comment),
            contentDescription = "Comment button"
        )
        Image(
            modifier = Modifier
                .constrainAs(sendRef) {
                    top.linkTo(mediaRef.bottom, 8.dp)
                    start.linkTo(commentRef.end, 16.dp)
                }
                .size(25.dp)
                .noRippleClickable {

                },
            painter = painterResource(id = R.drawable.send),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
            contentDescription = "Send button"
        )
        Image(
            modifier = Modifier
                .constrainAs(bookmarkRef) {
                    top.linkTo(mediaRef.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                }
                .width(25.dp)
                .height(20.dp)
                .noRippleClickable {

                },
            painter = painterResource(id = R.drawable.empty_bookmark),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
            contentDescription = "Bookmark button"
        )


        Text(
            modifier = Modifier.constrainAs(likeCountRef) {
                start.linkTo(parent.start, 16.dp)
                top.linkTo(likeRef.bottom, 8.dp)
            },
            text = imageMedia.like_count.toCommaNumber() + " likes",
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(captionRef) {
                    top.linkTo(likeCountRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 16.dp),
            text = buildAnnotatedString {
                val captionStyle = MaterialTheme.typography.body2
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = captionStyle.fontSize,
                        letterSpacing = captionStyle.letterSpacing
                    )
                ) {
                    append(imageMedia.user.username)
                }
                append(" ")
                withStyle(
                    SpanStyle(
                        fontWeight = captionStyle.fontWeight,
                        fontSize = captionStyle.fontSize,
                        letterSpacing = captionStyle.letterSpacing
                    )
                ) {
                    append(imageMedia.caption.text)
                }
            }

        )

        Text(
            modifier = Modifier.constrainAs(viewAllNCommentsRef) {
                start.linkTo(parent.start, 16.dp)
                top.linkTo(captionRef.bottom)
            },
            text = stringResource(
                R.string.view_all_comments,
                imageMedia.comment_count.toCommaNumber()
            ),
            style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.textFieldHint),
            maxLines = 1
        )
        Column(modifier = Modifier
            .constrainAs(previewCommentsColumnRef) {
                start.linkTo(parent.start)
                top.linkTo(viewAllNCommentsRef.bottom)
                end.linkTo(parent.end)
            }
            .padding(horizontal = 16.dp)) {
            imageMedia.preview_comments.forEach {
                PreviewComment(
                    modifier = Modifier.fillMaxWidth(),
                    username = it.user.username,
                    comment = it.text,
                    onPreviewCommentLikePress = onPreviewCommentLikePress
                )
            }
        }

        Text(
            modifier = Modifier.constrainAs(postCreateTimeRef) {
                start.linkTo(parent.start, 16.dp)
                top.linkTo(previewCommentsColumnRef.bottom, 4.dp)
            },
            text = (imageMedia.taken_at * 1000).toInstaTime(),
            style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.textFieldHint),
            maxLines = 1
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}
@Composable
fun PreviewComment(
    modifier: Modifier = Modifier,
    username: String,
    comment: String,
    onPreviewCommentLikePress: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = buildAnnotatedString {
                val captionStyle = MaterialTheme.typography.body2
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = captionStyle.fontSize,
                        letterSpacing = captionStyle.letterSpacing
                    )
                ) {
                    append(username)
                }
                append(" ")
                withStyle(
                    SpanStyle(
                        fontWeight = captionStyle.fontWeight,
                        fontSize = captionStyle.fontSize,
                        letterSpacing = captionStyle.letterSpacing
                    )
                ) {
                    append(comment)
                }
            }, maxLines = 1
        )

        com.composeinstagram.LikeButton(
            modifier = Modifier.size(15.dp),
            onClick = {
                onPreviewCommentLikePress()
            }
        )
    }
}