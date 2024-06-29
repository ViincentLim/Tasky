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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(database = getDatabaseBuilder(this.applicationContext).getAppDatabase())
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(getDatabaseBuilder(LocalContext.current).getAppDatabase())
}