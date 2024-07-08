package ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.unit.dp
import model.Task
import ui.vectors.rememberCircle
import ui.vectors.rememberNotifications

@Composable
fun TaskItemView(
    task: Task,
    onDelete: () -> Unit,
    onComplete: () -> Unit
) {
    Row(Modifier
        .padding(8.dp)) {
        //can change the size but not colour and stroke for this
        //idk why (circle icon)
        IconButton(
            onClick = onComplete,
            modifier = Modifier
                .padding(horizontal = 0.5f.dp)
//                .border(2.dp, Color.Blue)
                .align(Alignment.CenterVertically)
        ) {
            Icon(imageVector = rememberCircle(), contentDescription = "checkbox")
        }

        //COLUMN FOR NAME AND TIME
        Column(Modifier
            .weight(1f)
            .fillMaxHeight()
//            .border(2.dp, Color.Red)
            .align(Alignment.CenterVertically)
        ) {

            //NAME
            Text(
                text = task.name,
                modifier = Modifier
                    .padding(horizontal = 2.dp),
                style = MaterialTheme.typography.h5
            )

            //START & END TIME (FORMATTED TEXT)
            //ONLY SHOW IF THE TEXT IS NOT EMPTY
            val timetext = task.taskTileFormattedTime
//            val timetext = "STARTTIME to ENDTIME" //FOR TESTING
            if (timetext.isNotEmpty()) {
                Text(
                    text = timetext,
                    modifier = Modifier
//                        .border(2.dp, Color.Red)
                        .padding(horizontal = 2.dp),
                    style = MaterialTheme.typography.subtitle2
            )}

        }

        //BELL ICON
        if (task.reminderAt!=null){
            Icon(imageVector = rememberNotifications(),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically))

        }






        //Spacer(Modifier.weight(1f))
//        IconButton(onClick = onDelete, modifier = Modifier.align(Alignment.CenterVertically)) {
//            Icon(Icons.Default.Delete, contentDescription = "Delete task")
//        }
    }
}