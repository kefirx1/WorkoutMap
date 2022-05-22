package pl.dev.workoutmapcompose.ui.screenWeightHistory


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot
import pl.dev.workoutmapcompose.DateTimeFunctionalities
import pl.dev.workoutmapcompose.TextModifier
import pl.dev.workoutmapcompose.WeightHistoryActivity
import pl.dev.workoutmapcompose.data.WeightHistory
import pl.dev.workoutmapcompose.ui.components.DialogAlerts
import pl.dev.workoutmapcompose.ui.components.HeaderComponent
import pl.dev.workoutmapcompose.ui.theme.*


@Composable
fun MainWeightHistoryView(
    instance: WeightHistoryActivity,
    viewModel: WeightHistoryViewModel
) {

    viewModel.getWeightHistory()

    var openInsertWeightDialog by remember {
        mutableStateOf(false)
    }
    var openRowWeightDialog by remember {
        mutableStateOf(false)
    }

    var clickedRowWeightHistory by remember {
        mutableStateOf(WeightHistory("", 0))
    }

    if(openInsertWeightDialog) {
        openInsertWeightDialog = DialogAlerts.insertNewWeightDialogAlert(
            instance = instance,
            viewModel = viewModel
        )
    }

    if(openRowWeightDialog) {
        openRowWeightDialog = DialogAlerts.onWeightHistoryRowClickDialogAlert(
            instance = instance,
            viewModel = viewModel,
            weightHistory = clickedRowWeightHistory
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(10.dp)
    ) {

        HeaderComponent(
            screenName = "HISTORIA WAGI",
            instance = instance
        )

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            viewModel.weightHistoryResult.value?.let {
                if (it.isNotEmpty()) {

                    val listOfDataPoint = transformToListOfDataPoint(it)
                    val listOfXAxisLabels = getLabelForXAxis(it)

                    WeightHistoryLineGraph(arrayListOf(listOfDataPoint), listOfXAxisLabels)

                } else {

                    Text(
                        modifier = Modifier
                            .padding(bottom = 40.dp, top = 40.dp),
                        text = "Nie dodałeś jeszcze \n swojej wagi",
                        style = MaterialTheme.typography.caption,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )

                }
            }

            Spacer(
                modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth()
            )

            Button(
                modifier = Modifier
                    .height(45.dp)
                    .fillMaxWidth(0.5f)
                    .padding(end = 4.dp)
                    .shadow(ambientColor = Color.Black, shape = RectangleShape, elevation = 10.dp),
                onClick = {
                    openInsertWeightDialog = true
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                ),
            ) {
                Text(
                    text = "DODAJ",
                    style = MaterialTheme.typography.caption,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 6.dp)
                    .background(color = Color.Black)
                    .height(2.dp)
                    .fillMaxWidth()
            )



            if (!viewModel.weightHistoryResult.value.isNullOrEmpty()) {

                val sortedWeightHistoryList =
                    viewModel.weightHistoryResult.value!!.sortedByDescending {
                        it.weighingDate
                    }

                LazyColumn {
                    items(count = sortedWeightHistoryList.size) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    openRowWeightDialog = true
                                    clickedRowWeightHistory = sortedWeightHistoryList[it]
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(
                                    text = DateTimeFunctionalities.convertDateInSecToDateString(sortedWeightHistoryList[it].weighingDate),
                                    style = MaterialTheme.typography.caption,
                                    fontSize = 25.sp
                                )

                                Text(
                                    text = "${sortedWeightHistoryList[it].weight}kg",
                                    style = MaterialTheme.typography.caption,
                                    fontSize = 25.sp
                                )
                            }

                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "delete",
                                tint = MaterialTheme.typography.caption.color
                            )

                        }
                        Spacer(
                            modifier = Modifier
                                .padding(top = 6.dp, bottom = 6.dp)
                                .background(color = Color.Black)
                                .height(1.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun WeightHistoryLineGraph(lines: ArrayList<ArrayList<DataPoint>>, xAxisLabel: ArrayList<String>) {
    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    dataPoints = lines[0],
                    connection = LinePlot.Connection(color = BlueGray900),
                    intersection = LinePlot.Intersection(color = BlueGray500),
                    highlight = LinePlot.Highlight(color = Purple200),
                )
            ),
            selection = LinePlot.Selection(
                highlight = LinePlot.Connection(

                )
            ),
            xAxis = LinePlot.XAxis(
                content  = { min, offset, max ->
                    for (it in 0 until xAxisLabel.size) {
                        val value = it * offset + min
                        Text(
                            text = "     " + xAxisLabel[it],
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            text = ""
                        )
                        if (value > max) {
                            break
                        }
                    }
                }

            ),
            grid = LinePlot.Grid(
                color = MaterialTheme.typography.caption.color,
                steps = 5
            ),
        ),
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
            .height(200.dp)
    )
}

private fun getLabelForXAxis(
    weightHistoryList: ArrayList<WeightHistory>
): ArrayList<String> {

    val labelDataList: ArrayList<String> = ArrayList()

    val sortedWeightHistoryList = weightHistoryList.sortedBy {
        it.weighingDate
    }
    sortedWeightHistoryList.forEach{
        labelDataList.add(DateTimeFunctionalities.convertTimeInSecToDateStringShortened(it.weighingDate))
    }
    return labelDataList
}

private fun transformToListOfDataPoint(
    weightHistoryList: ArrayList<WeightHistory>
): ArrayList<DataPoint> {

    val dataPointsList: ArrayList<DataPoint> = ArrayList()

    val sortedWeightHistoryList = weightHistoryList.sortedBy {
        it.weighingDate
    }

    var iterator = 1f

    sortedWeightHistoryList.forEach {
        dataPointsList.add(DataPoint(iterator, it.weight.toFloat()))
        iterator += 2
    }

    return dataPointsList
}