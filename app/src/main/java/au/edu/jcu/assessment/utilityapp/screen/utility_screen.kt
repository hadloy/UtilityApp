package au.edu.jcu.assessment.utilityapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import au.edu.jcu.assessment.utilityapp.R
import au.edu.jcu.assessment.utilityapp.viewmodel.UserProfileViewModel
import coil.compose.AsyncImage

@Composable
fun UtilityScreen(
    //pass the view model
    userViewModel: UserProfileViewModel = viewModel()
) {
    //collect user data from view model
    val sampleUser by userViewModel.user.collectAsStateWithLifecycle()
    //verify which field is visible
    val visibleFields by userViewModel.visibleFields.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        //display app name + icon
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.qa_app_icon),
                contentDescription = "QA Test Data Generator app icon",
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = "QA Test Data Generator",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Text(
            text = "Generate fictional user data for software testing.",
            style = MaterialTheme.typography.bodyMedium
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            )
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
                //display user picture if in visible fields
                if ("picture" in visibleFields) {
                    AsyncImage(
                        model = sampleUser.pictureUrl,
                        contentDescription = "User profile picture",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                //display user data if in visible fields
                if ("gender" in visibleFields) {
                    ProfileField("Gender", sampleUser.gender)
                }
                if ("name" in visibleFields) {
                    ProfileField("Name",sampleUser.name)
                }
                if ("email" in visibleFields) {
                    ProfileField(label = "Email", value= sampleUser.email)
                }
                if ("phone" in visibleFields) {
                    ProfileField(label = "Phone", value = sampleUser.phone)
                }
                if ("nationality" in visibleFields) {
                    ProfileField(label = "Nationality", value = sampleUser.nationality)
                }
                if ("address" in visibleFields) {
                    ProfileField(label = "Address", value = sampleUser.address)
                }
                if ("birthday" in visibleFields) {
                    ProfileField(label = "Birthday", value = sampleUser.birthday)
                }
                if ("username" in visibleFields) {
                    ProfileField(label = "Username", value = sampleUser.username)
                }
                if ("password" in visibleFields) {
                    ProfileField(label = "Password", value = sampleUser.password)
                }
            }
        }
        Button(
            onClick = { userViewModel.fetchRandomUser() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generate User")
        }
    }
}
@Composable
//To give a string multiple styles
fun ProfileField(
    label: String,
    value: String
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(fontWeight = FontWeight.Bold)
            ) {
                append("$label: ")
            }
            append(value)
        },
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium
    )
}