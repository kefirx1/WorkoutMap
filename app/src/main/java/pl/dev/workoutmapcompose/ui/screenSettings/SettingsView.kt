package pl.dev.workoutmapcompose.ui.screenSettings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.dev.workoutmapcompose.SettingsActivity
import pl.dev.workoutmapcompose.ui.components.DialogAlerts
import pl.dev.workoutmapcompose.ui.components.HeaderComponent
import pl.dev.workoutmapcompose.ui.theme.*

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
            .background(MaterialTheme.colors.background)
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
                    style = buttonsSettings
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
                    style = buttonsSettings
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
                    style = buttonsSettings
                )
            }
        }
    }
}