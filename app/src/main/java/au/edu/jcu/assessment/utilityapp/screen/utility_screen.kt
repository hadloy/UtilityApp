package au.edu.jcu.assessment.utilityapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import au.edu.jcu.assessment.utilityapp.viewmodel.CounterViewModel
import au.edu.jcu.assessment.utilityapp.viewmodel.QuoteViewModel

@Composable
fun UtilityScreen() {
    //var counter = 0
    //var counter by remember { mutableIntStateOf(0) } //remember state at Composable level; doesn't remember if you rotate the phone (for assignment => need to remember state); good for toggle button/animation.
    //viewModel.counter //remember state at ViewModel level: UI and data are separate (recommended). Need to define the class as viewModel()
    //Create another folder (subpackage: keep many functions in one file) inside kotlin+java -> au.edu.jcu.assessment.utilityapp -> new package -> screen
    val viewModel: CounterViewModel = viewModel<CounterViewModel>()
    val counter by viewModel.count.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("QA Test Data User Generator", style = MaterialTheme.typography.headlineMedium)
        Text("Counter: $counter", style = MaterialTheme.typography.bodyLarge)
        Button(onClick = { viewModel.increment() }) {
            Text("Increment")
        }
        val quoteViewModel: QuoteViewModel = viewModel()
        val quote by quoteViewModel.quote.collectAsState()
        Text("Quote: $quote", style = MaterialTheme.typography.bodyLarge)
        Button(onClick = { quoteViewModel.loadQuote() }) {
            Text("Get Quote")
        }
    }
}
