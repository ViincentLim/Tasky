package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.Task
import ui.vectors.rememberCircle
import ui.vectors.rememberNotifications

@Composable
fun TaskItemView(
    task: Task,
    onDelete: () -> Unit,
    onComplete: () -> Unit,
) {
    Row(
        Modifier.padding(8.dp)
    ) {
        Checkbox(onComplete)
        NameTimeColumn(task)
        if (task.reminderAt != null) {
            BellIcon(Modifier.align(Alignment.CenterVertically))
        }
    }
}

@Composable
private fun RowScope.Checkbox(onComplete: () -> Unit) {
    IconButton(
        onClick = onComplete,
        modifier = Modifier.padding(horizontal = 0.5f.dp).align(Alignment.CenterVertically)
    ) {
        Icon(
            imageVector = rememberCircle(),
            tint = Color.Blue,
            contentDescription = "checkbox"
        )
    }
}

@Composable
private fun RowScope.NameTimeColumn(task: Task) {
    Column(
        Modifier.Companion.weight(1f).fillMaxHeight().align(Alignment.CenterVertically)
    ) {
        NameText(task.name, Modifier.padding(horizontal = 2.dp))

        // Shows formatted time if it's not empty
        val formattedTime = task.formattedTime
        if (formattedTime.isNotEmpty()) {
            TimeText(
                formattedTime, Modifier.padding(horizontal = 2.dp)
            )
        }
    }
}

@Composable
private fun NameText(text: String, modifier: Modifier) {
    Text(
        text = text, modifier = modifier, style = MaterialTheme.typography.h5
    )
}

@Composable
private fun TimeText(text: String, modifier: Modifier) {
    Text(
        text = text, modifier = modifier, style = MaterialTheme.typography.subtitle2
    )
}

@Composable
private fun BellIcon(modifier: Modifier) {
    Icon(
        imageVector = rememberNotifications(), contentDescription = null, modifier = modifier
    )
}
