# Smart Driver Profiler

An Android application that analyzes driving behavior using on-device machine learning to provide real-time feedback and trip analysis.

## Overview

Smart Driver Profiler is a standalone Android app that runs entirely on-device, processing sensor data offline to evaluate driving patterns and provide personalized recommendations. The app uses TensorFlow Lite for efficient machine learning inference without requiring internet connectivity.

## Features

- **Real-time Trip Monitoring**: Track your driving sessions with live data visualization
- **On-Device ML Analysis**: TensorFlow Lite model processes driving data locally for privacy and speed
- **Sensor Integration**: 
  - GPS tracking for location and speed
  - Accelerometer for detecting sudden movements
  - Gyroscope for measuring orientation changes
- **Trip History**: Browse and analyze past trips with detailed metrics
- **Offline Functionality**: All processing happens on-device, no internet required
- **Driving Recommendations**: Get personalized feedback based on your driving patterns
- **Modern UI**: Built with Jetpack Compose for a smooth, responsive experience

## Technologies Used

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **ML Framework**: TensorFlow Lite
- **Database**: Room (local SQLite database)
- **Sensors**: Android Location Services, Motion Sensors
- **Architecture**: MVVM pattern with modern Android best practices

## Requirements

- Android 8.0 (API level 26) or higher
- GPS-enabled device
- Accelerometer and gyroscope sensors

## Setup

1. Clone or download this repository
2. **First-time setup**: If you don't have Android Studio or Gradle installed, see [BUILD_SETUP.md](BUILD_SETUP.md) for detailed setup instructions
3. Open the project in Android Studio (recommended) or ensure you have the Android SDK and Gradle installed
4. Sync Gradle dependencies
5. Ensure you have the TFLite model file in the `assets` folder
6. Build and run the app on your device or emulator

## Building from Source

### Quick Build (Windows)

Use the provided build script for the easiest experience:

```bash
build_apk.bat
```

This script will:
- Clean previous builds
- Compile the debug APK
- Copy it to the project root as `SmartDriverProfiler-debug.apk`
- Display the file location and size

### Manual Build

If you prefer to build manually or are on Mac/Linux:

#### Debug APK (Recommended for Testing)
```bash
# Windows
gradlew.bat assembleDebug

# Mac/Linux
./gradlew assembleDebug
```

**Output**: `app/build/outputs/apk/debug/app-debug.apk`

#### Release APK (For Production)
```bash
# Windows
gradlew.bat assembleRelease

# Mac/Linux
./gradlew assembleRelease
```

**Output**: `app/build/outputs/apk/release/app-release.apk`

> **Note**: Release builds require a signing keystore. For simple distribution, use the debug APK.

### First-Time Build

If this is your first time building:

1. Ensure you have **Android Studio** or the **Android SDK** installed
2. Java 17 or higher is required
3. The project uses Gradle wrapper, so no separate Gradle installation needed

### Sharing the App

Once you've built the APK:

1. **Locate the APK**: 
   - If using `build_apk.bat`: `SmartDriverProfiler-debug.apk` in project root
   - If building manually: `app/build/outputs/apk/debug/app-debug.apk`

2. **Share it**:
   - Email the APK file
   - Upload to cloud storage (Google Drive, Dropbox)
   - Use ADB to install directly: `adb install SmartDriverProfiler-debug.apk`

3. **Provide instructions**: Share the [INSTALL.md](INSTALL.md) guide with recipients so they know how to install it

## Installation

### For End Users

Want to install this app on your phone? Follow these simple steps:

1. **Download** the latest APK file (look for `SmartDriverProfiler-debug.apk`)
2. **Transfer** the APK to your Android device
3. **Enable** installation from unknown sources (one-time setup)
4. **Install** the app and grant necessary permissions

📖 **Detailed installation instructions**: See [INSTALL.md](INSTALL.md) for step-by-step guidance, troubleshooting, and screenshots.

### For Developers

If you want to build from source, see the [Building from Source](#building-from-source) section below.

## How It Works

1. **Start a Trip**: Launch the app and begin tracking your drive
2. **Data Collection**: The app continuously collects GPS coordinates, speed, acceleration, and orientation data
3. **Real-Time Processing**: Sensor data is processed and analyzed in real-time using the on-device ML model
4. **Trip Completion**: Stop the trip to receive a detailed analysis and driving score
5. **Review History**: Access past trips to track improvements over time

## Permissions

The app requires the following permissions:
- `ACCESS_FINE_LOCATION` - For GPS tracking
- `ACCESS_COARSE_LOCATION` - For location services
- `FOREGROUND_SERVICE` - To continue tracking during active trips

## Privacy

All data processing happens locally on your device. No data is transmitted to external servers, ensuring complete privacy of your driving information.

## Project Structure

```
app/
├── src/main/
│   ├── java/com/smartdriver/profiler/
│   │   ├── sensors/          # GPS and motion sensor handlers
│   │   ├── ml/               # TensorFlow Lite model integration
│   │   ├── database/         # Room database entities and DAOs
│   │   ├── ui/               # Jetpack Compose screens
│   │   └── viewmodels/       # MVVM ViewModels
│   ├── res/                  # Resources (layouts, strings, themes)
│   └── assets/               # TFLite model files
└── build.gradle.kts          # App-level build configuration
```

## Contributing

This is a personal project. If you have suggestions or find issues, feel free to document them.

## License

[Specify your license here]

---

**Note**: This app is designed for educational and personal use. Always follow local traffic laws and avoid interacting with your device while driving.
