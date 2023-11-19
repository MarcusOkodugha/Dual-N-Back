package mobappdev.example.nback_cimpl.ui.screens
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import mobappdev.example.nback_cimpl.ui.viewmodels.GameViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import mobappdev.example.nback_cimpl.R
//import mobappdev.example.nback_cimpl.ui.viewmodels.FakeVM
import mobappdev.example.nback_cimpl.ui.viewmodels.GameType


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

    // Container
    Column(
        modifier = Modifier
            .fillMaxSize()
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ){
             if (gameState.eventValue != -1) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Current eventValue is: ${gameState.eventValue}",
                    textAlign = TextAlign.Center
                )
            }
        }
            Text(
                style = MaterialTheme.typography.headlineLarge,
                color = Color(240, 244, 251),
                text = "N = "+ nBackValue.toString(),
            )
        Column(
            modifier = Modifier
                .weight(0.75f)
                .fillMaxSize(),

            verticalArrangement = Arrangement.Center,
        ) {
            //grid

            Grid(grid = gridState, highlightedTilePosition = highlightedTilePositionState,isTileHighlighted = isTileHighlighted)
        }
        Column(
        ) {
            GameButtons(vm = vm)
        }
    }
}
@Composable
fun Grid(
    grid: List<List<Boolean>>,
    highlightedTilePosition: Pair<Int, Int>?,
    isTileHighlighted: Boolean,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp
    var maxScreenSize:Dp
    if (screenHeight>screenWidth){
        maxScreenSize=screenWidth.dp
    }else{
        maxScreenSize=screenHeight.dp
    }
    Column(
        modifier = Modifier
            .size(width = maxScreenSize, height = maxScreenSize)
            .padding(40.dp)
        ,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        for (i in grid.indices) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (j in grid[i].indices) {
                    Tile(
                        isHighlighted = highlightedTilePosition == Pair(i, j),isTileHighlighted= isTileHighlighted
                    )
                }
            }
        }
    }
}

@Composable
fun Tile(
    isHighlighted: Boolean,
    isTileHighlighted: Boolean,
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(
                if (isHighlighted && isTileHighlighted) Color(231, 181, 106) else Color(
                    199,
                    216,
                    240
                )
            )
            .clip(RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center,

    ) {

    }
}
@Composable
fun GameButtons(
    vm: GameViewModel,
) {
    val buttonColor by vm.buttonColor.collectAsState()
    val gameState by vm.gameState.collectAsState()

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        when (gameState.gameType) {
            GameType.AudioVisual -> {
                Row(
                    modifier = Modifier
                        .padding(vertical = 40.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val buttonModifier = Modifier
                        .width(100.dp) // Adjust the width as needed

                    Button(
                        modifier = buttonModifier,
                        onClick = {
                            vm.handleButtonTypePressed(GameType.Audio)
                        },
                        colors = ButtonDefaults.buttonColors(buttonColor),
                        shape = RoundedCornerShape(2.dp),
                    ) {
                        Text(text = "Sound")
                    }
                    Button(
                        modifier = buttonModifier,
                        onClick = {
                        },
                        colors = ButtonDefaults.buttonColors(buttonColor),
                        shape = RoundedCornerShape(2.dp),
                    ) {
                        Text(text = "Both")
                    }

                    Button(
                        modifier = buttonModifier,
                        onClick = {
                            vm.handleButtonTypePressed(GameType.Visual)

                        },
                        colors = ButtonDefaults.buttonColors(buttonColor),
                        shape = RoundedCornerShape(2.dp),
                    ) {
                        Text(text = "Position")
                    }
                }
            }
            GameType.Audio -> {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(3f / 2f)
                        .border(2.dp, Color(24, 30, 37))
                    ,
                    onClick = { vm.handleButtonTypePressed(GameType.Audio) },
                    colors = ButtonDefaults.buttonColors(buttonColor),
                    shape = RoundedCornerShape(2.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.sound_on),
                        contentDescription = "Sound",
                    )
                }
            }
            GameType.Visual -> {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(3f / 2f)
                        .border(2.dp, Color(24, 30, 37))
                    ,
                    onClick = { vm.handleButtonTypePressed(GameType.Visual) },
                    colors = ButtonDefaults.buttonColors(buttonColor),
                    shape = RoundedCornerShape(2.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.visual),
                        contentDescription = "Visual",
                    )
                }
            }
        }
    }
}

