package com.example.basicstatecodelab

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicstatecodelab.ui.theme.BasicStateCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicStateCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WellnessCounter(Modifier)
                }
            }
        }
    }
}

@Composable
fun WellnessCounter(modifier: Modifier) {
//    WaterCounter(modifier = modifier)
    StateFulCounter(modifier)
}

@Composable
fun WaterCounter(modifier: Modifier) {
    Column(modifier.padding(16.dp)) {
//        val count = remember { mutableStateOf(0) }    //1
//        var count by remember { mutableStateOf(0) }    //2
        var count by rememberSaveable { mutableStateOf(0) }    //2.1 - rememberSaveable survive configuration changes
//        var (value, setValue) = remember { mutableStateOf(0) }    //3

        if (count > 0) {
            //        Text(text = "You've had ${count.value} glasses.")
            Text(text = "You've had $count glasses.")
        } else {
            Text(text = "You seem dehydrated! Drink some water")
        }

        Row(modifier.padding(top = 16.dp)) {
            Button(
                onClick = { count++ },
                enabled = count < 10
            ) {
                Text(text = "Add one glass!")
            }
            Button(
                onClick = { count = 0 },
                modifier.padding(start = 16.dp)
            ) {
                Text(text = "Reset Water Count!")
            }
        }

        if (count > 0) {
            var showTask by remember { mutableStateOf(true) }
            if (showTask) {
                WellnessTaskItem(
                    taskName = "Have you jog for Half an hour today?",
                    onClose = {
                        showTask = false    // To close the Wellness dialog
                    },
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun WellnessTaskItem(
    taskName: String,
    onClose: () -> Unit,
    modifier: Modifier
) {
    Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom) {
        Card(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier=Modifier.padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = taskName,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(2f)
                )

                IconButton(onClick = onClose, Modifier.padding(5.dp)) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun StateFulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(
        count = count,
        onIncrement = {
            count++
            Log.d("TAG", "!@# StateFulCounter: count => $count")
        },
        modifier
    )
}

@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text(text = "You've had $count glasses.")
        }
        Button(onClick =  onIncrement , modifier.padding(top = 16.dp), enabled = count < 10) {
            Text(text = "Add one")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BasicStateCodelabTheme {
//        WellnessCounter(Modifier)
        WellnessTaskItem("Have you jog for Half an hour today?", {}, Modifier)
    }
}