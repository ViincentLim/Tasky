package ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.Task

@Composable
fun TaskInputBar(
    onCreateTask: (Task) -> Unit,
    modifier: Modifier = Modifier,
) {
    var taskName by remember { mutableStateOf("") }

    fun createTask() {
        onCreateTask(Task(name = taskName.trim()))
        taskName = ""
    }

    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        TaskNameTextField(
            taskName = taskName,
            onTaskNameChange = { taskName = it },
            onDone = {
                createTask()
            },
            modifier = Modifier.weight(1f)
        )
        CreateTaskButton(
            onClick = { createTask() }, modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
private fun TaskNameTextField(
    taskName: String,
    onTaskNameChange: (String) -> Unit,
    onDone: KeyboardActionScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = taskName,
        onValueChange = onTaskNameChange,
        placeholder = { Text("Enter a new task", fontSize = 20.sp) },
        keyboardActions = KeyboardActions(onDone = onDone),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        modifier = modifier,
        textStyle = MaterialTheme.typography.body1.merge(fontSize = 20.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent, focusedBorderColor = Color.Transparent
        ),
    )
}

@Composable
private fun CreateTaskButton(
    onClick: () -> Unit, modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick, modifier = modifier
    ) {
        Icon(Icons.Default.Add, contentDescription = "Create task")
    }
}