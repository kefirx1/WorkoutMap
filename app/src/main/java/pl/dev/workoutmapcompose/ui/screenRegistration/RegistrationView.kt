package pl.dev.workoutmapcompose.ui.screenRegistration

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.NumberPicker
import pl.dev.workoutmapcompose.App.Companion.applicationContext
import pl.dev.workoutmapcompose.data.UserInfo
import pl.dev.workoutmapcompose.ui.theme.Purple500
import pl.dev.workoutmapcompose.ui.theme.mainFamily
import pl.dev.workoutmapcompose.ui.utils.RegistrationHeaderComponent
import java.util.*


fun exitRegister(
    instance: RegisterActivity,
    viewModel: RegistrationViewModel,
    nameString: String,
    surnameString: String,
    dateOfBirth: String,
    genderString: String,
    heightString: String
){

    if(nameString.isBlank() || surnameString.isBlank() || dateOfBirth.isBlank()){
        Toast.makeText(applicationContext(), "Musisz uzupelnic wszystkie dane poprawnie", Toast.LENGTH_SHORT).show()
    }else{

        val calendar = Calendar.getInstance()

        viewModel.insertUser(
            UserInfo(
                userFirebaseID = calendar.timeInMillis.toString(),
                name = nameString,
                surName = surnameString,
                dateOfBirth = dateOfBirth,
                height = heightString,
                gender = genderString,
                lastTrainingDate = ""
            )
        )
        Log.e("TAG", "User added")
        instance.finish()
    }
}

@Suppress("FunctionName")
@Composable
fun MainRegistration(
    instance: RegisterActivity,
    viewModel: RegistrationViewModel
) {
    val possibleGenderValues = listOf("Mężczyzna", "Kobieta", "Inna")
    var nameTextState by remember {
        mutableStateOf(TextFieldValue())
    }
    var surnameTextState by remember {
        mutableStateOf(TextFieldValue())
    }
    var heightPickerState by remember {
        mutableStateOf(160)
    }
    var genderSelected by remember {
        mutableStateOf("Mężczyzna")
    }
    var genderExpanded by remember {
        mutableStateOf(false)
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
        mutableStateOf("$mDay/${mMonth+1}/$mYear")
    }
    var dateOfBirth by remember {
        mutableStateOf("")
    }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
            .padding(10.dp)
    ){

        RegistrationHeaderComponent(
            screenName = "REJESTRACJA"
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
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
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
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
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        ),
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
                                Toast.makeText(
                                    applicationContext(),
                                    "Nie możesz ustawiać przyszłej daty",
                                    Toast.LENGTH_SHORT
                                ).show()
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
                            containerColor = MaterialTheme.colors.primary ,
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
                        text = "Płeć:",
                        style = MaterialTheme.typography.caption,
                        fontSize = 25.sp
                    )
                    Row(
                        modifier = Modifier
                            .clickable{
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

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    val nameString = nameTextState.text
                    val surnameString = surnameTextState.text
                    val genderString = genderSelected
                    val heightString = heightPickerState.toString()

                    exitRegister(
                        instance = instance,
                        viewModel = viewModel,
                        nameString = nameString,
                        surnameString = surnameString,
                        dateOfBirth = dateOfBirth,
                        genderString = genderString,
                        heightString = heightString
                    )

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colors.surface
                ),
            ) {
                Text(
                    text = "ZATWIERDŹ",
                    style = MaterialTheme.typography.caption,
                    fontSize = 30.sp
                )
            }
        }
    }
}