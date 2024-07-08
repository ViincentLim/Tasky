package database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import model.Task

@Dao
interface TasksDao {
    @Query("SELECT * FROM task WHERE completed==0")
    fun getPendingTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE completed==1")
    fun getCompletedTasks(): Flow<List<Task>>



    @Query("SELECT * FROM task")
    fun getAll(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)

}

