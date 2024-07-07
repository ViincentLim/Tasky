package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import model.Task

@Database(entities = [Task::class], version = 1)
@TypeConverters(InstantConverterByMinute::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTasksDao(): TasksDao
}

fun RoomDatabase.Builder<AppDatabase>.getAppDatabase(): AppDatabase {
    return this.fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
