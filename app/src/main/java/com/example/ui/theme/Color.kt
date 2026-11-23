package com.example.ui.theme

import androidx.compose.ui.graphics.Color

val ObsidianBackground = Color(0xFF0B0F17)
val CharcoalSurface = Color(0xFF131926)
val SlateSurfaceVariant = Color(0xFF1C2434)
val ElevatedCard = Color(0xFF1E293B)

val ElectricCyan = Color(0xFF38BDF8)
val DeepCyanContainer = Color(0xFF0C4A6E)
val AuroraViolet = Color(0xFFA78BFA)
val DeepVioletContainer = Color(0xFF3B2475)
val MintEmerald = Color(0xFF34D399)
val DeepEmeraldContainer = Color(0xFF064E3B)
val CoralRose = Color(0xFFFB7185)

val CrispTextPrimary = Color(0xFFF1F5F9)
val CrispTextSecondary = Color(0xFF94A3B8)
val SubtleOutline = Color(0xFF2D3748)

fun parseHexColor(hex: String, fallback: Color = ElectricCyan): Color {
    val clean = hex.removePrefix("#").trim()
    return runCatching {
        val value = clean.toLong(16)
        when (clean.length) {
            6 -> Color(0xFF000000L or value)
            8 -> Color(value)
            else -> fallback
        }
    }.getOrDefault(fallback)
}
