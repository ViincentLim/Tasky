import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import model.Task
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.CreateTaskRow
import ui.TaskItemView

@Composable
@Preview
fun App(database: AppDatabase) {
    val tasksDao = remember { database.getTasksDao() }
    val tasks by remember { tasksDao.getAll() }.collectAsState(emptyList())
    val coroutineScopeIO = CoroutineScope(Dispatchers.IO)

    var createTaskName by remember { mutableStateOf("") }
    val onCreateTask = onCreateTask@{
        if (createTaskName.trim().isBlank()) return@onCreateTask
        coroutineScopeIO.launch {
            tasksDao.insert(
                Task(
                    name = createTaskName.trim()
                )
            )
            createTaskName = ""
        }
        return@onCreateTask
    }

    MaterialTheme {
        Box(Modifier.safeDrawingPadding()) {
            Column {
                LazyColumn(Modifier.weight(1f)) {
                    items(items = tasks, key = { it.id }) { task ->
                        TaskItemView(task = task, onDelete = {
                            coroutineScopeIO.launch {
                                tasksDao.delete(task)
                            }
                        })
                    }
                }
                CreateTaskRow(
                    taskName = createTaskName,
                    onTaskNameChange = { createTaskName = it },
                    onCreateTask = onCreateTask,
                )
            }
        }
    }
}