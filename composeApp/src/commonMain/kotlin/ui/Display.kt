package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import database.TasksDao
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import model.toFormattedString
import network.chaintech.kmp_date_time_picker.ui.datetimepicker.WheelDateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.TimeFormat
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import ui.vectors.Arrow_back_ios
import ui.vectors.Calendar_clock

@Composable
fun Display(tasksDao:TasksDao, displayTaskID:Int, onBack:() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val task = runBlocking { tasksDao.getTaskbyID(displayTaskID) }

        var nametext by remember { mutableStateOf(task.name) }
        var descriptiontext by remember { mutableStateOf(task.description) }

        var timeAtVarNum by remember { mutableStateOf(0) }
        var startAtInst by remember { mutableStateOf(task.startAt)}
        var endAtInst by remember { mutableStateOf(task.endAt)}
        var reminderAtInst by remember { mutableStateOf(task.reminderAt)}
        var showDateTimePicker by remember { mutableStateOf(false) }

        //TODO: REFACTOR LATER
        Column(modifier = Modifier.fillMaxWidth()) {

            Row(modifier = Modifier.fillMaxWidth()) { //HEADER ROW
                IconButton(
                    onClick = { //update all of task's values
                        task.name = nametext
                        task.description = descriptiontext
                        task.startAt = startAtInst
                        task.endAt = endAtInst
                        task.reminderAt = reminderAtInst

                        runBlocking { tasksDao.update(task) }
                        onBack() //onBack: go back to the task page
                    },
                    modifier = Modifier.align(Alignment.CenterVertically).padding(3.dp)
                ) { Icon(imageVector = Arrow_back_ios, contentDescription = null) }

                TextField(
                    value = nametext,
                    onValueChange = { nametext = it },
                    textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) { //DESCRIPTION ROW
                TextField(
                    value = descriptiontext ?: "",
                    onValueChange = { descriptiontext = it },
                    placeholder = { Text(text = "Description") },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) { //STARTAT ROW
                if (startAtInst==null){//display button that says add time

                    OutlinedButton(
                        onClick = {timeAtVarNum = 0;showDateTimePicker = true},
                        border = BorderStroke(2.dp, Color.LightGray),
                        shape = CircleShape,
                    ) {Text(text="Add Start Time", color = Color.Black)}

                } else {//display time w/ calendar icon
                    Text(modifier = Modifier.weight(1f), text=startAtInst!!.toFormattedString())
                    IconButton(
                        onClick = { timeAtVarNum = 0;showDateTimePicker = true },
                    ) { Icon(imageVector = Calendar_clock, contentDescription = null) }
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) { //ENDAT ROW
                if (endAtInst==null){//display button that says add time

                    OutlinedButton(
                        onClick = {timeAtVarNum = 1;showDateTimePicker = true},
                        border = BorderStroke(2.dp, Color.LightGray),
                        shape = CircleShape,
                    ) {Text(text="Add End Time", color = Color.Black)}

                } else {//display time w/ calendar icon
                    Text(modifier = Modifier.weight(1f), text=endAtInst!!.toFormattedString())
                    IconButton(
                        onClick = { timeAtVarNum = 1;showDateTimePicker = true },
                    ) { Icon(imageVector = Calendar_clock, contentDescription = null) }
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) { //REMINDERAT ROW
                if (reminderAtInst==null){//display button that says add time

                    OutlinedButton(
                        onClick = {timeAtVarNum = 2;showDateTimePicker = true},
                        border = BorderStroke(2.dp, Color.LightGray),
                        shape = CircleShape,
                    ) {Text(text="Add Reminder", color = Color.Black)}

                } else {//display time w/ calendar icon
                    Text(modifier = Modifier.weight(1f), text=reminderAtInst!!.toFormattedString())
                    IconButton(
                        onClick = { timeAtVarNum = 2;showDateTimePicker = true },
                    ) { Icon(imageVector = Calendar_clock, contentDescription = null) }
                }
            }


        }//COLUMN END


        //DateTime Picker Dialog
        if (showDateTimePicker) {
            WheelDateTimePickerView(
                modifier = Modifier.padding(top = 18.dp, bottom = 10.dp).fillMaxWidth(),
                titleStyle = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                ),
                doneLabelStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF007AFF),
                ),
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    borderColor = Color.LightGray,
                ),
                timeFormat = TimeFormat.AM_PM,
                dateTextColor = Color(0xff007AFF),
                rowCount = 5,
                height = 170.dp,
                dateTextStyle = TextStyle(
                    fontWeight = FontWeight(600),
                ),


                //above parameters are all style
                showDatePicker = showDateTimePicker,
                dateTimePickerView = DateTimePickerView.BOTTOM_SHEET_VIEW,
                startDate = getActiveLDT(timeAtVarNum, startAtInst, endAtInst, reminderAtInst), //set default date to start/end/reminderAt depending on selection (use current time if null)
                onDoneClick = { //assign picked time to start/end/reminderAt
                    when (timeAtVarNum){
                        0 -> startAtInst = it.toInstant(TimeZone.currentSystemDefault())
                        1 -> endAtInst = it.toInstant(TimeZone.currentSystemDefault())
                        2 -> reminderAtInst = it.toInstant(TimeZone.currentSystemDefault())
                    }
                    showDateTimePicker = false //close dialog
                },
                onDismiss = { showDateTimePicker = false } //close dialog
            )
        }//DateTime Picker Dialog END


    }

}

fun getActiveLDT(
    timeAtVarNum: Int, startAtInst: Instant?, endAtInst: Instant?, reminderAtInst: Instant?, ): LocalDateTime {
    val selectedInstant : Instant = when (timeAtVarNum){
        0 -> startAtInst ?: Clock.System.now()
        1 -> endAtInst ?: Clock.System.now()
        2 -> reminderAtInst ?: Clock.System.now()
        else -> Clock.System.now()
    }
    return selectedInstant.toLocalDateTime(TimeZone.currentSystemDefault())
}

