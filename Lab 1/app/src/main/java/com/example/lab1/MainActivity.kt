package com.example.lab1

import android.os.Bundle
import android.widget.RadioButton
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
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
                var height: Float = 0F
                var waist: Float = 0F
                var gender: Int = 0
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    TitleText()
                    Spacer(modifier = Modifier.height(50.dp))
                    height = SizeSlider(measurement = "Height")
                    Spacer(modifier = Modifier.height(20.dp))
                    waist = SizeSlider(measurement = "Waist")
                    Spacer(modifier = Modifier.height(20.dp))
                    gender = GenderSelect()
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    DisplayRFM(height = height, waist = waist, gender = gender)
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
fun SizeSlider(measurement: String): Float {
    var sliderPosition by remember { mutableStateOf(50f) }
    Slider(
        modifier = Modifier.semantics { contentDescription = "Localized Description" },
        value = sliderPosition,
        onValueChange = { sliderPosition = it },
        valueRange = 1f..100f,
        onValueChangeFinished = {
        },
        )
    var stepInt = sliderPosition.toInt()
    Text(text = "$measurement: ${stepInt.toInt()} inches")
    return sliderPosition
}
object Gender {
    const val male = "Male"
    const val female = "Female"
}
@Composable
fun GenderSelect(): Int {
    val selectedGender = remember {
        mutableStateOf(Gender.male)
    }
    Text(
        text = "Select a Gender",
        fontSize = 20.sp
    )
    Spacer(modifier = Modifier.size(10.dp))
    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selectedGender.value == Gender.male,
            onClick = { selectedGender.value = Gender.male },
            colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.secondary),

        )
        Text(Gender.male)
        RadioButton(
            selected = selectedGender.value == Gender.female,
            onClick = { selectedGender.value = Gender.female },
            colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.secondary)
        )
        Text(Gender.female)
    }
    if(selectedGender.value == Gender.male) {
        return 0
    }
    else {
        return 1
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayRFM(height: Float, waist: Float, gender: Int) {
    val ctx = LocalContext.current
    var fatMass: Float
    var fatRange = ""
    var color = 0
    ExtendedFloatingActionButton(
        onClick = {
            fatMass = (64 - (20 * (height / waist)) + (12 * gender))

            if (gender == 0) { // Male
                if (fatMass < 9) {
                    fatRange = "Underfat"
                    color = 1 // Blue
                }
                else if ((fatMass >= 9) && (fatMass < 19)) {
                    fatRange = "Healthy"
                    color = 2 // Green
                }
                else if ((fatMass >= 19) && (fatMass < 25)) {
                    fatRange = "Overfat"
                    color = 3 // Yellow
                }
                else if (fatMass >= 25) {
                    fatRange = "Obese"
                    color = 4 // Red
                }
            }
            else if (gender == 1) { // Female
                if (fatMass < 21) {
                    fatRange = "Underfat"
                    color = 1 // Blue
                }
                else if ((fatMass >= 21) && (fatMass < 33)) {
                    fatRange = "Healthy"
                    color = 2 // Green
                }
                else if ((fatMass >= 33) && (fatMass < 39)) {
                    fatRange = "Overfat"
                    color = 3 // Yellow
                }
                else if (fatMass >= 39) {
                    fatRange = "Obese"
                    color = 4 // Red
                }
            }
            Toast.makeText(ctx, "$fatRange", Toast.LENGTH_SHORT).show()
        },
    ) {
        Text(text = "Calculate RFM")
    }
    /*Scaffold() {

    }*/
}