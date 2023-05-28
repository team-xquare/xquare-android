package com.xquare.xquare_android.feature.profile

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.semicolon.design.Body1
import com.semicolon.design.Body2
import com.semicolon.design.Subtitle4
import com.semicolon.design.color.primary.gray.*
import com.semicolon.design.color.primary.white.white
import com.xquare.domain.entity.profile.ProfileEntity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.CenterAppBar
import com.xquare.xquare_android.util.DevicePaddings
import com.xquare.xquare_android.util.makeToast
import com.xquare.xquare_android.util.toFile
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.io.File

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: ProfileViewModel = hiltViewModel()
    var profile: ProfileEntity? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        viewModel.fetchProfile()
        viewModel.eventFlow.collect {
            when (it) {
                is ProfileViewModel.Event.Success -> {
                    profile = it.data
                }
                is ProfileViewModel.Event.Failure -> {
                    makeToast(context, "프로필을 불러오지 못했습니다")
                }
                is ProfileViewModel.Event.UploadFileSuccess -> {
                    viewModel.fixProfileImage(it.data[0])
                }
                is ProfileViewModel.Event.UploadFileFailure -> {
                    makeToast(context, "이미지를 불러오지 못했습니다")
                }
                is ProfileViewModel.Event.ImageChangeSuccess -> {
                    viewModel.fetchProfile()
                }
                is ProfileViewModel.Event.ImageChangeFailure -> {
                    makeToast(context, "이미지를 변경하지 못했습니다")
                }
            }
        }
    }
    Profile(
        profile = profile,
        onBackPress = { navController.popBackStack() },
        sendImage = {
            viewModel.uploadFile(it)
        }
    )
}

@Composable
private fun Profile(
    profile: ProfileEntity?,
    onBackPress: () -> Unit,
    sendImage: (File) -> Unit,
) {
    val context = LocalContext.current
    var galleryState by remember { mutableStateOf(false) }

    val openWebViewGallery =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data!!.data?.run {
                    sendImage(toFile(context,this))
                }
            }
            galleryState = false
        }
    val openGalleryLauncher =
        Intent(Intent.ACTION_PICK).apply {
            this.type = "image/*"
            this.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        }

    if (galleryState) {
        openWebViewGallery.launch(openGalleryLauncher)
    }

    Scaffold(
        modifier = Modifier
            .background(white)
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp,
                bottom = DevicePaddings.navigationBarHeightDp.dp
            ),
        topBar = {
            Box(modifier = Modifier.padding(bottom = 25.dp)) {
                CenterAppBar(
                    painter = painterResource(R.drawable.ic_back),
                    text = "마이페이지",
                    onIconClick = onBackPress
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = it.calculateTopPadding()),
            horizontalAlignment = Alignment.Start
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val profileImageModifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                    ) {
                        galleryState = true
                    }

                Box{
                    Image(
                        modifier = profileImageModifier,
                        painter = rememberAsyncImagePainter(
                            model = profile?.profileFileName,
                            placeholder = ColorPainter(gray200),
                            error = painterResource(id = R.drawable.ic_profile_default),
                        ),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                    Image(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.BottomEnd),
                        painter = painterResource(R.drawable.ic_camera),
                        contentDescription = null
                    )
                }
                Spacer(Modifier.size(16.dp))
                Column {
                    Subtitle4(text = profile?.name ?: "")
                    Text(
                        text = "${profile?.grade ?: ""}학년 ${profile?.classNum ?: ""}반 ${profile?.num ?: ""}번",
                        fontWeight = FontWeight.Normal,
                    )
                }

            }
            Spacer(Modifier.size(20.dp))
            Column {
                Body2(text = "아이디", color = gray900)
                Spacer(Modifier.size(8.dp))
                ProfileInfoText(text = profile?.accountId ?: "")
            }
            Spacer(Modifier.size(20.dp))
            Column {
                Body2(text = "생년월일", color = gray900)
                Spacer(Modifier.size(8.dp))
                ProfileInfoText(text = profile?.birthday?.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                    ?: "")
            }
        }
    }
}

@Composable
private fun ProfileInfoText(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, color = gray300, shape = RoundedCornerShape(8.dp))
            .background(color = gray50)
            .padding(start = 16.dp, bottom = 10.dp, top = 5.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Body1(text = text, color = gray800)
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfilePreview() {
    Profile(profile = ProfileEntity("qwer1234",
        "신희원",
        LocalDate.parse("2004-09-25"),
        3,
        2,
        9,
        null),
        onBackPress = {}
    ) {
    }
}