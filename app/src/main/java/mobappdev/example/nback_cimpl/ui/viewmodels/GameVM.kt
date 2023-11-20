package mobappdev.example.nback_cimpl.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mobappdev.example.nback_cimpl.GameApplication
import mobappdev.example.nback_cimpl.NBackHelper
import mobappdev.example.nback_cimpl.model.data.UserPreferencesRepository
import mobappdev.example.nback_cimpl.model.data.SettingsRepository
import mobappdev.example.nback_cimpl.model.data.Settings
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.speech.tts.TextToSpeech.SUCCESS
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.isActive

import java.util.Locale
/**
 * This is the GameViewModel.
 *
 * It is good practice to first make an interface, which acts as the blueprint
 * for your implementation. With this interface we can create fake versions
 * of the viewmodel, which we can use to test other parts of our app that depend on the VM.
 *
 * Our viewmodel itself has functions to start a game, to specify a gametype,
 * and to check if we are having a match
 *
 * Date: 25-08-2023
 * Version: Version 1.0
 * Author: Yeetivity
 *
 */


interface GameViewModel {
    val gameState: StateFlow<GameState>
    val score: StateFlow<Int>
    val highscore: StateFlow<Int>
    val nBack:  StateFlow<Int>
    val grid: StateFlow<List<List<Boolean>>>
    val highlightedTilePosition: StateFlow<Pair<Int, Int>?>
    val buttonColor:StateFlow<Color>
    val combinations:  StateFlow<Int>
    val size:  StateFlow<Int>
    val length:  StateFlow<Int>
    val isTileHighlighted: StateFlow<Boolean>
    val eventInterval: StateFlow<Long>

    fun setGameType(gameType: GameType)
    fun startGame()
    fun increaseNCounter()
    fun decreaseNCounter()
    fun increaseCombinations()
    fun decreaseCombinatons()
    fun increaseSize()
    fun decreaseSize()
    fun increaseLenght()
    fun decreaseLenght()
    fun increaseEventInterval()
    fun decreaseEventInterval()
    fun checkMatch(buttonType: GameType)
    fun setButtonColor(color: Color)
}
// Inside your GameVM class

class GameVM(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val settingsRepository: SettingsRepository
): GameViewModel, ViewModel(),OnInitListener {
    private var counter = 0
    private val _gameState = MutableStateFlow(GameState())
    override val gameState: StateFlow<GameState>
        get() = _gameState.asStateFlow()

    private val _score = MutableStateFlow(0)
    override val score: StateFlow<Int>
        get() = _score

    private val _highscore = MutableStateFlow(0)
    override val highscore: StateFlow<Int>
        get() = _highscore

    private val _nBack= MutableStateFlow(1)
    override val nBack: StateFlow<Int>
        get() = _nBack.asStateFlow()
    private val _grid: MutableStateFlow<List<List<Boolean>>> = MutableStateFlow(emptyList())
    override val grid: StateFlow<List<List<Boolean>>>
        get() = _grid.asStateFlow()

    private var job: Job? = null  // coroutine job for the game event
//    private val eventInterval: Long = 2000L  // 2000 ms (2s)
    private val _eventInterval = MutableStateFlow(2000L)
    override val eventInterval: StateFlow<Long>
        get() = _eventInterval.asStateFlow()


    private val nBackHelper = NBackHelper()  // Helper that generate the event array
//    private var events = emptyArray<Int>()  // Array with all events
    private var eventsAudio = emptyArray<Int>()  // Array with all events
    private var eventsVisual = emptyArray<Int>()  // Array with all events
    private val _highlightedTilePosition = MutableStateFlow<Pair<Int, Int>?>(null)
    override val highlightedTilePosition: StateFlow<Pair<Int, Int>?>
        get() = _highlightedTilePosition.asStateFlow()
    private val _textToSpeech: TextToSpeech by lazy {
        TextToSpeech(GameApplication.instance, this)
    }
    val _buttonColor = MutableStateFlow(Color.Transparent)
    override val buttonColor: StateFlow<Color>
        get() = _buttonColor.asStateFlow()
    private val _combinations= MutableStateFlow(1)
    override val combinations: StateFlow<Int>
        get() = _combinations.asStateFlow()
    private val _size= MutableStateFlow(3)
    override val size: StateFlow<Int>
        get() = _size.asStateFlow()
    private val _lenght= MutableStateFlow(20)
    override val length: StateFlow<Int>
        get() = _lenght.asStateFlow()
    private val _isTileHighlighted = MutableStateFlow(false)
    override val isTileHighlighted: StateFlow<Boolean>
        get() = _isTileHighlighted.asStateFlow()

    override fun setButtonColor(color: Color) {
        _buttonColor.value = color
    }

    override fun setGameType(gameType: GameType) {
        // update the gametype in the gamestate
        _gameState.value = _gameState.value.copy(gameType = gameType)
    }

    override fun startGame() {
        job?.cancel()  // Cancel any existing game loop
        reset()
        // Get the events from our C-model (returns IntArray, so we need to convert to Array<Int>)
        eventsVisual = nBackHelper.generateNBackString(length.value, combinations.value, 30, nBack.value).toList().toTypedArray()  // Todo Higher Grade: currently the size etc. are hardcoded, make these based on user input
        eventsAudio = nBackHelper.generateNBackString(length.value, combinations.value, 30, nBack.value).toList().toTypedArray()  // Todo Higher Grade: currently the size etc. are hardcoded, make these based on user input
//        eventsVisual = arrayOf(1, 1, 4, 4, 1, 5, 1, 1, 9, 4)//todo remve hardcodeing
//        eventsAudio = arrayOf(2, 3, 4, 4, 1, 5, 1, 1, 9, 4)//todo remve hardcodeing

        val currentSettings = Settings(
            numberOfEvents = _lenght.value,
            eventInterval = _eventInterval.value,
            nBack = nBack.value,
            gridSize = size.value,
            numberOfSpokenLetters = _combinations.value
        )


        job = viewModelScope.launch {
        settingsRepository.saveSettings(currentSettings)
            when (gameState.value.gameType) {
                GameType.Audio -> runAudioGame()
                GameType.AudioVisual -> runAudioVisualGame()
                GameType.Visual -> runVisualGame()
            }
            if (_score.value > _highscore.value) {
                _highscore.value = _score.value
                userPreferencesRepository.saveHighScore(_score.value)
            }
        }
    }
    private fun reset(){
        //rests all settings
        counter = 0
        _buttonColor.value = Color(96, 140, 219)
        _grid.value = emptyList()
    }

    private suspend fun runAudioGame() {
        speakLetter(convertToLetter(counter+1))
        for (value in eventsAudio) {
            _gameState.value = _gameState.value.copy(eventValueAudio = value)
            speakLetter(convertToLetter(value))
            delay(eventInterval.value)
            counter++
        }
    }

    private suspend fun runVisualGame(){
        for (value in eventsVisual) {
            _gameState.value = _gameState.value.copy(eventValueVisual = value)
            updateGrid()
            _isTileHighlighted.value = !_isTileHighlighted.value
            updateHighlightedTilePosition()
            delay(eventInterval.value/2)
            _isTileHighlighted.value = !_isTileHighlighted.value
            updateHighlightedTilePosition()
            delay(eventInterval.value/2)
            counter++
        }
    }

private suspend fun runAudioVisualGame() {
    for ((audioValue, visualValue) in eventsAudio.zip(eventsVisual)) {
        _gameState.value = _gameState.value.copy(eventValueAudio = audioValue)
        _gameState.value = _gameState.value.copy(eventValueVisual = visualValue)
        updateGrid()
        speakLetter(convertToLetter(audioValue))
        _isTileHighlighted.value = true
        updateHighlightedTilePosition()
        delay(eventInterval.value / 2)
        _isTileHighlighted.value = false
        updateHighlightedTilePosition()
        delay(eventInterval.value / 2)
        counter++

        // Now, you can use visualValue in a similar way if needed
    }
}

override fun checkMatch(buttonType: GameType) {
    val currentEventValueAudio = _gameState.value.eventValueAudio
    val currentEventValueVisual = _gameState.value.eventValueVisual
    val nBackValue = _nBack.value
    val targetIndex = counter - nBackValue
    val isMatchAudio = targetIndex >= 0 && eventsAudio.getOrNull(targetIndex) == currentEventValueAudio

    val isMatchVisual = targetIndex >= 0 && eventsVisual.getOrNull(targetIndex) == currentEventValueVisual
    if ((gameState.value.gameType==GameType.Audio||gameState.value.gameType==GameType.AudioVisual)&&buttonType==GameType.Audio){
        if (_gameState.value.matchedEventsAudio.contains(currentEventValueAudio)) {
            return
        }
        if (isMatchAudio) {
            _buttonColor.value =Color.Green
            _score.value ++
            _gameState.value = _gameState.value.copy(matchedEventsAudio = _gameState.value.matchedEventsAudio + currentEventValueAudio)
        } else{
            _buttonColor.value = Color.Red
            _score.value --
        }
    }
    if ((gameState.value.gameType==GameType.Visual||gameState.value.gameType==GameType.AudioVisual)&&buttonType==GameType.Visual){
        if (_gameState.value.matchedEventsVisual.contains(currentEventValueVisual)) {
            return
        }
         if (isMatchVisual) {
             _buttonColor.value =Color.Green
             _score.value ++
             _gameState.value = _gameState.value.copy(matchedEventsVisual = _gameState.value.matchedEventsVisual + currentEventValueVisual)
        } else{
             _buttonColor.value = Color.Red
             _score.value --
        }
    }
    if (gameState.value.gameType==GameType.AudioVisual&&buttonType==GameType.AudioVisual){
        if (isMatchVisual&&isMatchAudio) {
            _buttonColor.value =Color.Green
            _score.value ++
            _gameState.value = _gameState.value.copy(matchedEventsAudioVisual = _gameState.value.matchedEventsVisual + currentEventValueVisual)//test
        } else{
            _buttonColor.value = Color.Red
            _score.value --
        }
    }
    viewModelScope.launch {
        delay(eventInterval.value / 2)
        _buttonColor.value = Color(96, 140, 219)

    }
}
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as GameApplication)
                GameVM(application.userPreferencesRespository,application.settingsRepository)
            }
        }
    }
    init {
        // Load settings when the ViewModel is initialized
        viewModelScope.launch {
            userPreferencesRepository.highscore.collect {
                _highscore.value = it
            }
            val loadedSettings = settingsRepository.loadSettings()
            _nBack.value = loadedSettings.nBack
            _size.value = loadedSettings.gridSize
            _combinations.value = loadedSettings.numberOfSpokenLetters
            _lenght.value = loadedSettings.numberOfEvents
            _eventInterval.value = loadedSettings.eventInterval
        }
    }
    private fun updateHighlightedTilePosition() {
        val eventValue = _gameState.value.eventValueVisual
        val row = (eventValue - 1) / size.value
        val col = (eventValue - 1) % size.value
        _highlightedTilePosition.value = Pair(row, col)
    }
    private fun updateGrid() {
        val grid = List(size.value) { List(size.value) { false } }
        _grid.value = grid
    }
    override fun onInit(status: Int) {
        if (status == SUCCESS) {
            _textToSpeech.language = Locale.getDefault()
        } else {
            Log.e("GameVM", "TextToSpeech initialization failed.")
        }
    }

    private fun speakLetter(letter: Char) {
        _textToSpeech.speak(letter.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
    }
    private fun convertToLetter(value: Int): Char {
        require(value in 1..26) { "Value must be between 1 and 26 inclusive" }
        return ('A'.code + value - 1).toChar()
    }
    override fun increaseNCounter() {
        _nBack.value +=1
    }
    override fun decreaseNCounter() {
        if (_nBack.value>1){
            _nBack.value -=1
        }
    }
    override fun increaseSize() {
        _size.value +=1
    }
    override fun decreaseSize() {
        if (_size.value>1){
            _size.value -=1
        }
    }
    override fun increaseCombinations() {
        _combinations.value +=1
    }
    override fun decreaseCombinatons() {
        if (_combinations.value>1){
            _combinations.value -=1
        }
    }
    override fun increaseLenght() {
        _lenght.value +=1
    }
    override fun decreaseLenght() {
        if (_lenght.value>1){
            _lenght.value -=1
        }
    }

    override fun increaseEventInterval() {
        _eventInterval.value +=1000
    }

    override fun decreaseEventInterval() {
        if (_eventInterval.value>1000){
            _eventInterval.value -=1000
        }
    }
}

enum class GameType{
    Audio,
    Visual,
    AudioVisual
}

data class GameState(
    // You can use this state to push values from the VM to your UI.
    val gameType: GameType = GameType.Visual,  // Type of the game
    val eventValueAudio: Int = -1,  // The value of the array string
    val eventValueVisual: Int = -1,  // The value of the array string
    val matchedEventsAudio: Set<Int> = emptySet(),
    val matchedEventsVisual: Set<Int> = emptySet(),
    val matchedEventsAudioVisual: Set<Int> = emptySet()
)


