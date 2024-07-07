package database

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

class InstantConverterByMinute {
    @TypeConverter
    fun fromInt(value: Int?): Instant? {
        return value?.let { Instant.fromEpochSeconds(it * 60L) }
    }

    @TypeConverter
    fun toInt(date: Instant?): Int? {
        return date?.epochSeconds?.let { (it / 60).toInt() }
    }
}