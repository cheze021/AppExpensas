package ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.ExpenseManager
import getColorsTheme
import kotlinx.datetime.LocalDate
import model.Expense
import presentacion.ExpensesUiState
import utils.SwipeToDeleteContainer
import utils.formatDate
import kotlin.math.exp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpensesScreen(
    uiState: ExpensesUiState,
    onExpenseClick: (expense: Expense) -> Unit,
    onDeleteExpense: (expense: Expense) -> Unit,
    onViewAllClick: () -> Unit
) {

    val colors = getColorsTheme()

    when (uiState) {
        is ExpensesUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ExpensesUiState.Success -> {
            if(uiState.expenses.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    stickyHeader {
                        Column(
                            modifier = Modifier.background(colors.backgroundColor)
                        ) {
                            ExpensesTotalHeader(uiState.total)
                            AllExpensesHeader(onViewAllClick = onViewAllClick)
                        }
                    }

                    items(items = uiState.expenses, key = { it.id }) { expense ->
                        SwipeToDeleteContainer(
                            item = expense,
                            onDelete = onDeleteExpense
                        ) {
                            ExpensesItem(expense = expense, onExpenseClick = onExpenseClick)
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "No se encontraron expensas, por favor agrega una utilizando el menú lateral y la opción 'Agregar una expensa'",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1
                    )
                }
            }

        }

        is ExpensesUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: ${uiState.message}",
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }


}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpensesTotalHeader(total: Double) {
    val colors = getColorsTheme()
    var expanded by rememberSaveable { mutableStateOf(false) }

    val height by animateDpAsState(
        if (expanded) 130.dp else 50.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Card(
        shape = RoundedCornerShape(30.dp),
        backgroundColor = colors.buttonEditAddColor,
        elevation = 6.dp,
        onClick = { expanded = !expanded }
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.Top
            ) {

                Text(
                    text = "Gastos acumulados",
                    color = Color.White
                )

            }

            Box(
                modifier = Modifier.fillMaxWidth().height(height).padding(16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (expanded) {
                    Text(
                        text = "$$total",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )

                    Text(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        text = "UYU",
                        color = Color.White
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .align(Alignment.BottomEnd),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = if (expanded) "Ocultar Total" else "Mostrar Total",
                    color = Color.LightGray
                )
            }

        }
    }
}

@Composable
fun AllExpensesHeader(onViewAllClick: () -> Unit) {
    val colors = getColorsTheme()

    Row(
        modifier = Modifier.padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "Expensas",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            color = colors.textColor
        )
        Button(
            shape = RoundedCornerShape(50),
            onClick = onViewAllClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = colors.colorViewAllButton),
            enabled = true
        ) {
            Text(text = "Ver Todas")
        }
    }
}

@Composable
fun ExpensesItem(expense: Expense, onExpenseClick: (expense: Expense) -> Unit) {
    val colors = getColorsTheme()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp)
            .clickable { onExpenseClick(expense) },
        backgroundColor = colors.cardColor,
        shape = RoundedCornerShape(30),
        elevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = RoundedCornerShape(35),
                color = colors.stoneBeige
            ) {
                Image(
                    modifier = Modifier.padding(10.dp),
                    imageVector = expense.icon,
                    colorFilter = ColorFilter.tint(Color.White),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Imagen Icono Expense Item"
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = expense.category.displayName.uppercase(),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = colors.textColor
                )
                Text(
                    text = expense.description,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                val dateFormatted = formatDate(LocalDate.parse(expense.date.toString()))

                Text(
                    text = dateFormatted,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "$${expense.amount}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = colors.textColor
                )
            }
        }
    }

}