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
fun getColorsTheme() : Colors {


    val StoneBeige = Color(190, 185, 175)
    val ColorExpenseItem = Color(0xFFF1F1F1)
    val BackgroundColor = Color(242, 240, 236)
    val TextColor = Color(28, 28, 30)
    val AddIconColor = Color(28, 28, 30)
    val ColorArrowRound = Color.Gray.copy(alpha = .2f)
    val ColorViewAllButton = Color(0xFFF5F5F5)
    val TopAppBarColor = Color(220, 215, 205)
    val ButtonEditAddColor = Color(136, 94, 74)
    val ButtonDeleteColor = Color(253, 41, 47)
    val CardColor = Color(0xFFFAFAFA)

    return Colors(
        stoneBeige = StoneBeige,
        colorExpenseItem = ColorExpenseItem,
        backgroundColor = BackgroundColor,
        textColor = TextColor,
        addIconColor = AddIconColor,
        colorArrowRound = ColorArrowRound,
        colorViewAllButton = ColorViewAllButton,
        topAppBarColor = TopAppBarColor,
        buttonEditAddColor = ButtonEditAddColor,
        buttonDeleteColor = ButtonDeleteColor,
        cardColor = CardColor
    )
}

data class Colors(
    val stoneBeige: Color,
    val colorExpenseItem: Color,
    val backgroundColor: Color,
    val textColor: Color,
    val addIconColor: Color,
    val colorArrowRound: Color,
    val colorViewAllButton: Color,
    val topAppBarColor: Color,
    val buttonEditAddColor: Color,
    val buttonDeleteColor: Color,
    val cardColor: Color
)

val categoryColors = mapOf(
    ExpenseCategory.LUZ to Color(0xFFB5EAD7),
    ExpenseCategory.INTERNET to Color(0xFFF6C1FF),
    ExpenseCategory.SUPER to Color(0xFFAEC6CF),
    ExpenseCategory.PEDIDOS_YA to Color(0xFFD5AAFF),
    ExpenseCategory.ALQUILER to Color(0xFFFFF5BA),
    ExpenseCategory.MERIENDAS to Color(0xFFFFD8B1),
    ExpenseCategory.OTROS to Color(0xFFE2F0CB),
)