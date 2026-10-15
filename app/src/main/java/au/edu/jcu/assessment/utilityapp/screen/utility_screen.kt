package au.edu.jcu.assessment.utilityapp.screen

// Jetpack Compose UI components.
import android.R.attr.label
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ViewModel and lifecycle imports.
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

// Project-specific imports.
import au.edu.jcu.assessment.utilityapp.R
import au.edu.jcu.assessment.utilityapp.viewmodel.UserProfileViewModel

// Library used to load profile pictures from URLs.
import coil.compose.AsyncImage

// Material icons used in the profile fields.
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Wc

// Displays an icon in a Compose layout.
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector

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
            .padding(16.dp),

        verticalArrangement = Arrangement.SpaceBetween
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
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {

            // Arrange the profile title, picture and fields vertically.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                // Consistent spacing without stretching the content.
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

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
                            icon = Icons.Filled.Person,
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
                                // Choose the icon for each field
                                val fieldIcon = when (label) {
                                    "Gender" -> Icons.Filled.Wc
                                    "Nationality" -> Icons.Filled.Public
                                    "Email" -> Icons.Filled.Email
                                    "Phone" -> Icons.Filled.Phone
                                    "Address" -> Icons.Filled.LocationOn
                                    "Birthday" -> Icons.Filled.DateRange
                                    "Username" -> Icons.Filled.AccountCircle
                                    "Password" -> Icons.Filled.Lock
                                    else -> null
                                }

                                ProfileField(
                                    label = label,
                                    value = value,
                                    icon = fieldIcon,
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
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    icon: ImageVector? = null
) {

    // Individual card for the profile information.
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        // Arrange the field label and value vertically.
        Column( // Keep field label and value vertically aligned.
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Row(// First line: icon and field label.
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(// Display the field name next to icon.
                    text = label.uppercase(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = value,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )

        }

    }
}
