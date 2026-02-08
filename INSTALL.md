# Installing Smart Driver Profiler

This guide will help you install the Smart Driver Profiler app on your Android device.

## Requirements

- Android device running **Android 8.0 (Oreo) or higher**
- GPS-enabled device
- Accelerometer and gyroscope sensors (most modern smartphones have these)
- At least 50 MB of free storage space

## Installation Steps

### Step 1: Download the APK

You can get the APK file in one of these ways:
- Download from the shared link (if provided)
- Copy directly from the developer
- Build it yourself (see [README.md](README.md) for build instructions)

### Step 2: Transfer APK to Your Phone

Choose one of these methods:

**Option A: USB Cable**
1. Connect your phone to your computer via USB
2. Copy the APK file to your phone's Downloads folder
3. Safely disconnect your phone

**Option B: Email**
1. Email the APK file to yourself
2. Open the email on your phone
3. Download the attachment

**Option C: Cloud Storage**
1. Upload the APK to Google Drive, Dropbox, or OneDrive
2. Open the cloud storage app on your phone
3. Download the APK file

**Option D: ADB (Advanced Users)**
```bash
adb install app-debug.apk
```

### Step 3: Enable Installation from Unknown Sources

Since this app isn't from the Google Play Store, you need to allow installation from "unknown sources."

**For Android 8.0 - 12:**
1. When you tap the APK file, you'll see a prompt
2. Tap **Settings** when prompted
3. Enable **Allow from this source**
4. Go back and tap **Install**

**For Android 13+:**
1. Go to **Settings** → **Security** → **More security settings**
2. Tap **Install unknown apps**
3. Select your file manager or browser (e.g., Files, Chrome)
4. Enable **Allow from this source**

**Alternative Path (varies by manufacturer):**
- Samsung: Settings → Biometrics and security → Install unknown apps
- Google Pixel: Settings → Apps → Special app access → Install unknown apps
- Other brands: Settings → Security → Unknown sources

### Step 4: Install the App

1. Use your phone's file manager to navigate to the Downloads folder
2. Tap on the **SmartDriverProfiler-debug.apk** file (or similar name)
3. Review the permissions the app needs:
   - **Location**: For GPS tracking during trips
   - **Sensors**: For accelerometer and gyroscope data
   - **Notifications**: For trip recording notifications
4. Tap **Install**
5. Wait for the installation to complete (usually takes 10-30 seconds)
6. Tap **Open** to launch the app, or find it in your app drawer

### Step 5: Grant Permissions

When you first open the app:
1. The app will request **location permissions** → Tap **Allow**
2. Choose "**Allow all the time**" or "**Allow only while using the app**"
3. Grant any additional permissions as requested
4. The app is now ready to use!

## Troubleshooting

### "App not installed" Error
- **Cause**: Conflicting app signature or corrupted APK
- **Solution**: 
  - If you have an older version installed, uninstall it first then try again
  - Re-download the APK file (it may have been corrupted during download)

### "Installation blocked" Message
- **Cause**: Unknown sources not enabled
- **Solution**: Follow Step 3 again carefully for your Android version

### App Crashes on First Launch
- **Cause**: Missing permissions or incompatible Android version
- **Solution**: 
  - Ensure your Android version is 8.0 or higher
  - Go to Settings → Apps → Smart Driver Profiler → Permissions
  - Enable Location permissions manually

### GPS Not Working
- **Cause**: Location services disabled or permissions not granted
- **Solution**:
  - Enable GPS in Quick Settings or Settings → Location
  - Grant location permissions to the app (see above)
  - Ensure "High accuracy" mode is enabled

### Sensors Not Detecting Movement
- **Cause**: Background restrictions or battery optimization
- **Solution**:
  - Go to Settings → Apps → Smart Driver Profiler
  - Disable battery optimization for this app
  - Allow background activity

## Uninstalling

To remove the app:
1. Go to **Settings** → **Apps**
2. Find **Smart Driver Profiler**
3. Tap **Uninstall**
4. Confirm the uninstallation

## Privacy & Security

- ✅ This app processes all data **locally on your device**
- ✅ No data is sent to external servers
- ✅ No internet connection required for core functionality
- ✅ You can revoke permissions at any time in Settings

## Need Help?

If you encounter issues not covered here, contact the app developer or check the [README.md](README.md) for more information.

---

**Version**: 1.0  
**Last Updated**: February 2026
