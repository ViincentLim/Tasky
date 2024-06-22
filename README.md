# Tasky

Tasky is a powerful task management mobile application designed to help users stay organised and on top of their
tasks.

With features like nested tasks, reminders, and a scheduler, Tasky makes it easy to manage daily to-dos and
long-term projects.

## Features

- Tasks
- Scheduler

## Technology

The mobile app is built with Kotlin Multiplatform (KMP). The UI is shared using Compose Multiplatform.

### Platforms

- Android
- iOS

## Getting started

### Requirements

- Android Studio: Android Studio is a recommended IDE with built in tools that makes KMP development easier.
  - Kotlin plugin: The Kotlin plugin is bundled with each Android Studio release. However, it still needs to be
    updated to the latest version to avoid compatibility issues.
  - Kotlin Multiplatform plugin: The Kotlin Multiplatform plugin can be installed in Android Studio.
- XCode (iOS): To build the iOS version of the app, XCode is required.
- JDK: Run `java -version` to verify whether a JDK is installed.

### Project Structure

This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across the application.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code compiled only for the platform indicated in the folder name.
    For example, to use Apple’s CoreCrypto for the iOS part of the Kotlin app, `iosMain` would be
    the right folder for such calls.

* `/iosApp` contains iOS applications. Even when UI is shared with Compose Multiplatform,
  this entry point is needed for the iOS app. This is also where SwiftUI code for the project should
  be added.

Learn more
about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…