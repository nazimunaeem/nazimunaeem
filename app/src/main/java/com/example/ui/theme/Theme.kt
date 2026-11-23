package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val StudioDarkColorScheme = darkColorScheme(
    primary = ElectricCyan,
    onPrimary = Color(0xFF04263B),
    primaryContainer = DeepCyanContainer,
    onPrimaryContainer = Color(0xFFBAE6FD),
    secondary = AuroraViolet,
    onSecondary = Color(0xFF231147),
    secondaryContainer = DeepVioletContainer,
    onSecondaryContainer = Color(0xFFEDE9FE),
    tertiary = MintEmerald,
    onTertiary = Color(0xFF022C22),
    tertiaryContainer = DeepEmeraldContainer,
    onTertiaryContainer = Color(0xFFA7F3D0),
    background = ObsidianBackground,
    onBackground = CrispTextPrimary,
    surface = CharcoalSurface,
    onSurface = CrispTextPrimary,
    surfaceVariant = SlateSurfaceVariant,
    onSurfaceVariant = CrispTextSecondary,
    surfaceContainer = CharcoalSurface,
    surfaceContainerHigh = SlateSurfaceVariant,
    surfaceContainerHighest = ElevatedCard,
    outline = SubtleOutline,
    error = CoralRose,
    onError = Color(0xFF4C0519)
)

val ReadmeCraftShapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun ReadmeCraftTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = StudioDarkColorScheme,
        typography = ReadmeCraftTypography,
        shapes = ReadmeCraftShapes,
        content = content
    )
}
