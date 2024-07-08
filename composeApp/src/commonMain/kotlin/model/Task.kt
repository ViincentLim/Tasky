package model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlin.time.Duration

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String? = null,
    var completed: Boolean = false,
    val startAt: Instant? = null,
    val endAt: Instant? = null,
    val reminderAt: Instant? = null,
) {
    val duration: Duration?
        get() = startAt?.let { endAt?.minus(startAt) }



    private fun instToStr(instant:Instant?): String {
        return ""//FOR CONVERSION OF STARTAT & ENDAT
    }
    val taskTileFormattedTime: String
        get() {
            if (startAt!=null) {
                //ONLY STARTAT
                if (endAt==null){ return instToStr(startAt)}

                //STARTAT to ENDAT
                else {return instToStr(startAt)+" to "+instToStr(endAt)}
            }
            //ONLY ENDAT
            else if (endAt!=null){ return "Ends "+instToStr(endAt) }
            return ""
        }


}