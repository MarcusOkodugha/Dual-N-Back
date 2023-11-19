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
import mobappdev.example.nback_cimpl.data.UserPreferencesRepository

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

    fun setGameType(gameType: GameType)
    fun startGame()

    fun checkMatch()
    fun resetNCounter()

    fun increaseNCounter()
    fun decreaseNCounter()
}


// Inside your GameVM class

class GameVM(
    private val userPreferencesRepository: UserPreferencesRepository
): GameViewModel, ViewModel() {
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
    private val eventInterval: Long = 2000L  // 2000 ms (2s)

    private val nBackHelper = NBackHelper()  // Helper that generate the event array
    private var events = emptyArray<Int>()  // Array with all events
    private val gridSize = 3
//    private var grid: List<List<Boolean>> = List(gridSize) { List(gridSize) { false } }
//    private var highlightedTilePosition: Pair<Int, Int>? = null
    private val _highlightedTilePosition = MutableStateFlow<Pair<Int, Int>?>(null)
    override val highlightedTilePosition: StateFlow<Pair<Int, Int>?>
        get() = _highlightedTilePosition.asStateFlow()

    override fun setGameType(gameType: GameType) {
        // update the gametype in the gamestate
        _gameState.value = _gameState.value.copy(gameType = gameType)
    }

    override fun startGame() {
        job?.cancel()  // Cancel any existing game loop

        // Get the events from our C-model (returns IntArray, so we need to convert to Array<Int>)
        events = nBackHelper.generateNBackString(10, 9, 30, nBack.value).toList().toTypedArray()  // Todo Higher Grade: currently the size etc. are hardcoded, make these based on user input
        Log.d("GameVM", "The following sequence was generated: ${events.contentToString()}")

        job = viewModelScope.launch {
            when (gameState.value.gameType) {
                GameType.Audio -> runAudioGame()
                GameType.AudioVisual -> runAudioVisualGame()
                GameType.Visual -> runVisualGame(events)
            }
            // Todo: update the highscore
        }
    }

    override fun checkMatch() {
        /**
         * Todo: This function should check if there is a match when the user presses a match button
         * Make sure the user can only register a match once for each event.
         */
    }

    override fun resetNCounter() {
        _nBack.value = 0
    }

    override fun increaseNCounter() {
        _nBack.value +=1
    }

    override fun decreaseNCounter() {
        if (_nBack.value>1){
            _nBack.value -=1
        }

    }

    private fun runAudioGame() {
        // Todo: Make work for Basic grade
    }

    private suspend fun runVisualGame(events: Array<Int>){
        // Todo: Replace this code for actual game code
        for (value in events) {
            _gameState.value = _gameState.value.copy(eventValue = value)
            updateGrid()
            updateHighlightedTilePosition()
            delay(eventInterval)
        }
    }

    private fun runAudioVisualGame(){
        // Todo: Make work for Higher grade
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as GameApplication)
                GameVM(application.userPreferencesRespository)
            }
        }
    }

    init {
        // Code that runs during creation of the vm
        viewModelScope.launch {
            userPreferencesRepository.highscore.collect {
                _highscore.value = it
            }
        }
    }
//    fun getHighlightedTilePosition(): StateFlow<Pair<Int, Int>?> {
//        return highlightedTilePosition
//    }
    private fun updateHighlightedTilePosition() {
        val eventValue = _gameState.value.eventValue
        val row = (eventValue - 1) / gridSize
        val col = (eventValue - 1) % gridSize
        _highlightedTilePosition.value = Pair(row, col)
    }
    private fun updateGrid() {
        val grid = List(gridSize) { List(gridSize) { false } }

        _grid.value = grid
    }


}

//}

// Class with the different game types
enum class GameType{
    Audio,
    Visual,
    AudioVisual
}

data class GameState(
    // You can use this state to push values from the VM to your UI.
    val gameType: GameType = GameType.Visual,  // Type of the game
    val eventValue: Int = -1  // The value of the array string
)

class FakeVM: GameViewModel{
    override val gameState: StateFlow<GameState>
        get() = MutableStateFlow(GameState()).asStateFlow()
    override val score: StateFlow<Int>
        get() = MutableStateFlow(2).asStateFlow()
    override val highscore: StateFlow<Int>
        get() = MutableStateFlow(42).asStateFlow()
//    override val nBack: Int
//        get() = 2
    override val nBack: StateFlow<Int>
        get() = MutableStateFlow(0).asStateFlow()
    override val grid: StateFlow<List<List<Boolean>>>
        get() = MutableStateFlow(generateFakeGrid()).asStateFlow()

    override val highlightedTilePosition: StateFlow<Pair<Int, Int>?>
        get() = MutableStateFlow(null).asStateFlow()

    private fun generateFakeGrid(): List<List<Boolean>> {
        // Implement logic to generate a fake grid
        return List(3) { List(3) { true } }
    }
    override fun setGameType(gameType: GameType) {
    }

    override fun startGame() {
    }

    override fun checkMatch() {
    }

    override fun resetNCounter() {
        TODO("Not yet implemented")
    }

    override fun increaseNCounter() {
        TODO("Not yet implemented")
    }

    override fun decreaseNCounter() {
        TODO("Not yet implemented")
    }
}