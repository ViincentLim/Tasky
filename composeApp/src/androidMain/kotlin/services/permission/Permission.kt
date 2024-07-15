package services.permission

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

fun checkAndRequestPermissions(activity: Activity) {
//    ctx.getSystemService(PowerManager::class.java).isIgnoringBatteryOptimizations("com.tasky")
    if (ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // TODO: Explain why the app requires these permission
            ActivityCompat.requestPermissions(
                activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1
            )
        }
    }
}