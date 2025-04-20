package com.example.rgbapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.rgbapp.ui.theme.RGBAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RGBAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RGBColorPickerScreen()
                }
            }
        }
    }
}

@Composable
fun RGBColorPickerScreen() {
    var showDialog by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf(Color.White) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(selectedColor)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { showDialog = true }) {
            Text("Select Color")
        }
    }

    if (showDialog) {
        ColorPickerDialog(
            onDismiss = { showDialog = false },
            onConfirm = { color ->
                selectedColor = color
                showDialog = false
            }
        )
    }
}

@Composable
fun ColorPickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (Color) -> Unit
) {
    var redText by remember { mutableStateOf(TextFieldValue("0")) }
    var greenText by remember { mutableStateOf(TextFieldValue("0")) }
    var blueText by remember { mutableStateOf(TextFieldValue("0")) }
    var redSlider by remember { mutableStateOf(0f) }
    var greenSlider by remember { mutableStateOf(0f) }
    var blueSlider by remember { mutableStateOf(0f) }

    // Sync text fields and sliders
    fun updateValues(r: Int, g: Int, b: Int) {
        redText = TextFieldValue(r.toString())
        greenText = TextFieldValue(g.toString())
        blueText = TextFieldValue(b.toString())
        redSlider = r.toFloat()
        greenSlider = g.toFloat()
        blueSlider = b.toFloat()
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Select RGB Color", style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(16.dp))

                // Red Input
                ColorInputField(
                    label = "Red",
                    value = redText,
                    onValueChange = { value ->
                        redText = value
                        val num = value.text.toIntOrNull() ?: 0
                        if (num in 0..255) {
                            redSlider = num.toFloat()
                        }
                    }
                )
                Slider(
                    value = redSlider,
                    onValueChange = {
                        redSlider = it
                        redText = TextFieldValue(it.toInt().toString())
                    },
                    valueRange = 0f..255f
                )

                // Green Input
                ColorInputField(
                    label = "Green",
                    value = greenText,
                    onValueChange = { value ->
                        greenText = value
                        val num = value.text.toIntOrNull() ?: 0
                        if (num in 0..255) {
                            greenSlider = num.toFloat()
                        }
                    }
                )
                Slider(
                    value = greenSlider,
                    onValueChange = {
                        greenSlider = it
                        greenText = TextFieldValue(it.toInt().toString())
                    },
                    valueRange = 0f..255f
                )

                // Blue Input
                ColorInputField(
                    label = "Blue",
                    value = blueText,
                    onValueChange = { value ->
                        blueText = value
                        val num = value.text.toIntOrNull() ?: 0
                        if (num in 0..255) {
                            blueSlider = num.toFloat()
                        }
                    }
                )
                Slider(
                    value = blueSlider,
                    onValueChange = {
                        blueSlider = it
                        blueText = TextFieldValue(it.toInt().toString())
                    },
                    valueRange = 0f..255f
                )

                // Color Preview
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(
                            Color(
                                red = redSlider.toInt(),
                                green = greenSlider.toInt(),
                                blue = blueSlider.toInt()
                            )
                        )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            onConfirm(
                                Color(
                                    red = redSlider.toInt(),
                                    green = greenSlider.toInt(),
                                    blue = blueSlider.toInt()
                                )
                            )
                        }
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}

@Composable
fun ColorInputField(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            if (it.text.isEmpty() || (it.text.toIntOrNull() ?: 0) in 0..255) {
                onValueChange(it)
            }
        },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}