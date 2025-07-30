package com.example.unit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun Converter() {
    var Ivalue by remember { mutableStateOf("") }
    var Ovalue by remember { mutableStateOf("") }

    var Iunit by remember { mutableStateOf("BDT") }
    var Ounit by remember { mutableStateOf("BDT") }

    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }

    var iValueFactor by remember { mutableStateOf(1.0) }
    var oValueFactor by remember { mutableStateOf(1.0) }

    fun Calculate(){
        val In = Ivalue.toDoubleOrNull() ?: 0.0
        val results = (In * oValueFactor) / iValueFactor
        Ovalue = String.format(" %.2f ", results)
    }

    Box (
        modifier = Modifier.fillMaxSize()
            .background(
              brush = Brush.linearGradient(
                  colors = listOf(
                      colorResource( R.color.blue_sky),
                      colorResource(R.color.green_forest_soft)
                  )
                )
            )
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                "Currency Converter",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = Ivalue,
                onValueChange = {
                    Ivalue = it
                    Calculate()
                },
                label = { Text("Enter Value") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedLabelColor = Color.DarkGray,
                    unfocusedLabelColor = Color.DarkGray,
                    cursorColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth(.9f)
            )
            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DropButton(
                    Iunit,
                    iExpanded,
                    { iExpanded = it },
                    { unit, factor ->
                        Iunit = unit
                        iValueFactor = factor
                        Calculate()
                    }
                )
                DropButton(
                    Ounit,
                    oExpanded,
                    { oExpanded = it },
                    { unit, factor ->
                        Ounit = unit
                        oValueFactor = factor
                        Calculate()
                    }
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                "Convert Value: $Ovalue $Ounit",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Screen(modifier: Modifier = Modifier) {
    Converter()
}

@Composable
fun DropButton(
    label : String,
    expanded : Boolean,
    onExpandChanges : (Boolean) -> Unit,
    onSelectOpt : (String, Double) -> Unit
) {
    Box{
        Button(
            onClick = { onExpandChanges(!expanded) },
            modifier = Modifier
                .width(130.dp)
                .height(50.dp)
                .shadow(8.dp, RoundedCornerShape(18.dp))
                .clip(RoundedCornerShape(10.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            )
        )  {

            Text(label)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "ArDrDo icon",
                modifier = Modifier.rotate(
                    if (expanded) 180f else 0f
                )
            )
        }

        DropdownMenu(
            expanded,
            {onExpandChanges(false)}
        )
        {

            listOf(
                "USD" to 0.0091,
                "EUR" to 0.0084,
                "INR" to 0.76,
                "BDT" to 1.0,
                "JPY" to 1.44,
                "SAR" to 0.034
            ).forEach {
                (unit, factor) ->
                DropdownMenuItem(
                    {Text(unit)},
                    {
                        onSelectOpt(unit, factor)
                        onExpandChanges(false)
                    }
                )
            }
        }
    }
}