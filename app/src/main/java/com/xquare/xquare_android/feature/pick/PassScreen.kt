package com.xquare.xquare_android.feature.pick

import android.os.Build
import android.text.TextUtils
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.semicolon.design.Body1
import com.semicolon.design.Subtitle4
import com.semicolon.design.color.primary.gray.gray200
import com.semicolon.design.color.primary.gray.gray300
import com.semicolon.design.color.primary.gray.gray50
import com.semicolon.design.color.primary.gray.gray700
import com.semicolon.design.color.primary.gray.gray800
import com.semicolon.design.color.primary.white.white
import com.xquare.xquare_android.MainActivity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.Header
import com.xquare.xquare_android.util.DevicePaddings

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PassScreen(
    navController: NavController,
) {
    val context = LocalContext.current
    val mainActivity: MainActivity = context as MainActivity
    val vm: PassViewModel = hiltViewModel()
    val passData = vm.passData.collectAsState().value

    LaunchedEffect(Unit) {
        mainActivity.window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        vm.fetchPassData()
    }
    Scaffold(
        modifier = Modifier
            .background(white)
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp,
                bottom = DevicePaddings.navigationBarHeightDp.dp,
            ),
        topBar = {
            Header(
                painter = painterResource(id = R.drawable.ic_back),
                title = "외출증",
                onIconClick = { navController.popBackStack() }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.size(12.dp))
                PreventCaptureText()
                Spacer(modifier = Modifier.size(44.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        painter = rememberAsyncImagePainter(
                            model = passData.profile_file_name,
                            placeholder = ColorPainter(gray200),
                            error = painterResource(id = R.drawable.ic_profile_default)
                        ),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Subtitle4(text = passData.student_number, Modifier.size(30.dp))
                    Subtitle4(text = passData.student_name, Modifier.size(30.dp))
                }
                Spacer(modifier = Modifier.size(20.dp))
                PassInfoText(title = "외출 날짜", content = passData.picnic_date.toString())
                Spacer(modifier = Modifier.size(18.dp))
                PassInfoText(
                    title = "외출시간",
                    content = buildAnnotatedString {
                        append(passData.start_time)
                        append(" ~ ")
                        append(passData.end_time)
                    }.toString()
                )
                Spacer(modifier = Modifier.size(17.dp))
                PassInfoText(title = "사유", content = passData.reason,)
                Spacer(modifier = Modifier.size(20.dp))
                PassInfoText(title = "확인교사", content = passData.teacher_name)
                Spacer(modifier = Modifier.size(75.dp))
                PreventCaptureText()
            }
        }
    }
}

@Composable
private fun PassInfoText(
    title: String,
    content: String,
) {
    Column {
        Subtitle4(text = title, color = gray700, fontWeight = FontWeight.Medium)
        Spacer(Modifier.size(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(color = gray50)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            contentAlignment = Alignment.CenterStart

        ) {
            Body1(
                text = content,
                color = gray800,
                modifier = Modifier
                    .scale(1.1f)
                    .padding(start = 2.dp, bottom = 2.dp),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun PreventCaptureText() {
    val content = "캡쳐 방지용 애니메이션입니다. 캡쳐 방지용 애니메이션입니다. 캡쳐 방지용 애니메이션입니다. 캡쳐 방지용 애니메이션입니다."
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                text = content
                textSize = 14F
                isSingleLine = true
                ellipsize = TextUtils.TruncateAt.MARQUEE
                isSelected = true
            }
        }
    )
}
