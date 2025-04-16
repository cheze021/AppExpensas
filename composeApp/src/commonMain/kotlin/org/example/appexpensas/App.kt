package org.example.appexpensas

import AppTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Monitor
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                                    "expensesGraphics" -> navigator.navigate("/")
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
                            TopAppBar(
                                elevation = 0.dp,
                                title = {
                                    Text(
                                        text = titleTopBar,
                                        fontSize = 25.sp,
                                        color = colors.textColor
                                    )
                                },
                                navigationIcon = {
                                    if (isEditOrAddExpenses) {
                                        IconButton(
                                            onClick = {
                                                navigator.popBackStack()
                                            }
                                        ) {
                                            Icon(
                                                modifier = Modifier.padding(16.dp),
                                                imageVector = Icons.Default.ArrowBack,
                                                contentDescription = "Back arrow icon",
                                                tint = colors.textColor,
                                            )
                                        }
                                    } else {
                                        IconButton(
                                            onClick = {
                                                scope.launch { drawerState.open() }
                                            }
                                        ) {
                                            Icon(
                                                modifier = Modifier.padding(16.dp),
                                                imageVector = Icons.Default.Apps,
                                                contentDescription = "Dashboard icon",
                                                tint = colors.textColor,
                                            )
                                        }
                                    }

                                },
                                backgroundColor = colors.backgroundColor
                            )
                        },
                        floatingActionButton = {
                            if(!isEditOrAddExpenses) {
                                FloatingActionButton(
                                    modifier = Modifier.padding(8.dp),
                                    onClick = {
                                        navigator.navigate("/addExpenses")
                                    },
                                    shape = RoundedCornerShape(50),
                                    backgroundColor = colors.addIconColor,
                                    contentColor = Color.White
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        tint = Color.White,
                                        contentDescription = "Add icon"
                                    )
                                }
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

    return titleTopBar.value
}

@Composable
fun AppDrawer(
    onItemClick: (String) -> Unit,
    closeDrawer: () -> Unit
) {
    val colors = getColorsTheme()

    ModalDrawerSheet {
        Text("Menú", modifier = Modifier.padding(16.dp), fontSize = 20.sp)

        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)

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