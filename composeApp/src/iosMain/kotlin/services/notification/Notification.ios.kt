package services.notification

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter

actual fun scheduleNotification(
    id: Int,
    title: String,
    text: String,
    timestamp: Instant,
    platformOptions: PlatformNotificationOptions,
) {
    val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()

    notificationCenter.requestAuthorizationWithOptions(UNAuthorizationOptionAlert + UNAuthorizationOptionBadge + UNAuthorizationOptionSound) { granted, error ->
        if (error != null) {
            println("Permission not granted: ${error.localizedDescription}")
        } else if (granted) {
            println("Permission granted")
        } else {
            println("Permission denied")
        }
    }

    val notification = UNMutableNotificationContent().apply {
        setTitle(title)
        setBody(text)
        setSound(UNNotificationSound.defaultSound)
    }

    val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(
//        timeInterval = timestamp.epochSeconds.toDouble(),
        timeInterval = (timestamp - Clock.System.now()).inWholeSeconds.toDouble(),
        repeats = false,
    )

    val request = UNNotificationRequest.requestWithIdentifier(
        id.toString(),
        content = notification,
        trigger = trigger
    )

    notificationCenter.addNotificationRequest(request) { nsError ->
        if (nsError != null) {
            println("Error scheduling notification: $nsError")
        } else {
            println("Notification scheduled successfully")
        }
    }
}