package com.styog.prog38448assignment1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.styog.prog38448assignment1.ui.theme.PROG38448Assignment1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PROG38448Assignment1Theme {
                var expanded by remember { mutableStateOf(false) }
                var peopleExpanded by remember { mutableStateOf(false) }
                var selectedTip by remember { mutableIntStateOf(10) }
                var people by remember { mutableIntStateOf(1) }
                var amount by remember { mutableStateOf("") }
                var customTip by remember { mutableStateOf("") }
                var tipValue by remember { mutableStateOf("") }
                var totalValue by remember { mutableStateOf("") }
                var perPersonValue by remember { mutableStateOf("") }

                val tipValues = stringArrayResource(id = R.array.tip_values)
                val peopleValues =
                    stringArrayResource(id = R.array.people_values).map { it.toInt() }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(topBar = {
                        Row(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            DropdownMenu(
                                expanded = false,
                                onDismissRequest = { }
                            ) {

                            }
                        }
                    }) { padding ->
                        print(padding)
                        Column(
                            modifier = Modifier.padding(26.dp)
                        ) {
                            Row(
                                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Amount")
                                Spacer(modifier = Modifier.width(16.dp))
                                TextField(value = amount, onValueChange = {
                                    amount = it
                                })
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "Tip %")
                                Spacer(modifier = Modifier.width(16.dp))
                                Row(modifier = Modifier.clickable { expanded = true }) {
                                    Text(text = if (selectedTip != -1) "$selectedTip%" else "other")
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "tip dropdown"
                                    )
                                }
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    tipValues.forEach { tip ->
                                        DropdownMenuItem(onClick = {
                                            selectedTip = if (tip == "other") {
                                                -1
                                            } else {
                                                customTip = ""
                                                tip.toInt()
                                            }
                                            expanded = false
                                        }, text = { Text(tip) })
                                    }
                                }
                                TextField(value = customTip, onValueChange = {
                                    customTip = it
                                }, enabled = selectedTip == -1)
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Row {
                                Text(text = "#people ")
                                Spacer(modifier = Modifier.width(16.dp))
                                Row(modifier = Modifier.clickable { peopleExpanded = true }) {
                                    Text(text = "$people")
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "people dropdown"
                                    )
                                }
                                DropdownMenu(
                                    expanded = peopleExpanded,
                                    onDismissRequest = { peopleExpanded = false }
                                ) {
                                    peopleValues.forEach { person ->
                                        DropdownMenuItem(onClick = {
                                            people = person
                                            peopleExpanded = false
                                        }, text = { Text(person.toString()) })
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))

                            Row {
                                Button(onClick = {
                                    try {
                                        amount.toInt()
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Amount must be entered",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@Button
                                    }
                                    val tip = if (selectedTip == -1) {
                                        try {
                                            customTip.toInt()
                                        } catch (e: Exception) {
                                            Toast.makeText(
                                                this@MainActivity,
                                                "Other % must be entered",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            return@Button
                                        }
                                    } else {
                                        selectedTip
                                    }
                                    val total = amount.toDouble() + (amount.toDouble() * tip / 100)
                                    val perPerson = total / people

                                    tipValue =
                                        String.format("$%.2f", (amount.toDouble() * tip / 100))
                                    totalValue = String.format("$%.2f", total)
                                    perPersonValue = String.format("$%.2f", perPerson)
                                }) {
                                    Text(text = "Calculate")
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Button(onClick = {
                                    amount = ""
                                    customTip = ""
                                    tipValue = ""
                                    totalValue = ""
                                    perPersonValue = ""
                                    people = 1
                                    selectedTip = 10
                                }) {
                                    Text(text = "Clear")
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Row {
                                Text("Tip is: ")
                                Text(tipValue)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Row {
                                Text("Total is: ")
                                Text(totalValue)
                            }
                            if (perPersonValue != totalValue) {
                                Spacer(modifier = Modifier.height(16.dp))

                                Row {
                                    Text("Per person: ")
                                    Text(perPersonValue)
                                }
                            }
                        }

                    }

                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PROG38448Assignment1Theme {
        Greeting("Android")
    }
}