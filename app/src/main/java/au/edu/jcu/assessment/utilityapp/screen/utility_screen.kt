
package au.edu.jcu.assessment.utilityapp.screen

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
import coil.compose.AsyncImage //load images
import androidx.compose.foundation.layout.size // set image dimension
import androidx.compose.foundation.shape.CircleShape //define shape
import androidx.compose.ui.draw.clip //crop image
import androidx.compose.ui.Alignment //position in layout
import androidx.compose.ui.layout.ContentScale //how to resize an image
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import au.edu.jcu.assessment.utilityapp.viewmodel.UserProfileViewModel


@Composable
fun UtilityScreen(
    userViewModel: UserProfileViewModel = viewModel() //pass the view model
) {
    val sampleUser by userViewModel.user.collectAsStateWithLifecycle()//collect user data from view model

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
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Sample User Profile",
                    style = MaterialTheme.typography.titleMedium
                )
                //display user picture
                AsyncImage(
                    model = sampleUser.pictureUrl,
                    contentDescription = "User profile picture",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                //display user data
                Text(text = "Name: ${sampleUser.name}") //calling user data from UserProfile class
                Text(text = "Email: ${sampleUser.email}")
                Text(text = "Phone: ${sampleUser.phone}")
                Text(text = "Nationality: ${sampleUser.nationality}")
                Text(text = "Address: ${sampleUser.address}")
                Text(text = "Birthday: ${sampleUser.birthday}")
                Text(text = "Password: ${sampleUser.password}")

            }
        }
        Button(
            onClick = {
                userViewModel.generateUser()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generate User")
        }
    }
}
