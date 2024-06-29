package ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Task

@Composable
fun TaskItemView(
    task: Task, onDelete: () -> Unit
) {
    Row(Modifier.padding(8.dp).fillMaxWidth()) {
        Text(
            text = task.name,
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
                .align(Alignment.CenterVertically),
            style = MaterialTheme.typography.h6
        )
        IconButton(onClick = onDelete, modifier = Modifier.align(Alignment.CenterVertically)) {
            Icon(Icons.Default.Delete, contentDescription = "Delete task")
        }
    }
}