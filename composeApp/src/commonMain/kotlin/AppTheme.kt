import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.ExpenseCategory

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(primary = Color.Black),
        shapes = MaterialTheme.shapes.copy(
            small = AbsoluteCutCornerShape(0.dp),
            medium = AbsoluteCutCornerShape(0.dp),
            large = AbsoluteCutCornerShape(0.dp)
        )
    ) {
        content()
    }
}

@Composable
fun getColorsTheme() : DarkModeColors {
    val isDarkMode = false

    val Purple = Color(0xFF6A66FF)
    val ColorExpenseItem = if (isDarkMode) Color(0xFF090808) else Color(0xFFF1F1F1)
    val BackgroundColor = if (isDarkMode) Color(0XFF1E1C1C) else Color(252,250,246)
    val TextColor = if (isDarkMode) Color.White else Color.Black
    val AddIconColor = if (isDarkMode) Purple else Color.Black
    val ColorArrowRound = if (isDarkMode) Purple else Color.Gray.copy(alpha = .2f)
    val ColorViewAllButton = Color(0xFFDCDCDC)
    val TopAppBarColor = Color(242,238,230)

    return DarkModeColors(
        purple = Purple,
        colorExpenseItem = ColorExpenseItem,
        backgroundColor = BackgroundColor,
        textColor = TextColor,
        addIconColor = AddIconColor,
        colorArrowRound = ColorArrowRound,
        colorViewAllButton = ColorViewAllButton,
        topAppBarColor = TopAppBarColor
    )
}

data class DarkModeColors(
    val purple: Color,
    val colorExpenseItem: Color,
    val backgroundColor: Color,
    val textColor: Color,
    val addIconColor: Color,
    val colorArrowRound: Color,
    val colorViewAllButton: Color,
    val topAppBarColor: Color
)

val categoryColors = mapOf(
    ExpenseCategory.GROCERIES to Color(0xFFB5EAD7),
    ExpenseCategory.HOUSE to Color(0xFFF6C1FF),
    ExpenseCategory.PARTY to Color(0xFFAEC6CF),
    ExpenseCategory.SNACKS to Color(0xFFD5AAFF),
    ExpenseCategory.COFFEE to Color(0xFFFFF5BA),
    ExpenseCategory.CAR to Color(0xFFFFD8B1),
    ExpenseCategory.OTHER to Color(0xFFE2F0CB),
)