package pl.dev.workoutmapcompose.ui.utils

import android.app.DatePickerDialog
import android.content.Intent
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.ListItemPicker
import com.chargemap.compose.numberpicker.NumberPicker
import pl.dev.workoutmapcompose.App.Companion.applicationContext
import pl.dev.workoutmapcompose.MainActivity
import pl.dev.workoutmapcompose.RegisterActivity
import pl.dev.workoutmapcompose.SettingsActivity
import pl.dev.workoutmapcompose.WorkoutActivity
import pl.dev.workoutmapcompose.data.Exercise
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.data.UserInfo
import pl.dev.workoutmapcompose.data.WeightHistory
import pl.dev.workoutmapcompose.ui.screenAddNewTrainingPlan.AddNewTrainingPlanViewModel
import pl.dev.workoutmapcompose.ui.screenDashboard.DashboardViewModel
import pl.dev.workoutmapcompose.ui.screenSettings.SettingsViewModel
import pl.dev.workoutmapcompose.ui.screenTrainingPlans.TrainingPlansViewModel
import pl.dev.workoutmapcompose.ui.screenWeightHistory.WeightHistoryViewModel
import pl.dev.workoutmapcompose.ui.theme.BlueGray50
import pl.dev.workoutmapcompose.ui.theme.BlueGray900
import pl.dev.workoutmapcompose.ui.theme.Purple500
import pl.dev.workoutmapcompose.ui.theme.mainFamily
import java.util.*


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
                                showShortToastError(
                                    textError = toastCorrectText
                                )
                                instance.startActivity(Intent(instance, RegisterActivity::class.java))
                            } else {
                                showShortToastError(
                                    textError = toastFailureText
                                )
                            }
                        }
                    ) {
                        Text(
                            text = confirmButtonText,
                            style = MaterialTheme.typography.caption,
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
                            style = MaterialTheme.typography.caption,
                            fontSize = 20.sp,
                        )
                    }
                },
                textContentColor = MaterialTheme.typography.caption.color,
                titleContentColor = MaterialTheme.typography.caption.color,
                containerColor = MaterialTheme.colors.surface
            )
        }

        return openDialog
    }


    @Composable
    fun wipeTrainingPlansDataDialogAlert(
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
                                showShortToastError(
                                    textError = toastCorrectText
                                )
                            } else {
                                showShortToastError(
                                    textError = toastFailureText
                                )
                            }
                        }
                    ) {
                        Text(
                            text = confirmButtonText,
                            style = MaterialTheme.typography.caption,
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
                            style = MaterialTheme.typography.caption,
                            fontSize = 20.sp,
                        )
                    }
                },
                textContentColor = MaterialTheme.typography.caption.color,
                titleContentColor = MaterialTheme.typography.caption.color,
                containerColor = MaterialTheme.colors.surface
            )
        }

        return openDialog
    }


    @Composable
    fun changePersonalDataDialogAlert(
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
        var agePickerState by remember { mutableStateOf(viewModel.userInfoResult.value!!.age.toInt()) }
        val possibleGenderValues = listOf("Mężczyzna", "Kobieta", "Inna")
        var genderPickerState by remember { mutableStateOf(viewModel.userInfoResult.value!!.gender) }
        var heightPickerState by remember { mutableStateOf(viewModel.userInfoResult.value!!.height.toInt()) }

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
                                        color = MaterialTheme.typography.caption.color,
                                        fontFamily = mainFamily,
                                        fontSize = 20.sp
                                    ),
                                    singleLine = true,
                                    label = {
                                        Text(
                                            text = "Imię",
                                            color = MaterialTheme.typography.caption.color,
                                            fontFamily = mainFamily,
                                            fontSize = 15.sp
                                        )
                                    },
                                    colors = TextFieldDefaults.textFieldColors(
                                        containerColor = MaterialTheme.colors.secondary,
                                        focusedIndicatorColor = Purple500,
                                        cursorColor = Purple500
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

                                OutlinedTextField(
                                    value = surnameTextState,
                                    onValueChange = { surnameTextState = it },
                                    textStyle = TextStyle(
                                        color = MaterialTheme.typography.caption.color,
                                        fontFamily = mainFamily,
                                        fontSize = 20.sp
                                    ),
                                    singleLine = true,
                                    label = {
                                        Text(
                                            text = "Nazwisko",
                                            color = MaterialTheme.typography.caption.color,
                                            fontFamily = mainFamily,
                                            fontSize = 15.sp
                                        )
                                    },
                                    colors = TextFieldDefaults.textFieldColors(
                                        containerColor = MaterialTheme.colors.secondary,
                                        focusedIndicatorColor = Purple500,
                                        cursorColor = Purple500
                                    )
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
                                    style = MaterialTheme.typography.caption,
                                    fontSize = 20.sp
                                )
                                NumberPicker(
                                    value = agePickerState,
                                    range = 1..99,
                                    onValueChange = {
                                        agePickerState = it
                                    },
                                    textStyle = MaterialTheme.typography.caption,
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .width(150.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Płeć:",
                                    style = MaterialTheme.typography.caption,
                                    fontSize = 20.sp
                                )
                                ListItemPicker(
                                    label = { it },
                                    value = genderPickerState,
                                    onValueChange = { genderPickerState = it },
                                    list = possibleGenderValues,
                                    textStyle = MaterialTheme.typography.caption
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
                                style = MaterialTheme.typography.caption,
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
                                textStyle = MaterialTheme.typography.caption
                            )
                        }

                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            try {
                                val updatedUserInfo = UserInfo(
                                    userFirebaseID = viewModel.userInfoResult.value!!.userFirebaseID,
                                    name = viewModel.userInfoResult.value!!.name,
                                    surName = viewModel.userInfoResult.value!!.surName,
                                    age = viewModel.userInfoResult.value!!.age,
                                    height = viewModel.userInfoResult.value!!.height,
                                    gender = viewModel.userInfoResult.value!!.gender,
                                    lastTrainingDate = viewModel.userInfoResult.value!!.lastTrainingDate
                                )

                                if(nameTextState.text.isNotBlank() && surnameTextState.text.isNotBlank()){
                                    updatedUserInfo.name = nameTextState.text
                                    updatedUserInfo.surName = surnameTextState.text
                                    updatedUserInfo.age = agePickerState.toString()
                                    updatedUserInfo.height = heightPickerState.toString()
                                    updatedUserInfo.gender = genderPickerState
                                }else if(nameTextState.text.isNotBlank() && surnameTextState.text.isBlank()){
                                    updatedUserInfo.name = nameTextState.text
                                    updatedUserInfo.age = agePickerState.toString()
                                    updatedUserInfo.height = heightPickerState.toString()
                                    updatedUserInfo.gender = genderPickerState
                                }else if(nameTextState.text.isBlank() && surnameTextState.text.isNotBlank()){
                                    updatedUserInfo.surName = surnameTextState.text
                                    updatedUserInfo.age = agePickerState.toString()
                                    updatedUserInfo.height = heightPickerState.toString()
                                    updatedUserInfo.gender = genderPickerState
                                }else{
                                    updatedUserInfo.age = agePickerState.toString()
                                    updatedUserInfo.height = heightPickerState.toString()
                                    updatedUserInfo.gender = genderPickerState
                                }
                                viewModel.updateUserPersonalInfo(userInfo = updatedUserInfo)
                                showShortToastError(
                                    textError = toastCorrectText
                                )
                                openDialog = false

                            } catch (e: Exception) {
                                showShortToastError(
                                    textError = toastFailureText
                                )
                            }
                        }
                    ) {
                        Text(
                            text = confirmButtonText,
                            style = MaterialTheme.typography.caption,
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
                            style = MaterialTheme.typography.caption,
                            fontSize = 20.sp,
                        )
                    }
                },
                textContentColor = MaterialTheme.typography.caption.color,
                titleContentColor = MaterialTheme.typography.caption.color,
                containerColor = MaterialTheme.colors.surface
            )
        }

        return openDialog

    }


    @Composable
    fun insertNewWeightDialogAlert(
        viewModel: WeightHistoryViewModel
    ): Boolean {

        val dialogTitle = "Dodaj nową wagę"
        val confirmButtonText = "DODAJ"
        val dismissButtonText = "ANULUJ"
        val toastCorrectText = "Waga została dodana"
        val toastFailureText = "Błąd - waga nie została dodana"
        val nonWeightError = "Musisz podać wagę"
        val futureDateError =  "Nie możesz ustawiać przyszłej daty"
        val tooMuchWeightError = "Podałeś za dużą wagę"

        val mCalendar = Calendar.getInstance()

        val mYear = mCalendar.get(Calendar.YEAR)
        val mMonth = mCalendar.get(Calendar.MONTH)
        val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
        var cYear = mCalendar.get(Calendar.YEAR)
        var cMonth = mCalendar.get(Calendar.MONTH)
        var cDay = mCalendar.get(Calendar.DAY_OF_MONTH)

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
                        LocalContext.current,
                        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
                            cYear = mYear
                            cMonth = mMonth
                            cDay = mDayOfMonth
                        }, mYear, mMonth, mDay,
                    )

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
                                .shadow(ambientColor = Color.Black, shape = CircleShape, elevation = 10.dp),
                            onClick = {
                                mDatePickerDialog.show()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colors.secondary ,
                                contentColor = MaterialTheme.typography.caption.color,
                            ),
                        ) {
                            Text(
                                text = "Data: " + mDate.value,
                                color = MaterialTheme.typography.caption.color,
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
                                    keyboardType = KeyboardType.Decimal
                                ),
                                value = weightTextState,
                                onValueChange = { weightTextState = it },
                                textStyle = TextStyle(
                                    color = BlueGray50,
                                    fontFamily = mainFamily,
                                    fontSize = 25.sp
                                ),
                                maxLines = 1,
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = BlueGray900
                                )
                            )
                            Text(
                                text = "kg",
                                style = MaterialTheme.typography.caption,
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
                                showShortToastError(
                                    textError = nonWeightError
                                )
                            }else{
                                if(cYear>mYear || cMonth>mMonth || cDay>mDay){
                                    showShortToastError(
                                        textError = futureDateError
                                    )
                                }else{
                                    if(weightTextState.text.toFloat()>300){
                                        showShortToastError(
                                            textError = tooMuchWeightError
                                        )
                                    }else{
                                        openDialog = false
                                        val newWeightHistory = WeightHistory(
                                            weight = weightTextState.text,
                                            weighingDate = DateTimeFunctionalities.convertIntValuesToDateInSec(cYear, cMonth, cDay)
                                        )

                                        try{
                                            viewModel.insertNewWeightHistory(weightHistory = newWeightHistory)
                                            showShortToastError(
                                                textError = toastCorrectText
                                            )
                                        }catch (e: Exception){
                                            showShortToastError(
                                                textError = toastFailureText
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    ) {
                        Text(
                            text = confirmButtonText,
                            style = MaterialTheme.typography.caption,
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
                            style = MaterialTheme.typography.caption,
                            fontSize = 20.sp,
                        )
                    }
                },
                textContentColor = MaterialTheme.typography.caption.color,
                titleContentColor = MaterialTheme.typography.caption.color,
                containerColor = MaterialTheme.colors.surface
            )
        }

        return openDialog

    }


    @Composable
    fun onWeightHistoryRowClickDialogAlert(
        viewModel: WeightHistoryViewModel,
        weightHistory: WeightHistory
    ): Boolean {

        val dialogTitle = DateTimeFunctionalities.convertDateInSecToDateString(weightHistory.weighingDate)
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
                                showShortToastError(
                                    textError = toastCorrectText
                                )
                            } catch (e: Exception) {
                                showShortToastError(
                                    textError = toastFailureText
                                )
                            }

                        }
                    ) {
                        Text(
                            text = confirmButtonText,
                            style = MaterialTheme.typography.caption,
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
                            style = MaterialTheme.typography.caption,
                            fontSize = 20.sp,
                        )
                    }
                },
                textContentColor = MaterialTheme.typography.caption.color,
                titleContentColor = MaterialTheme.typography.caption.color,
                containerColor = MaterialTheme.colors.surface
            )
        }

        return openDialog

    }


    @Composable
    fun addExerciseForTrainingPlanDialogAlert(
        viewModel: AddNewTrainingPlanViewModel,
        selectedExercisesList: SnapshotStateList<Exercise>
    ): Boolean {

        val dialogTitle = "Dodaj ćwiczenię"
        val confirmButtonText = "DODAJ"
        val dismissButtonText = "ANULUJ"
        val toastCorrectText = "Ćwiczenie zostało dodane"
        val toastFailureText = "Błąd - ćwiczenie nie została usunięta"
        val incorrectSetsNumberError = "Liczba serii musi mieścić się w przedziale 1-20"
        val incorrectDataError = "Podaj odpowiednie dane"


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
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
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
                                    text = TextModifier.convertExerciseNameToBetterText(exerciseSelected),
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
                                                text = TextModifier.convertExerciseNameToBetterText(it)
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
                                .height(20.dp)
                        )

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(0.5f),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal
                            ),
                            value = setsTextState,
                            onValueChange = {
                                if(it.text.isNotBlank() && it.text.toFloat()<=20){
                                    setsTextState = it
                                }else{
                                    setsTextState = TextFieldValue("")
                                    showShortToastError(
                                        textError = incorrectSetsNumberError
                                    )
                                }
                            },
                            textStyle = TextStyle(
                                color = MaterialTheme.typography.caption.color,
                                fontFamily = mainFamily,
                                fontSize = 20.sp
                            ),
                            singleLine = true,
                            label = {
                                Text(
                                    text = "Liczba serii",
                                    color = MaterialTheme.typography.caption.color,
                                    fontFamily = mainFamily,
                                    fontSize = 15.sp
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = MaterialTheme.colors.secondary,
                                focusedIndicatorColor = Purple500,
                                cursorColor = Purple500
                            )
                        )

                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            newExercise.type = exerciseTypesSelected
                            newExercise.name = exerciseSelected
                            try{
                                newExercise.numberOfSets = setsTextState.text.toFloat().toInt()
                            }catch (e: Exception){
                                showShortToastError(
                                    textError = incorrectDataError
                                )
                            }
                            if (newExercise.name == "Wybierz ćwiczenie" || newExercise.name.isBlank() || newExercise.type.isBlank()) {
                                showShortToastError(
                                    textError = incorrectDataError
                                )
                            } else {
                                if (newExercise.numberOfSets in 1..20) {
                                    openDialog = false
                                    try {
                                        selectedExercisesList.add(newExercise)
                                        showShortToastError(
                                            textError = toastCorrectText
                                        )
                                    } catch (e: Exception) {
                                        showShortToastError(
                                            textError = toastFailureText
                                        )
                                    }
                                } else {
                                    showShortToastError(
                                        textError = incorrectSetsNumberError
                                    )
                                }

                            }
                        }
                    ) {
                        Text(
                            text = confirmButtonText,
                            style = MaterialTheme.typography.caption,
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
                            style = MaterialTheme.typography.caption,
                            fontSize = 20.sp,
                        )
                    }
                },
                textContentColor = MaterialTheme.typography.caption.color,
                titleContentColor = MaterialTheme.typography.caption.color,
                containerColor = MaterialTheme.colors.surface
            )
        }

        return openDialog

    }


    @Composable
    fun trainingPlansInfoDialogAlert(
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
                                        color = MaterialTheme.colors.secondary
                                    )
                                    .padding(10.dp)
                            ) {

                                Text(
                                    text = "${trainingPlan.exercise[it].type} - ${TextModifier.convertExerciseNameToBetterText(trainingPlan.exercise[it].name)} (${trainingPlan.exercise[it].numberOfSets} serii)",
                                    style = MaterialTheme.typography.caption,
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
                                showShortToastError(
                                    textError = toastCorrectText
                                )
                            } catch (e: Exception) {
                                showShortToastError(
                                    textError = toastFailureText
                                )
                            }
                        }
                    ) {
                        Text(
                            text = confirmButtonText,
                            style = MaterialTheme.typography.caption,
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
                            style = MaterialTheme.typography.caption,
                            fontSize = 20.sp,
                        )
                    }
                },
                textContentColor = MaterialTheme.typography.caption.color,
                titleContentColor = MaterialTheme.typography.caption.color,
                containerColor = MaterialTheme.colors.surface
            )
        }

        return openDialog

    }


    @Composable
    fun workoutStartDialogAlert(
        instance: MainActivity,
        viewModel: DashboardViewModel
    ): Boolean {

        viewModel.getTrainingPlansList()

        val dialogTitle = "TRENUJ"
        val confirmButtonText = "START"
        val dismissButtonText = "COFNIJ"
        val chooseTrainingPlanError = "Wybierz plan treningowy"

        val currentDayInt = DateTimeFunctionalities.getCurrentDayToIntValue()
        val trainingPlans = viewModel.trainingPlansListResult.value!!

        var defaultSelection = -1

        for(i in 0 until trainingPlans.size){
            if(trainingPlans[i].assignedDay == currentDayInt){
                defaultSelection = i
            }
        }

        var openDialog by remember {
            mutableStateOf(true)
        }

        var selectedTraining by remember {
            mutableStateOf(defaultSelection)
        }

        if (openDialog) {
            AlertDialog(
                modifier = Modifier
                    .height(350.dp),
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
                            .height(300.dp)

                    ) {
                        items(count = trainingPlans.size) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.typography.caption.color
                                    )
                                    .selectable(
                                        selected = selectedTraining == it,
                                        onClick = {
                                            if(selectedTraining != it){
                                                selectedTraining = it
                                            }
                                        }
                                    )
                                    .shadow(ambientColor = Color.Black, elevation = 4.dp)
                                    .background(
                                        if(selectedTraining == it)
                                            MaterialTheme.colors.secondary
                                        else
                                            MaterialTheme.colors.primary
                                    )
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {

                                Text(
                                    text = trainingPlans[it].planName,
                                    style = MaterialTheme.typography.caption,
                                    fontSize = 30.sp
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
                            if(selectedTraining!=-1){
                                openDialog = false
                                val intent = Intent(instance, WorkoutActivity::class.java).apply {
                                    putExtra("TRAINING_INDEX", selectedTraining)
                                }
                                instance.startActivity(intent)
                            }else{
                                showShortToastError(
                                    textError = chooseTrainingPlanError
                                )
                            }
                        }
                    ) {
                        Text(
                            text = confirmButtonText,
                            style = MaterialTheme.typography.caption,
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
                            style = MaterialTheme.typography.caption,
                            fontSize = 20.sp,
                        )
                    }
                },
                textContentColor = MaterialTheme.typography.caption.color,
                titleContentColor = MaterialTheme.typography.caption.color,
                containerColor = MaterialTheme.colors.surface
            )
        }

        return openDialog

    }


    @Composable
    fun workoutEndDialogAlert(
        instance: WorkoutActivity
    ): Boolean {

        val dialogTitle = "KONIEC TRENINGU"
        val dialogText = "Jeśli zakończysz aktualny trening wyniki zostaną wyzerowane!"
        val confirmButtonText = "ZAKOŃCZ"
        val dismissButtonText = "COFNIJ"

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
                            instance.finish()
                        }
                    ) {
                        Text(
                            text = confirmButtonText,
                            style = MaterialTheme.typography.caption,
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
                            style = MaterialTheme.typography.caption,
                            fontSize = 20.sp,
                        )
                    }
                },
                textContentColor = MaterialTheme.typography.caption.color,
                titleContentColor = MaterialTheme.typography.caption.color,
                containerColor = MaterialTheme.colors.surface
            )
        }

        return openDialog

    }


    private fun showShortToastError(textError: String) {
        Toast.makeText(
            applicationContext(),
            textError,
            Toast.LENGTH_SHORT
        ).show()
    }

