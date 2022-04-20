package pl.dev.workoutmapcompose.ui.screenRegistration

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.ListItemPicker
import com.chargemap.compose.numberpicker.NumberPicker
import pl.dev.workoutmapcompose.RegisterActivity
import pl.dev.workoutmapcompose.data.UserInfo
import pl.dev.workoutmapcompose.ui.theme.BlueGray50
import pl.dev.workoutmapcompose.ui.theme.BlueGray800
import pl.dev.workoutmapcompose.ui.theme.BlueGray900
import pl.dev.workoutmapcompose.ui.theme.mainFamily
import java.util.*


fun exitRegister(
    instance: RegisterActivity,
    viewModel: RegistrationViewModel,
    nameString: String,
    surnameString: String,
    ageString: String,
    genderString: String,
    heightString: String
){
    if(nameString.isBlank() || surnameString.isBlank()){
        Toast.makeText(instance, "Musisz uzupelnic wszystkie dane", Toast.LENGTH_SHORT).show()
    }else{

        val calendar = Calendar.getInstance()

        viewModel.insertUser(
            UserInfo(
                userFirebaseID = calendar.timeInMillis.toString(),
                name = nameString,
                surName = surnameString,
                age = ageString,
                height = heightString,
                gender = genderString,
                monday = "",
                tuesday = "",
                wednesday = "",
                thursday = "",
                friday = "",
                saturday = "",
                sunday = "",
                lastTrainingDate = ""
            )
        )
        Log.e("TAG", "User added")
        instance.finish()
    }
}

@Composable
fun MainRegistration(
    instance: RegisterActivity,
    viewModel: RegistrationViewModel
) {
    var nameTextState by remember { mutableStateOf(TextFieldValue()) }
    var surnameTextState by remember { mutableStateOf(TextFieldValue()) }
    var agePickerState by remember { mutableStateOf(18) }
    val possibleGenderValues = listOf("Mężczyzna", "Kobieta", "Inna")
    var genderPickerState by remember { mutableStateOf(possibleGenderValues[1]) }
    var heightPickerState by remember { mutableStateOf(160) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGray900)
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = "Imię:",
                    color = BlueGray50,
                    fontFamily = mainFamily,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Left
                )
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
                TextField(
                    value = nameTextState,
                    onValueChange = { nameTextState = it },
                    textStyle = TextStyle(color = BlueGray50, fontFamily = mainFamily, fontSize = 20.sp)
                )
            }
        }
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
                    fontSize = 25.sp,
                    textAlign = TextAlign.Left
                )
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
                TextField(
                    value = surnameTextState,
                    onValueChange = { surnameTextState = it },
                    textStyle = TextStyle(color = BlueGray50, fontFamily = mainFamily, fontSize = 20.sp)
                )
            }

        }
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
                    textStyle = TextStyle(color = BlueGray50, fontFamily = mainFamily)
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
        Button(
            onClick = {
                val nameString = nameTextState.text
                val surnameString = surnameTextState.text
                val ageString = agePickerState.toString()
                val genderString = genderPickerState
                val heightString = heightPickerState.toString()

                exitRegister(
                    instance,
                    viewModel,
                    nameString,
                    surnameString,
                    ageString,
                    genderString,
                    heightString
                )

            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = BlueGray800
            ),
        ) {
            Text(
                text = "ZATWIERDŹ",
                color = BlueGray50,
                fontFamily = mainFamily,
                fontSize = 30.sp
            )
        }


    }
}