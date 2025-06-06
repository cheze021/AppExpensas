package data

import com.expenseApp.db.AppDatabase
import consts.Consts
import domain.ExpenseRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.datetime.LocalDate
import model.Expense
import model.ExpenseCategory
import model.NetworkExpense

class ExpenseRepoImpl(
    private val appDatabase: AppDatabase,
    private val httpClient: HttpClient
) : ExpenseRepository {

    private val queries = appDatabase.expensesDbQueries

    override suspend fun getAllExpenses(): List<Expense> {
        return if(queries.selectAll().executeAsList().isEmpty()) {
            val networkResponse = httpClient.get(Consts.URL.BASE_URL + "/expenses").body<List<NetworkExpense>>()
            if(networkResponse.isEmpty()) return emptyList()
            val expenses = networkResponse.map {
                Expense(
                    id = it.id,
                    amount = it.amount,
                    category = ExpenseCategory.valueOf(it.categoryName),
                    description = it.description,
                    date = LocalDate.parse(it.date)
                )
            }
            expenses.forEach {
                queries.insert(it.amount, it.category.name, it.description, it.date.toString())
            }

            expenses
        } else {
            queries.selectAll().executeAsList().map {
                Expense(
                    id = it.id,
                    amount = it.amount,
                    category = ExpenseCategory.valueOf(it.categoryName),
                    description = it.description,
                    date = LocalDate.parse(it.date)
                )
            }
        }
    }

    override suspend fun addNewExpense(expense: Expense) {
        queries.transaction {
            queries.insert(
                amount = expense.amount,
                categoryName = expense.category.name,
                description = expense.description,
                date = expense.date.toString()
            )
        }

        httpClient.post(Consts.URL.BASE_URL + "/expenses") {
            contentType(ContentType.Application.Json)
            setBody(
                NetworkExpense(
                    amount = expense.amount,
                    categoryName = expense.category.name,
                    description = expense.description,
                    date = expense.date.toString()
                )
            )
        }
    }

    override suspend fun editExpense(expense: Expense) {
        queries.transaction {
            queries.update(
                id = expense.id,
                amount = expense.amount,
                categoryName = expense.category.name,
                description = expense.description,
                date = expense.date.toString()
            )
        }

        httpClient.put(Consts.URL.BASE_URL + "/expenses/${expense.id}") {
            contentType(ContentType.Application.Json)
            setBody(
                NetworkExpense(
                    id = expense.id,
                    amount = expense.amount,
                    categoryName = expense.category.name,
                    description = expense.description,
                    date = expense.date.toString()
                )
            )
        }
    }

    override suspend fun deleteExpense(expense: Expense) {
        httpClient.delete(Consts.URL.BASE_URL + "/expenses/${expense.id}")

        queries.transaction {
            queries.delete(id = expense.id,)
        }
    }

    override fun getCategories(): List<ExpenseCategory> {
        return queries.categories().executeAsList().map {
            ExpenseCategory.valueOf(it)
        }
    }
}