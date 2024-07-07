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
    val startAt: Instant? = null,
    val endAt: Instant? = null,
    val reminderAt: Instant? = null,
) {
    val duration: Duration?
        get() = startAt?.let { endAt?.minus(startAt) }
}