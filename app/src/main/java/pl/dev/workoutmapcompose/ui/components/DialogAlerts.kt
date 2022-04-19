package pl.dev.workoutmapcompose.ui.components

import android.app.DatePickerDialog
import android.content.Intent
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.dev.workoutmapcompose.*
import pl.dev.workoutmapcompose.data.Exercise
import pl.dev.workoutmapcompose.data.WeightHistory
import pl.dev.workoutmapcompose.ui.screenAddNewTrainingPlan.AddNewTrainingPlanViewModel
import pl.dev.workoutmapcompose.ui.screenSettings.SettingsViewModel
import pl.dev.workoutmapcompose.ui.screenWeightHistory.WeightHistoryViewModel
import pl.dev.workoutmapcompose.ui.theme.BlueGray50
import pl.dev.workoutmapcompose.ui.theme.BlueGray500
import pl.dev.workoutmapcompose.ui.theme.BlueGray800
import pl.dev.workoutmapcompose.ui.theme.mainFamily
import java.util.*
import kotlin.collections.ArrayList

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


    @Composable
    fun insertNewWeightDialogAlert(
        instance: WeightHistoryActivity,
        viewModel: WeightHistoryViewModel
    ): Boolean {

        val dialogTitle = "Dodaj nową wagę"
        val confirmButtonText = "DODAJ"
        val dismissButtonText = "ANULUJ"
        val toastCorrectText = "Waga została dodana"
        val toastFailureText = "Błąd - waga nie została dodana"

        val mContext = LocalContext.current
        val mCalendar = Calendar.getInstance()

        val mYear = mCalendar.get(Calendar.YEAR)
        val mMonth = mCalendar.get(Calendar.MONTH)
        val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
        var cYear = mCalendar.get(Calendar.YEAR)
        var cMonth = mCalendar.get(Calendar.MONTH)
        var cDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        println("c - $cYear $cMonth $cDay")

        mCalendar.time = Date()

        val mDate = remember {
            mutableStateOf("$mDay/${mMonth+1}/$mYear")
        }

        var weightTextState by remember {
            mutableStateOf(TextFieldValue())
        }

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
                        fontSize = 30.sp
                    )
                },
                text = {
                    val mDatePickerDialog = DatePickerDialog(
                        mContext,
                        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
                            cYear = mYear
                            cMonth = mMonth
                            cDay = mDayOfMonth
                        }, mYear, mMonth, mDay
                    )

                    println("m - $mYear $mMonth $mDay")

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        Text(
                            modifier = Modifier
                                .height(15.dp),
                            text = ""
                        )

                        Button(
                            modifier = Modifier
                                .shadow(ambientColor = Color.Black, shape = RectangleShape, elevation = 10.dp),
                            onClick = {
                                mDatePickerDialog.show()
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = BlueGray500
                            ),
                        ) {
                            Text(
                                text = mDate.value,
                                color = Color.White
                            )
                        }

                        Spacer(
                            modifier = Modifier
                                .size(40.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth(0.4f),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                value = weightTextState,
                                onValueChange = { weightTextState = it },
                                textStyle = TextStyle(color = BlueGray50, fontFamily = mainFamily, fontSize = 25.sp),
                                maxLines = 1,
                            )
                            Text(
                                text = "kg",
                                color = BlueGray50,
                                fontFamily = mainFamily,
                                fontSize = 40.sp,
                                textAlign = TextAlign.Left
                            )

                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if(weightTextState.text.isBlank()){
                                Toast.makeText(
                                    instance,
                                    "Musisz podać wagę",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }else{
                                if(cYear>mYear || cMonth>mMonth || cDay>mDay){
                                    Toast.makeText(
                                        instance,
                                        "Nie możesz ustawiać przyszłej daty",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }else{
                                    openDialog = false
                                    val newWeightHistory = WeightHistory(
                                        weight = weightTextState.text,
                                        weighingDate = Convert.convertIntValuesToTimeInSec(cYear, cMonth, cDay)
                                    )

                                    try{
                                        viewModel.insertNewWeightHistory(weightHistory = newWeightHistory)
                                        Toast.makeText(
                                            instance,
                                            toastCorrectText,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }catch (e: Exception){
                                        Toast.makeText(
                                            instance,
                                            toastFailureText,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
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
    fun onWeightHistoryRowClickDialogAlert(
        instance: WeightHistoryActivity,
        viewModel: WeightHistoryViewModel,
        weightHistory: WeightHistory
    ): Boolean {

        val dialogTitle = Convert.convertTimeInSecToDateString(weightHistory.weighingDate)
        val dialogText = "Zatwierdzenie spowoduje usunięcie danego zapisu wagi, nie można tego cofnąć!"
        val confirmButtonText = "USUŃ"
        val dismissButtonText = "ANULUJ"
        val toastCorrectText = "Waga została usunięta"
        val toastFailureText = "Błąd - waga nie została usunięta"


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
                        fontSize = 30.sp
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

                            try {
                                viewModel.deleteWeightHistory(weightHistory = weightHistory)
                                Toast.makeText(
                                    instance,
                                    toastCorrectText,
                                    Toast.LENGTH_SHORT
                                ).show()
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


    @Composable
    fun addExerciseForTrainingPlanDialogAlert(
        instance: AddNewTrainingPlanActivity,
        viewModel: AddNewTrainingPlanViewModel,
        selectedExercisesList: SnapshotStateList<Exercise>
    ): Boolean {

        val dialogTitle = "Dodaj ćwiczenię"
        val confirmButtonText = "DODAJ"
        val dismissButtonText = "ANULUJ"
        val toastCorrectText = "Ćwiczenie zostało dodane"
        val toastFailureText = "Błąd - ćwiczenie nie została usunięta"


        var openDialog by remember {
            mutableStateOf(true)
        }
        var newExercise by remember {
            mutableStateOf(Exercise("","", 0))
        }
        var setsTextState by remember {
            mutableStateOf(TextFieldValue())
        }
        val exerciseTypes = listOf("Klatka piersiowa", "Plecy", "Barki", "Biceps", "Triceps", "Nogi", "Przedramiona", "Brzuch")
        var exerciseTypesSelectedType by remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }

        if (openDialog) {
            AlertDialog(
                onDismissRequest = {
                    openDialog = false
                },
                title = {
                    Text(
                        text = dialogTitle,
                        fontFamily = mainFamily,
                        fontSize = 30.sp
                    )
                },
                text = {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(0.4f),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            value = setsTextState,
                            onValueChange = { setsTextState = it },
                            textStyle = TextStyle(color = BlueGray50, fontFamily = mainFamily, fontSize = 25.sp),
                            maxLines = 1,
                        )

                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {

                            newExercise.numberOfSets = setsTextState.text.toInt()

                            if (newExercise.name.isBlank() || newExercise.type.isBlank()) {
                                Toast.makeText(
                                    instance,
                                    "Podaj odpowiednie dane",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                if (newExercise.numberOfSets in 1..10) {
                                    openDialog = false
                                    try {
                                        selectedExercisesList.add(newExercise)
                                        Toast.makeText(
                                            instance,
                                            toastCorrectText,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            instance,
                                            toastFailureText,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    Toast.makeText(
                                        instance,
                                        "Liczba serii musi mieścić się w przedziale 1-10",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

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