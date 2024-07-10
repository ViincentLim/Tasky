package model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlin.time.Duration

/**
 * Converts an Instant to a formatted string.
 */
fun Instant.toFormattedString(): String {
    // TODO: Implement this function
    return ""
}

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

    val formattedTime: String
        get() {
            if (startAt == null && endAt == null) return ""
            if (endAt == null) return startAt!!.toFormattedString()
            if (startAt == null) return "from ${endAt.toFormattedString()}"
            return "${startAt.toFormattedString()} to ${endAt.toFormattedString()}"
        }
}