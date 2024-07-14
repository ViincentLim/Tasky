package modules

import database.AppDatabase
import org.koin.dsl.module

val commonModule = module {
    single { get<AppDatabase>().getTasksDao() }
}