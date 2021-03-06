package pl.dev.workoutmapcompose.ui.utils

import android.app.DatePickerDialog
import android.content.Intent
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.NumberPicker
import pl.dev.workoutmapcompose.data.*
import pl.dev.workoutmapcompose.ui.screenAddNewTrainingPlan.AddNewTrainingPlanViewModel
import pl.dev.workoutmapcompose.ui.screenDashboard.DashboardViewModel
import pl.dev.workoutmapcompose.ui.screenDashboard.MainActivity
import pl.dev.workoutmapcompose.ui.screenRegistration.RegisterActivity
import pl.dev.workoutmapcompose.ui.screenSettings.SettingsActivity
import pl.dev.workoutmapcompose.ui.screenSettings.SettingsViewModel
import pl.dev.workoutmapcompose.ui.screenTrainingPlans.TrainingPlansViewModel
import pl.dev.workoutmapcompose.ui.screenWeightHistory.WeightHistoryViewModel
import pl.dev.workoutmapcompose.ui.screenWorkout.WorkoutActivity
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

        val dialogTitle = "Usu?? dane"
        val dialogText = "Zatwierdzenie spowoduje usuni??cie wszelkich post??p??w i cofnie Ciebie do stanu pocz??tkowego aplikacji. Nie mo??na tego cofn????!!"
        val confirmButtonText = "USU??"
        val dismissButtonText = "ANULUJ"
        val toastCorrectText = "Dane zosta??y usuni??te"
        val toastFailureText = "B????d - dane nie zosta??y usuni??te"

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
                                showShortToast(
                                    text = toastCorrectText
                                )
                                instance.startActivity(Intent(instance, RegisterActivity::class.java))
                            } else {
                                showShortToast(
                                    text = toastFailureText
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

        val dialogTitle = "Usu?? plany"
        val dialogText = "Zatwierdzenie spowoduje usuni??cie wszystkich zapisanych plan??w treningowych razem z ich postepami. Nie mo??na tego cofn????!!"
        val confirmButtonText = "USU??"
        val dismissButtonText = "ANULUJ"
        val toastCorrectText = "Plany zosta??y usuni??te"
        val toastFailureText = "B????d - plany nie zosta??y usuni??te"

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
                                showShortToast(
                                    text = toastCorrectText
                                )
                            } else {
                                showShortToast(
                                    text = toastFailureText
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

        val dialogTitle = "Zmie?? dane personalne"
        val confirmButtonText = "ZMIE??"
        val dismissButtonText = "ANULUJ"
        val toastCorrectText = "Dane zosta??y zmienione"
        val toastFailureText = "B????d - dane nie zosta??y zmienione"
        val possibleGenderValues = listOf("M????czyzna", "Kobieta", "Inna", "Nie chc?? podawa??")

        var nameTextState by remember {
            mutableStateOf(TextFieldValue())
        }
        var surnameTextState by remember {
            mutableStateOf(TextFieldValue())
        }
        var heightPickerState by remember {
            mutableStateOf(viewModel.userInfoResult.value!!.height.toInt())
        }
        var genderSelected by remember {
            mutableStateOf(viewModel.userInfoResult.value!!.gender)
        }
        var genderExpanded by remember {
            mutableStateOf(false)
        }
        var openDialog by remember {
            mutableStateOf(true)
        }

        val mCalendar = Calendar.getInstance()

        val mYear = mCalendar.get(Calendar.YEAR)
        val mMonth = mCalendar.get(Calendar.MONTH)
        val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
        var cYear = mCalendar.get(Calendar.YEAR)
        var cMonth = mCalendar.get(Calendar.MONTH)
        var cDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        mCalendar.time = Date()

        var mDate by remember {
            mutableStateOf(DateTimeFunctionalities.convertDateInSecToDateString(viewModel.userInfoResult.value!!.dateOfBirth.toInt()))
        }
        var dateOfBirth by remember {
            mutableStateOf(viewModel.userInfoResult.value!!.dateOfBirth)
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
                    val focusManager = LocalFocusManager.current
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                focusManager.clearFocus()
                            },
                    ) {

                        Spacer(
                            modifier = Modifier
                                .height(20.dp)
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Spacer(
                                modifier = Modifier
                                    .height(10.dp)
                            )
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
                                        text = "Imi??",
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

                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                        )


                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
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

                        Spacer(
                            modifier = Modifier
                                .height(40.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.6f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                val mDatePickerDialog = DatePickerDialog(
                                    LocalContext.current,
                                    { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                                        if(cYear<mYear || cMonth<mMonth || cDay<mDayOfMonth){
                                            showShortToast(
                                                text = "Nie mo??esz ustawia?? przysz??ej daty",
                                            )
                                        }else {
                                            mDate = "$mDayOfMonth/${mMonth+1}/$mYear"
                                            val calendar = Calendar.getInstance()
                                            calendar.set(mYear, mMonth, mDayOfMonth)
                                            dateOfBirth = (calendar.timeInMillis / 1000).toString()
                                            cYear = mYear
                                            cMonth = mMonth
                                            cDay = mDayOfMonth
                                        }
                                    }, mYear, mMonth, mDay,
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
                                        text = "Data urodzenia:\n$mDate",
                                        color = MaterialTheme.typography.caption.color,
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "P??e??:",
                                    style = MaterialTheme.typography.caption,
                                    fontSize = 25.sp
                                )
                                Row(
                                    modifier = Modifier
                                        .clickable {
                                            genderExpanded = !genderExpanded
                                        }
                                        .padding(top = 10.dp)
                                ) {

                                    Text(
                                        text = genderSelected,
                                        fontFamily = mainFamily,
                                        fontSize = 20.sp,
                                        color = MaterialTheme.typography.caption.color
                                    )

                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Dropdown",
                                        tint = MaterialTheme.typography.caption.color
                                    )

                                    DropdownMenu(
                                        modifier = Modifier
                                            .fillMaxHeight(0.3f),
                                        expanded = genderExpanded,
                                        onDismissRequest = {
                                            genderExpanded = false
                                        }
                                    ) {
                                        possibleGenderValues.forEach {
                                            DropdownMenuItem(
                                                onClick = {
                                                    genderSelected = it
                                                    genderExpanded = false
                                                }
                                            ) {
                                                Text(
                                                    text = it
                                                )
                                            }
                                            Divider()
                                        }
                                    }

                                }
                            }
                        }

                        Spacer(
                            modifier = Modifier
                                .height(35.dp)
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
                                    dateOfBirth = dateOfBirth,
                                    height = heightPickerState.toString(),
                                    gender = genderSelected,
                                    lastTrainingDate = viewModel.userInfoResult.value!!.lastTrainingDate
                                )
                                if(nameTextState.text.isNotBlank() && surnameTextState.text.isNotBlank()){
                                    updatedUserInfo.name = nameTextState.text
                                    updatedUserInfo.surName = surnameTextState.text
                                }else if(nameTextState.text.isNotBlank() && surnameTextState.text.isBlank()){
                                    updatedUserInfo.name = nameTextState.text

                                }else if(nameTextState.text.isBlank() && surnameTextState.text.isNotBlank()){
                                    updatedUserInfo.surName = surnameTextState.text
                                }
                                viewModel.updateUserPersonalInfo(userInfo = updatedUserInfo)
                                showShortToast(
                                    text = toastCorrectText
                                )
                                openDialog = false

                            } catch (e: Exception) {
                                showShortToast(
                                    text = toastFailureText
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

        val dialogTitle = "Dodaj now?? wag??"
        val confirmButtonText = "DODAJ"
        val dismissButtonText = "ANULUJ"
        val toastCorrectText = "Waga zosta??a dodana"
        val toastFailureText = "B????d - waga nie zosta??a dodana"
        val nonWeightError = "Musisz poda?? wag??"
        val futureDateError =  "Nie mo??esz ustawia?? przysz??ej daty"
        val tooMuchWeightError = "Poda??e?? za du???? wag??"

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

                    val focusManager = LocalFocusManager.current

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                focusManager.clearFocus()
                            },
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
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()
                                    }
                                ),
                                value = weightTextState,
                                onValueChange = { weightTextState = it },
                                textStyle = TextStyle(
                                    color = BlueGray50,
                                    fontFamily = mainFamily,
                                    fontSize = 25.sp
                                ),
                                singleLine = true,
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
                                showShortToast(
                                    text = nonWeightError
                                )
                            }else{
                                if(cYear>mYear || cMonth>mMonth || cDay>mDay){
                                    showShortToast(
                                        text = futureDateError
                                    )
                                }else{
                                    if(weightTextState.text.toFloat()>300){
                                        showShortToast(
                                            text = tooMuchWeightError
                                        )
                                    }else{
                                        openDialog = false
                                        val newWeightHistory = WeightHistory(
                                            weight = weightTextState.text,
                                            weighingDate = DateTimeFunctionalities.convertIntValuesToDateInSec(cYear, cMonth, cDay)
                                        )

                                        try{
                                            viewModel.insertNewWeightHistory(weightHistory = newWeightHistory)
                                            showShortToast(
                                                text = toastCorrectText
                                            )
                                        }catch (e: Exception){
                                            showShortToast(
                                                text = toastFailureText
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
        val dialogText = "Zatwierdzenie spowoduje usuni??cie danego zapisu wagi, nie mo??na tego cofn????!"
        val confirmButtonText = "USU??"
        val dismissButtonText = "ANULUJ"
        val toastCorrectText = "Waga zosta??a usuni??ta"
        val toastFailureText = "B????d - waga nie zosta??a usuni??ta"


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
                                showShortToast(
                                    text = toastCorrectText
                                )
                            } catch (e: Exception) {
                                showShortToast(
                                    text = toastFailureText
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

        val dialogTitle = "Dodaj ??wiczeni??"
        val confirmButtonText = "DODAJ"
        val dismissButtonText = "ANULUJ"
        val toastCorrectText = "??wiczenie zosta??o dodane"
        val toastFailureText = "B????d - ??wiczenie nie zosta??a usuni??ta"
        val incorrectSetsNumberError = "Liczba serii musi mie??ci?? si?? w przedziale 1-20"
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
        var exerciseTypesSelected by remember { mutableStateOf("Wybierz parti??") }
        var exerciseSelected by remember { mutableStateOf("Wybierz ??wiczenie") }
        var expanded by remember { mutableStateOf(false) }
        var expanded2 by remember { mutableStateOf(false) }

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
                    val focusManager = LocalFocusManager.current
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                focusManager.clearFocus()
                            },
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
                                modifier = Modifier
                                    .fillMaxHeight(0.3f),
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
                                            exerciseSelected = "Wybierz ??wiczenie"
                                        }
                                    ) {
                                        Text(
                                            text = it
                                        )
                                    }
                                    Divider()
                                }
                            }

                        }

                        if (exerciseTypesSelected != "Wybierz parti??") {
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
                                    modifier = Modifier
                                        .fillMaxHeight(0.3f),
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
                                        Divider()
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
                                    showShortToast(
                                        text = incorrectSetsNumberError
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
                                showShortToast(
                                    text = incorrectDataError
                                )
                            }
                            if (newExercise.name == "Wybierz ??wiczenie" || newExercise.name.isBlank() || newExercise.type.isBlank()) {
                                showShortToast(
                                    text = incorrectDataError
                                )
                            } else {
                                if (newExercise.numberOfSets in 1..20) {
                                    openDialog = false
                                    try {
                                        selectedExercisesList.add(newExercise)
                                        showShortToast(
                                            text = toastCorrectText
                                        )
                                    } catch (e: Exception) {
                                        showShortToast(
                                            text = toastFailureText
                                        )
                                    }
                                } else {
                                    showShortToast(
                                        text = incorrectSetsNumberError
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
        val confirmButtonText = "USU??"
        val dismissButtonText = "COFNIJ"
        val toastCorrectText = "Plan zosta?? usuni??ty"
        val toastFailureText = "B????d - plan nie zosta??a usuni??ty"

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

                    Text(
                        text = "Dzie?? treningu: ${DateTimeFunctionalities.getDayNameFromIntValue(trainingPlan.assignedDay)}",
                        fontFamily = mainFamily,
                        fontSize = 20.sp
                    )

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
                                showShortToast(
                                    text = toastCorrectText
                                )
                            } catch (e: Exception) {
                                showShortToast(
                                    text = toastFailureText
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

        for(i in trainingPlans.indices){
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
                                showShortToast(
                                    text = chooseTrainingPlanError
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
        val dialogText = "Je??li zako??czysz aktualny trening wyniki zostan?? wyzerowane!"
        val confirmButtonText = "ZAKO??CZ"
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


    @Composable
    fun workoutProgressInfoDialogAlert(
        viewModel: DashboardViewModel,
        exerciseProgress: ExerciseProgress,
        exerciseSelectedName: String
    ): Boolean {

        var setIndexSelected by remember {
            mutableStateOf(0)
        }
        var expanded by remember {
            mutableStateOf(false)
        }
        val newExerciseProgress by remember {
            mutableStateOf(
                ExerciseProgress(
                    dateOfWorkout = exerciseProgress.dateOfWorkout,
                    setsList =  exerciseProgress.setsList
                )
            )
        }
        var newRepsTextState by remember {
            mutableStateOf(TextFieldValue("0"))
        }
        var newWeightTextState by remember {
            mutableStateOf(TextFieldValue("0"))
        }
        var openDialog by remember {
            mutableStateOf(true)
        }
        val dialogTitle = DateTimeFunctionalities.convertDateInSecToDateString(
            exerciseProgress.dateOfWorkout.toInt()
        )
        val textInfo = "Zmie?? wybrane warto??ci serii, a nast??pnie kliknij 'ZAPISZ'"
        val confirmButtonText = "ZAPISZ"
        val dismissButtonText = "COFNIJ"

        fun setOutlinedTextFieldStates(exerciseProgressString: String): Boolean {

            val middleIndex = exerciseProgressString.indexOf("x")
            val repsValueString = exerciseProgressString.substring(0, middleIndex)
            val weightValueString = exerciseProgressString.substring(middleIndex + 1)

            newRepsTextState = TextFieldValue(repsValueString)
            newWeightTextState = TextFieldValue(weightValueString)

            return true
        }

        remember {
            setOutlinedTextFieldStates(
                exerciseProgressString = exerciseProgress.setsList[setIndexSelected]
            )
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
                        fontSize = 20.sp
                    )
                },
                text = {

                    val focusManager = LocalFocusManager.current

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                focusManager.clearFocus()
                            },
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Text(
                            text = textInfo,
                            fontFamily = mainFamily,
                            fontSize = 15.sp
                        )
                        Spacer(
                                modifier = Modifier
                                    .height(20.dp)
                                    .fillMaxWidth()
                                )

                        Row(
                            modifier = Modifier
                                .clickable {
                                    expanded = !expanded
                                }
                                .padding(top = 10.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = "${setIndexSelected + 1} seria | ${exerciseProgress.setsList[setIndexSelected]}",
                                fontFamily = mainFamily,
                                fontSize = 20.sp,
                                color = MaterialTheme.typography.caption.color
                            )

                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown",
                                tint = MaterialTheme.typography.caption.color
                            )

                            DropdownMenu(
                                modifier = Modifier
                                    .fillMaxHeight(0.3f),
                                expanded = expanded,
                                onDismissRequest = {
                                    expanded = false
                                }
                            ) {
                                exerciseProgress.setsList.forEachIndexed { index, it ->
                                    DropdownMenuItem(
                                        onClick = {
                                            setIndexSelected = index
                                            setOutlinedTextFieldStates(exerciseProgressString = it)
                                            expanded = false
                                        }
                                    ) {
                                        Text(
                                            text = "${index + 1} seria | $it"
                                        )
                                    }
                                    Divider()
                                }
                            }
                        }

                        Spacer(
                            modifier = Modifier
                                .height(20.dp)
                                .fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {

                            OutlinedTextField(
                                modifier = Modifier
                                    .width(110.dp),
                                value = newRepsTextState,
                                onValueChange = { newRepsTextState = it },
                                textStyle = TextStyle(
                                    color = MaterialTheme.typography.caption.color,
                                    fontFamily = mainFamily,
                                    fontSize = 20.sp
                                ),
                                singleLine = true,
                                label = {
                                    Text(
                                        text = "Powtorzenia",
                                        color = MaterialTheme.typography.caption.color,
                                        fontFamily = mainFamily,
                                        fontSize = 15.sp
                                    )
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = MaterialTheme.colors.secondary,
                                    focusedIndicatorColor = Purple500,
                                    cursorColor = Purple500
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Decimal,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = {
                                        focusManager.moveFocus(FocusDirection.Right)
                                    }
                                )
                            )

                            OutlinedTextField(
                                modifier = Modifier
                                    .width(110.dp),
                                value = newWeightTextState,
                                onValueChange = { newWeightTextState = it },
                                textStyle = TextStyle(
                                    color = MaterialTheme.typography.caption.color,
                                    fontFamily = mainFamily,
                                    fontSize = 20.sp
                                ),
                                singleLine = true,
                                label = {
                                    Text(
                                        text = "Obciazenie",
                                        color = MaterialTheme.typography.caption.color,
                                        fontFamily = mainFamily,
                                        fontSize = 15.sp
                                    )
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = MaterialTheme.colors.secondary,
                                    focusedIndicatorColor = Purple500,
                                    cursorColor = Purple500
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Decimal,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()
                                    }
                                )
                            )

                        }


                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {

                            if (newRepsTextState.text.isNotBlank() && newWeightTextState.text.isNotBlank()) {
                                newExerciseProgress.setsList.removeAt(setIndexSelected)
                                newExerciseProgress.setsList.add(setIndexSelected, "${newRepsTextState.text}x${newWeightTextState.text}")


                                updateProgressInfo(
                                    viewModel = viewModel,
                                    newExerciseProgress = newExerciseProgress,
                                    exerciseSelectedName = exerciseSelectedName,
                                )


                                openDialog = false
                            } else {
                                showShortToast(
                                    text = "Nie mo??esz zapisa?? pustych warto??ci"
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

    private fun updateProgressInfo(
        viewModel: DashboardViewModel,
        newExerciseProgress: ExerciseProgress,
        exerciseSelectedName: String
    ){

        viewModel.updateProgressHistory(
            newExerciseProgress = newExerciseProgress,
            exerciseSelectedName = exerciseSelectedName,
        )

    }
