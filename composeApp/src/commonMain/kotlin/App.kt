import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import database.TasksDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import services.notification.PlatformNotificationOptions
import services.notification.scheduleNotification
import ui.TaskInputBar
import ui.TaskItemView
import ui.Display
import kotlin.time.Duration.Companion.seconds

@Composable
@Preview
fun App(tasksDao: TasksDao = koinInject()) {
    val pendingTasks by remember { tasksDao.getTasks(completed = false) }.collectAsState(
        emptyList()
    )
    val completedTasks by remember { tasksDao.getTasks(completed = true) }.collectAsState(
        emptyList()
    )
    val coroutineScopeIO = CoroutineScope(Dispatchers.IO)
    val coroutineScopeMain = CoroutineScope(Dispatchers.Main)
    MaterialTheme {
        var infoVisible by remember { mutableStateOf(false) }
        var displayTaskID by remember { mutableStateOf(0)}

        Column(Modifier.safeDrawingPadding()) {
            LazyColumn(Modifier.weight(1f)) {
                // Pending tasks
                items(items = pendingTasks, key = { it.id }) { task ->
                    TaskItemView(task = task,
                        onDelete = { coroutineScopeIO.launch { tasksDao.delete(task) } },
                        onComplete = {
                            task.completed = true
                            coroutineScopeIO.launch { tasksDao.update(task) }
                        },
                        onDisplayInfo = { infoVisible=true; displayTaskID = task.id }
                    )
                }
                // Completed tasks
                // TODO: Put this in an accordion
                items(items = completedTasks, key = { it.id }) {
                    // TODO: Implement TaskItemView for completed tasks
                }
            }





            TaskInputBar(onCreateTask = { task ->
                coroutineScopeIO.launch {
                    tasksDao.insert(task)
                }
            })
        }

        AnimatedVisibility(
            visible=infoVisible,
            enter = slideInHorizontally(initialOffsetX = {it/2}),
            exit = slideOutHorizontally(targetOffsetX = {it})
        ) { Display(tasksDao, displayTaskID, onBack={infoVisible=false}) }

    }
}