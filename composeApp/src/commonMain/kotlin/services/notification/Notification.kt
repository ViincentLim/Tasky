package services.notification

import kotlinx.datetime.Instant

expect fun scheduleNotification(
    id: Int,
    title: String,
    text: String,
    timestamp: Instant,
    platformOptions: PlatformNotificationOptions,
)

data class PlatformNotificationOptions(val androidChannelId: String)