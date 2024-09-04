package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import model.Task
import ui.vectors.rememberCircle
import ui.vectors.rememberNotifications
import kotlin.math.roundToInt


private enum class HorizontalDragValue {Settled, StartToEnd, EndToStart}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.TaskItemView(
    task: Task,
    onDelete: () -> Unit,
    onComplete: () -> Unit,
    onDisplayInfo: (Int) -> Unit,
) {
    var boxSize by remember { mutableFloatStateOf(0f)}
    val scope = rememberCoroutineScope()

    val animationDuration = 1000 //in milliseconds
//    KIV for smoother animation
//    val animationPauseDuration = 400 //in milliseconds

    val anchors = DraggableAnchors {
        HorizontalDragValue.Settled at 0f
        HorizontalDragValue.StartToEnd at boxSize
        HorizontalDragValue.EndToStart at -boxSize / 7
    }
    val state = remember {
        AnchoredDraggableState(
            initialValue = HorizontalDragValue.Settled,
            positionalThreshold = { distance -> distance * 1f},
            velocityThreshold = { 100f },
            animationSpec = tween()
        )
    }

    SideEffect{state.updateAnchors(anchors)}
    val iconsBackgroundColor = if (state.offset>1) Color(66, 133, 244, 255)
    else if (state.offset<1) Color(219, 68, 55, 255)
    else Color.Transparent


    LaunchedEffect(key1 = state.targetValue){
        if (state.targetValue==HorizontalDragValue.StartToEnd){
            delay(animationDuration.toLong())
            onComplete()
        }
    }

    AnimatedVisibility(
        visible = (state.targetValue!=HorizontalDragValue.StartToEnd),
        exit= shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {

        //Box(modifier = Modifier.fillMaxWidth().animateItemPlacement)
        Box(modifier = Modifier
            .fillMaxWidth()
            .animateItemPlacement())
        {
            StaticBackground(iconsBackgroundColor, onDelete)

            Box(modifier = Modifier
                .graphicsLayer { boxSize = size.width }
                .offset { IntOffset(x = state.requireOffset().roundToInt(), y=0) }
                .fillMaxWidth()
                .height(70.dp)
                .anchoredDraggable(state, Orientation.Horizontal)
                .background(Color.LightGray)
                .border(2.dp,  Color.White)
            ){
                //TODO: find a way for this to affect the animated visibility in App.kt
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.Center)
                    .offset(x = 12.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {onDisplayInfo(task.id)}
                    )
//                    .border(2.dp, Color.Cyan) //for debug
                    .padding(155.dp)

                )
                MainTileRow(onComplete, task)
            }
        }

    }

}
@Composable
private fun BoxScope.StaticBackground(
    iconsBackgroundColor: Color,
    onDelete: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(iconsBackgroundColor)
            .border(2.dp, Color.White) //cyan for debug, white for actual
            .align(Alignment.CenterEnd),
        contentAlignment = Alignment.CenterEnd
    ) {
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = null, tint = Color.White)
        }
    }
}

@Composable
private fun BoxScope.MainTileRow(onComplete: () -> Unit, task: Task){
    Row(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
    ) {
        Checkbox(onComplete)
        NameTimeColumn(task)
        if (task.reminderAt != null) {
            BellIcon(modifier = Modifier
//                .border(2.dp, Color.Red) //for debug
                .align(Alignment.CenterVertically))
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
        // Shows formatted time if it's not empty
        val formattedTime = task.formattedTime
//        val formattedTime = "Thu 9:09pm 11 Jul" //debug

        if (formattedTime.isEmpty()) {
            //this centres the name if there is no timetext
            NameText(task.name, Modifier.padding(horizontal = 2.dp).height(50.dp).wrapContentHeight())
        }
        else {
            NameText(task.name, Modifier.padding(horizontal = 2.dp))
            TimeText(formattedTime, Modifier.padding(horizontal = 2.dp))
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
