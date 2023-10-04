package com.xquare.xquare_android.feature.github

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.semicolon.design.Body1
import com.semicolon.design.Body2
import com.semicolon.design.Body3
import com.semicolon.design.Subtitle4
import com.semicolon.design.color.primary.gray.gray50
import com.semicolon.design.color.primary.gray.gray700
import com.semicolon.design.color.primary.gray.gray900
import com.semicolon.design.color.primary.purple.purple200
import com.xquare.domain.entity.github.GithubInformationEntity
import com.xquare.domain.entity.github.GithubListEntity
import com.xquare.xquare_android.util.rememberAsyncGifImagePainter
import java.text.DecimalFormat

fun formatNumberWithComma(number: Int): String {
    val decimalFormat = DecimalFormat("#,###")
    return decimalFormat.format(number)
}

@Composable
fun GithubRankingItem(
    githubInformation: GithubInformationEntity?,
    borderState: Boolean,
) {
    val borderColor = if (borderState) purple200 else gray50
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
            .background(color = gray50, shape = RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(12.dp))
            .padding(start = 15.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = rememberAsyncGifImagePainter(githubInformation?.profileFilename),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(30.dp)),
        )
        Spacer(Modifier.size(10.dp))
        Column {
            Body2(
                text = "${githubInformation?.ranking}위 : ${githubInformation?.name}",
                fontWeight = FontWeight.Medium,
                color = gray900,
            )
            Body3(
                text = "@${githubInformation?.username}",
                fontWeight = FontWeight.Normal,
                color = gray700,
            )
        }
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(40.dp)
                .padding(bottom = 5.dp)
        ) {
            Body1(
                text = "${githubInformation?.contributions?.let { formatNumberWithComma(it) }} 커밋",
                fontWeight = FontWeight.Medium,
                color = gray900,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun GithubItem(
    githubUser: GithubListEntity.GithubUserListEntity,
    modifier: Modifier = Modifier,
    crown: Painter,
    color: Color,
    centerPadding: Dp,
    tint: Color,
    image: Dp
) {
    Column(
        modifier = modifier
            .background(gray50, RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = color, shape = RoundedCornerShape(12.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier.padding(top = 10.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Icon(
                    painter = crown,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.TopCenter),
                    tint = tint
                )
                Image(
                    painter = rememberAsyncGifImagePainter(githubUser.profile_file_name),
                    /*rememberAsyncImagePainter(
                        model = githubList.profile_file_name,
                        placeholder = ColorPainter(gray200),
                        error = painterResource(id = R.drawable.ic_profile_default)
                    )*/
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = (15.5).dp)
                        .size(image)
                        .clip(RoundedCornerShape(30.dp)),
                )
            }
            Spacer(Modifier.height(centerPadding))
            Subtitle4(text = githubUser.name, color = gray900, fontWeight = FontWeight.SemiBold)
            Body3(
                text = " @${githubUser.username}",
                color = gray700,
                fontWeight = FontWeight.Normal
            )
            Body3(
                text = "${formatNumberWithComma(githubUser.contributions)} 커밋",
                color = gray900,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun GithubAllRankingItem(
    githubUser: GithubListEntity.GithubUserListEntity,
    borderState: Boolean,
) {
    val borderColor = if (borderState) {
        purple200
    } else {
        gray50
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
            .background(color = gray50, shape = RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(12.dp))
            .padding(start = 15.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = rememberAsyncGifImagePainter(githubUser.profile_file_name),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(30.dp)),
        )
        Spacer(Modifier.size(10.dp))
        Column {
            Body2(
                text = "${githubUser.ranking}위 : ${githubUser.name}",
                fontWeight = FontWeight.Medium,
                color = gray900
            )
            Body3(text = "@${githubUser.username}", fontWeight = FontWeight.Normal, color = gray700)
        }
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(40.dp)
                .padding(bottom = 5.dp)
        ) {
            Body1(
                text = "${formatNumberWithComma(githubUser.contributions)} 커밋",
                fontWeight = FontWeight.Medium,
                color = gray900,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
    Spacer(Modifier.size(12.dp))
}
