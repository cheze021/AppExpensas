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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.ExpenseManager
import getColorsTheme
import model.Expense
import presentacion.ExpensesUiState
import kotlin.math.exp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpensesScreen(uiState: ExpensesUiState, onExpenseClick: (expense: Expense) -> Unit) {

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
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                stickyHeader {
                    Column(
                        modifier = Modifier.background(colors.backgroundColor)
                    ) {
                        ExpensesTotalHeader(uiState.total)
                        AllExpensesHeader()
                    }
                }

                items(uiState.expenses) { expense ->
                    ExpensesItem(expense = expense, onExpenseClick = onExpenseClick)
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
        backgroundColor = Color.Black,
        elevation = 5.dp,
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
                        text = "USD",
                        color = Color.Gray
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
fun AllExpensesHeader() {
    val colors = getColorsTheme()

    Row(
        modifier = Modifier.padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "All Expenses",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            color = colors.textColor
        )
        Button(
            shape = RoundedCornerShape(50),
            onClick = {//Crear click mas adelante
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
            enabled = false
        ) {
            Text(text = "View All")
        }
    }
}

@Composable
fun ExpensesItem(expense: Expense, onExpenseClick: (expense: Expense) -> Unit) {
    val colors = getColorsTheme()

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp, vertical = 2.dp).clickable {
            onExpenseClick(expense)
        },
        backgroundColor = colors.colorExpenseItem,
        shape = RoundedCornerShape(30)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = RoundedCornerShape(35),
                color = colors.purple
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
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            ) {
                Text(
                    text = expense.category.name,
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

            Text(
                text = "$${expense.amount}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = colors.textColor
            )
        }
    }
}