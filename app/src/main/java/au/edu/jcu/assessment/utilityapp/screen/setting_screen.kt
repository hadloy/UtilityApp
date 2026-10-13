package au.edu.jcu.assessment.utilityapp.screen

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

@Composable
fun SettingsScreen(
    userViewModel: UserProfileViewModel
) {
    // Observe the nationality stored in the ViewModel
    val selectedNationality by userViewModel
        .selectedNationality
        .collectAsStateWithLifecycle()

    // Controls whether the dropdown is open
    var expanded by remember { mutableStateOf(false) }

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
        "gender" to "Gender",
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
    Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Nationality",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Choose the nationality of generated profiles.",
            style = MaterialTheme.typography.bodyMedium
        )

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
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

            // Make the whole field clickable
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
                            userViewModel.updateNationality(code)
                            expanded = false
                        }
                    )
                }
            }
        }
        Text(
            text = "Visible profile fields",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Choose which information to display.",
            style = MaterialTheme.typography.bodyMedium
        )

        profileFields.forEach { (field, label) ->
            Row(
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

        Text(
            text = "Appearance",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Dark mode")

            Switch(
                checked = darkModeEnabled,
                onCheckedChange = { enabled ->
                    userViewModel.updateDarkMode(enabled)
                }
            )
        }
    }
}