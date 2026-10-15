package au.edu.jcu.assessment.utilityapp.screen

// Jetpack Compose UI components.
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll

// Material 3 components.
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

// Compose state and layout utilities.
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

// ViewModel and lifecycle imports.
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

// Project-specific imports.
import au.edu.jcu.assessment.utilityapp.R
import au.edu.jcu.assessment.utilityapp.viewmodel.UserProfileViewModel

// Library used to load profile pictures from URLs.
import coil.compose.AsyncImage

@Composable
fun UtilityScreen(
    userViewModel: UserProfileViewModel = viewModel()
) {
    val sampleUser by userViewModel.user.collectAsStateWithLifecycle() //update screen when user changes
    val visibleFields by userViewModel.visibleFields.collectAsStateWithLifecycle()//Verify which field is enabled in settings

    // Create a list containing only the fields enabled in Settings.
    val profileFields = buildList {
        if ("gender" in visibleFields) {
            add("Gender" to sampleUser.gender)
        }
        if ("nationality" in visibleFields) {
            add("Nationality" to sampleUser.nationality)
        }
        if ("email" in visibleFields) {
            add("Email" to sampleUser.email)
        }
        if ("phone" in visibleFields) {
            add("Phone" to sampleUser.phone)
        }
        if ("address" in visibleFields) {
            add("Address" to sampleUser.address)
        }
        if ("birthday" in visibleFields) {
            add("Birthday" to sampleUser.birthday)
        }
        if ("username" in visibleFields) {
            add("Username" to sampleUser.username)
        }
        if ("password" in visibleFields) {
            add("Password" to sampleUser.password)
        }
    }

    // Main screen layout.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

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

        // Main Card containing the generated user profile.
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface //white card
            )
        ) {

            // Arrange the profile title, picture and fields vertically.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sample User Profile",
                    style = MaterialTheme.typography.titleMedium
                )

                // Display the profile picture only if enabled in Settings.
                if ("picture" in visibleFields) {
                    // AsyncImage loads the image from the API URL.
                    AsyncImage(
                        model = sampleUser.pictureUrl,
                        contentDescription = "User profile picture",

                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop //circular shape
                    )
                }

                // Column containing all visible profile fields.
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    // Display Name on one row full width
                    if ("name" in visibleFields) {
                        ProfileField(
                            label = "Name",
                            value = sampleUser.name,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Split the fields into groups of two.
                    // If a field is hidden, the remaining fields readjust
                    profileFields.chunked(2).forEach { fieldPair ->

                        // Display up to two cards horizontally.
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min), //size of tallest card
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            fieldPair.forEach { (label, value) ->
                                ProfileField(
                                    label = label,
                                    value = value,
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                )
                            }
                            //filled the last row if only one field
                            if (fieldPair.size == 1) {
                                Spacer(
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Button to request another random user from the API.
        Button(
            onClick = {
                userViewModel.fetchRandomUser()
            },

            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generate User")
        }
    }
}

// Reusable composable that displays one profile field.
@Composable
fun ProfileField(
    label: String,//ex:name
    value: String,//ex: Sophie Taylor
    modifier: Modifier = Modifier
) {

    // Individual information Card.
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        // Field label above its value.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface //adapt color to dark mode
            )
        }
    }
}
