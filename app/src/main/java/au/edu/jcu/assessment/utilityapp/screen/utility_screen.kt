package au.edu.jcu.assessment.utilityapp.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import au.edu.jcu.assessment.utilityapp.R
import au.edu.jcu.assessment.utilityapp.viewmodel.UserProfileViewModel
import coil.compose.AsyncImage

@Composable
fun UtilityScreen(
    userViewModel: UserProfileViewModel = viewModel()
) {
    val sampleUser by userViewModel.user.collectAsStateWithLifecycle() //update screen when user changes
    val visibleFields by userViewModel.visibleFields.collectAsStateWithLifecycle()//Verify which field is enabled in settings
    val configuration = LocalConfiguration.current //read device configuration and update when rotation
    val isLandscape =
        configuration.orientation == Configuration.ORIENTATION_LANDSCAPE // verify if in landscape
    val darkModeEnabled by userViewModel
        .darkModeEnabled
        .collectAsStateWithLifecycle()
    // Choose a readable text colour for the selected theme.
    if (darkModeEnabled) {
        androidx.compose.ui.graphics.Color.White
    } else {
        androidx.compose.ui.graphics.Color.Black
    }
    // Create a list containing only the fields enabled in Settings.
    val profileFields = buildList {
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

    if (isLandscape) {// Choose which screen layout to display.

        LandscapeProfileLayout(// Display landscape composable.
            pictureUrl = sampleUser.pictureUrl,
            userName = sampleUser.name,
            profileFields = profileFields,
            showPicture = "picture" in visibleFields,
            showName = "name" in visibleFields,
            onGenerateClick = {
                userViewModel.fetchRandomUser()
            }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),

            verticalArrangement = Arrangement.Center
        ) {

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
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Column containing all visible profile fields.
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Display the app icon and title inside the profile card.
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.qa_app_icon),
                                contentDescription = "QA Test Data Generator app icon",
                                modifier = Modifier.size(40.dp)
                            )

                            Text(
                                text = "QA Test Data Generator",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // Explain the purpose of the application.
                        Text(
                            text = "Generate fictional user data for software testing.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.fillMaxWidth()
                                .padding(bottom =10.dp)
                        )
                        // Display the profile picture
                        if ("picture" in visibleFields) {
                            AsyncImage(
                                model = sampleUser.pictureUrl,
                                contentDescription = "User profile picture",
                                modifier = Modifier
                                    .size(100.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop //circular shape
                            )
                        }
                        // Display Name on one row full width
                        if ("name" in visibleFields) {
                            ProfileField(
                                label = "Name",
                                value = sampleUser.name,
                                icon = Icons.Filled.Person,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        // Address full-width and group other visible fields to fill incomplete row.
                        val compactFields = profileFields.filter { it.first != "Address" }
                        // Separate the fields into those before and after Address.
                        val addressIndex = profileFields.indexOfFirst { it.first == "Address" }


                        // Split fields into groups of two, but give Address its own full-width row.
                        val portraitRows = buildList {

                            // Round up to an even number of fields before Address.
                            val fieldsToPlaceBeforeAddress =
                                if (addressIndex >= 0) {
                                    (addressIndex + 1) / 2 * 2
                                } else {
                                    compactFields.size
                                }

                            // Create pairs of fields before Address.
                            compactFields
                                .take(fieldsToPlaceBeforeAddress)
                                .chunked(2)
                                .forEach { fieldPair ->
                                    add(fieldPair)
                                }

                            // Display Address on its own full-width row, if enabled.
                            profileFields.find { it.first == "Address" }?.let { addressField ->
                                add(listOf(addressField))
                            }

                            // Display the remaining fields after Address.
                            compactFields
                                .drop(fieldsToPlaceBeforeAddress)
                                .chunked(2)
                                .forEach { fieldPair ->
                                    add(fieldPair)
                                }
                        }

                        portraitRows.forEach { fieldPair ->

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
                                            .weight(if (label == "Address") 2f else 1f)
                                            .fillMaxHeight()
                                    )
                                }
                                // Fill an incomplete row, except when Address uses the full width.
                                if (fieldPair.size == 1 && fieldPair.first().first != "Address") {
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

@Composable
fun LandscapeProfileLayout(
    pictureUrl: String,
    userName: String,
    profileFields: List<Pair<String, String>>,
    showPicture: Boolean,
    showName: Boolean, //Check if Name is enabled in Settings
    onGenerateClick: () -> Unit
) {
    // Arrange the profile into left and right sections.
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // LEFT SECTION: picture, name and Generate button.
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (showPicture) {// Only display the picture when enabled.
                AsyncImage(
                    model = pictureUrl,
                    contentDescription = "User profile picture",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            if (showName) { // Only display Name when enabled in Settings.
                ProfileField(
                    label = "Name",
                    value = userName,
                    icon = Icons.Filled.Person,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Button(
                onClick = onGenerateClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Generate User")
            }
        }

        // RIGHT SECTION: remaining user information.
        Column(
            modifier = Modifier.weight(3f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Display the remaining fields in two columns.
            profileFields.chunked(2).forEach { fieldPair ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Display each field in the current row.
                    fieldPair.forEach { (label, value) ->
                        val fieldIcon = when (label) {
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
                    if (fieldPair.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

