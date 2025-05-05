package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button

import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import getColorsTheme

@Composable
fun FinancialBalanceScreen(
    ingresos: Double,
    gastos: Double
) {
    val colors = getColorsTheme()
    val ahorro = (ingresos - gastos) / 1.5


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.backgroundColor)
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        Text("Balance Financiero", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
            backgroundColor = colors.cardColor,
            shape = RoundedCornerShape(5),
            elevation = 6.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Ingresos Totales: $${(ingresos)}")
                Text("Gastos Totales: $${(gastos)}")
                Text("Ahorro Estimado: $${(ahorro)}", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { /* TODO: editar ingresos */ },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colors.buttonEditAddColor,
                    contentColor = Color.White
                )) {
                Text("Editar Ingresos")
            }
            Button(
                onClick = { /* TODO: ver detalle de gastos */ },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colors.buttonEditAddColor,
                    contentColor = Color.White
                )) {
                Text("Ver Detalles")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Consejo:", style = MaterialTheme.typography.titleMedium)
        Text(
            text = if (ahorro > 0)
                "¡Buen trabajo! Este mes podrías ahorrar $${(ahorro)}."
            else
                "Cuidado: tus gastos superan tus ingresos. Revisa tus gastos.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp),
            color = if (ahorro > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )
    }
}