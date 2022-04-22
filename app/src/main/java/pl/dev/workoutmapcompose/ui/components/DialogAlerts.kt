package pl.dev.workoutmapcompose.ui.components

import android.app.DatePickerDialog
import android.content.Intent
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
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
import com.chargemap.compose.numberpicker.ListItemPicker
import com.chargemap.compose.numberpicker.NumberPicker
import pl.dev.workoutmapcompose.*
import pl.dev.workoutmapcompose.data.Exercise
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.data.WeightHistory
import pl.dev.workoutmapcompose.ui.screenAddNewTrainingPlan.AddNewTrainingPlanViewModel
import pl.dev.workoutmapcompose.ui.screenSettings.SettingsViewModel
import pl.dev.workoutmapcompose.ui.screenTrainingPlans.TrainingPlansViewModel
import pl.dev.workoutmapcompose.ui.screenWeightHistory.WeightHistoryViewModel
import pl.dev.workoutmapcompose.ui.theme.*
import java.util.*

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
                textContentColor = BlueGray50,
                titleContentColor = BlueGray50,
                containerColor = BlueGray800
            )
        }

        return openDialog
    }


    @Composable
    fun wipeTrainingPlansDataDialogAlert(
        instance: SettingsActivity,
        viewModel: SettingsViewModel
    ): Boolean {

        val dialogTitle = "Usuń plany"
        val dialogText = "Zatwierdzenie spowoduje usunięcie wszystkich zapisanych planów treningowych razem z ich postepami. Nie można tego cofnąć!!"
        val confirmButtonText = "USUŃ"
        val dismissButtonText = "ANULUJ"
        val toastCorrectText = "Plany zostały usunięte"
        val toastFailureText = "Błąd - plany nie zostały usunięte"

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
                            if (viewModel.wipeTrainingPlans()) {
                                Toast.makeText(
                                    instance,
                                    toastCorrectText,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
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
                textContentColor = BlueGray50,
                titleContentColor = BlueGray50,
                containerColor = BlueGray800
            )
        }

        return openDialog
    }


    @Composable
    fun changePersonalDataDialogAlert(
        instance: SettingsActivity,
        viewModel: SettingsViewModel
    ): Boolean {

        viewModel.getUserInfo()

        val dialogTitle = "Zmień dane personalne"
        val confirmButtonText = "ZMIEŃ"
        val dismissButtonText = "ANULUJ"
        val toastCorrectText = "Dane zostały zmienione"
        val toastFailureText = "Błąd - dane nie zostały zmienione"

        var nameTextState by remember { mutableStateOf(TextFieldValue()) }
        var surnameTextState by remember { mutableStateOf(TextFieldValue()) }
        var agePickerState by remember { mutableStateOf(viewModel.userInfoListResult.value!!.age.toInt()) }
        val possibleGenderValues = listOf("Mężczyzna", "Kobieta", "Inna")
        var genderPickerState by remember { mutableStateOf(viewModel.userInfoListResult.value!!.gender) }
        var heightPickerState by remember { mutableStateOf(viewModel.userInfoListResult.value!!.height.toInt()) }

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
                            .padding(top = 10.dp),
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

                                OutlinedTextField(
                                    value = nameTextState,
                                    onValueChange = { nameTextState = it },
                                    textStyle = TextStyle(
                                        color = BlueGray50,
                                        fontFamily = mainFamily,
                                        fontSize = 20.sp
                                    ),
                                    maxLines = 1,
                                    label = {
                                        Text(
                                            text = "Imię",
                                            color = BlueGray50
                                        )
                                    }
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

                                OutlinedTextField(
                                    value = surnameTextState,
                                    onValueChange = { surnameTextState = it },
                                    textStyle = TextStyle(
                                        color = BlueGray50,
                                        fontFamily = mainFamily,
                                        fontSize = 20.sp
                                    ),
                                    maxLines = 1,
                                    label = {
                                        Text(
                                            text = "Nazwisko",
                                            color = BlueGray50
                                        )
                                    }
                                )

                            }
                        }

                        Spacer(
                            modifier = Modifier
                                .height(20.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Column(
                                modifier = Modifier
                                    .width(150.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = "Wiek:",
                                    color = BlueGray50,
                                    fontFamily = mainFamily,
                                    fontSize = 20.sp
                                )
                                NumberPicker(
                                    value = agePickerState,
                                    range = 1..99,
                                    onValueChange = {
                                        agePickerState = it
                                    },
                                    textStyle = TextStyle(color = BlueGray50, fontFamily = mainFamily),

                                    )
                            }
                            Column(
                                modifier = Modifier
                                    .width(150.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Płeć:",
                                    color = BlueGray50,
                                    fontFamily = mainFamily,
                                    fontSize = 20.sp
                                )
                                ListItemPicker(
                                    label = { it },
                                    value = genderPickerState,
                                    onValueChange = { genderPickerState = it },
                                    list = possibleGenderValues,
                                    textStyle = TextStyle(color = BlueGray50, fontFamily = mainFamily)
                                )
                            }
                        }

                        Spacer(
                            modifier = Modifier
                                .height(20.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                text = "Wzrost: (cm)",
                                color = BlueGray50,
                                fontFamily = mainFamily,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(end = 20.dp)
                            )
                            NumberPicker(
                                value = heightPickerState,
                                range = 100..250,
                                onValueChange = {
                                    heightPickerState = it
                                },
                                textStyle = TextStyle(color = BlueGray50, fontFamily = mainFamily)
                            )
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
                                    viewModel.updateUserAge(agePickerState.toString())
                                    viewModel.updateUserHeight(heightPickerState.toString())
                                    viewModel.updateUserGender(genderPickerState)
                                    Toast.makeText(
                                        instance,
                                        toastCorrectText,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    openDialog = false
                                }else if(nameTextState.text.isNotBlank() && surnameTextState.text.isBlank()){
                                    viewModel.updateUserName(nameTextState.text)
                                    viewModel.updateUserAge(agePickerState.toString())
                                    viewModel.updateUserHeight(heightPickerState.toString())
                                    viewModel.updateUserGender(genderPickerState)
                                    Toast.makeText(
                                        instance,
                                        toastCorrectText,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    openDialog = false
                                }else if(nameTextState.text.isBlank() && surnameTextState.text.isNotBlank()){
                                    viewModel.updateUserSurname(surnameTextState.text)
                                    viewModel.updateUserAge(agePickerState.toString())
                                    viewModel.updateUserHeight(heightPickerState.toString())
                                    viewModel.updateUserGender(genderPickerState)
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
                textContentColor = BlueGray50,
                titleContentColor = BlueGray50,
                containerColor = BlueGray800
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
                                contentColor = BlueGray500
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
                textContentColor = BlueGray50,
                titleContentColor = BlueGray50,
                containerColor = BlueGray800
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
                textContentColor = BlueGray50,
                titleContentColor = BlueGray50,
                containerColor = BlueGray800
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
        val newExercise by remember {
            mutableStateOf(Exercise("","", 0))
        }
        var setsTextState by remember {
            mutableStateOf(TextFieldValue(""))
        }
        val exerciseTypes = listOf("Klatka piersiowa", "Plecy", "Barki", "Biceps", "Triceps", "Nogi", "Przedramiona", "Brzuch")
        var exerciseTypesSelected by remember { mutableStateOf("Wybierz partię") }
        var exerciseSelected by remember { mutableStateOf("Wybierz ćwiczenie") }
        var expanded by remember { mutableStateOf(false) }
        var expanded2 by remember { mutableStateOf(false) }

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
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(
                            modifier = Modifier
                                .clickable {
                                    expanded = !expanded
                                }
                                .padding(top = 10.dp)
                        ) {

                            Text(
                                text = exerciseTypesSelected,
                                fontSize = 15.sp
                            )

                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown"
                            )

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = {
                                    expanded = false
                                }
                            ) {
                                exerciseTypes.forEach {
                                    DropdownMenuItem(
                                        onClick = {
                                            exerciseTypesSelected = it
                                            expanded = false
                                            viewModel.getExercisesArrayList(exerciseTypesSelected)
                                            exerciseSelected = "Wybierz ćwiczenie"
                                        }
                                    ) {
                                        Text(
                                            text = it
                                        )
                                    }
                                }
                            }

                        }

                        if (exerciseTypesSelected != "Wybierz partię") {
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        expanded2 = !expanded2
                                    }
                                    .padding(top = 10.dp)
                            ) {

                                Text(
                                    text = exerciseSelected,
                                    fontSize = 15.sp
                                )

                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Dropdown"
                                )

                                DropdownMenu(
                                    expanded = expanded2,
                                    onDismissRequest = {
                                        expanded2 = false
                                    }
                                ) {
                                    viewModel.exercisesListResult.value.forEach {
                                        DropdownMenuItem(
                                            onClick = {
                                                exerciseSelected = it
                                                expanded2 = false
                                            }
                                        ) {
                                            Text(
                                                text = it
                                            )
                                        }
                                    }
                                }
                            }
                        } else {
                            Spacer(
                                modifier = Modifier
                                    .height(40.dp)
                            )
                        }

                        Spacer(
                            modifier = Modifier
                                .height(15.dp)
                        )

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .background(
                                    color = BlueGray800
                                ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal
                            ),
                            value = setsTextState,
                            onValueChange = {
                                if(it.text.isNotBlank() && it.text.toFloat()<40){
                                    setsTextState = it
                                }else{
                                    setsTextState = TextFieldValue("")
                                    Toast.makeText(
                                        instance,
                                        "Zła liczba serii",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            textStyle = TextStyle(
                                color = BlueGray50,
                                fontFamily = mainFamily,
                                fontSize = 25.sp
                            ),
                            maxLines = 1,
                            label = {
                                Text(
                                    text = "Liczba serii",
                                    color = BlueGray50
                                )
                            }
                        )

                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            try{
                                newExercise.numberOfSets = setsTextState.text.toFloat().toInt()
                                newExercise.type = exerciseTypesSelected
                                newExercise.name = exerciseSelected
                            }catch (e: Exception){
                                Toast.makeText(
                                    instance,
                                    "Podaj odpowiednie dane",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

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
                textContentColor = BlueGray50,
                titleContentColor = BlueGray50,
                containerColor = BlueGray800
            )
        }

        return openDialog

    }


    @Composable
    fun trainingPlansInfoDialogAlert(
        instance: TrainingPlansActivity,
        viewModel: TrainingPlansViewModel,
        trainingPlan: TrainingPlan
    ): Boolean {

        val dialogTitle = trainingPlan.planName
        val confirmButtonText = "USUŃ"
        val dismissButtonText = "COFNIJ"
        val toastCorrectText = "Plan został usunięty"
        val toastFailureText = "Błąd - plan nie została usunięty"

        var openDialog by remember {
            mutableStateOf(true)
        }

        if (openDialog) {
            AlertDialog(
                modifier = Modifier
                    .height(400.dp),
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

                    LazyColumn(
                        modifier = Modifier
                            .height(350.dp)

                    ) {
                        items(count = trainingPlan.exercise.size) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(ambientColor = Color.Black, elevation = 4.dp)
                                    .background(
                                        color = BlueGray900
                                    )
                                    .padding(10.dp)
                            ) {

                                Text(
                                    text = "${trainingPlan.exercise[it].type} - ${trainingPlan.exercise[it].name} (${trainingPlan.exercise[it].numberOfSets} serii)"
                                )
                            }

                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                            )

                        }
                    }

                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog = false

                            try {
                                viewModel.deleteTrainingPlan(trainingPlan = trainingPlan)
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
                textContentColor = BlueGray50,
                titleContentColor = BlueGray50,
                containerColor = BlueGray800
            )
        }

        return openDialog

    }



}