package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import categoryColors
import io.github.koalaplot.core.pie.DefaultSlice
import io.github.koalaplot.core.pie.PieChart
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import model.CategoryStats
import presentacion.ExpensesUiState
import kotlin.math.roundToInt

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun ExpensesGraphicsScreen(uiState: ExpensesUiState) {
    when (uiState) {
        is ExpensesUiState.Loading -> {
            Text("Cargando datos...")
        }

        is ExpensesUiState.Error -> {
            Text("Error al cargar los datos.")
        }

        is ExpensesUiState.Success -> {
            val expenses = uiState.expenses

            if (expenses.isEmpty()) {
                Text("No hay expensas registradas aún.")
                return
            }

            // Agrupar por categoría
            val categoryTotals = expenses.groupBy { it.category }
                .mapValues { entry -> entry.value.sumOf { it.amount } } // Double

            val labels = categoryTotals.keys.map { it.name }
            val values = categoryTotals.values.map { it.toFloat() }

            val grouped = expenses.groupBy { it.category }
            val stats = grouped.map { (category, items) ->
                val amount = items.sumOf { it.amount }
                val color = categoryColors[category] ?: Color.Gray
                CategoryStats(category.name, amount, color)
            }

            val total = stats.sumOf { it.amount }
            val sortedStats = stats.sortedByDescending { it.amount }
            val colors = stats.map { it.color }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                PieChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(8.dp),
                    values = values,
                    label = { index ->
                        Text(
                            labels[index],
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    },
                    slice = { i: Int ->
                        DefaultSlice(
                            color = colors[i],
                            hoverExpandFactor = 1.05f
                        )
                    }
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    backgroundColor = Color(0xFFFAFAFA),
                    shape = RoundedCornerShape(5),
                    elevation = 6.dp
                ) {
                    Column(
                        modifier = Modifier.padding(4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Category Graphics Reference",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.subtitle1
                        )
                        sortedStats.forEach { stat ->
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(stat.color, shape = CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                val percent = (stat.amount / total * 100 * 100).roundToInt() / 100.0
                                Text(
                                    text = "${stat.category}: $percent%",
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}