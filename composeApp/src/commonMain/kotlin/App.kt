import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.CreateTaskRow
import ui.TaskItemView

@Composable
@Preview
fun App(database: AppDatabase) {
    val tasksDao = remember { database.getTasksDao() }
    val tasks by remember { tasksDao.getAll() }.collectAsState(emptyList())
    val coroutineScopeIO = CoroutineScope(Dispatchers.IO)

    MaterialTheme {
        Column(Modifier.safeDrawingPadding()) {
            LazyColumn(Modifier.weight(1f)) {
                items(items = tasks, key = { it.id }) { task ->
                    TaskItemView(task = task, onDelete = {
                        coroutineScopeIO.launch {
                            tasksDao.delete(task)
                        }
                    })
                }
            }
            CreateTaskRow(onCreateTask = { task ->
                coroutineScopeIO.launch {
                    tasksDao.insert(task)
                }
            })
        }
    }
}