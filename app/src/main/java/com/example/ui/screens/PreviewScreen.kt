package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.data.ReadmePresetsAndCatalog
import com.example.model.DecorativePreset
import com.example.ui.components.DecorativeDividerView
import com.example.ui.components.GitHubStatsPreviewCard
import com.example.ui.components.ProfileHeroBannerCard
import com.example.ui.components.ShieldsBadgePill
import com.example.ui.components.StreakAndQuotePreviewCards
import com.example.ui.components.TechBadgeWrap
import com.example.ui.components.TopLanguagesPreviewCard
import com.example.ui.theme.parseHexColor
import com.example.ui.viewmodel.ReadmeUiState

@Composable
fun PreviewScreen(
    uiState: ReadmeUiState,
    onApplyPreset: (DecorativePreset) -> Unit,
    onCopyMarkdown: () -> Unit,
    onShareMarkdown: () -> Unit,
    modifier: Modifier = Modifier
) {
    val config = uiState.config
    val palette = config.themePalette
    val selectedTech = ReadmePresetsAndCatalog.techCatalog.filter { it.id in config.selectedTechIds }
    val enabledAbout = config.aboutItems.filter { it.enabled && it.value.isNotBlank() }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Quick Decorative Preset Switcher
        item {
            Column(modifier = Modifier.widthIn(max = 680.dp).fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.AutoAwesome,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Quick Decorative Presets",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(ReadmePresetsAndCatalog.decorativePresets, key = { it.id }) { preset ->
                        val selected = uiState.activePresetId == preset.id
                        FilterChip(
                            selected = selected,
                            onClick = { onApplyPreset(preset) },
                            label = { Text(preset.name) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            modifier = Modifier.testTag("preset_chip_${preset.id}")
                        )
                    }
                }
            }
        }

        // Action Bar: Copy & Share
        item {
            Row(
                modifier = Modifier.widthIn(max = 680.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onCopyMarkdown,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("preview_copy_button")
                ) {
                    Icon(Icons.Rounded.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Copy README.md")
                }
                OutlinedButton(
                    onClick = onShareMarkdown,
                    modifier = Modifier
                        .height(48.dp)
                        .testTag("preview_share_button")
                ) {
                    Icon(Icons.Rounded.Share, contentDescription = "Share README.md", modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Share")
                }
            }
        }

        // Hero Banner Preview
        item {
            ProfileHeroBannerCard(
                config = config,
                modifier = Modifier.widthIn(max = 680.dp)
            )
        }

        // Social & Profile View Badges Row
        item {
            Surface(
                modifier = Modifier.widthIn(max = 680.dp).fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (config.widgets.showProfileViews) {
                            ShieldsBadgePill(
                                label = "Profile Views",
                                value = config.githubUsername.ifBlank { "nazimunaeem" },
                                accentHex = palette.primaryHex,
                                badgeStyle = config.badgeStyle
                            )
                        }
                        if (config.location.isNotBlank()) {
                            ShieldsBadgePill(
                                label = config.location,
                                accentHex = palette.surfaceHex,
                                badgeStyle = config.badgeStyle
                            )
                        }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (config.email.isNotBlank()) {
                            ShieldsBadgePill("Email", null, palette.primaryHex, config.badgeStyle)
                        }
                        if (config.linkedinHandle.isNotBlank()) {
                            ShieldsBadgePill("LinkedIn", null, "0A66C2", config.badgeStyle)
                        }
                        if (config.xHandle.isNotBlank()) {
                            ShieldsBadgePill("X", null, "1E293B", config.badgeStyle)
                        }
                    }
                }
            }
        }

        item {
            DecorativeDividerView(
                dividerStyle = config.dividerStyle,
                palette = palette,
                modifier = Modifier.widthIn(max = 680.dp)
            )
        }

        // About Me Decorative Card
        if (enabledAbout.isNotEmpty()) {
            item {
                Surface(
                    modifier = Modifier.widthIn(max = 680.dp).fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "✨ About Me",
                            style = MaterialTheme.typography.titleLarge,
                            color = parseHexColor(palette.primaryHex)
                        )
                        enabledAbout.forEach { item ->
                            Text(
                                text = buildAnnotatedString {
                                    append("${item.emoji}  ")
                                    withStyle(
                                        SpanStyle(
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    ) {
                                        append("${item.label}: ")
                                    }
                                    withStyle(
                                        SpanStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    ) {
                                        append(item.value)
                                    }
                                },
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            item {
                DecorativeDividerView(
                    dividerStyle = config.dividerStyle,
                    palette = palette,
                    modifier = Modifier.widthIn(max = 680.dp)
                )
            }
        }

        // Tech Stack Badges Preview
        if (selectedTech.isNotEmpty()) {
            item {
                Surface(
                    modifier = Modifier.widthIn(max = 680.dp).fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "🛠️ Tech Stack & Tools",
                            style = MaterialTheme.typography.titleLarge,
                            color = parseHexColor(palette.primaryHex)
                        )
                        selectedTech.groupBy { it.category }.forEach { (category, items) ->
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(
                                    text = category,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                TechBadgeWrap(badges = items, badgeStyle = config.badgeStyle)
                            }
                        }
                    }
                }
            }

            item {
                DecorativeDividerView(
                    dividerStyle = config.dividerStyle,
                    palette = palette,
                    modifier = Modifier.widthIn(max = 680.dp)
                )
            }
        }

        // GitHub Analytics Widgets Preview
        if (config.widgets.showGitHubStats) {
            item {
                GitHubStatsPreviewCard(
                    username = config.githubUsername,
                    palette = palette,
                    modifier = Modifier.widthIn(max = 680.dp)
                )
            }
        }

        if (config.widgets.showTopLangs) {
            item {
                TopLanguagesPreviewCard(
                    palette = palette,
                    selectedTechCount = selectedTech.size,
                    modifier = Modifier.widthIn(max = 680.dp)
                )
            }
        }

        if (config.widgets.showStreakStats || config.widgets.showTrophies || config.widgets.showQuoteCard) {
            item {
                Column(modifier = Modifier.widthIn(max = 680.dp).fillMaxWidth()) {
                    StreakAndQuotePreviewCards(
                        username = config.githubUsername,
                        palette = palette,
                        showStreak = config.widgets.showStreakStats,
                        showTrophies = config.widgets.showTrophies,
                        showQuote = config.widgets.showQuoteCard
                    )
                }
            }
        }

        // Decorative Footer Wave Preview
        if (config.widgets.showFooterWave) {
            item {
                val primary = parseHexColor(palette.primaryHex)
                val secondary = parseHexColor(palette.secondaryHex)
                Surface(
                    modifier = Modifier.widthIn(max = 680.dp).fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = parseHexColor(palette.bgHex)
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        val path = Path().apply {
                            moveTo(0f, size.height)
                            lineTo(0f, size.height * 0.45f)
                            cubicTo(
                                size.width * 0.25f, 0f,
                                size.width * 0.55f, size.height * 0.85f,
                                size.width, size.height * 0.3f
                            )
                            lineTo(size.width, size.height)
                            close()
                        }
                        drawPath(
                            path = path,
                            brush = Brush.horizontalGradient(listOf(secondary.copy(alpha = 0.7f), primary.copy(alpha = 0.85f)))
                        )
                    }
                }
            }
        }

        item { Spacer(Modifier.height(16.dp)) }
    }
}
