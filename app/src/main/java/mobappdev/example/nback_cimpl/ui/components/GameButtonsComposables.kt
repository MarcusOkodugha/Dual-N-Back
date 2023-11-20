package mobappdev.example.nback_cimpl.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mobappdev.example.nback_cimpl.ui.viewmodels.GameType
import mobappdev.example.nback_cimpl.ui.viewmodels.GameViewModel

@Composable
fun GameButtons(
    vm: GameViewModel,
) {
    val buttonColor by vm.buttonColor.collectAsState()
    val gameState by vm.gameState.collectAsState()

    Box(
        modifier = Modifier
            .padding(top = 60.dp, start = 0.dp, end = 0.dp, bottom = 30.dp)
        ,
        contentAlignment = Alignment.Center
    ) {
        when (gameState.gameType) {
            GameType.AudioVisual -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val buttonModifier = Modifier
                        .width(100.dp)

                    Button(
                        modifier = buttonModifier,
                        onClick = {
                            vm.checkMatch(GameType.Audio)
                        },
                        colors = ButtonDefaults.buttonColors(buttonColor),
                        shape = RoundedCornerShape(2.dp),
                    ) {
                        Text(text = "Sound")
                    }
                    Button(
                        modifier = buttonModifier,
                        onClick = {
                            vm.checkMatch(GameType.AudioVisual)
                        },
                        colors = ButtonDefaults.buttonColors(buttonColor),
                        shape = RoundedCornerShape(2.dp),
                    ) {
                        Text(text = "Both")
                    }

                    Button(
                        modifier = buttonModifier,
                        onClick = {
                            vm.checkMatch(GameType.Visual)

                        },
                        colors = ButtonDefaults.buttonColors(buttonColor),
                        shape = RoundedCornerShape(2.dp),
                    ) {
                        Text(text = "Position")
                    }
                }
            }

            GameType.Audio -> {
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Button(

                        onClick = { vm.checkMatch(GameType.Audio) },
                        colors = ButtonDefaults.buttonColors(buttonColor),
                        shape = RoundedCornerShape(2.dp),
                    ) {
                        Text(text = "Sound")
                    }
                }
            }
            GameType.Visual -> {
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Button(
                        onClick = { vm.checkMatch(GameType.Visual) },
                        colors = ButtonDefaults.buttonColors(buttonColor),
                        shape = RoundedCornerShape(2.dp),
                    ) {
                        Text(text = "Position")
                    }
                }
            }
        }
    }
}
