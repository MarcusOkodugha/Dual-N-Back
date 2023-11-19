package mobappdev.example.nback_cimpl.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import mobappdev.example.nback_cimpl.R
import mobappdev.example.nback_cimpl.ui.viewmodels.GameViewModel
import mobappdev.example.nback_cimpl.ui.viewmodels.GameType

/**
 * This is the Home screen composable
 *
 * Currently this screen shows the saved highscore
 * It also contains a button which can be used to show that the C-integration works
 * Furthermore it contains two buttons that you can use to start a game
 *
 * Date: 25-08-2023
 * Version: Version 1.0
 * Author: Yeetivity
 *
 */

@Composable
fun HomeScreen(
    vm: GameViewModel,
    navController: NavController
) {
    val highscore by vm.highscore.collectAsState()  // Highscore is its own StateFlow
    val gameState by vm.gameState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val nBackValue by vm.nBack.collectAsState()


    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color(152, 185, 234)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(32.dp),
                text = "High Score  $highscore",
                style = MaterialTheme.typography.headlineLarge,
                color = Color(240, 244, 251),

                )
            // Todo: You'll probably want to change this "BOX" part of the composable
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    Modifier.fillMaxWidth(),
                ) {
                    Settings(vm = vm)

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Button(
                            onClick ={
                                vm.setGameType(GameType.Audio)
                                vm.startGame()
                                navController.navigate("game")
                            },
                            modifier = Modifier
                            ,
                            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
                            shape = RoundedCornerShape(2.dp),
                        ) {
                            Text(text = "Sart Audio Game")
                        }
                        Button(
                            onClick ={
                                vm.setGameType(GameType.Visual)
                                vm.startGame()
                                navController.navigate("game")
                            },
                            modifier = Modifier
                            ,
                            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
                            shape = RoundedCornerShape(2.dp),
                        ) {
                            Text(text = "Sart Visual Game")
                        }
                        Button(
                            onClick ={
                                vm.setGameType(GameType.AudioVisual)
                                vm.startGame()
                                navController.navigate("game")
                            },
                            modifier = Modifier

                            ,
                            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
                            shape = RoundedCornerShape(2.dp),
                        ) {
                            Text(text = "Sart Audio Visual Game")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Settings(
    vm: GameViewModel,

){
    val combinations by vm.combinations.collectAsState()
    val nBackValue by vm.nBack.collectAsState()
    val size by vm.size.collectAsState()
    val length by vm.length.collectAsState()

    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        //left button
        Button(
            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
            onClick = { vm.decreaseNCounter()},
            shape = RoundedCornerShape(2.dp),

            ) {
            Text(text = "-1")
        }
        Text(
            text = "N = "+ nBackValue.toString(),
        )
        Button(
            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
            onClick = { vm.increaseNCounter()},
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(text = "+1")
        }
    }
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        Button(
            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
            onClick = { vm.decreaseCombinatons()},
            shape = RoundedCornerShape(2.dp),
            ) {
            Text(text = "-1")
        }
        Text(
            text = "Combinations = "+ combinations.toString(),
        )
        Button(
            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
            onClick = { vm.increaseCombinations()},
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(text = "+1")
        }
    }
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        Button(
            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
            onClick = { vm.decreaseSize()},
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(text = "-1")
        }
        Text(
            text = "Size = "+ size.toString(),
        )
        Button(
            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
            onClick = { vm.increaseSize()},
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(text = "+1")
        }
    }
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        Button(
            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
            onClick = { vm.decreaseLenght()},
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(text = "-1")
        }
        Text(
            text = "Length = "+ length.toString(),
        )
        Button(
            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
            onClick = { vm.increaseLenght()},
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(text = "+1")
        }
    }

}

//@Preview
//@Composable
//fun HomeScreenPreview() {
//    // Since I am injecting a VM into my homescreen that depends on Application context, the preview doesn't work.
//    Surface(){
////        HomeScreen(FakeVM())
//    }
//}