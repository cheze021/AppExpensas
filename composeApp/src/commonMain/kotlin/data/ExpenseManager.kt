package data

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import model.Expense
import model.ExpenseCategory
import kotlin.random.Random

object ExpenseManager {

    private var currentId = 1L

    val fakeExpenseList = mutableListOf(
        Expense(
            id = currentId++,
            amount = 1000.00,
            category = ExpenseCategory.LUZ,
            description = "UTE",
            date = randomPastDate()
        ),
        Expense(
            id = currentId++,
            amount = 750.00,
            category = ExpenseCategory.INTERNET,
            description = "Antel",
            date = randomPastDate()
        ),
        Expense(
            id = currentId++,
            amount = 500.00,
            category = ExpenseCategory.SUPER,
            description = "Surtido",
            date = randomPastDate()
        ),
        Expense(
            id = currentId++,
            amount = 13650.00,
            category = ExpenseCategory.ALQUILER,
            description = "Alquiler apto",
            date = randomPastDate()
        ),
        Expense(
            id = currentId++,
            amount = 350.00,
            category = ExpenseCategory.MERIENDAS,
            description = "Merienda en Robusta",
            date = randomPastDate()
        ),
        Expense(
            id = currentId++,
            amount = 750.00,
            category = ExpenseCategory.PEDIDOS_YA,
            description = "Comida pedida",
            date = randomPastDate()
        ),
        Expense(
            id = currentId++,
            amount = 350.00,
            category = ExpenseCategory.OTROS,
            description = "Servicios varios",
            date = randomPastDate()
        )
    )

    fun randomPastDate(daysBack: Int = 30): LocalDate {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val randomDaysAgo = Random.nextInt(0, daysBack)
        return today.minus(DatePeriod(days = randomDaysAgo))
    }
}