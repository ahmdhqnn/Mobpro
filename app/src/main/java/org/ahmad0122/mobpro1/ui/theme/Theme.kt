@file:Suppress("DEPRECATION")

package org.ahmad0122.mobpro1.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val GreenDarkColorScheme = darkColorScheme(
    primary = LightGreen,
    secondary = Green,
    tertiary = DarkGreen,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

private val GreenLightColorScheme = lightColorScheme(
    primary = Green,
    secondary = LightGreen,
    tertiary = DarkGreen,
    background = Color.White,
    surface = Color(0xFFF5F5F5),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val BlueDarkColorScheme = darkColorScheme(
    primary = LightBlue,
    secondary = Blue,
    tertiary = DarkBlue,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

private val BlueLightColorScheme = lightColorScheme(
    primary = Blue,
    secondary = LightBlue,
    tertiary = DarkBlue,
    background = Color.White,
    surface = Color(0xFFF5F5F5),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val PurpleDarkColorScheme = darkColorScheme(
    primary = LightPurple,
    secondary = Purple,
    tertiary = DarkPurple,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

private val PurpleLightColorScheme = lightColorScheme(
    primary = Purple,
    secondary = LightPurple,
    tertiary = DarkPurple,
    background = Color.White,
    surface = Color(0xFFF5F5F5),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val OrangeDarkColorScheme = darkColorScheme(
    primary = LightOrange,
    secondary = Orange,
    tertiary = DarkOrange,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

private val OrangeLightColorScheme = lightColorScheme(
    primary = Orange,
    secondary = LightOrange,
    tertiary = DarkOrange,
    background = Color.White,
    surface = Color(0xFFF5F5F5),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val RedDarkColorScheme = darkColorScheme(
    primary = LightRed,
    secondary = Red,
    tertiary = DarkRed,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

private val RedLightColorScheme = lightColorScheme(
    primary = Red,
    secondary = LightRed,
    tertiary = DarkRed,
    background = Color.White,
    surface = Color(0xFFF5F5F5),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun TaskListTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    themeColor: Int = 0,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> {
            when (themeColor) {
                0 -> if (darkTheme) GreenDarkColorScheme else GreenLightColorScheme // Default green
                1 -> if (darkTheme) BlueDarkColorScheme else BlueLightColorScheme
                2 -> if (darkTheme) PurpleDarkColorScheme else PurpleLightColorScheme
                3 -> if (darkTheme) OrangeDarkColorScheme else OrangeLightColorScheme
                4 -> if (darkTheme) RedDarkColorScheme else RedLightColorScheme
                else -> if (darkTheme) GreenDarkColorScheme else GreenLightColorScheme
            }
        }
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}