package mobappdev.example.nback_cimpl.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mobappdev.example.nback_cimpl.ui.components.GameButtons
import mobappdev.example.nback_cimpl.ui.components.Grid
import mobappdev.example.nback_cimpl.ui.components.GridHorizontal
import mobappdev.example.nback_cimpl.ui.viewmodels.GameViewModel


@Composable
fun GameScreen(
    vm: GameViewModel
){
    val gameState by vm.gameState.collectAsState()
    val gridState by vm.grid.collectAsState()
    val highlightedTilePositionState by vm.highlightedTilePosition.collectAsState()
    val isTileHighlighted by vm.isTileHighlighted.collectAsState()
    val nBackValue by vm.nBack.collectAsState()
    val buttonColor by vm.buttonColor.collectAsState()
    val score by vm.score.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
                Text(
                    text = "Score ${score.toString()}",
                    textAlign = TextAlign.Center
                )
             if (gameState.eventValueVisual != -1) {
                Text(
                    text = "Event value ${gameState.eventValueVisual.toString()}",
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = "N = "+ nBackValue.toString(),
            )
        }
        Column(
            modifier = Modifier
                .weight(0.75f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            ColumnOrRowBasedOnOrientation(vm = vm,grid = gridState, highlightedTilePosition = highlightedTilePositionState,isTileHighlighted = isTileHighlighted)
        }
    }
}

@Composable
fun ColumnOrRowBasedOnOrientation(
    vm: GameViewModel,
    grid: List<List<Boolean>>,
    highlightedTilePosition: Pair<Int, Int>?,
    isTileHighlighted: Boolean,
) {
    val config = LocalConfiguration.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Grid(vm=vm,grid = grid, highlightedTilePosition = highlightedTilePosition,isTileHighlighted = isTileHighlighted)
                GameButtons(vm = vm)
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                GridHorizontal( vm=vm, grid = grid, highlightedTilePosition = highlightedTilePosition,isTileHighlighted = isTileHighlighted)
                Column (
                    verticalArrangement = Arrangement.Center,
                ){
                    GameButtons(vm = vm)
                }
            }
        }
    }
}

