package com.example.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab1.ui.theme.Lab1Theme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    TitleText()
                    HeightSlider()

                }

            }
        }
    }
}

@Composable
fun TitleText() {
    Text(
        text = "Welcome to the RFM Calculator",
        color = MaterialTheme.colorScheme.primary,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun HeightSlider() {
    var sliderPosition by remember { mutableStateOf(40f) }
    Slider(
        modifier = Modifier.semantics { contentDescription = "Localized Description" },
        value = sliderPosition,
        onValueChange = { sliderPosition = it },
        valueRange = 40f..80f,
        onValueChangeFinished = {
            // launch some business logic update with the state you hold
            // viewModel.updateSelectedSliderValue(sliderPosition)
        },
        //steps = 100

    )
    var stepInt = (sliderPosition).toInt()

    Text(text = "Height: $stepInt inches")
}
