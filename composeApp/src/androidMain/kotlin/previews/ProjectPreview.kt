package previews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import data.ExpenseManager
import presentacion.ExpensesUiState
import ui.AllExpensesHeader
import ui.ExpensesGraphicsScreen
import ui.ViewAllExpensesScreen
import ui.ExpensesItem
import ui.ExpensesScreen
import ui.ExpensesTotalHeader
import ui.TotalExpensesValue

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
        AllExpensesHeader(
            onViewAllClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpenseItemPreview() {
    Box(modifier = Modifier.padding(8.dp)) {
        ExpensesItem(expense = ExpenseManager.fakeExpenseList[0], onExpenseClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpenseScreenPreview() {
    ExpensesScreen(
        uiState = ExpensesUiState.Success(
            expenses = ExpenseManager.fakeExpenseList,
            total = 1052.00
        ),
        onExpenseClick = {},
        onDeleteExpense = {},
        onViewAllClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun AllExpensesScreenPreview() {
    ViewAllExpensesScreen(
        uiState = ExpensesUiState.Success(
            expenses = ExpenseManager.fakeExpenseList,
            total = 0.0
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun TotalExpensesValuePreview() {
    TotalExpensesValue(
        expenses = ExpenseManager.fakeExpenseList
    )
}

@Preview(showBackground = true)
@Composable
private fun ExpensesGraphicScreenPreview() {
    ExpensesGraphicsScreen(
        uiState = ExpensesUiState.Success(
            expenses = ExpenseManager.fakeExpenseList,
            total = 0.0
        )
    )
}