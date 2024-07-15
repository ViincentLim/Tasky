package model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
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

    val formattedTime: String
        get() {
            if (startAt == null && endAt == null) return ""
            if (endAt == null) return "starts ${startAt!!.toFormattedString()}"
            if (startAt == null) return "ends ${endAt.toFormattedString()}"
            return "${startAt.toFormattedString()} to ${endAt.toFormattedString()}"
        }
}

/** Converts an Instant to a formatted string ('Thu 7:07pm, 11 Jul').*/
fun Instant.toFormattedString(): String {
    // change TimeZone here if necessary
    return this.toLocalDateTime(TimeZone.currentSystemDefault()).format(
        LocalDateTime.Format {
            date(LocalDate.Format{
                dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED)
                chars(" ")
            })

            time(LocalTime.Format{
                amPmHour()
                chars(":")
                minute()
                amPmMarker("am","pm")
                chars(", ")
            })

            date(LocalDate.Format{
                dayOfMonth()
                chars(" ")
                monthName(MonthNames.ENGLISH_ABBREVIATED)
            })
        }
    )

}