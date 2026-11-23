package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.model.ProfileReadmeConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

private val Context.readmeDataStore: DataStore<Preferences> by preferencesDataStore(name = "readme_craft_prefs")

class ProfileRepository(private val context: Context) {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    private val configKey = stringPreferencesKey("profile_readme_config_json")

    val profileConfigFlow: Flow<ProfileReadmeConfig> = context.readmeDataStore.data.map { prefs ->
        val raw = prefs[configKey]
        if (raw.isNullOrBlank()) {
            ReadmePresetsAndCatalog.defaultProfileConfig()
        } else {
            runCatching { json.decodeFromString<ProfileReadmeConfig>(raw) }
                .getOrElse { ReadmePresetsAndCatalog.defaultProfileConfig() }
        }
    }

    suspend fun saveConfig(config: ProfileReadmeConfig) {
        context.readmeDataStore.edit { prefs ->
            prefs[configKey] = json.encodeToString(ProfileReadmeConfig.serializer(), config)
        }
    }

    suspend fun resetToDefault(): ProfileReadmeConfig {
        val defaultConfig = ReadmePresetsAndCatalog.defaultProfileConfig()
        saveConfig(defaultConfig)
        return defaultConfig
    }
}
