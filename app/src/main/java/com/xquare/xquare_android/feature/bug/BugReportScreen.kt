package com.xquare.xquare_android.feature.bug

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.semicolon.design.Body2
import com.semicolon.design.Body3
import com.semicolon.design.color.primary.black.black
import com.semicolon.design.color.primary.gray.gray300
import com.semicolon.design.color.primary.gray.gray50
import com.semicolon.design.color.primary.purple.purple400
import com.semicolon.design.color.system.red.red400
import com.semicolon.design.notoSansFamily
import com.xquare.domain.entity.reports.BugEntity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.Header
import com.xquare.xquare_android.util.DevicePaddings
import com.xquare.xquare_android.util.toFile
import java.io.File

internal object MenuItem {
    const val HOME = "홈"
    const val SCHEDULE = "일정"
    const val FEED = "피드"
    const val APPLY = "신청"
    const val ALL = "전체"
}

private fun String.toEntityWhere() =
    when (this) {
        MenuItem.HOME -> "HOME"
        MenuItem.SCHEDULE -> "SCHEDULE"
        MenuItem.FEED -> "FEED"
        MenuItem.APPLY -> "APPLICATION"
        MenuItem.ALL -> "ALL"
        else -> "HOME"
    }

@Composable
fun BugReportScreen(
    navController: NavController,
) {
    val bugViewModel: BugViewModel = hiltViewModel()
    val context = LocalContext.current
    var photos by remember { mutableStateOf(emptyList<String>()) }

    LaunchedEffect(Unit) {
        bugViewModel.eventFlow.collect {
            when (it) {
                is BugViewModel.Event.Success -> {
                    Toast.makeText(context, "버그 제보 성공", Toast.LENGTH_SHORT).show()
                    navController.navigateUp()
                }

                is BugViewModel.Event.Failure -> Toast.makeText(
                    context,
                    "버그 제보 실패",
                    Toast.LENGTH_SHORT
                ).show()

                is BugViewModel.Event.UploadFileSuccess -> {
                    photos += it.data[0]
                    Toast.makeText(context, "사진 추가 성공", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    BugreportContent(
        onIconClick = { navController.popBackStack() },
        onBtnClick = {
            bugViewModel.uploadBug(it)
        },
        sendImage = {
            bugViewModel.uploadFile(it)
        },
        photos = photos,
    )


}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "ResourceType")
@Composable
private fun BugreportContent(
    onIconClick: () -> Unit,
    onBtnClick: (BugEntity) -> Unit,
    sendImage: (File) -> Unit,
    photos: List<String>,
) {
    val context = LocalContext.current
    var where by remember { mutableStateOf("홈") }
    var explanationText by remember { mutableStateOf("") }
    val headerBtnEnabled = explanationText.isNotEmpty()
    var galleryState by remember { mutableStateOf(false) }
    var images by remember { mutableStateOf(emptyList<Uri?>()) }
    var selectedImageUri: Uri? = null
    val openWebViewGallery =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data!!.clipData?.run {
                    for (i in 0 until itemCount) {
                        sendImage(toFile(context, getItemAt(i).uri))
                        selectedImageUri = getItemAt(i).uri
                        images += selectedImageUri
                    }
                }
            }
            galleryState = false
        }

    val openGalleryLauncher =
        Intent(Intent.ACTION_PICK).apply {
            this.type = "image/*"
            this.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
    if (galleryState) {
        openWebViewGallery.launch(openGalleryLauncher)
    }
    Scaffold(
        modifier = Modifier.padding(
            top = DevicePaddings.statusBarHeightDp.dp,
            bottom = DevicePaddings.navigationBarHeightDp.dp
        ),
        topBar = {
            Header(
                painter = painterResource(id = R.drawable.ic_back),
                title = "버그 제보",
                btnText = "제출",
                btnEnabled = headerBtnEnabled,
                onIconClick = onIconClick,
                onBtnClick = {
                    onBtnClick(
                        BugEntity(
                            explanationText,
                            where.toEntityWhere(),
                            image_urls = photos
                        )
                    )
                    explanationText = ""
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            BugreportGuideText(text = "어디서 버그가 발생했나요?")
            Spacer(modifier = Modifier.size(8.dp))
            BugreportWhereMenu(text = where, checkWhere = { where = it })
            Spacer(modifier = Modifier.size(30.dp))
            Text(text = "사진을 첨부해 주세요")
            Spacer(modifier = Modifier.size(10.dp))
            if (images.isEmpty()) {
                Column(
                    modifier = Modifier
                        .clickable { galleryState = true }
                        .size(150.dp)
                        .background(gray50),
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        painter = rememberAsyncImagePainter(model = R.drawable.ic_add_photo),
                        contentDescription = "insertBugPhoto",
                    )
                }
            } else {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 12.dp),
                ) {
                    items(images) {
                        Column(
                            modifier = Modifier
                                .size(150.dp)
                                .clickable { galleryState = true }
                                .padding(end = 4.dp),
                        ) {
                            AsyncImage(
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize(),
                                model = it,
                                contentDescription = "bugPhoto",
                                error = rememberAsyncImagePainter(model = selectedImageUri),
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            BugreportGuideText(text = "버그에 대해 요약해서 설명해주세요.")
            Spacer(modifier = Modifier.size(8.dp))
            BugreportExplanationText(
                text = explanationText,
                onTextChange = { explanationText = it },
                placeholder = "설명을 입력해주세요",
            )
        }
    }
}

@Composable
private fun BugreportGuideText(
    text: String
) {
    Row {
        Body2(text = text)
        Body2(text = "*", color = red400)
    }
}

@Composable
private fun BugreportWhereMenu(
    text: String,
    checkWhere: (String) -> Unit
) {
    val menu = listOf("홈 ", "일정", "피드", "신청", "전체")
    var isDrop by remember { mutableStateOf(false) }
    var targetValue by remember { mutableStateOf(0F) }
    val rotateValue: Float by animateFloatAsState(
        targetValue = targetValue,
        tween(300)
    )

    Column(
        modifier = Modifier
            .width(60.dp)
            .background(
                color = gray50,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                ) {
                    isDrop = true
                    targetValue = 180F
                }
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Body3(text = text)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_drop),
                contentDescription = "menu btn",
                tint = Color(0xFF757575),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.End)
                    .size(12.dp)
                    .rotate(rotateValue)
            )
        }
        DropdownMenu(
            expanded = isDrop,
            onDismissRequest = {
                isDrop = false
                targetValue = 0F
            },
            modifier = Modifier
                .width(60.dp)
                .background(
                    color = gray50,
                )
        ) {
            menu.forEach {
                if (it != text) {
                    Spacer(modifier = Modifier.height(3.dp))
                    Body3(
                        text = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                            ) {
                                checkWhere(it)
                                isDrop = false
                                targetValue = 0F
                            }
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BugreportExplanationText(
    text: String,
    isSecret: Boolean = false,
    onTextChange: (String) -> Unit,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        textStyle = TextStyle(
            fontFamily = notoSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = black,
            lineHeight = 24.sp,
        ),
        keyboardOptions = keyboardOptions,
        cursorBrush = SolidColor(purple400),
        visualTransformation = if (isSecret) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(gray50)
            .border(width = 1.dp, color = gray300, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        TextFieldDefaults.TextFieldDecorationBox(
            value = text,
            visualTransformation = VisualTransformation.None,
            innerTextField = it,
            singleLine = false,
            enabled = false,
            placeholder = { Text(text = placeholder) },
            interactionSource = interactionSource,
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                start = 2.dp, top = 1.dp
            )
        )
    }
}