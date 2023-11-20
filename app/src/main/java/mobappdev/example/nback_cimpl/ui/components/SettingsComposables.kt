package mobappdev.example.nback_cimpl.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import mobappdev.example.nback_cimpl.ui.viewmodels.GameType
import mobappdev.example.nback_cimpl.ui.viewmodels.GameViewModel
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
