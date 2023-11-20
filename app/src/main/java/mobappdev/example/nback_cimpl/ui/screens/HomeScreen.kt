package mobappdev.example.nback_cimpl.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
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
//                .fillMaxSize()
                .padding(it)
                .background(Color(152, 185, 234)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
//                modifier = Modifier.padding(32.dp),
                text = "High Score  $highscore",
                style = MaterialTheme.typography.headlineLarge,
                color = Color(240, 244, 251),

                )
            Box(
                modifier = Modifier
//                    .padding(horizontal = 60.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                ColumnOrRowBasedOnOrientation(vm = vm, navController = navController)

//                Column(
//                    Modifier.fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Settings(vm = vm)
//                    GameButtons(vm = vm,navController = navController)
//                }
            }
        }
    }
}

@Composable
fun ColumnOrRowBasedOnOrientation(
    vm: GameViewModel,
    navController: NavController
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
                Settings(vm = vm)
                Spacer(modifier = Modifier.height(16.dp)) // Add some vertical space
                GameButtons(vm = vm, navController = navController)
            }
        } else {
            Row(
                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp) // Add some horizontal space
            ) {
                SettingsHorizontal(vm = vm)
//                Spacer(modifier = Modifier.width(16.dp)) // Add some horizontal space
                GameButtonsHorizontal(vm = vm, navController = navController)
            }
        }
    }
}

@Composable
fun GameButtons(
    vm: GameViewModel,
    navController: NavController
){
    Column(
        modifier = Modifier
            .fillMaxWidth(),

        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            onClick ={
                vm.setGameType(GameType.Audio)
                vm.startGame()
                navController.navigate("game")
            },
            modifier = Modifier
                .size(270.dp,45.dp)
            ,
            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(text = "Sart Audio Game")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick ={
                vm.setGameType(GameType.Visual)
                vm.startGame()
                navController.navigate("game")
            },
            modifier = Modifier
                .size(270.dp,45.dp)
            ,
            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(text = "Sart Visual Game")
        }
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick ={
                vm.setGameType(GameType.AudioVisual)
                vm.startGame()
                navController.navigate("game")
            },
            modifier = Modifier
                .size(270.dp,45.dp)
            ,
            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(text = "Audio Visual Game")
        }
    }
}
@Composable
fun GameButtonsHorizontal(
    vm: GameViewModel,
    navController: NavController
){
    Column(
        modifier = Modifier
            .fillMaxWidth(),

        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Button(
            onClick ={
                vm.setGameType(GameType.Audio)
                vm.startGame()
                navController.navigate("game")
            },
            modifier = Modifier
                .size(270.dp,45.dp)
            ,
            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(text = "Sart Audio Game")
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick ={
                vm.setGameType(GameType.Visual)
                vm.startGame()
                navController.navigate("game")
            },
            modifier = Modifier
                .size(270.dp,45.dp)
            ,
            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(text = "Sart Visual Game")
        }
        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick ={
                vm.setGameType(GameType.AudioVisual)
                vm.startGame()
                navController.navigate("game")
            },
            modifier = Modifier
                .size(270.dp,45.dp)
            ,
            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(text = "Audio Visual Game")
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
    val eventInterval by vm.eventInterval.collectAsState()

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 100.dp,
                start = 60.dp,
                end = 60.dp
            )
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
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
            .fillMaxWidth()
            .padding(horizontal = 60.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
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
            text = "Combinations "+ combinations.toString(),
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
            .fillMaxWidth()
            .padding(horizontal = 60.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
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
            text = "Size "+ size.toString(),
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
            .fillMaxWidth()
            .padding(horizontal = 60.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
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
            text = "Length "+ length.toString(),
        )
        Button(
            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
            onClick = { vm.increaseLenght()},
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(text = "+1")
        }
    }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Button(
            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
            onClick = { vm.decreaseEventInterval()},
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(text = "-1")
        }
        Text(
            text = "Interval "+ eventInterval.toString(),
        )
        Button(
            colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
            onClick = { vm.increaseEventInterval()},
            shape = RoundedCornerShape(2.dp),
        ) {
            Text(text = "+1")
        }
    }

}
@Composable
fun SettingsHorizontal(
    vm: GameViewModel,
){
    val combinations by vm.combinations.collectAsState()
    val nBackValue by vm.nBack.collectAsState()
    val size by vm.size.collectAsState()
    val length by vm.length.collectAsState()
    val eventInterval by vm.eventInterval.collectAsState()
    Column(
        modifier = Modifier
            .padding(top = 0.dp, start = 50.dp, end = 0.dp)
            .fillMaxWidth(0.4f)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
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
            horizontalArrangement = Arrangement.SpaceBetween,
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
            horizontalArrangement = Arrangement.SpaceBetween,
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
            horizontalArrangement = Arrangement.SpaceBetween,
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
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Button(
                colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
                onClick = { vm.decreaseEventInterval()},
                shape = RoundedCornerShape(2.dp),
            ) {
                Text(text = "-1")
            }
            Text(
                text = "Interval = "+ eventInterval.toString(),
            )
            Button(
                colors = ButtonDefaults.buttonColors( Color(96, 140, 219)),
                onClick = { vm.increaseEventInterval()},
                shape = RoundedCornerShape(2.dp),
            ) {
                Text(text = "+1")
            }
        }
    }



}
