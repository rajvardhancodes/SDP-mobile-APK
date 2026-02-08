@echo off
REM Smart Driver Profiler - APK Build Script
REM This script builds a debug APK for easy distribution

echo ========================================
echo Smart Driver Profiler - APK Builder
echo ========================================
echo.

REM Check if gradlew exists
if not exist "gradlew.bat" (
    echo ERROR: gradlew.bat not found!
    echo Please run this script from the project root directory.
    pause
    exit /b 1
)

echo [1/3] Cleaning previous builds...
call gradlew.bat clean

echo.
echo [2/3] Building debug APK...
echo This may take a few minutes...
call gradlew.bat assembleDebug

if %errorlevel% neq 0 (
    echo.
    echo ERROR: Build failed!
    echo Please check the error messages above.
    pause
    exit /b 1
)

echo.
echo [3/3] Copying APK to project root...

REM Copy APK to root with a friendly name
if exist "app\build\outputs\apk\debug\app-debug.apk" (
    copy "app\build\outputs\apk\debug\app-debug.apk" "SmartDriverProfiler-debug.apk"
    echo.
    echo ========================================
    echo BUILD SUCCESSFUL!
    echo ========================================
    echo.
    echo APK Location:
    echo   - File: SmartDriverProfiler-debug.apk
    echo   - Full path: %CD%\SmartDriverProfiler-debug.apk
    echo.
    echo You can now:
    echo   1. Transfer this APK to your Android phone
    echo   2. Share it with others
    echo   3. Install it following INSTALL.md instructions
    echo.
    echo File size:
    dir "SmartDriverProfiler-debug.apk" | find "SmartDriverProfiler-debug.apk"
    echo.
) else (
    echo ERROR: APK file not found at expected location!
    echo Expected: app\build\outputs\apk\debug\app-debug.apk
)

echo ========================================
pause
