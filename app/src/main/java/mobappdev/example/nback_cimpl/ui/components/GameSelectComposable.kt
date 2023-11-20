package mobappdev.example.nback_cimpl.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mobappdev.example.nback_cimpl.ui.viewmodels.GameType
import mobappdev.example.nback_cimpl.ui.viewmodels.GameViewModel

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