package com.xquare.xquare_android.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.semicolon.design.Body1
import com.semicolon.design.color.primary.gray.gray50
import kotlinx.coroutines.launch

@Stable
private val BottomSheetShape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 16.dp,
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActionSheet(
    state: ModalBottomSheetState,
    list: List<String>,
    onClick: (Int) -> Unit,
    fontWeight: FontWeight = FontWeight.Normal,
    content: @Composable () -> Unit,
) {
    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = state,
        sheetShape = BottomSheetShape,
        sheetContent = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = gray50)
                    .padding(top = 10.dp, bottom = 30.dp, end = 280.dp)
            ){
                itemsIndexed(list) { index, item ->
                    BottomSheetItem(
                        text = item
                    ) { onClick(index) }
                }
            }
        },
        sheetBackgroundColor = Color.Transparent,
    ) {
        content()
    }

}

@Composable
private fun BottomSheetItem(
    text: String,
    onClick: () -> Unit
) {
    DefaultModalButton(
        modifier = Modifier.fillMaxWidth(),
        text = text
    ) {
        onClick()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview(showBackground = true)
fun PreviewActionSheet() {
    val actionSheetScope = rememberCoroutineScope()
    val actionSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ActionSheet(
        state = actionSheetState,
        list = listOf("1","2"),
        onClick = {
            actionSheetScope.launch {
                actionSheetState.hide()
            }
        }
    ) {
        Body1(
            text = "open",
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    actionSheetScope.launch {
                        actionSheetState.show()
                    }
                }
        )
    }
}
