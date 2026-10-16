package au.edu.jcu.assessment.utilityapp.screen

import android.R.attr.checked
import android.R.attr.top
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import au.edu.jcu.assessment.utilityapp.viewmodel.UserProfileViewModel
import androidx.compose.foundation.layout.FlowRow
import android.content.res.Configuration
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun SettingsScreen(
    userViewModel: UserProfileViewModel
) {
    // Observe the nationality stored in the ViewModel
    val selectedNationality by userViewModel
        .selectedNationality
        .collectAsStateWithLifecycle()

    // Country names and their API codes
    val nationalities = listOf(
        "Random" to null,
        "Australia" to "au",
        "Canada" to "ca",
        "France" to "fr",
        "Mexico" to "mx",
        "United States" to "us",
        "United Kingdom" to "gb",
    )

    // Find the label for the selected nationality
    val selectedLabel = nationalities
        .firstOrNull { it.second == selectedNationality }
        ?.first ?: "Random"


    //Observe the visible fields in the ViewModel
    val visibleFields by userViewModel
        .visibleFields
        .collectAsStateWithLifecycle()

    val profileFields = listOf(
        "picture" to "Profile picture",
        "name" to "Name",
        "email" to "Email",
        "phone" to "Phone",
        "nationality" to "Nationality",
        "address" to "Address",
        "birthday" to "Date of birth",
        "username" to "Username",
        "password" to "Password"
    )
    val darkModeEnabled by userViewModel
        .darkModeEnabled
        .collectAsStateWithLifecycle()

// Detect the device orientation to adapt the Settings layout.
    val configuration = LocalConfiguration.current
    val isLandscape =
        configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

// Use three checkbox columns in landscape and two in portrait.
    val checkboxColumns = if (isLandscape) 3 else 2

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            // Use less padding in portrait to give controls more room.
            .padding(
                horizontal = if (isLandscape) 24.dp else 16.dp,
                vertical = if (isLandscape) 24.dp else 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
// Display Dark mode beside the title in landscape.
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium
            )

            if (isLandscape) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Dark mode",
                        modifier = Modifier.padding(end = 16.dp)
                    )

                    Switch(
                        checked = darkModeEnabled,
                        onCheckedChange = { enabled ->
                            userViewModel.updateDarkMode(enabled)
                        }
                    )
                }
            }
        }

// Arrange nationality settings horizontally in landscape.
        if (isLandscape) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column() {
                    Text(
                        text = "Nationality",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                // Reuse the nationality dropdown in landscape mode.
                NationalityDropdown(
                    selectedLabel = selectedLabel,
                    nationalities = nationalities,
                    onNationalitySelected = { code ->
                        userViewModel.updateNationality(code)
                    },
                    modifier = Modifier.fillMaxWidth(0.50f)
                )
            }
        }
        else {
            // Display nationality settings vertically in portrait mode.
            Text(
                text = "Nationality",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Reuse the same dropdown component.
            NationalityDropdown(
                selectedLabel = selectedLabel,
                nationalities = nationalities,
                onNationalitySelected = { code ->
                    userViewModel.updateNationality(code)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
            Text(
                text = "Visible profile fields",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(
                    top = if (!isLandscape) 16.dp else 0.dp
                )
            )


            //Field options in responsive two-column layout
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                maxItemsInEachRow = checkboxColumns
            ) {
                profileFields.forEach { (field, label) ->

                    // Each option takes half of the available row width.
                    Row(
                        modifier = Modifier.fillMaxWidth(1f / checkboxColumns),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = field in visibleFields,
                            onCheckedChange = { isChecked ->
                                userViewModel.updateFieldVisibility(
                                    field,
                                    isChecked
                                )
                            }
                        )

                        Text(text = label)
                    }
                }
            }
        if (!isLandscape) {
            Text(
                text = "Appearance",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Dark mode",
                    modifier = Modifier.padding(end = 16.dp
                    )
                )

                Switch(
                    checked = darkModeEnabled,
                    onCheckedChange = { enabled ->
                        userViewModel.updateDarkMode(enabled)
                    }
                )
            }
        }
    }
}
@Composable
private fun NationalityDropdown(
    selectedLabel: String,
    nationalities: List<Pair<String, String?>>,
    onNationalitySelected: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    // Controls whether the dropdown menu is open.
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedLabel,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select nationality") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Open nationality menu"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Make the entire field clickable.
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            nationalities.forEach { (country, code) ->
                DropdownMenuItem(
                    text = { Text(country) },
                    onClick = {
                        onNationalitySelected(code)
                        expanded = false
                    }
                )
            }
        }
    }
}