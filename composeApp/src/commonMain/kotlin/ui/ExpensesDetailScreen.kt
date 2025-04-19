package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.TitleTopBarTypes
import getColorsTheme
import kotlinx.coroutines.launch
import model.Expense
import model.ExpenseCategory

@Composable
fun ExpensesDetailScreen(
    expenseToEdit: Expense? = null,
    categoryList: List<ExpenseCategory> = emptyList(),
    addExpenseAndNavigateBack: (Expense) -> Unit,
    deleteExpenseAndNavigateBack: (Expense) -> Unit
) {
    val colors = getColorsTheme()
    var price by rememberSaveable { mutableStateOf(expenseToEdit?.amount ?: 0.0) }
    var description by rememberSaveable { mutableStateOf(expenseToEdit?.description ?: "") }
    var expenseCategory by rememberSaveable { mutableStateOf(expenseToEdit?.category?.name ?: "") }
    var categorySelected by rememberSaveable { mutableStateOf(expenseToEdit?.category?.name ?: "Select a category") }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(sheetState.targetValue) {
        if(sheetState.targetValue == ModalBottomSheetValue.Expanded) {
            keyboardController?.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            CategoryBottomSheetContent(categoryList) {
                categorySelected = it.name
                expenseCategory = it.name
                scope.launch {
                    sheetState.hide()
                }
            }
        }
    ) {
        Column (modifier = Modifier
            .fillMaxSize()
            .background(colors.backgroundColor)
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
        ){
            ExpenseAmount(
                priceContent = price,
                onPriceChange = { price = it },
                keyboardController = keyboardController
            )

            Spacer(modifier = Modifier.height(10.dp))

            ExpenseTypeSelector(
                categorySelected = categorySelected,
                openBottomSheet = {
                    scope.launch {
                        sheetState.show()
                    }
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            ExpenseDescription(
                descriptionContent = description,
                onDescriptionChange = {
                    description = it
                },
                keyboardController = keyboardController
            )

            Spacer(modifier = Modifier.weight(1f))


            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25)),
                onClick = {
                    val expense = Expense(
                        amount = price,
                        category = ExpenseCategory.valueOf(expenseCategory),
                        description = description
                    )
                    val expenseFromEdit = expenseToEdit?.id?.let { expense.copy(id = it) }
                    addExpenseAndNavigateBack(expenseFromEdit ?: expense)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colors.purple,
                    contentColor = Color.White
                ),
                enabled = price != 0.0 && description.isNotBlank() && expenseCategory.isNotBlank()
            ) {
                expenseToEdit?.let {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = TitleTopBarTypes.EDIT.value,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                    return@Button
                }
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = TitleTopBarTypes.ADD.value,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }

            // Only show delete button when editing an existing expense
            if (expenseToEdit != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(25)),
                    onClick = {
                        deleteExpenseAndNavigateBack(expenseToEdit)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = TitleTopBarTypes.DELETE.value,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun ExpenseAmount(
    priceContent: Double,
    onPriceChange: (Double) -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    val colors = getColorsTheme()
    var text by rememberSaveable { mutableStateOf("$priceContent") }
    Column (
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = "Amount",
            fontSize = 18.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Medium
        )
        Row(
            modifier = Modifier
                .background(Color.White)
                .border(
                    border = BorderStroke(width = 1.dp, Color.LightGray),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "USD",
                fontSize = 16.sp,
                color = colors.textColor,
                fontWeight = FontWeight.Normal
            )

            TextField(
                modifier = Modifier.weight(1f),
                placeholder = { Text("0.00") },
                value = text,
                onValueChange = { newText ->
                    val numericText = newText.filter { it.isDigit() || it == '.' }
                    text = if(numericText.isNotEmpty() && numericText.count { it == '.'} <= 1) {
                        try {
                            val newValue = numericText.toDouble()
                            onPriceChange(newValue)
                            numericText
                        } catch (e: NumberFormatException) {
                            onPriceChange(0.0)
                            ""
                        }
                    } else {
                        onPriceChange(0.0)
                        ""
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colors.textColor,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent
                ),
                textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Normal)
            )
        }

    }
}

@Composable
private fun ExpenseTypeSelector(
    categorySelected: String,
    openBottomSheet: () -> Unit
) {
    val colors = getColorsTheme()

    Column (
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = "Expenses made for",
            fontSize = 18.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Medium
        )
        Row(
            modifier = Modifier
                .background(Color.White)
                .border(
                    border = BorderStroke(width = 1.dp, Color.LightGray),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = categorySelected,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = colors.textColor
            )

            IconButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(35)),
                onClick = { openBottomSheet() }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Arrow down icon",
                    tint = colors.textColor
                )
            }
        }

    }
}

@Composable
fun ExpenseDescription(
    descriptionContent: String,
    onDescriptionChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    var text by rememberSaveable { mutableStateOf(descriptionContent) }
    val colors = getColorsTheme()

    Column (
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = "Description",
            fontSize = 18.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Medium
        )

        Row(
            modifier = Modifier
                .background(Color.White)
                .border(
                    border = BorderStroke(width = 1.dp, Color.LightGray),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth().height(120.dp),
                placeholder = { Text("Write a description...") },
                value = text,
                onValueChange = { newText ->
                    if(newText.length <= 200){
                        text = newText
                        onDescriptionChange(newText)
                    }
                },
                maxLines = 3,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colors.textColor,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent
                ),
                textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Normal),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                )
            )
        }
    }
}

@Composable
private fun CategoryBottomSheetContent(
    categories: List<ExpenseCategory>,
    onCategorySelected: (ExpenseCategory) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(16.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {
        items(categories) { category ->
            CategoryItem(category, onCategorySelected)
        }
    }
}

@Composable
private fun CategoryItem(category: ExpenseCategory, onCategorySelected: (ExpenseCategory) -> Unit) {
    Column (
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
            onCategorySelected(category)
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            modifier = Modifier.size(40.dp).clip(CircleShape),
            imageVector = category.icon,
            contentDescription = "Category Icon",
            contentScale = ContentScale.Crop
        )
        Text(text = category.name)
    }
}