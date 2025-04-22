package org.example.appexpensas

import AppTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Monitor
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.TitleTopBarTypes
import getColorsTheme
import kotlinx.coroutines.launch
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import navigation.Navigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    PreComposeApp {

        KoinContext {
            val colors = getColorsTheme()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            AppTheme {
                val navigator = rememberNavigator()
                val titleTopBar = getTitleTopAppBar(navigator)
                val isEditOrAddExpenses = titleTopBar != TitleTopBarTypes.DASHBOARD.value

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        AppDrawer(
                            onItemClick = { route ->
                                when (route) {
                                    "addExpenses" -> navigator.navigate("/addExpenses")
                                    "allExpenses" -> navigator.navigate("/allExpenses")
                                    "expensesGraphics" -> navigator.navigate("/expensesGraphic")
                                }
                            },
                            closeDrawer = {
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(colors.backgroundColor)
                                    .padding(horizontal = 16.dp, vertical = 12.dp)
                                    .clip(RoundedCornerShape(20.dp)),
                                color = colors.topAppBarColor,
                                shadowElevation = 6.dp
                            ) {
                                TopAppBar(
                                    backgroundColor = Color.Transparent,
                                    elevation = 0.dp,
                                    title = {
                                        Text(
                                            text = titleTopBar,
                                            fontSize = 22.sp,
                                            letterSpacing = 0.5.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colors.textColor
                                        )
                                    },
                                    navigationIcon = {
                                        IconButton(
                                            onClick = {
                                                if (isEditOrAddExpenses) {
                                                    navigator.popBackStack()
                                                } else {
                                                    scope.launch { drawerState.open() }
                                                }
                                            },
                                            modifier = Modifier
                                                .padding(start = 8.dp)
                                                .size(40.dp)
                                                .clip(CircleShape)
                                                .background(colors.stoneBeige)
                                        ) {
                                            Icon(
                                                imageVector = if (isEditOrAddExpenses) Icons.Default.ArrowBack else Icons.Default.Menu,
                                                contentDescription = null,
                                                tint = colors.textColor
                                            )
                                        }
                                    },
                                )
                            }
                        }
                    ) {
                        Navigation(navigator)
                    }
                }
            }
        }


    }
}

@Composable
fun getTitleTopAppBar(navigator: Navigator): String {
    var titleTopBar = TitleTopBarTypes.DASHBOARD

    val isOnAddExpenses =
        navigator.currentEntry.collectAsState(null).value?.route?.route.equals("/addExpenses/{id}?")
    if (isOnAddExpenses) {
        titleTopBar = TitleTopBarTypes.ADD
    }

    val isEditExpenses = navigator.currentEntry.collectAsState(null).value?.path<Long>("id")
    isEditExpenses?.let {
        titleTopBar = TitleTopBarTypes.EDIT
    }

    val isAllExpenses = navigator.currentEntry.collectAsState(null).value?.route?.route.equals("/allExpenses")
    if(isAllExpenses) {
        titleTopBar = TitleTopBarTypes.ALL_EXPENSES
    }

    val isExpensesGrapgics = navigator.currentEntry.collectAsState(null).value?.route?.route.equals("/expensesGraphic")
    if(isExpensesGrapgics) {
        titleTopBar = TitleTopBarTypes.GRAPHICS
    }


    return titleTopBar.value
}

@Composable
fun AppDrawer(
    onItemClick: (String) -> Unit,
    closeDrawer: () -> Unit
) {
    val colors = getColorsTheme()

    ModalDrawerSheet {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.topAppBarColor.copy(alpha = 0.7f))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Menú",
                color = colors.textColor,
                fontSize = 20.sp
            )

            IconButton(
                onClick = { closeDrawer() }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cerrar menú",
                    tint = Color.Black
                )
            }
        }

        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)

        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(colors.topAppBarColor.copy(alpha = 0.55f))
        ) {
            NavigationDrawerItem(
                label = { Text("Todas las expensas") },
                selected = false,
                onClick = {
                    onItemClick("allExpenses")
                    closeDrawer()
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Dashboard icon"
                    )
                }
            )

            NavigationDrawerItem(
                label = { Text("Agregar una expensa") },
                selected = false,
                onClick = {
                    onItemClick("addExpenses")
                    closeDrawer()
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Dashboard icon"
                    )
                }
            )

            NavigationDrawerItem(
                label = { Text("Gráfico de gastos") },
                selected = false,
                onClick = {
                    onItemClick("expensesGraphics")
                    closeDrawer()
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.BarChart,
                        contentDescription = "Dashboard icon"
                    )
                }
            )
        }
    }
}