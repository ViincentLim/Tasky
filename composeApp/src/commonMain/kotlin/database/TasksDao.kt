package database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import model.Task

@Dao
interface TasksDao {
    @Query("SELECT * FROM task WHERE completed == :completed")
    fun getTasks(completed: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE id == :taskID")
    suspend fun getTaskbyID(taskID:Int): Task

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)
}

