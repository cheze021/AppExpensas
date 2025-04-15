package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import getColorsTheme
import model.Expense
import presentacion.ExpensesUiState
import utils.SwipeToDeleteContainer

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllExpensesScreen(
    uiState: ExpensesUiState
) {
    val colors = getColorsTheme()

    when(uiState) {
        is ExpensesUiState.Success -> {
            val groupedExpenses = uiState.expenses.groupBy { it.category }

            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                groupedExpenses.forEach { (category, expensesInCategory) ->
                    item {
                        CategoryExpensesCard(category.name, expensesInCategory)
                    }
                }
            }
        }

        is ExpensesUiState.Loading -> TODO()

        is ExpensesUiState.Error -> TODO()

    }
}

@Composable
fun CategoryExpensesCard(categoryName: String, expenses: List<Expense>) {
    val colors = getColorsTheme()
    val icon = expenses.firstOrNull()?.icon

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        backgroundColor = colors.colorExpenseItem,
        shape = RoundedCornerShape(10)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(colors.purple, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = "Ícono categoría",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Text(
                    text = categoryName,
                    style = MaterialTheme.typography.subtitle1,
                    color = colors.textColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            expenses.forEach { expense ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = expense.description,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "$${expense.amount}",
                        style = MaterialTheme.typography.body2,
                        color = colors.textColor,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}