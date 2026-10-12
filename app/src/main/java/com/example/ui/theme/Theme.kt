package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
  primary = KidsPrimary,
  onPrimary = Color.White,
  primaryContainer = KidsPrimaryContainer,
  onPrimaryContainer = KidsPrimary,
  secondary = KidsSecondary,
  onSecondary = Color.White,
  secondaryContainer = KidsSecondaryContainer,
  onSecondaryContainer = Color(0xFF78350F),
  tertiary = KidsTertiary,
  onTertiary = Color.White,
  tertiaryContainer = KidsTertiaryContainer,
  onTertiaryContainer = Color(0xFF065F46),
  background = KidsBackground,
  onBackground = TextDark,
  surface = KidsSurface,
  onSurface = TextDark,
  surfaceVariant = Color(0xFFF1F5F9),
  onSurfaceVariant = TextMuted,
  outline = CardBorder
)

private val DarkColorScheme = darkColorScheme(
  primary = Color(0xFF818CF8),
  onPrimary = Color(0xFF1E1B4B),
  primaryContainer = Color(0xFF312E81),
  onPrimaryContainer = Color(0xFFE0E7FF),
  secondary = Color(0xFFFBBF24),
  onSecondary = Color(0xFF451A03),
  secondaryContainer = Color(0xFF78350F),
  onSecondaryContainer = Color(0xFFFEF3C7),
  tertiary = Color(0xFF34D399),
  onTertiary = Color(0xFF064E3B),
  tertiaryContainer = Color(0xFF065F46),
  onTertiaryContainer = Color(0xFFD1FAE5),
  background = DarkBackground,
  onBackground = DarkText,
  surface = DarkSurface,
  onSurface = DarkText,
  surfaceVariant = Color(0xFF334155),
  onSurfaceVariant = DarkTextMuted,
  outline = Color(0xFF475569)
)

val KidsShapes = Shapes(
  small = RoundedCornerShape(12.dp),
  medium = RoundedCornerShape(20.dp),
  large = RoundedCornerShape(28.dp),
  extraLarge = RoundedCornerShape(36.dp)
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep consistent playful branding across versions
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
    shapes = KidsShapes,
    typography = Typography,
    content = content
  )
}
