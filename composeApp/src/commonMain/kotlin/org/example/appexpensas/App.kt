package org.example.appexpensas

import AppTheme
import androidx.compose.runtime.*
import data.ExpenseManager
import data.ExpenseRepoImpl
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.viewmodel.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentacion.ExpensesViewModel
import ui.ExpensesScreen

@Composable
@Preview
fun App() {
    PreComposeApp {
        val viewModel = viewModel(modelClass = ExpensesViewModel::class) {
            ExpensesViewModel(ExpenseRepoImpl(ExpenseManager))
        }
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        AppTheme {

            ExpensesScreen(
                uiState = uiState, onExpenseClick = {})

        }
    }
}