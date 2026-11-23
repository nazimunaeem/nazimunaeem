package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.model.BadgeStyle
import com.example.model.BannerStyle
import com.example.model.DecorativeWidgetsConfig
import com.example.model.DividerStyle
import com.example.model.ReadmeThemePalette
import com.example.ui.components.DecorativeDividerView
import com.example.ui.components.ShieldsBadgePill
import com.example.ui.theme.parseHexColor
import com.example.ui.viewmodel.ReadmeUiState

@Composable
fun DecorativesScreen(
    uiState: ReadmeUiState,
    onSelectBannerStyle: (BannerStyle) -> Unit,
    onSelectThemePalette: (ReadmeThemePalette) -> Unit,
    onSelectDividerStyle: (DividerStyle) -> Unit,
    onSelectBadgeStyle: (BadgeStyle) -> Unit,
    onUpdateWidgets: ((DecorativeWidgetsConfig) -> DecorativeWidgetsConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    val config = uiState.config

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Color Theme Palette
        item {
            SectionCard(title = "Color Palette & Stats Theme") {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(ReadmeThemePalette.entries) { palette ->
                        val selected = config.themePalette == palette
                        val primary = parseHexColor(palette.primaryHex)
                        val secondary = parseHexColor(palette.secondaryHex)
                        val bg = parseHexColor(palette.bgHex)

                        Surface(
                            modifier = Modifier
                                .width(136.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .clickable { onSelectThemePalette(palette) }
                                .testTag("theme_palette_${palette.name}"),
                            shape = RoundedCornerShape(14.dp),
                            color = bg,
                            border = BorderStroke(
                                width = if (selected) 2.dp else 1.dp,
                                color = if (selected) primary else MaterialTheme.colorScheme.outline
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                        Box(Modifier.size(12.dp).clip(CircleShape).background(primary))
                                        Box(Modifier.size(12.dp).clip(CircleShape).background(secondary))
                                        Box(Modifier.size(12.dp).clip(CircleShape).background(parseHexColor(palette.tertiaryHex)))
                                    }
                                    if (selected) {
                                        Icon(
                                            imageVector = Icons.Rounded.CheckCircle,
                                            contentDescription = "Selected theme",
                                            tint = primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = palette.displayName,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = parseHexColor(palette.textHex)
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. Header Banner Style
        item {
            SectionCard(title = "Header Banner Style") {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    BannerStyle.entries.forEach { style ->
                        val selected = config.bannerStyle == style
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onSelectBannerStyle(style) }
                                .testTag("banner_style_${style.name}"),
                            shape = RoundedCornerShape(12.dp),
                            color = if (selected) {
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            },
                            border = BorderStroke(
                                1.dp,
                                if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(style.displayName, style = MaterialTheme.typography.titleSmall)
                                    Text(
                                        style.subtitle,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                if (selected) {
                                    Icon(
                                        Icons.Rounded.CheckCircle,
                                        contentDescription = "Selected banner",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 3. Divider Style
        item {
            SectionCard(title = "Decorative Section Dividers") {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    DividerStyle.entries.forEach { style ->
                        val selected = config.dividerStyle == style
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onSelectDividerStyle(style) }
                                .testTag("divider_style_${style.name}"),
                            shape = RoundedCornerShape(12.dp),
                            color = if (selected) {
                                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.35f)
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                            },
                            border = BorderStroke(
                                1.dp,
                                if (selected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outline
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(style.displayName, style = MaterialTheme.typography.titleSmall)
                                    if (selected) {
                                        Icon(
                                            Icons.Rounded.CheckCircle,
                                            contentDescription = "Selected divider",
                                            tint = MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                                DecorativeDividerView(
                                    dividerStyle = style,
                                    palette = config.themePalette
                                )
                            }
                        }
                    }
                }
            }
        }

        // 4. Badge Style
        item {
            SectionCard(title = "Shields.io Badge Style") {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(BadgeStyle.entries) { style ->
                            FilterChip(
                                selected = config.badgeStyle == style,
                                onClick = { onSelectBadgeStyle(style) },
                                label = { Text(style.displayName) },
                                modifier = Modifier.testTag("badge_style_${style.name}")
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ShieldsBadgePill("Kotlin", null, "7F52FF", config.badgeStyle)
                        ShieldsBadgePill("Compose", null, "4285F4", config.badgeStyle)
                        ShieldsBadgePill("Views", "Live", config.themePalette.primaryHex, config.badgeStyle)
                    }
                }
            }
        }

        // 5. Decorative Widgets Toggles
        item {
            SectionCard(title = "Decorative Widgets & Cards") {
                val widgets = config.widgets
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    WidgetToggleRow(
                        title = "Animated Typing Banner",
                        subtitle = "Typewriter effect cycling your highlights",
                        checked = widgets.showTypingBanner,
                        tag = "toggle_typing"
                    ) { checked -> onUpdateWidgets { it.copy(showTypingBanner = checked) } }

                    WidgetToggleRow(
                        title = "Profile Views Counter",
                        subtitle = "Live visitor counter badge at top",
                        checked = widgets.showProfileViews,
                        tag = "toggle_views"
                    ) { checked -> onUpdateWidgets { it.copy(showProfileViews = checked) } }

                    WidgetToggleRow(
                        title = "GitHub Stats Card",
                        subtitle = "Stars, commits, PRs & grade ring",
                        checked = widgets.showGitHubStats,
                        tag = "toggle_stats"
                    ) { checked -> onUpdateWidgets { it.copy(showGitHubStats = checked) } }

                    WidgetToggleRow(
                        title = "Top Languages Card",
                        subtitle = "Compact language breakdown chart",
                        checked = widgets.showTopLangs,
                        tag = "toggle_langs"
                    ) { checked -> onUpdateWidgets { it.copy(showTopLangs = checked) } }

                    WidgetToggleRow(
                        title = "Contribution Streak Card",
                        subtitle = "Daily commit streak & longest run",
                        checked = widgets.showStreakStats,
                        tag = "toggle_streak"
                    ) { checked -> onUpdateWidgets { it.copy(showStreakStats = checked) } }

                    WidgetToggleRow(
                        title = "GitHub Trophies Row",
                        subtitle = "Achievement badges showcase",
                        checked = widgets.showTrophies,
                        tag = "toggle_trophies"
                    ) { checked -> onUpdateWidgets { it.copy(showTrophies = checked) } }

                    WidgetToggleRow(
                        title = "Developer Quote Card",
                        subtitle = "Rotating programming quote banner",
                        checked = widgets.showQuoteCard,
                        tag = "toggle_quote"
                    ) { checked -> onUpdateWidgets { it.copy(showQuoteCard = checked) } }

                    WidgetToggleRow(
                        title = "Gradient Footer Wave",
                        subtitle = "Matching SVG wave footer",
                        checked = widgets.showFooterWave,
                        tag = "toggle_footer"
                    ) { checked -> onUpdateWidgets { it.copy(showFooterWave = checked) } }
                }
            }
        }

        item { Spacer(Modifier.height(16.dp)) }
    }
}

@Composable
private fun SectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier.widthIn(max = 680.dp).fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            content()
        }
    }
}

@Composable
private fun WidgetToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    tag: String,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.width(12.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.testTag(tag)
        )
    }
}
