package pl.dev.workoutmapcompose

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.lifecycle.ViewModelProvider
import pl.dev.workoutmapcompose.datbase.WMViewModel
import pl.dev.workoutmapcompose.ui.theme.BlueGray50
import pl.dev.workoutmapcompose.ui.theme.BlueGray800
import pl.dev.workoutmapcompose.ui.theme.BlueGray900
import pl.dev.workoutmapcompose.ui.theme.mainFamily

class SettingsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        MainActivity.viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(WMViewModel::class.java)

        setContent {
            MainSettingsView(instance = this)
        }
    }

}



fun exitSettings(
    instance: SettingsActivity
){
    //TODO
    instance.finish()
}


@Composable
fun wipeDataAlertDialog(
    instance: SettingsActivity
): Boolean {

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
                    text = "Usuń dane",
                    fontFamily = mainFamily,
                    fontSize = 30.sp,
                )
            },
            text = {
                Text(
                    text = "Zatwierdzenie spowoduje usunięcie wszelkich postępów i cofnie Ciebie do stanu początkowego aplikacji. Nie można tego cofnąć!!",
                    fontSize = 15.sp,
                )
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        if(MainActivity.viewModel.wipeData()){
                            Toast.makeText(instance, "Dane zostały zresetowane", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(instance, RegisterActivity::class.java)
                            instance.startActivity(intent)
                        }else{
                            Toast.makeText(instance, "Dane nie mogły być usunięte", Toast.LENGTH_SHORT)
                                .show()
                        }

                    }
                ) {
                    Text(
                        text = "USUŃ",
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
                        text = "ANULUJ",
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
fun MainSettingsView(
    instance: SettingsActivity
) {

    var openWipeDataDialog by remember {
        mutableStateOf(false)
    }

    if(openWipeDataDialog){
        openWipeDataDialog = wipeDataAlertDialog(instance)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGray900)
            .padding(10.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 35.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    exitSettings(instance)
                }) {
                Icon(
                    Icons.Filled.ArrowBack, contentDescription = "Back button"
                )
            }
            Text(
                text = "USTAWIENIA",
                color = Color.Black,
                fontFamily = mainFamily,
                fontSize = 30.sp
            )
        }

        Spacer(
            modifier = Modifier
                .background(color = Color.Black)
                .height(2.dp)
                .fillMaxWidth()
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

                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = BlueGray800
                )
            ) {
                Text(
                    text = "Zmień dane",
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