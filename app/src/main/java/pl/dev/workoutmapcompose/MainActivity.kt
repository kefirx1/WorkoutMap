package pl.dev.workoutmapcompose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import pl.dev.workoutmapcompose.datbase.WMViewModel
import pl.dev.workoutmapcompose.ui.theme.BlueGray50
import pl.dev.workoutmapcompose.ui.theme.BlueGray800
import pl.dev.workoutmapcompose.ui.theme.BlueGray900
import pl.dev.workoutmapcompose.ui.theme.mainFamily

class MainActivity : ComponentActivity(){
    companion object{
        lateinit var viewModel: WMViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @OptIn(ExperimentalPagerApi::class)
    override fun onResume() {
        super.onResume()
        Log.e("TAG", "RESUME")

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(WMViewModel::class.java)

        setContent {

            if(!viewModel.userExist()){

                Log.e("TAG", "User not exist")
                //REGISTER USER
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)

            }else{
                MainDashboard(this)
            }

        }

    }


}

@ExperimentalPagerApi
@Composable
fun MainDashboard(
    instance: MainActivity,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGray900)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.85f)
                .fillMaxWidth()
        ) {
            DashboardHorizontalPager(
                instance = instance
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Button(
                modifier = Modifier
                    .fillMaxSize(),
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = BlueGray800
                ),
            ) {
                Text(
                    text = "TRENUJ",
                    color = BlueGray50,
                    fontFamily = mainFamily,
                    fontSize = 40.sp
                )
            }

        }
    }
}



@ExperimentalPagerApi
@Composable
fun DashboardHorizontalPager(
    instance: MainActivity,
) {
    HorizontalPager(
        count = 2,
        modifier = Modifier
            .fillMaxSize()
    ){ page ->
        Column() {
            Text(text = "page - $page")
        }
    }

}

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainDashboard(MainActivity())
}
