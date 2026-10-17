package au.edu.jcu.assessment.utilityapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import au.edu.jcu.assessment.utilityapp.screen.SettingsScreen
import au.edu.jcu.assessment.utilityapp.screen.UtilityScreen
import au.edu.jcu.assessment.utilityapp.ui.theme.MobileTech_UtilityAppTheme
import au.edu.jcu.assessment.utilityapp.viewmodel.UserProfileViewModel

// Detect whether the device is in portrait or landscape.
import android.content.res.Configuration
import androidx.compose.ui.platform.LocalConfiguration

// Display navigation vertically in landscape mode.
import androidx.compose.material3.NavigationRail

// Arrange navigation and screen content horizontally.
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.NavigationRailItem

//Background images
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val userViewModel: UserProfileViewModel = viewModel()
            val darkModeEnabled by userViewModel
                .darkModeEnabled
                .collectAsStateWithLifecycle()
            MobileTech_UtilityAppTheme(
                darkTheme = darkModeEnabled
            ) {
                UtilityApp(userViewModel = userViewModel)
            }
        }
    }
}
@Composable
fun UtilityApp(
    userViewModel: UserProfileViewModel
) {
    var selectedTab: String by remember {
        mutableStateOf("Utility")
    }
    val configuration = LocalConfiguration.current // Read device configuration.
    val isLandscape = //check if is in landscape
        configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    // Observe the existing dark mode setting.
    val darkModeEnabled by userViewModel.darkModeEnabled
        .collectAsStateWithLifecycle()

    // Choose the background image based on the theme.
    val backgroundRes = if (darkModeEnabled) {
        R.drawable.dark_mode_background
    } else {
        R.drawable.light_mode_background
    }
    Scaffold(
        containerColor = androidx.compose.ui.graphics.Color.Transparent,
        bottomBar = {
            if (!isLandscape) {
                NavigationBar {
                    NavigationBarItem(
                        icon = {
                            Icon(Icons.Filled.Groups, contentDescription = "Generator")
                        },
                        label = { Text("Generator") },
                        selected = selectedTab == "Utility",
                        onClick = { selectedTab = "Utility" },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                        label = { Text("Settings") },
                        selected = selectedTab == "Settings",
                        onClick = { selectedTab = "Settings" },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        // Arrange the navigation and screen side by side.
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            // Display vertical navigation only in landscape.
            if (isLandscape) {
                NavigationRail {
                    NavigationRailItem(
                        selected = selectedTab == "Utility",
                        onClick = { selectedTab = "Utility" },
                        icon = {
                            Icon(
                                Icons.Filled.Groups,
                                contentDescription = "Generator"
                            )
                        },
                        label = { Text("Generator") }
                    )

                    NavigationRailItem(
                        selected = selectedTab == "Settings",
                        onClick = { selectedTab = "Settings" },
                        icon = {
                            Icon(
                                Icons.Filled.Settings,
                                contentDescription = "Settings"
                            )
                        },
                        label = { Text("Settings") }
                    )
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                // Background image fills the available screen area.
                Image(
                    painter = painterResource(id = backgroundRes),
                    contentDescription = null, // Decorative image.
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Existing screen content appears above the background.
                when (selectedTab) {
                    "Utility" -> UtilityScreen(userViewModel = userViewModel)
                    "Settings" -> SettingsScreen(userViewModel = userViewModel)
                }
            }
        }
    }
}

