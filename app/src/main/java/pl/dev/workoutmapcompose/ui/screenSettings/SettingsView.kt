package pl.dev.workoutmapcompose.ui.screenSettings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.dev.workoutmapcompose.SettingsActivity
import pl.dev.workoutmapcompose.ui.components.DialogAlerts
import pl.dev.workoutmapcompose.ui.components.HeaderComponent
import pl.dev.workoutmapcompose.ui.theme.BlueGray50
import pl.dev.workoutmapcompose.ui.theme.BlueGray800
import pl.dev.workoutmapcompose.ui.theme.BlueGray900
import pl.dev.workoutmapcompose.ui.theme.mainFamily

fun exitSettings(
    instance: SettingsActivity
){
    //TODO
    instance.finish()
}

@Composable
fun MainSettingsView(
    instance: SettingsActivity,
    viewModel: SettingsViewModel
) {

    var openWipeDataDialog by remember {
        mutableStateOf(false)
    }
    var openChangePersonalDataDialog by remember {
        mutableStateOf(false)
    }
    var openWipeTrainingPlansDataDialog by remember {
        mutableStateOf(false)
    }

    if(openWipeDataDialog) {
        openWipeDataDialog = DialogAlerts.wipeDataDialogAlert(
            instance = instance,
            viewModel = viewModel
        )
    }

    if(openChangePersonalDataDialog) {
        openChangePersonalDataDialog = DialogAlerts.changePersonalDataDialogAlert(
            instance = instance,
            viewModel = viewModel
        )
    }

    if(openWipeTrainingPlansDataDialog) {
        openWipeTrainingPlansDataDialog = DialogAlerts.wipeTrainingPlansDataDialogAlert(
            instance = instance,
            viewModel = viewModel
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGray900)
            .padding(10.dp)
    ) {

        HeaderComponent(
            screenName = "USTAWIENIA",
            instance = instance
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            TextButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    openChangePersonalDataDialog = true
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = BlueGray800
                )
            ) {
                Text(
                    text = "Zmień dane personalne",
                    color = BlueGray50,
                    fontFamily = mainFamily,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Left
                )
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    openWipeTrainingPlansDataDialog = true
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = BlueGray800
                )
            ) {
                Text(
                    text = "Usuń plany treningowe",
                    color = BlueGray50,
                    fontFamily = mainFamily,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Left
                )
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    openWipeDataDialog = true
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = BlueGray800
                )
            ) {
                Text(
                    text = "Resetuj ustawienia",
                    color = BlueGray50,
                    fontFamily = mainFamily,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Left
                )
            }
        }
    }
}