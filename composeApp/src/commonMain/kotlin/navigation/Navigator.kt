package navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import getColorsTheme
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import org.koin.core.parameter.parametersOf
import presentacion.ExpensesViewModel
import ui.ExpensesDetailScreen
import ui.ExpensesScreen

@Composable
fun Navigation(navigator: Navigator) {

    val colors = getColorsTheme()
    val viewModel = koinViewModel(ExpensesViewModel::class) { parametersOf() }


    NavHost(
        modifier = Modifier.background(colors.backgroundColor),
        navigator = navigator,
        initialRoute = "/home"
    ) {
        scene(route = "/home") {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ExpensesScreen(uiState) { epxense ->
                navigator.navigate("/addExpenses/${epxense.id}")
            }
        }

        scene(route = "/addExpenses/{id}?") { backStackEntry ->
            val idFromPath = backStackEntry.path<Long>("id")
            val expenseToEditOrAdd = try {
                idFromPath?.let { id -> viewModel.getExpenseWithID(id) }
            } catch (e: NoSuchElementException) {
                null
            }

            ExpensesDetailScreen(
                expenseToEdit = expenseToEditOrAdd,
                categoryList = viewModel.getCategories(),
                addExpenseAndNavigateBack = { expense ->
                    if(expenseToEditOrAdd == null) {
                        viewModel.addNewExpense(expense)
                    } else {
                        viewModel.editExpense(expense)
                    }
                    navigator.popBackStack()
                },
                deleteExpenseAndNavigateBack = { expense ->
                    viewModel.deleteExpense(expense)
                    navigator.popBackStack()
                }
            )
        }
    }

}