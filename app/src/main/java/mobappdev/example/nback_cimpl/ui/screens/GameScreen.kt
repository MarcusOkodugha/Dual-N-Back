package mobappdev.example.nback_cimpl.ui.screens
import android.util.Log
import androidx.compose.foundation.background
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
import mobappdev.example.nback_cimpl.ui.viewmodels.FakeVM

@Composable
fun GameScreen(
    vm: GameViewModel
){
    val gameState by vm.gameState.collectAsState()
    val gridState by vm.grid.collectAsState()
    val highlightedTilePositionState by vm.highlightedTilePosition.collectAsState()
    val nBackValue by vm.nBack.collectAsState()
    // Container
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .background(Color.Yellow)
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

            Grid(grid = gridState, highlightedTilePosition = highlightedTilePositionState)
        }
        Column(
//            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
//                    .weight(0.25f),
            ){

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
    //                    .background(Color.Cyan)
                ){
                    Button(//left button sound
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .aspectRatio(3f / 2f),
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors( Color(221, 91, 80)),
                        shape = RoundedCornerShape(2.dp),
                        ) {
                        Icon(
                            painter = painterResource(id = R.drawable.sound_on),
                            contentDescription = "Sound",
                            modifier = Modifier
//                                .aspectRatio(5f / 2f)
                        )
                    }
                    Button(//right button Position
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(3f / 2f),
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors( Color(159, 212, 161)),
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
}
@Composable
fun Grid(
    grid: List<List<Boolean>>,
    highlightedTilePosition: Pair<Int, Int>?,
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
//            .background(Color.Red)
            .padding(40.dp)
        ,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        for (i in grid.indices) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
//                    .background(Color.Green)
                ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (j in grid[i].indices) {
                    Tile(
                        isHighlighted = highlightedTilePosition == Pair(i, j)
                    )
                }
            }
        }
    }
}

@Composable
fun Tile(
    isHighlighted: Boolean
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(if (isHighlighted) Color(231, 181, 106) else Color(199, 216, 240))
            .clip(RoundedCornerShape(20.dp)),
//            .clickable { /* Handle tile click if needed */ },
        contentAlignment = Alignment.Center,

    ) {

        if (isHighlighted) {

        }
    }
}