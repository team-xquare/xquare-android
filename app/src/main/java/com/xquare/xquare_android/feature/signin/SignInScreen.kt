package com.xquare.xquare_android.feature.signin

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.semicolon.design.Body2
import com.semicolon.design.button.ColoredLargeButton
import com.semicolon.design.color.primary.gray.gray700
import com.semicolon.design.color.primary.white.white
import com.xquare.domain.entity.auth.SignInEntity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.AppBar
import com.xquare.xquare_android.component.TextField
import com.xquare.xquare_android.navigation.AppNavigationItem
import com.xquare.xquare_android.util.DevicePaddings
import com.xquare.xquare_android.util.makeToast

@Composable
fun SignInScreen(navController: NavController) {
    val context = LocalContext.current
    val signInViewModel: SignInViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        signInViewModel.eventFlow.collect {
            when (it) {
                is SignInViewModel.Event.Success -> {
                    navController.navigate(AppNavigationItem.Main.route) { popUpTo(0) }
                }
                is SignInViewModel.Event.BadRequest -> {
                    makeToast(context, "잘못된 요청입니다")
                }
                is SignInViewModel.Event.NotFound -> {
                    makeToast(context, "아이디와 비밀번호를 확인해주세요")
                }
                is SignInViewModel.Event.Timeout -> {
                    makeToast(context, "요청 시간이 초과되었습니다")
                }
                is SignInViewModel.Event.TooManyRequest -> {
                    makeToast(context, "현재 요청이 너무 많습니다")
                }
            }
        }
    }
    SignIn(
        onBackClick = { navController.popBackStack() },
        onSignInClick = { signInViewModel.signIn(it) },
        onFindAccountClick = {
            //makeToast(context, "Xquare 페이스북 페이지로 문의 해주세요.")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100091948951498"))
            context.startActivity(intent)
            //TODO("아이디/비밀번호 찾기로 이동")
        }
    )
}

@Composable
private fun SignIn(
    onBackClick: () -> Unit,
    onSignInClick: (SignInEntity) -> Unit,
    onFindAccountClick: () -> Unit,
) {
    var accountId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isSignInEnabled = accountId.isNotEmpty() && password.isNotEmpty()
    Scaffold(
        modifier = Modifier
            .background(white)
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp,
                bottom = DevicePaddings.navigationBarHeightDp.dp
            ),
        topBar = {
            AppBar(
                painter = painterResource(R.drawable.ic_back),
                text = "로그인",
                onIconClick = onBackClick
            )
        }
    ) {
        Column(
            Modifier.padding(top = 16.dp + it.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(Modifier.padding(horizontal = 16.dp)) {
                TextField(
                    text = accountId,
                    placeholder = "아이디",
                    onTextChange = { text ->
                        accountId = text
                    }
                )
                Spacer(Modifier.size(8.dp))
                TextField(
                    text = password,
                    placeholder = "비밀번호",
                    isSecret = true,
                    onTextChange = { text ->
                        password = text
                    }
                )
            }
            Spacer(Modifier.size(16.dp))
            ColoredLargeButton(text = "로그인", isEnabled = isSignInEnabled) {
                onSignInClick(SignInEntity(accountId, password, ""))
            }
            Spacer(Modifier.size(16.dp))
            Body2(
                modifier = Modifier.clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    enabled = true
                ) { onFindAccountClick() },
                text = "아이디 찾기 / 비밀번호 찾기",
                color = gray700
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    SignIn(
        onBackClick = {},
        onSignInClick = {},
        onFindAccountClick = {}
    )
}