package model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.ElectricCar
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Moped
import androidx.compose.material.icons.filled.PartyMode
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.ViewCozy
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

data class Expense(
    val id:Long = -1,
    val amount: Double,
    val category: ExpenseCategory,
    val description: String,
    val date: LocalDate
) {
    val icon = category.icon

}

@Serializable
data class NetworkExpense(
    val id:Long = -1,
    val amount: Double,
    val categoryName: String,
    val description: String,
    val date: String
)

enum class ExpenseCategory(val icon: ImageVector, val displayName: String) {
    LUZ(Icons.Default.Lightbulb, "Luz"),
    INTERNET(Icons.Default.Wifi, "Internet"),
    SUPER(Icons.Default.ShoppingBasket, "Super"),
    ALQUILER(Icons.Default.Apartment, "Alquiler"),
    MERIENDAS(Icons.Default.Cookie, "Meriendas"),
    PEDIDOS_YA(Icons.Default.Moped, "Pedidos Ya"),
    OTROS(Icons.Default.ViewCozy, "Otros")
}

data class CategoryStats(
    val category: String,
    val amount: Double,
    val color: Color
)
