import data.ExpenseManager
import data.ExpenseRepoImpl
import model.Expense
import model.ExpenseCategory
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ExpenseRepoTest {

    private val expenseManager = ExpenseManager
    private val repo = ExpenseRepoImpl(expenseManager)

    @Test
    fun expense_list_is_not_empty() {
        // Given
        val expenseList = mutableListOf<Expense>()

        // When
        expenseList.addAll(repo.getAllExpenses())

        // Then
        assertTrue(expenseList.isNotEmpty())
    }

    @Test
    fun add_new_expense() {
        // Given
        val expenseList = repo.getAllExpenses()

        // When
        repo.addNewExpense(
            Expense(
                amount = 4.50,
                category = ExpenseCategory.CAR,
                description = "Combustible"
            )
        )

        // Then
        assertContains(expenseList, expenseList.find { it.id.toInt() == 7 })
    }

    @Test
    fun edit_expense() {
        // Given
        val expenseListBefore = repo.getAllExpenses()

        // When
        val newExpenseId = 7L
        repo.addNewExpense(
            Expense(
                amount = 4.50,
                category = ExpenseCategory.CAR,
                description = "Combustible"
            )
        )

        assertNotNull(expenseListBefore.find { it.id == 7L })

        val updatedExpense = Expense(
            id = newExpenseId,
            amount = 8.0,
            category = ExpenseCategory.OTHER,
            description = "Ropa"
        )
        repo.editExpense(updatedExpense)

        // Then
        val expenseListAfter = repo.getAllExpenses()
        assertEquals(updatedExpense, expenseListAfter.find { it.id == newExpenseId })
    }

    @Test
    fun get_all_categories() {
        // Given
        val categoryList = mutableListOf<ExpenseCategory>()

        // When
        categoryList.addAll(repo.getCategories())

        // Then
        assertTrue(categoryList.isNotEmpty())
    }
}