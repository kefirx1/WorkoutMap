package pl.dev.workoutmapcompose.ui.components

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.dev.workoutmapcompose.RegisterActivity
import pl.dev.workoutmapcompose.SettingsActivity
import pl.dev.workoutmapcompose.ui.screenSettings.SettingsViewModel
import pl.dev.workoutmapcompose.ui.theme.BlueGray50
import pl.dev.workoutmapcompose.ui.theme.BlueGray800
import pl.dev.workoutmapcompose.ui.theme.mainFamily

object DialogAlerts {

    @Composable
    fun wipeDataDialogAlert(
        instance: SettingsActivity,
        viewModel: SettingsViewModel
    ): Boolean {

        val dialogTitle = "Usuń dane"
        val dialogText = "Zatwierdzenie spowoduje usunięcie wszelkich postępów i cofnie Ciebie do stanu początkowego aplikacji. Nie można tego cofnąć!!"
        val confirmButtonText = "USUŃ"
        val dismissButtonText = "ANULUJ"
        val toastCorrectText = "Dane zostały usunięte"
        val toastFailureText = "Błąd - dane nie zostały usunięte"

        var openDialog by remember {
            mutableStateOf(true)
        }

        if (openDialog) {
            AlertDialog(
                onDismissRequest = {
                    openDialog = false
                },
                title = {
                    Text(
                        text = dialogTitle,
                        fontFamily = mainFamily,
                        fontSize = 30.sp,
                    )
                },
                text = {
                    Text(
                        text = dialogText,
                        fontSize = 15.sp,
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog = false
                            if (viewModel.wipeData()) {
                                Toast.makeText(
                                    instance,
                                    toastCorrectText,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                val intent = Intent(instance, RegisterActivity::class.java)
                                instance.startActivity(intent)
                            } else {
                                Toast.makeText(
                                    instance,
                                    toastFailureText,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    ) {
                        Text(
                            text = confirmButtonText,
                            color = BlueGray50,
                            fontFamily = mainFamily,
                            fontSize = 20.sp,
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog = false
                        }
                    ) {
                        Text(
                            text = dismissButtonText,
                            color = BlueGray50,
                            fontFamily = mainFamily,
                            fontSize = 20.sp,
                        )
                    }
                },
                backgroundColor = BlueGray800,
                contentColor = BlueGray50
            )
        }

        return openDialog

    }



    @Composable
    fun changePersonalDataDialogAlert(
        instance: SettingsActivity,
        viewModel: SettingsViewModel
    ): Boolean {

        val dialogTitle = "Zmień dane personalne"
        val confirmButtonText = "ZMIEŃ"
        val dismissButtonText = "ANULUJ"
        val toastCorrectText = "Dane zostały zmienione"
        val toastFailureText = "Błąd - dane nie zostały zmienione"

        var nameTextState by remember { mutableStateOf(TextFieldValue()) }
        var surnameTextState by remember { mutableStateOf(TextFieldValue()) }

        var openDialog by remember {
            mutableStateOf(true)
        }

        if (openDialog) {
            AlertDialog(
                onDismissRequest = {
                    openDialog = false
                },
                title = {
                    Text(
                        text = dialogTitle,
                        fontFamily = mainFamily,
                        fontSize = 30.sp,
                    )
                },
                text = {
                    Column(
                        modifier = Modifier
                            .padding(top = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                            ) {
                                Text(
                                    text = "Imię:",
                                    color = BlueGray50,
                                    fontFamily = mainFamily,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Left
                                )
                                Spacer(
                                    modifier = Modifier
                                        .height(10.dp)
                                )
                                TextField(
                                    value = nameTextState,
                                    onValueChange = { nameTextState = it },
                                    textStyle = TextStyle(
                                        color = BlueGray50,
                                        fontFamily = mainFamily,
                                        fontSize = 20.sp
                                    )
                                )
                            }
                        }

                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Nazwisko:",
                                    color = BlueGray50,
                                    fontFamily = mainFamily,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Left
                                )
                                Spacer(
                                    modifier = Modifier
                                        .height(10.dp)
                                )
                                TextField(
                                    value = surnameTextState,
                                    onValueChange = { surnameTextState = it },
                                    textStyle = TextStyle(
                                        color = BlueGray50,
                                        fontFamily = mainFamily,
                                        fontSize = 20.sp
                                    )
                                )
                            }

                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            try {
                                if(nameTextState.text.isNotBlank() && surnameTextState.text.isNotBlank()){
                                    viewModel.updateUserName(nameTextState.text)
                                    viewModel.updateUserSurname(surnameTextState.text)
                                    Toast.makeText(
                                        instance,
                                        toastCorrectText,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    openDialog = false
                                }else if(nameTextState.text.isNotBlank() && surnameTextState.text.isBlank()){
                                    viewModel.updateUserName(nameTextState.text)
                                    Toast.makeText(
                                        instance,
                                        toastCorrectText,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    openDialog = false
                                }else if(nameTextState.text.isBlank() && surnameTextState.text.isNotBlank()){
                                    viewModel.updateUserSurname(surnameTextState.text)
                                    Toast.makeText(
                                        instance,
                                        toastCorrectText,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    openDialog = false
                                }else{
                                    Toast.makeText(
                                        instance,
                                        "Wpisz nowe dane",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(
                                    instance,
                                    toastFailureText,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    ) {
                        Text(
                            text = confirmButtonText,
                            color = BlueGray50,
                            fontFamily = mainFamily,
                            fontSize = 20.sp,
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog = false
                        }
                    ) {
                        Text(
                            text = dismissButtonText,
                            color = BlueGray50,
                            fontFamily = mainFamily,
                            fontSize = 20.sp,
                        )
                    }
                },
                backgroundColor = BlueGray800,
                contentColor = BlueGray50
            )
        }

        return openDialog

    }



}