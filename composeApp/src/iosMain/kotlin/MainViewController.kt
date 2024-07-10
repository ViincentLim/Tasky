import androidx.compose.ui.window.ComposeUIViewController
import database.getAppDatabase
import database.getDatabaseBuilder
import org.koin.compose.KoinApplication
import org.koin.dsl.module

val iosModule = module {
    single { getDatabaseBuilder().getAppDatabase() }
}

@Suppress("unused", "FunctionName")
fun MainViewController() = ComposeUIViewController {
    KoinApplication(application = {
        modules(iosModule)
    }) {
        App()
    }
}