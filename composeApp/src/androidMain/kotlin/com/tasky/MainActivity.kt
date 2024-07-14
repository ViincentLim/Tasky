package com.tasky

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import database.getAppDatabase
import database.getDatabaseBuilder
import modules.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.core.context.stopKoin
import org.koin.dsl.module

val androidModule = module { single { getDatabaseBuilder(get()).getAppDatabase() } }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (GlobalContext.getKoinApplicationOrNull() != null) {
            stopKoin()
        }
        setContent {
            KoinApplication(application = {
                modules(
                    module { androidContext(this@MainActivity); single { this@MainActivity } },
                    androidModule,
                    commonModule
                )
            }) {
                App()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    val ctx = LocalContext.current
    KoinApplication(application = {
        modules(module { androidContext(ctx) }, androidModule, commonModule)
    }) {
        App()
    }
}