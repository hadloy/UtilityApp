
package au.edu.jcu.assessment.utilityapp.screen

import android.R.attr.name
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import au.edu.jcu.assessment.utilityapp.model.UserProfile

@Composable
fun UtilityScreen() {
    //sample data to see if the layout works with the new class
    val sampleUser = UserProfile(
        name =  "Sophie Taylor",
        email = "alex.martin@example.com",
        phone = "0400 000 000",
        nationality = "AU",
        pictureUrl = "https://randomuser.me/api/portraits/men/75.jpg",
        address = "25 King Street, Brisbane, Australia",
        birthday = "1995-04-12",
        password = "Example123!"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "QA Test Data Generator",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Generate fictional user data for software testing.",
            style = MaterialTheme.typography.bodyMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Sample User Profile",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(text = "Name: ${sampleUser.name}") //calling user data from UserProfile class
                Text(text = "Email: ${sampleUser.email}")
                Text(text = "Phone: ${sampleUser.phone}")
                Text(text = "Nationality: ${sampleUser.nationality}")
                Text(text = "Address: ${sampleUser.address}")
                Text(text = "Birthday: ${sampleUser.birthday}")
                Text(text = "Password: ${sampleUser.password}")

            }
        }
    }
}
