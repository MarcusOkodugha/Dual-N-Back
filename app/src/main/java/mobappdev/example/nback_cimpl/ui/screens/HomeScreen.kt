package mobappdev.example.nback_cimpl.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mobappdev.example.nback_cimpl.ui.components.GameButtons
import mobappdev.example.nback_cimpl.ui.components.GameButtonsHorizontal
import mobappdev.example.nback_cimpl.ui.components.Settings
import mobappdev.example.nback_cimpl.ui.components.SettingsHorizontal
import mobappdev.example.nback_cimpl.ui.viewmodels.GameViewModel


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
                .padding(it)
                .background(Color(152, 185, 234)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp, start = 0.dp, end = 0.dp),
                text = "High Score  $highscore",
                style = MaterialTheme.typography.headlineLarge,
                color = Color(240, 244, 251),

                )
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                ColumnOrRowBasedOnOrientation(vm = vm, navController = navController)
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
                Spacer(modifier = Modifier.height(30.dp))
                GameButtons(vm = vm, navController = navController)
            }
        } else {
            Row(

            ) {
                SettingsHorizontal(vm = vm)
                GameButtonsHorizontal(vm = vm, navController = navController)
            }
        }
    }
}
