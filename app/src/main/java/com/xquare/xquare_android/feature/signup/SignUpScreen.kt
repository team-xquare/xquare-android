package com.xquare.xquare_android.feature.signup

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.semicolon.design.button.ColoredLargeButton
import com.semicolon.design.color.primary.gray.gray400
import com.xquare.domain.entity.auth.SignUpEntity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.AnnotatedBody2
import com.xquare.xquare_android.component.AppBar
import com.xquare.xquare_android.component.HighlightedText
import com.xquare.xquare_android.component.TextField
import com.xquare.xquare_android.navigation.AppNavigationItem
import com.xquare.xquare_android.util.makeToast

@Composable
fun SignUpScreen(navController: NavController) {
    val context = LocalContext.current
    val signUpViewModel: SignUpViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        signUpViewModel.eventFlow.collect {
            when (it) {
                is SignUpViewModel.Event.Success -> {
                    navController.popBackStack()
                }
                is SignUpViewModel.Event.BadRequest -> {
                    makeToast(context, "인증코드, 아이디, 비밀번호를 확인해주세요")
                }
                is SignUpViewModel.Event.Timeout -> {
                    makeToast(context, "요청 시간이 초과되었습니다")
                }
                is SignUpViewModel.Event.ConflictAccountId -> {
                    makeToast(context, "중복되는 아이디 입니다")
                }
                is SignUpViewModel.Event.TooManyRequest -> {
                    makeToast(context, "현재 요청이 너무 많습니다")
                }
            }
        }
    }
    SignUp(
        onBackClick = {
            navController.popBackStack()
        },
        onPrivacyPolicyClick = {
            navController.navigate(AppNavigationItem.PrivacyPolicy.route)
        },
        onTermsClick = {
            navController.navigate(AppNavigationItem.TermsOfService.route)
        },
        onSignUpClick = {
            signUpViewModel.signUp(it)
        }
    )
}

@Composable
private fun SignUp(
    onBackClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onTermsClick: () -> Unit,
    onSignUpClick: (SignUpEntity) -> Unit
) {
    var verificationCode by remember { mutableStateOf("") }
    var accountId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var reEnteredPassword by remember { mutableStateOf("") }
    val isSignUpEnabled =
        if (verificationCode.length != 4) false
        else if (accountId.length < 6 || accountId.length > 20) false
        else if (password.length < 6) false
        else if (!password.contains("[A-Za-z0-9!@#.,]".toRegex())) false
        else password == reEnteredPassword
    val annotatedPrivacyPolicyAndTerms = buildAnnotatedString {
        append("가입시 ")
        pushStringAnnotation("policy", "")
        withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
            append("개인정보처리방침")
        }
        pop()
        append(" 및 ")
        pushStringAnnotation("terms", "")
        withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
            append("이용약관")
        }
        pop()
        append("에 동의하는 것으로 간주합니다.")
    }
    Scaffold(
        topBar = {
            AppBar(
                painter = painterResource(R.drawable.ic_placeholder),
                text = "회원가입",
                onIconClick = onBackClick
            )
        },
        bottomBar = {
            Column(Modifier.padding(bottom = 16.dp)) {
                ColoredLargeButton(text = "회원가입", isEnabled = isSignUpEnabled) {
                    onSignUpClick(SignUpEntity(verificationCode, accountId, password, null))
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp + it.calculateBottomPadding(),
                    top = 16.dp + it.calculateTopPadding()
                ),
        ) {
            HighlightedText("인증코드")
            Spacer(Modifier.size(8.dp))
            TextField(
                text = verificationCode,
                placeholder = "6자리를 입력해주세요",
                onTextChange = { text ->
                    verificationCode = text
                }
            )
            Spacer(Modifier.size(20.dp))
            HighlightedText("아이디")
            Spacer(Modifier.size(8.dp))
            TextField(
                text = accountId,
                placeholder = "영문, 숫지 6~20자",
                onTextChange = { text ->
                    accountId = text
                }
            )
            Spacer(Modifier.size(20.dp))
            HighlightedText("비밀번호")
            Spacer(Modifier.size(8.dp))
            TextField(
                text = password,
                placeholder = "숫자, 영문, 특수문자 조합 최소 6자",
                isSecret = true,
                onTextChange = { text ->
                    password = text
                }
            )
            Spacer(Modifier.size(20.dp))
            HighlightedText("비밀번호 재입력")
            Spacer(Modifier.size(8.dp))
            TextField(
                text = reEnteredPassword,
                placeholder = "재입력",
                isSecret = true,
                onTextChange = { text ->
                    reEnteredPassword = text
                }
            )
            Spacer(Modifier.size(20.dp))
            AnnotatedBody2(
                onClick = { offset ->
                    annotatedPrivacyPolicyAndTerms.getStringAnnotations("policy", offset, offset)
                        .firstOrNull()?.let {
                            onPrivacyPolicyClick()
                        }
                    annotatedPrivacyPolicyAndTerms.getStringAnnotations("terms", offset, offset)
                        .firstOrNull()?.let {
                            onTermsClick()
                        }
                },
                text = annotatedPrivacyPolicyAndTerms,
                color = gray400,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    SignUp(
        onBackClick = {},
        onPrivacyPolicyClick = {},
        onTermsClick = {},
        onSignUpClick = {}
    )
}