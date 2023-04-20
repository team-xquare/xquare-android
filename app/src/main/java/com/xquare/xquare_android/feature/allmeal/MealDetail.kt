import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.semicolon.design.Body1
import com.semicolon.design.Body2
import com.semicolon.design.color.primary.gray.gray50
import com.semicolon.design.color.primary.gray.gray700
import com.semicolon.design.color.primary.gray.gray800
import com.semicolon.design.color.primary.gray.gray900
import com.semicolon.design.color.primary.purple.purple200
import com.semicolon.design.color.primary.purple.purple400
import com.semicolon.design.color.system.green.green100
import com.xquare.domain.entity.meal.AllMealEntity
import com.xquare.xquare_android.util.toKorean

@Stable
private val MealDetailShape = RoundedCornerShape(16.dp)

@Composable
fun MealDetail(
    mealWithDateEntity: AllMealEntity.MealWithDateEntity,
    borderState: Boolean,
) {
    val borderColor = if (borderState) purple200 else gray50
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = gray50, shape = MealDetailShape)
            .border(width = 1.dp, color = borderColor, shape = MealDetailShape)
            .padding(16.dp)
    ) {
        val date = mealWithDateEntity.date
        val month = date.monthValue
        val day = date.dayOfMonth
        val dayOfTheWeek = date.dayOfWeek.toKorean()
        val breakfast = mealWithDateEntity.breakfast.joinToString(", ")
        val lunch = mealWithDateEntity.lunch.joinToString(", ")
        val dinner = mealWithDateEntity.dinner.joinToString(", ")
        Body1(
            text = "${month}월 ${day}일 (${dayOfTheWeek})",
            color = gray900,
            fontWeight = FontWeight.Medium
        )
        if (breakfast.isNotEmpty()) {
            Spacer(Modifier.size(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Body1(text = "아침", color = gray800)
                Spacer(Modifier.size(8.dp))
                Body2(text = mealWithDateEntity.caloriesOfBreakfast, color = gray700)
            }
            Spacer(Modifier.size(4.dp))
            Body2(text = breakfast, color = gray900)
        }
        if (lunch.isNotEmpty()) {
            Spacer(Modifier.size(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Body1(text = "점심", color = gray800)
                Spacer(Modifier.size(8.dp))
                Body2(text = mealWithDateEntity.caloriesOfLunch, color = gray700)
            }
            Spacer(Modifier.size(4.dp))
            Body2(text = lunch, color = gray900)
        }
        if (dinner.isNotEmpty()) {
            Spacer(Modifier.size(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Body1(text = "저녁", color = gray800)
                Spacer(Modifier.size(8.dp))
                Body2(text = mealWithDateEntity.caloriesOfDinner, color = gray700)
            }
            Spacer(Modifier.size(4.dp))
            Body2(text = dinner, color = gray900)
        }
    }
}

