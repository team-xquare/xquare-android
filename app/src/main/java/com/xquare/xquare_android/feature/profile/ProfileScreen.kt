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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.semicolon.design.Body1
import com.semicolon.design.Body3
import com.semicolon.design.Subtitle4
import com.semicolon.design.color.primary.gray.*
import com.semicolon.design.color.primary.purple.purple200
import com.semicolon.design.color.primary.purple.purple300
import com.semicolon.design.color.primary.purple.purple50
import com.semicolon.design.color.primary.white.white
import com.xquare.domain.entity.profile.ProfileEntity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.CenterAppBar
import com.xquare.xquare_android.component.modal.ConfirmModal
import com.xquare.xquare_android.navigation.AppNavigationItem
import com.xquare.xquare_android.util.DevicePaddings
import com.xquare.xquare_android.util.makeToast
import com.xquare.xquare_android.util.toFile
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
                is ProfileViewModel.Event.LogoutSuccess -> {
                    navController.navigate(AppNavigationItem.Onboard.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
    Profile(
        profile = profile,
        onBackPress = { navController.popBackStack() },
        sendImage = {
            viewModel.uploadFile(it)
        },
        viewModel = viewModel
    )
}

@Composable
private fun Profile(
    profile: ProfileEntity?,
    onBackPress: () -> Unit,
    sendImage: (File) -> Unit,
    viewModel: ProfileViewModel,
) {
    val context = LocalContext.current
    var galleryState by remember { mutableStateOf(false) }
    var logoutDialogState by remember { mutableStateOf(false) }
//    var gitState by remember { mutableStateOf(false) }

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
        Column {
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding(), start = 12.dp, end = 12.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(gray50)
                    .border(width = 2.dp, color = gray200, shape = RoundedCornerShape(8.dp)),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(12.dp)
                ) {
                    val profileImageModifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                        ) {
                            galleryState = true
                        }

                    Box {
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
                Row(modifier = Modifier.padding(start = 12.dp)) {
                    Body3(text = "아이디 : ", color = gray900)
                    Body3(text = profile?.accountId ?: "")
                }
                Row(modifier = Modifier.padding(12.dp)) {
                    Body3(text = "생년월일 : ", color = gray900)
                    Body3(
                        text = profile?.birthday.toString()
                    )
                }
            }

            if (logoutDialogState) {
                ConfirmModal(
                    message = "정말 로그아웃 하시겠습니까?",
                    confirmText = "예",
                    cancelText = "아니요",
                    onConfirm = { viewModel.logout() }
                ) {
                    logoutDialogState = false
                }
            }

            Spacer(modifier = Modifier.size(12.dp))
            Column {
                Body1(
                    text = "계정 설정",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 4.dp)
                )
                ColumnMenu(text = "로그아웃") {
                    logoutDialogState = true
                }
            }
//            Spacer(modifier = Modifier.size(12.dp))
//            Column {
//                Body1(
//                    text = "계정 연동",
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier
//                        .padding(start = 4.dp)
//                )
//                ButtonColumnMenu(text = "Github") {
//
//                }
//            }
        }
    }
}

@Composable
private fun ColumnMenu(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(start = 12.dp, end = 16.dp, top = 12.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                enabled = true
            ) { onClick() }
            .fillMaxWidth()
            .height(58.dp)
            .background(gray50, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        Body1(
            text = text,
            color = gray900,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
private fun ButtonColumnMenu(
    text: String,
    onClick: () -> Unit,
) {
    //val textColor = if (gitState) white else purple200
    //val buttonColor = if (gitState) purple300 else purple50
    Box(
        modifier = Modifier
            .padding(start = 12.dp, end = 16.dp, top = 12.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                enabled = true
            ) { onClick() }
            .fillMaxWidth()
            .height(52.dp)
            .background(gray50, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        Body1(
            text = text,
            color = gray900,
            modifier = Modifier.padding(start = 4.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 12.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Row(
                modifier = Modifier
                    .size(80.dp, 38.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(purple300),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(text = "연동하기", color = white)
            }
        }
    }
}