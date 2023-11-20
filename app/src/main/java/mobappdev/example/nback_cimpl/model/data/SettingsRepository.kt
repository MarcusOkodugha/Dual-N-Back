package mobappdev.example.nback_cimpl.model.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class SettingsRepository(private val dataStore: DataStore<Preferences>) {
    private companion object {
        val NUMBER_OF_EVENTS = intPreferencesKey("number_of_events")
        val EVENT_INTERVAL = longPreferencesKey("event_interval")
        val N_BACK = intPreferencesKey("n_back")
        val GRID_SIZE = intPreferencesKey("grid_size")
        val NUMBER_OF_SPOKEN_LETTERS = intPreferencesKey("number_of_spoken_letters")

        const val DEFAULT_NUMBER_OF_EVENTS = 10
        const val DEFAULT_EVENT_INTERVAL = 2000L
        const val DEFAULT_N_BACK = 1
        const val DEFAULT_GRID_SIZE = 3
        const val DEFAULT_NUMBER_OF_SPOKEN_LETTERS = 1
    }

    val settings: Flow<Settings> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e("Settigs", "Error reading preferences", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            Settings(
                numberOfEvents = preferences[NUMBER_OF_EVENTS] ?: DEFAULT_NUMBER_OF_EVENTS,
                eventInterval = preferences[EVENT_INTERVAL] ?: DEFAULT_EVENT_INTERVAL,
                nBack = preferences[N_BACK] ?: DEFAULT_N_BACK,
                gridSize = preferences[GRID_SIZE] ?: DEFAULT_GRID_SIZE,
                numberOfSpokenLetters = preferences[NUMBER_OF_SPOKEN_LETTERS] ?: DEFAULT_NUMBER_OF_SPOKEN_LETTERS
            )
        }

    suspend fun saveSettings(settings: Settings) {
        dataStore.edit { preferences: MutablePreferences ->
            preferences[NUMBER_OF_EVENTS] = settings.numberOfEvents
            preferences[EVENT_INTERVAL] = settings.eventInterval
            preferences[N_BACK] = settings.nBack
            preferences[GRID_SIZE] = settings.gridSize
            preferences[NUMBER_OF_SPOKEN_LETTERS] = settings.numberOfSpokenLetters
        }
    }
    suspend fun loadSettings(): Settings {
        return dataStore.data
            .catch {
                if (it is IOException) {
                    Log.e("Settings", "Error reading preferences", it)
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }
            .map { preferences ->
                Settings(
                    numberOfEvents = preferences[NUMBER_OF_EVENTS] ?: DEFAULT_NUMBER_OF_EVENTS,
                    eventInterval = preferences[EVENT_INTERVAL] ?: DEFAULT_EVENT_INTERVAL,
                    nBack = preferences[N_BACK] ?: DEFAULT_N_BACK,
                    gridSize = preferences[GRID_SIZE] ?: DEFAULT_GRID_SIZE,
                    numberOfSpokenLetters = preferences[NUMBER_OF_SPOKEN_LETTERS] ?: DEFAULT_NUMBER_OF_SPOKEN_LETTERS
                )
            }
            .first() // This collects the value from the flow and returns it
    }
}
data class Settings(
    val numberOfEvents: Int,
    val eventInterval: Long,
    val nBack: Int,
    val gridSize: Int,
    val numberOfSpokenLetters: Int
)