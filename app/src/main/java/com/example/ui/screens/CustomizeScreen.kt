package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.ReadmePresetsAndCatalog
import com.example.ui.viewmodel.ReadmeUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomizeScreen(
    uiState: ReadmeUiState,
    onUpdateIdentity: (
        githubUsername: String?,
        displayName: String?,
        roleHeadline: String?,
        location: String?,
        email: String?,
        websiteUrl: String?,
        linkedinHandle: String?,
        xHandle: String?
    ) -> Unit,
    onUpdateTypingPhrase: (Int, String) -> Unit,
    onUpdateAboutItem: (id: String, value: String?, enabled: Boolean?) -> Unit,
    onToggleTechBadge: (String) -> Unit,
    onTechSearchChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val config = uiState.config
    val query = uiState.techSearchQuery.trim()
    val filteredTech = remember(query) {
        if (query.isEmpty()) {
            ReadmePresetsAndCatalog.techCatalog
        } else {
            ReadmePresetsAndCatalog.techCatalog.filter {
                it.name.contains(query, ignoreCase = true) ||
                    it.category.contains(query, ignoreCase = true)
            }
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Identity & Header
        item {
            EditorGroupCard(title = "Profile Identity") {
                OutlinedTextField(
                    value = config.githubUsername,
                    onValueChange = { onUpdateIdentity(it, null, null, null, null, null, null, null) },
                    label = { Text("GitHub Username") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("input_github_username")
                )
                OutlinedTextField(
                    value = config.displayName,
                    onValueChange = { onUpdateIdentity(null, it, null, null, null, null, null, null) },
                    label = { Text("Display Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("input_display_name")
                )
                OutlinedTextField(
                    value = config.roleHeadline,
                    onValueChange = { onUpdateIdentity(null, null, it, null, null, null, null, null) },
                    label = { Text("Header Tagline") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("input_role_headline")
                )
                OutlinedTextField(
                    value = config.location,
                    onValueChange = { onUpdateIdentity(null, null, null, it, null, null, null, null) },
                    label = { Text("Location Badge") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("input_location")
                )
            }
        }

        // 2. Typing SVG Phrases
        item {
            EditorGroupCard(title = "Animated Typing Phrases") {
                (0..2).forEach { idx ->
                    val phrase = config.typingPhrases.getOrElse(idx) { "" }
                    OutlinedTextField(
                        value = phrase,
                        onValueChange = { onUpdateTypingPhrase(idx, it) },
                        label = { Text("Line ${idx + 1}") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("input_typing_$idx")
                    )
                }
            }
        }

        // 3. About Me Prompts (from GitHub profile starter template)
        item {
            EditorGroupCard(title = "About Me Sections") {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    config.aboutItems.forEach { item ->
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${item.emoji}  ${item.label}",
                                    style = MaterialTheme.typography.titleSmall,
                                    modifier = Modifier.weight(1f)
                                )
                                Switch(
                                    checked = item.enabled,
                                    onCheckedChange = { onUpdateAboutItem(item.id, null, it) },
                                    modifier = Modifier.testTag("switch_about_${item.id}")
                                )
                            }
                            if (item.enabled) {
                                OutlinedTextField(
                                    value = item.value,
                                    onValueChange = { onUpdateAboutItem(item.id, it, null) },
                                    placeholder = { Text("Enter details...") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("input_about_${item.id}")
                                )
                            }
                        }
                    }
                }
            }
        }

        // 4. Socials & Contact Links
        item {
            EditorGroupCard(title = "Socials & Contact Badges") {
                OutlinedTextField(
                    value = config.email,
                    onValueChange = { onUpdateIdentity(null, null, null, null, it, null, null, null) },
                    label = { Text("Email Address") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("input_email")
                )
                OutlinedTextField(
                    value = config.linkedinHandle,
                    onValueChange = { onUpdateIdentity(null, null, null, null, null, null, it, null) },
                    label = { Text("LinkedIn Username") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("input_linkedin")
                )
                OutlinedTextField(
                    value = config.xHandle,
                    onValueChange = { onUpdateIdentity(null, null, null, null, null, null, null, it) },
                    label = { Text("X (Twitter) Handle") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("input_x")
                )
            }
        }

        // 5. Tech Stack Selector
        item {
            EditorGroupCard(title = "Tech Stack Badges (${config.selectedTechIds.size} selected)") {
                OutlinedTextField(
                    value = uiState.techSearchQuery,
                    onValueChange = onTechSearchChange,
                    placeholder = { Text("Search skills, frameworks, tools...") },
                    leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) },
                    trailingIcon = {
                        if (uiState.techSearchQuery.isNotEmpty()) {
                            IconButton(onClick = { onTechSearchChange("") }) {
                                Icon(Icons.Rounded.Close, contentDescription = "Clear search")
                            }
                        }
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("input_tech_search")
                )

                val grouped = filteredTech.groupBy { it.category }
                if (grouped.isEmpty()) {
                    Text(
                        text = "No matching tech badges found.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    grouped.forEach { (category, items) ->
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = category,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items.forEach { tech ->
                                val selected = tech.id in config.selectedTechIds
                                FilterChip(
                                    selected = selected,
                                    onClick = { onToggleTechBadge(tech.id) },
                                    label = { Text(tech.name) },
                                    leadingIcon = if (selected) {
                                        {
                                            Icon(
                                                imageVector = Icons.Rounded.Check,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    } else null,
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    modifier = Modifier.testTag("tech_chip_${tech.id}")
                                )
                            }
                        }
                    }
                }
            }
        }

        item { Spacer(Modifier.height(16.dp)) }
    }
}

@Composable
private fun EditorGroupCard(
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
            verticalArrangement = Arrangement.spacedBy(10.dp)
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
