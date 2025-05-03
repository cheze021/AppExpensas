import kotlinx.datetime.LocalDate
import model.Expense
import model.ExpenseCategory
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ExampleTest {

    @Test
    fun sum_must_succeed() {
        // Given
        val x = 5
        val y = 10

        // When
        val result = x + y

        // Then
        assertEquals(15, result)
    }

    @Test
    fun sum_must_fail() {
        // Given
        val x = 5
        val y = 10

        // When
        val result = x + y

        // Then
        assertNotEquals(10, result)
    }

    @Test
    fun expense_model_list(){
        // Given
        val expenseList = mutableListOf<Expense>()
        val expense = Expense(id = 1, amount = 4.50, category = ExpenseCategory.CAR, description = "Combustible", date = LocalDate(2025, 1, 10))

        // When
        expenseList.add(expense)

        // Then
        assertContains(expenseList, expense)
    }

    @Test
    fun expense_model_param_success() {
        // Given
        val expenseList = mutableListOf<Expense>()
        val expense = Expense(id = 1, amount = 4.50, category = ExpenseCategory.OTHER, description = "Combustible", date = LocalDate(2025, 1, 10))
        val expense2 = Expense(id = 2, amount = 8.340, category = ExpenseCategory.OTHER, description = "Limpieza", date = LocalDate(2025, 1, 10))

        // When
        expenseList.add(expense)
        expenseList.add(expense2)

        // Then
        assertEquals(expense.category, expense2.category)
        assertEquals(expenseList[0].category, expenseList[1].category)
    }

    @Test
    fun expense_model_param_fail() {
        // Given
        val expenseList = mutableListOf<Expense>()
        val expense = Expense(id = 1, amount = 4.50, category = ExpenseCategory.CAR, description = "Combustible", date = LocalDate(2025, 1, 10))
        val expense2 = Expense(id = 2, amount = 8.340, category = ExpenseCategory.OTHER, description = "Limpieza", date = LocalDate(2025, 1, 10))

        // When
        expenseList.add(expense)
        expenseList.add(expense2)

        // Then
        assertNotEquals(expense.category, expense2.category)
        assertNotEquals(expenseList[0].category, expenseList[1].category)
    }

}