package presentacion

import domain.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.Expense
import model.ExpenseCategory
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

sealed class ExpensesUiState {
    object Loading : ExpensesUiState()
    data class Success(val expenses: List<Expense>, val total: Double) : ExpensesUiState()
    data class Error(val message: String) : ExpensesUiState()

}

class ExpensesViewModel(private val repo: ExpenseRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ExpensesUiState>(ExpensesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getExpenseList()
    }

    private fun getExpenseList() {
        viewModelScope.launch {
            try {
                val expenses = repo.getAllExpenses()
                _uiState.value = ExpensesUiState.Success(expenses, expenses.sumOf { it.amount })
            } catch (ex: Exception) {
                _uiState.value = ExpensesUiState.Error(ex.message ?: "Ocurrio un error")
            }
        }
    }

    private suspend fun updateExpenseList() {
        try {
            val expenses = repo.getAllExpenses()
            _uiState.value = ExpensesUiState.Success(expenses, expenses.sumOf { it.amount })
        } catch (ex: Exception) {
            _uiState.value = ExpensesUiState.Error(ex.message ?: "Ocurrio un error")
        }
    }

    fun addNewExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                repo.addNewExpense(expense)
                updateExpenseList()
            } catch (ex: Exception) {
                _uiState.value = ExpensesUiState.Error(ex.message ?: "Ocurrio un error")
            }
        }
    }


    fun editExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                repo.editExpense(expense)
                updateExpenseList()
            } catch (ex: Exception) {
                _uiState.value = ExpensesUiState.Error(ex.message ?: "Ocurrio un error")
            }
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                repo.deleteExpense(expense)
                updateExpenseList()
            } catch (ex: Exception) {
                _uiState.value = ExpensesUiState.Error(ex.message ?: "Ocurrio un error")
            }
        }
    }

    fun getExpenseWithID(id: Long): Expense? {
        return (_uiState.value as? ExpensesUiState.Success)?.expenses?.firstOrNull { it.id == id }
    }

    fun getCategories(): List<ExpenseCategory> {
        return repo.getCategories()
    }
}