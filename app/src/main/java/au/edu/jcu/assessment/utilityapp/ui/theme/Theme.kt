package au.edu.jcu.assessment.utilityapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White

private val DarkColorScheme = darkColorScheme(
    primary = CyanBlue,
    onPrimary = DarkNavy,
    secondary = TurquoiseGreen,
    tertiary = DeepPurple,

    primaryContainer = Color(0xFF174B75),
    onPrimaryContainer = White,
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = White,
    secondary = CyanBlue,
    tertiary = DeepPurple,

    // Main application background
    background = Color(0xFFF8FAFF),
    surface = White,

    // Soft blue backgrounds for cards and sections
    surfaceContainerLow = LightBlue,
    surfaceContainer = Color(0xFFE3ECFA),

    // Selected navigation elements and other accents
    primaryContainer = Color(0xFFD8E6FF),
    onPrimaryContainer = DarkNavy,

    // Main text colour
    onBackground = DarkNavy,
    onSurface = DarkNavy
)



@Composable
fun MobileTech_UtilityAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
