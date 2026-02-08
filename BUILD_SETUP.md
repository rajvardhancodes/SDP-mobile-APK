# Building Smart Driver Profiler APK - Setup Guide

## Current Status

Your Android project is configured, but the build tools are not yet set up. This guide will help you get everything ready to build the APK.

## Option 1: Using Android Studio (Recommended)

This is the easiest way to build your APK.

### Steps:

1. **Download Android Studio** (if not already installed):
   - Visit: https://developer.android.com/studio
   - Download and install Android Studio
   - Run through the setup wizard (it will download the Android SDK automatically)

2. **Open Your Project**:
   - Launch Android Studio
   - Click "Open an Existing Project"
   - Navigate to: `c:\Users\raj\Desktop\SDFE PPT\mobile apk`
   - Click "OK"

3. **Wait for Sync**:
   - Android Studio will automatically:
     - Download the Gradle wrapper
     - Sync all dependencies
     - Set up the build environment
   - This may take 5-10 minutes on first run

4. **Build the APK**:
   - Click **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)**
   - Wait for the build to complete
   - Click **locate** when the notification appears
   - The APK will be at: `app\build\outputs\apk\debug\app-debug.apk`

5. **Rename and Share**:
   - Copy `app-debug.apk` to your project root
   - Rename it to `SmartDriverProfiler-debug.apk`
   - Share it following the instructions in [INSTALL.md](INSTALL.md)

---

## Option 2: Command Line Setup (Advanced)

If you prefer to build from the command line without Android Studio:

### Prerequisites:

1. **Install Java Development Kit (JDK) 17**:
   - Download from: https://adoptium.net/
   - Install and add to PATH
   - Verify: `java -version` (should show version 17)

2. **Install Gradle**:
   - Download from: https://gradle.org/releases/
   - Get version 8.2 or higher
   - Extract and add to PATH
   - Verify: `gradle -version`

3. **Install Android SDK Command-Line Tools**:
   - Download from: https://developer.android.com/studio#command-tools
   - Extract to a folder (e.g., `C:\Android\cmdline-tools`)
   - Set `ANDROID_HOME` environment variable to the SDK root

### Generate Gradle Wrapper:

```powershell
cd "c:\Users\raj\Desktop\SDFE PPT\mobile apk"
gradle wrapper --gradle-version 8.2
```

This creates:
- `gradlew.bat` (Windows wrapper script)
- `gradle/wrapper/` directory

### Build the APK:

```powershell
.\gradlew.bat assembleDebug
```

The APK will be at: `app\build\outputs\apk\debug\app-debug.apk`

---

## Option 3: Online Build Services (Easiest, No Installation)

If you don't want to install anything locally, you can use online build services:

### GitHub Actions (Free):

1. Create a GitHub account (if you don't have one)
2. Create a new repository
3. Upload your project files
4. Create a workflow file (`.github/workflows/build.yml`):

```yaml
name: Build APK

on:
  push:
    branches: [ main ]
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      
      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
          
      - name: Grant execute permission for gradlew
        run: chmod +x gradlew
        
      - name: Build with Gradle
        run: ./gradlew assembleDebug
        
      - name: Upload APK
        uses: actions/upload-artifact@v4
        with:
          name: app-debug
          path: app/build/outputs/apk/debug/app-debug.apk
```

5. Push your code
6. Go to **Actions** tab
7. Download the built APK from the artifacts section

---

## Quick Start Recommendation

**For you**, I recommend:

### ✅ Use Android Studio (Option 1)

**Why?**
- One-click installation includes everything you need
- Automatic dependency management
- Easy debugging and code editing
- Best for long-term Android development

**Time**: ~30 minutes (including download)

### After Setup

Once you have the build tools ready:

1. Run the provided `build_apk.bat` script, OR
2. Use Android Studio's Build menu, OR
3. Use the command: `.\gradlew.bat assembleDebug`

The APK can then be shared with anyone following [INSTALL.md](INSTALL.md).

---

## Next Steps

1. **Choose your setup method** (I recommend Android Studio - Option 1)
2. **Follow the steps** for your chosen option
3. **Build the APK**
4. **Test installation** on your Android device
5. **Share with others** along with the INSTALL.md guide

## Need Help?

If you encounter issues during setup:
- **Android Studio errors**: Try "File → Invalidate Caches / Restart"
- **Gradle sync fails**: Check your internet connection, Gradle needs to download dependencies
- **Java not found**: Ensure JAVA_HOME environment variable is set
- **SDK not found**: Run Android Studio's SDK Manager and install missing components

---

**Current Situation**: Your project is ready, you just need to set up the build environment once. After that, building APKs will be quick and easy!
