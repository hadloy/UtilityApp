package au.edu.jcu.assessment.utilityapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UtilityScreen() {
    //var counter = 0
    var counter by remember { mutableIntStateOf(0) } //remember state at Composable level; doesn't remember if you rotate the phone (for assignment => need to remember state); good for toggle button/animation.
    //viewModel.counter //remember state at ViewModel level: UI and data are separate (recommended). Need to define the class as viewModel()

    //Create another folder (subpackage: keep many functions in one file) inside kotlin+java -> au.edu.jcu.assessment.utilityapp -> new package -> screen

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("QA Test Data User Generator", style = MaterialTheme.typography.headlineMedium)
        Text("Counter: $counter", style = MaterialTheme.typography.bodyLarge)
        Button(onClick = { counter++ }) {
            Text("Increment")
        }
        Button(onClick = { counter-- }) {
            Text("Decrement")
        }
        Button(onClick = { counter = 0 }) {
            Text("Reset")
        }
    }
}
