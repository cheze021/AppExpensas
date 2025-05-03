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
            amount = 70.0,
            category = ExpenseCategory.GROCERIES,
            description = "Weekly buy",
            date = randomPastDate()
        ),
        Expense(
            id = currentId++,
            amount = 20.4,
            category = ExpenseCategory.SNACKS,
            description = "McDonalds",
            date = randomPastDate()
        ),
        Expense(
            id = currentId++,
            amount = 5000.0,
            category = ExpenseCategory.CAR,
            description = "Chevrolet Corsa",
            date = randomPastDate()
        ),
        Expense(
            id = currentId++,
            amount = 120.00,
            category = ExpenseCategory.PARTY,
            description = "Weekend party",
            date = randomPastDate()
        ),
        Expense(
            id = currentId++,
            amount = 25.0,
            category = ExpenseCategory.HOUSE,
            description = "Cleaning",
            date = randomPastDate()
        ),
        Expense(
            id = currentId++,
            amount = 15.0,
            category = ExpenseCategory.OTHER,
            description = "Services",
            date = randomPastDate()
        )
    )

    fun randomPastDate(daysBack: Int = 30): LocalDate {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val randomDaysAgo = Random.nextInt(0, daysBack)
        return today.minus(DatePeriod(days = randomDaysAgo))
    }
}