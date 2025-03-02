package previews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import model.Expense
import model.ExpenseCategory
import ui.AllExpensesHeader
import ui.ExpensesItem
import ui.ExpensesTotalHeader

@Preview(showBackground = true)
@Composable
private fun ExpensesTotalHeaderPreview() {
    Box(modifier = Modifier.padding(16.dp)) {
        ExpensesTotalHeader(total = 108.29)
    }

}

@Preview(showBackground = true)
@Composable
private fun AllExpensesHeaderPreview() {
    Box(modifier = Modifier.padding(16.dp)) {
        AllExpensesHeader()
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpenseItemPreview() {
    Box(modifier = Modifier.padding(8.dp)) {
        ExpensesItem(expense = Expense(
            id = 1L,
            amount = 70.50,
            category = ExpenseCategory.CAR,
            description = "Cambio de aceite"
        ), onExpenseClick = {})
    }
}