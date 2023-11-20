package mobappdev.example.nback_cimpl.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Grid(
    grid: List<List<Boolean>>,
    highlightedTilePosition: Pair<Int, Int>?,
    isTileHighlighted: Boolean,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp
    var maxScreenSize: Dp
    if (screenHeight>screenWidth){
        maxScreenSize=screenWidth.dp
    }else{
        maxScreenSize=screenHeight.dp
    }
    Column(
        modifier = Modifier
            .size(width = maxScreenSize, height = maxScreenSize)
            .padding(top = 100.dp, start = 40.dp, end = 40.dp)
        ,
    ) {
        for (i in grid.indices) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                ,
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
            .padding(4.dp)
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
fun GridHorizontal(
    grid: List<List<Boolean>>,
    highlightedTilePosition: Pair<Int, Int>?,
    isTileHighlighted: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(horizontal = 40.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        for (i in grid.indices) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                ,
            ) {
                for (j in grid[i].indices) {
                    TileHorizontal(
                        isHighlighted = highlightedTilePosition == Pair(i, j),isTileHighlighted= isTileHighlighted
                    )
                }
            }
        }
    }
}

@Composable
fun TileHorizontal(
    isHighlighted: Boolean,
    isTileHighlighted: Boolean,
) {
    Box(
        modifier = Modifier
            .size(90.dp)
            .padding(4.dp)
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