import androidx.compose.ui.window.ComposeUIViewController
import database.getAppDatabase
import database.getDatabaseBuilder

@Suppress("unused", "FunctionName")
fun MainViewController() = ComposeUIViewController { App(getDatabaseBuilder().getAppDatabase()) }