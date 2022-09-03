package com.xquare.xquare_android.component

import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.semicolon.design.Typography
import com.semicolon.design.notoSansFamily

@Composable
fun AnnotatedBody2(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    lineHeight: Int = 21,
    letterSpacing: Int = 0,
    color: Color = Color.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {}
) {
    AnnotatedTypography(
        text = text,
        modifier = modifier,
        weight = fontWeight,
        size = 14,
        color = color,
        lineHeight = lineHeight,
        letterSpacing = letterSpacing,
        baselineToTop = 17.5f,
        baselineToBottom = 3.5f,
        textDecoration = textDecoration,
        textAlign = textAlign,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout
    )
}

@Composable
fun AnnotatedTypography(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    weight: FontWeight,
    size: Int,
    color: Color,
    lineHeight: Int,
    letterSpacing: Int,
    baselineToTop: Float,
    baselineToBottom: Float,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign?,
    overflow: TextOverflow,
    softWrap: Boolean,
    maxLines: Int,
    onTextLayout: (TextLayoutResult) -> Unit
) {

    val baselineModifier =
        modifier.paddingFromBaseline(top = baselineToTop.sp, bottom = baselineToBottom.sp)
    Text(
        style = TextStyle(
            color = color,
            lineHeight = lineHeight.sp,
            letterSpacing = letterSpacing.sp,
            fontSize = size.sp,
            fontFamily = notoSansFamily,
            fontWeight = weight,
            textDecoration = textDecoration,
            textAlign = textAlign,
        ),
        text = text,
        modifier = baselineModifier,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout
    )
}