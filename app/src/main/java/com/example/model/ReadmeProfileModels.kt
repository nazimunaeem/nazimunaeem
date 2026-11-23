package com.example.model

import kotlinx.serialization.Serializable

@Serializable
enum class BannerStyle(
    val displayName: String,
    val subtitle: String,
    val capsuleType: String
) {
    AURORA_WAVE(
        displayName = "Aurora Wave",
        subtitle = "Flowing cosmic wave header with glowing gradient",
        capsuleType = "waving"
    ),
    TYPING_TERMINAL(
        displayName = "Cyber Terminal",
        subtitle = "Animated typewriter command banner with cursor",
        capsuleType = "rect"
    ),
    CAPSULE_GRADIENT(
        displayName = "Venom Ribbon",
        subtitle = "Sharp angular cyber ribbon with bold title",
        capsuleType = "venom"
    ),
    MINIMAL_CYBER(
        displayName = "Soft Cylinder",
        subtitle = "Clean rounded pill banner with subtle glow",
        capsuleType = "cylinder"
    )
}

@Serializable
enum class ReadmeThemePalette(
    val displayName: String,
    val statsSlug: String,
    val bgHex: String,
    val surfaceHex: String,
    val primaryHex: String,
    val secondaryHex: String,
    val tertiaryHex: String,
    val textHex: String
) {
    TOKYO_NIGHT(
        displayName = "Tokyo Night",
        statsSlug = "tokyonight",
        bgHex = "1A1B27",
        surfaceHex = "24283B",
        primaryHex = "70A5FD",
        secondaryHex = "BF91F3",
        tertiaryHex = "38BDAE",
        textHex = "C0CAF5"
    ),
    DRACULA(
        displayName = "Dracula",
        statsSlug = "dracula",
        bgHex = "282A36",
        surfaceHex = "343746",
        primaryHex = "FF79C6",
        secondaryHex = "BD93F9",
        tertiaryHex = "50FA7B",
        textHex = "F8F8F2"
    ),
    RADICAL(
        displayName = "Radical",
        statsSlug = "radical",
        bgHex = "141321",
        surfaceHex = "1E1C31",
        primaryHex = "FE428E",
        secondaryHex = "F8D847",
        tertiaryHex = "A9FEF7",
        textHex = "E4E2E2"
    ),
    CATPPUCCIN_MOCHA(
        displayName = "Catppuccin",
        statsSlug = "catppuccin_mocha",
        bgHex = "1E1E2E",
        surfaceHex = "313244",
        primaryHex = "CBA6F7",
        secondaryHex = "89B4FA",
        tertiaryHex = "A6E3A1",
        textHex = "CDD6F4"
    ),
    GITHUB_DARK(
        displayName = "GitHub Dark",
        statsSlug = "github_dark",
        bgHex = "0D1117",
        surfaceHex = "161B22",
        primaryHex = "58A6FF",
        secondaryHex = "BC8CFF",
        tertiaryHex = "3FB950",
        textHex = "E6EDF3"
    ),
    EMERALD_MATRIX(
        displayName = "Emerald Matrix",
        statsSlug = "chartreuse-dark",
        bgHex = "0B1410",
        surfaceHex = "12211A",
        primaryHex = "34D399",
        secondaryHex = "22D3EE",
        tertiaryHex = "A3E635",
        textHex = "ECFDF5"
    )
}

@Serializable
enum class DividerStyle(
    val displayName: String,
    val description: String
) {
    RAINBOW_WAVE("Gradient Wave", "Smooth full-width gradient divider"),
    CYAN_PULSE("Neon Pulse", "Glowing dual-tone center pulse line"),
    PIXEL_STARS("Starlight", "Centered diamond & star ornaments"),
    CODE_BRACKETS("Code Brackets", "Developer </> centered separator"),
    MINIMAL_DOTS("Minimal Dots", "Clean spaced dot cadence")
}

@Serializable
enum class BadgeStyle(
    val displayName: String,
    val shieldsParam: String
) {
    FOR_THE_BADGE("For The Badge", "for-the-badge"),
    FLAT_SQUARE("Flat Square", "flat-square"),
    PLASTIC("Rounded Plastic", "plastic"),
    SOCIAL("Compact Flat", "flat")
}

@Serializable
data class AboutItem(
    val id: String,
    val emoji: String,
    val label: String,
    val value: String,
    val enabled: Boolean = true
)

@Serializable
data class TechBadgeItem(
    val id: String,
    val name: String,
    val category: String,
    val badgeColorHex: String,
    val logoSlug: String,
    val logoColorHex: String = "FFFFFF",
    val selected: Boolean = false
)

@Serializable
data class DecorativeWidgetsConfig(
    val showTypingBanner: Boolean = true,
    val showProfileViews: Boolean = true,
    val showGitHubStats: Boolean = true,
    val showTopLangs: Boolean = true,
    val showStreakStats: Boolean = true,
    val showTrophies: Boolean = false,
    val showQuoteCard: Boolean = true,
    val showFooterWave: Boolean = true
)

@Serializable
data class ProfileReadmeConfig(
    val githubUsername: String,
    val displayName: String,
    val roleHeadline: String,
    val typingPhrases: List<String>,
    val location: String,
    val email: String,
    val websiteUrl: String,
    val linkedinHandle: String,
    val xHandle: String,
    val bannerStyle: BannerStyle,
    val themePalette: ReadmeThemePalette,
    val dividerStyle: DividerStyle,
    val badgeStyle: BadgeStyle,
    val aboutItems: List<AboutItem>,
    val selectedTechIds: List<String>,
    val widgets: DecorativeWidgetsConfig
)

data class DecorativePreset(
    val id: String,
    val name: String,
    val tagline: String,
    val bannerStyle: BannerStyle,
    val themePalette: ReadmeThemePalette,
    val dividerStyle: DividerStyle,
    val badgeStyle: BadgeStyle
)
