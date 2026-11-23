package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.MarkdownGenerator
import com.example.data.ProfileRepository
import com.example.data.ReadmePresetsAndCatalog
import com.example.model.BadgeStyle
import com.example.model.BannerStyle
import com.example.model.DecorativePreset
import com.example.model.DecorativeWidgetsConfig
import com.example.model.DividerStyle
import com.example.model.ProfileReadmeConfig
import com.example.model.ReadmeThemePalette
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class StudioTab(val route: String) {
    PREVIEW("preview"),
    CUSTOMIZE("customize"),
    DECORATIVES("decoratives"),
    MARKDOWN("markdown")
}

data class ReadmeUiState(
    val config: ProfileReadmeConfig = ReadmePresetsAndCatalog.defaultProfileConfig(),
    val generatedMarkdown: String = MarkdownGenerator.generate(ReadmePresetsAndCatalog.defaultProfileConfig()),
    val techSearchQuery: String = "",
    val activePresetId: String? = "aurora_night",
    val isLoaded: Boolean = false
)

class ReadmeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProfileRepository(application.applicationContext)

    private val _uiState = MutableStateFlow(ReadmeUiState())
    val uiState: StateFlow<ReadmeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.profileConfigFlow.collect { storedConfig ->
                _uiState.update { current ->
                    current.copy(
                        config = storedConfig,
                        generatedMarkdown = MarkdownGenerator.generate(storedConfig),
                        activePresetId = matchPreset(storedConfig),
                        isLoaded = true
                    )
                }
            }
        }
    }

    private fun updateConfig(transform: (ProfileReadmeConfig) -> ProfileReadmeConfig) {
        val updated = transform(_uiState.value.config)
        _uiState.update {
            it.copy(
                config = updated,
                generatedMarkdown = MarkdownGenerator.generate(updated),
                activePresetId = matchPreset(updated)
            )
        }
        viewModelScope.launch {
            repository.saveConfig(updated)
        }
    }

    private fun matchPreset(config: ProfileReadmeConfig): String? =
        ReadmePresetsAndCatalog.decorativePresets.firstOrNull { preset ->
            preset.bannerStyle == config.bannerStyle &&
                preset.themePalette == config.themePalette &&
                preset.dividerStyle == config.dividerStyle &&
                preset.badgeStyle == config.badgeStyle
        }?.id

    fun applyPreset(preset: DecorativePreset) {
        updateConfig {
            it.copy(
                bannerStyle = preset.bannerStyle,
                themePalette = preset.themePalette,
                dividerStyle = preset.dividerStyle,
                badgeStyle = preset.badgeStyle
            )
        }
    }

    fun updateIdentity(
        githubUsername: String? = null,
        displayName: String? = null,
        roleHeadline: String? = null,
        location: String? = null,
        email: String? = null,
        websiteUrl: String? = null,
        linkedinHandle: String? = null,
        xHandle: String? = null
    ) {
        updateConfig { current ->
            current.copy(
                githubUsername = githubUsername ?: current.githubUsername,
                displayName = displayName ?: current.displayName,
                roleHeadline = roleHeadline ?: current.roleHeadline,
                location = location ?: current.location,
                email = email ?: current.email,
                websiteUrl = websiteUrl ?: current.websiteUrl,
                linkedinHandle = linkedinHandle ?: current.linkedinHandle,
                xHandle = xHandle ?: current.xHandle
            )
        }
    }

    fun updateTypingPhrase(index: Int, value: String) {
        updateConfig { current ->
            val mutable = current.typingPhrases.toMutableList()
            while (mutable.size <= index) mutable.add("")
            mutable[index] = value
            current.copy(typingPhrases = mutable)
        }
    }

    fun updateAboutItem(id: String, value: String? = null, enabled: Boolean? = null) {
        updateConfig { current ->
            val updatedItems = current.aboutItems.map { item ->
                if (item.id == id) {
                    item.copy(
                        value = value ?: item.value,
                        enabled = enabled ?: item.enabled
                    )
                } else {
                    item
                }
            }
            current.copy(aboutItems = updatedItems)
        }
    }

    fun toggleTechBadge(techId: String) {
        updateConfig { current ->
            val currentList = current.selectedTechIds
            val updatedList = if (techId in currentList) {
                currentList - techId
            } else {
                currentList + techId
            }
            current.copy(selectedTechIds = updatedList)
        }
    }

    fun updateTechSearchQuery(query: String) {
        _uiState.update { it.copy(techSearchQuery = query) }
    }

    fun setBannerStyle(style: BannerStyle) {
        updateConfig { it.copy(bannerStyle = style) }
    }

    fun setThemePalette(palette: ReadmeThemePalette) {
        updateConfig { it.copy(themePalette = palette) }
    }

    fun setDividerStyle(style: DividerStyle) {
        updateConfig { it.copy(dividerStyle = style) }
    }

    fun setBadgeStyle(style: BadgeStyle) {
        updateConfig { it.copy(badgeStyle = style) }
    }

    fun updateWidgets(transform: (DecorativeWidgetsConfig) -> DecorativeWidgetsConfig) {
        updateConfig { it.copy(widgets = transform(it.widgets)) }
    }

    fun resetToDefault() {
        viewModelScope.launch {
            val defaultConfig = repository.resetToDefault()
            _uiState.update {
                it.copy(
                    config = defaultConfig,
                    generatedMarkdown = MarkdownGenerator.generate(defaultConfig),
                    activePresetId = matchPreset(defaultConfig)
                )
            }
        }
    }
}
