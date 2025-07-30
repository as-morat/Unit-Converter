package com.example.unit

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Composable
fun UnitConverter(modifier: Modifier = Modifier) {

    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }

    var inputUnit by remember { mutableStateOf("Meter") }
    var outputUnit by remember { mutableStateOf("Meter") }

    var isInputExpanded by remember { mutableStateOf(false) }
    var isOutputExpanded by remember { mutableStateOf(false) }

    var inputConversionFactor by remember { mutableStateOf(1.0) }
    var outputConversionFactor by remember { mutableStateOf(1.0) }

    @SuppressLint("DefaultLocale")
    fun convertUnits() {
        val input = inputValue.toDoubleOrNull() ?: 0.0
        val result = input * inputConversionFactor / outputConversionFactor
        outputValue = String.format("%.2f", result)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Unit Converter",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                convertUnits()
            },
            label = {
                Text("Enter Value")
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DropDownButton(
                label = inputUnit,
                expanded = isInputExpanded,
                onExpandedChanges = { isInputExpanded = it },
                onOptionSelected = { unit, factor ->
                    inputUnit = unit
                    inputConversionFactor = factor
                    convertUnits()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            DropDownButton(
                label = outputUnit,
                expanded = isOutputExpanded,
                onExpandedChanges = { isOutputExpanded = it },
                onOptionSelected = { unit, factor ->
                    outputUnit = unit
                    outputConversionFactor = factor
                    convertUnits()
                }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Converted Value: $outputValue $outputUnit",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DropDownButton(
    label: String,
    expanded: Boolean,
    onExpandedChanges: (Boolean) -> Unit,
    onOptionSelected: (String, Double) -> Unit
) {
    Box {
        Button(
            onClick = { onExpandedChanges(!expanded) },
            modifier = Modifier.wrapContentSize()
        ) {
            Text(label)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "ArrowDropDown Icon",
                modifier = Modifier.rotate(if (expanded) 180f else 0f)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChanges(false) }
        ) {
            listOf(
                "Millimeter" to 0.001,
                "Centimeter" to 0.01,
                "Meter" to 1.0,
                "Feet" to 0.3048
            ).forEach { (unit, factor) ->
                DropdownMenuItem(
                    text = { Text(unit) },
                    onClick = {
                        onOptionSelected(unit, factor)
                        onExpandedChanges(false)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUnitConverter() {
    UnitConverter()
}
