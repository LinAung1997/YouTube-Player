# YouTube Player - Professional Android Media Application

A feature-rich, production-ready Android application for playing YouTube videos with advanced playback controls, gesture controls, subtitle support, and comprehensive history management.

## Features

### Core Playback
- **Multiple URL Format Support**: youtube.com, youtu.be, m.youtube.com, youtube.com/shorts
- **AndroidX Media3 (ExoPlayer)**: Professional media engine with hardware acceleration
- **Automatic Video Detection**: Extract video ID, load metadata, thumbnails, and channel info
- **Resume Playback**: Automatically save and resume from last position
- **Picture-in-Picture**: Continue playback in mini window

### Player Controls
- **Professional UI**: Play/Pause, Previous, Next, Seek Bar with time display
- **Fullscreen Support**: Seamless full-screen experience
- **Double-Tap Seeking**: Rewind 10s (left), Forward 10s (right)
- **Playback Speed**: 0.25x to 3.0x with memory of user preference
- **Audio Boost**: 100% to 200% software amplification
- **Screen Lock**: Prevent accidental touches during playback

### Gesture Controls
- **Left Vertical Swipe**: Brightness adjustment with overlay
- **Right Vertical Swipe**: Volume control with overlay
- **Single Tap**: Show/hide controls
- **Double Tap**: Play/Pause
- **Configurable Sensitivity**: Adjust gesture detection sensitivity

### Subtitle System
- **Multiple Formats**: SRT, VTT, ASS, SSA support
- **Manual Loading**: File picker for internal storage and SD card
- **Auto-Detection**: Automatic search in common folders
- **Customization**: Size, color, background, position, delay
- **Encoding Support**: UTF-8, Unicode, Zawgyi

### History Management
- **Automatic Tracking**: Every watched video is recorded
- **Rich Metadata**: Title, URL, ID, thumbnail, channel, duration, watch count
- **Search & Sort**: Multiple sorting options and search capability
- **Export/Import**: TXT, CSV, JSON support
- **Quick Actions**: Resume, share, copy URL, add to favorites

### Favorites System
- **One-Tap Favorites**: Easy to add/remove
- **Search & Sort**: Find your favorite videos quickly
- **Persistent Storage**: SQLite database
- **Quick Access**: Dedicated favorites section on home

### Additional Features
- **Sleep Timer**: 5, 10, 15, 30, 60 minute options
- **Background Playback**: Continue watching with screen off
- **Foreground Service**: Media notifications with controls
- **Clipboard Monitoring**: Detect YouTube URLs in clipboard
- **Cache Management**: Media3 cache with size management
- **Network Handling**: Auto-reconnect, retry on failure
- **Playback Statistics**: Real-time resolution, bitrate, buffer status
- **Material Design 3**: Modern, responsive UI
- **Dark/Light Mode**: Full theme support with system preference detection

### Settings
- **Appearance**: Theme customization
- **Playback**: Default speed, auto-resume, auto-fullscreen
- **Gestures**: Enable/disable and sensitivity control
- **Subtitles**: Default size, color, encoding
- **Storage**: Cache and history management

## Technical Architecture

### Architecture Pattern
- **MVVM**: Model-View-ViewModel pattern
- **Repository Pattern**: Clean data access layer
- **Dependency Injection**: Manual DI for clean code

### Technology Stack
- **Min SDK**: API 21 (Android 5.0)
- **Target SDK**: API 35 (Latest Stable)
- **Language**: Java
- **Database**: SQLite with Room (optional)
- **Media Engine**: AndroidX Media3 (ExoPlayer 2.x)
- **UI Framework**: Material Design 3 (MDC)

### Key Libraries
- AndroidX Core & Compat
- AndroidX Media3 / ExoPlayer
- Material Design Components
- Android Lifecycle
- SQLite
- Retrofit (for API calls, if needed)

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/hnl/ytplayer/
│   │   │   ├── ui/
│   │   │   │   ├── activities/
│   │   │   │   ├── fragments/
│   │   │   │   ├── adapters/
│   │   │   │   └── viewmodels/
│   │   │   ├── player/
│   │   │   │   ├── PlayerManager.java
│   │   │   │   ├── GestureController.java
│   │   │   │   └── SubtitleManager.java
│   │   │   ├── data/
│   │   │   │   ├── database/
│   │   │   │   ├── dao/
│   │   │   │   └── repository/
│   │   │   ├── utils/
│   │   │   │   ├── YouTubeExtractor.java
│   │   │   │   ├── ClipboardHelper.java
│   │   │   │   └── PermissionManager.java
│   │   │   ├── services/
│   │   │   │   └── MediaNotificationService.java
│   │   │   └── App.java
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   ├── values/
│   │   │   ├── drawable/
│   │   │   └── anim/
│   │   └── AndroidManifest.xml
│   └── test/
├── build.gradle
└── proguard-rules.pro
```

## Prerequisites

- Android Studio Hedgehog or newer
- JDK 11 or higher
- Android SDK API 35
- Minimum 2GB RAM for development

## Installation & Build Instructions

### 1. Clone Repository
```bash
git clone https://github.com/LinAung1997/Youtube-Player.git
cd Youtube-Player
```

### 2. Open in Android Studio
- Launch Android Studio
- File → Open → Select the project directory
- Wait for Gradle sync to complete

### 3. Configure Android SDK
- File → Project Structure → SDK Location
- Ensure API 35 SDK is installed
- Apply changes

### 4. Build the Project
```bash
# Using Gradle wrapper
./gradlew build

# Or in Android Studio
Build → Make Project
```

### 5. Run on Device/Emulator
```bash
./gradlew installDebug
```

Or use Android Studio's Run button (Shift+F10)

### 6. Release Build
```bash
./gradlew assembleRelease
# APK located in: app/build/outputs/apk/release/
```

## Configuration

### API Keys (if needed)
1. Create `local.properties` in project root
2. Add any required API keys

### Default Settings
Edit `app/src/main/res/values/strings.xml` and `dimens.xml` for default values

## Database

The app uses SQLite for local storage:

### Tables
- **history**: Video watch history
- **favorites**: Favorite videos
- **settings**: User preferences

Database is automatically created on first launch.

## Permissions

The app requires the following permissions:

- `INTERNET`: Stream video content
- `ACCESS_NETWORK_STATE`: Monitor connection
- `WAKE_LOCK`: Screen/CPU wake lock
- `READ_EXTERNAL_STORAGE`: Read subtitle files
- `READ_MEDIA_VIDEO`: Access video files (Android 13+)
- `FOREGROUND_SERVICE`: Media notifications
- `POST_NOTIFICATIONS`: Notification display

All permissions follow Android permission guidelines with proper runtime request handling.

## Performance Optimization

- **Memory Management**: Proper lifecycle-aware resource cleanup
- **Battery Optimization**: Efficient background service usage
- **Network Optimization**: Smart buffering and caching
- **UI Performance**: Smooth scrolling with ViewHolder pattern
- **Device Optimization**: Supports both low-end (API 21) and high-end devices

## Code Quality

- ✅ Clean Architecture Principles
- ✅ MVVM Pattern Implementation
- ✅ No Deprecated APIs
- ✅ Proper Exception Handling
- ✅ Global Crash Handler
- ✅ Memory Leak Prevention
- ✅ Material Design Compliance
- ✅ Comprehensive Documentation

## Building APK for Distribution

### Debug APK
```bash
./gradlew assembleDebug
```

### Release APK (Unsigned)
```bash
./gradlew assembleRelease
```

### Sign APK
```bash
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 \
  -keystore ~/my-release-key.keystore \
  app/build/outputs/apk/release/app-release-unsigned.apk \
  alias_name
```

### Verify Signature
```bash
jarsigner -verify -verbose -certs app-release-signed.apk
```

## Testing

The project includes:
- Unit tests for core components
- Instrumented tests for UI
- Test resources and mock data

Run tests with:
```bash
./gradlew test                    # Unit tests
./gradlew connectedAndroidTest    # Instrumented tests
```

## Troubleshooting

### Gradle Sync Issues
```bash
# Clean and rebuild
./gradlew clean
./gradlew build
```

### SDK Issues
- Verify API 35 SDK is installed in SDK Manager
- Check Android Studio's Java version: min JDK 11

### Runtime Issues
- Check permissions in Android Settings
- Clear app data: Settings → Apps → YouTube Player → Storage → Clear Data
- Check logcat for detailed error messages

### Performance Issues
- Reduce video quality setting
- Close background apps
- Clear cache in app settings

## Contributing

For improvements and bug fixes:
1. Create a feature branch
2. Make your changes
3. Test thoroughly on multiple devices
4. Submit a pull request

## License

This project is provided as-is for educational and personal use.

## Support & Feedback

For issues, feature requests, or questions:
- Check existing GitHub issues
- Create a new issue with detailed description
- Include device info and Android version

## Version History

### v1.0.0 (Initial Release)
- Core video playback
- History management
- Favorites system
- Gesture controls
- Subtitle support
- Settings panel
- Material Design 3 UI

---

**Developed with ❤️ for Android enthusiasts**

For the latest updates and documentation, visit: https://github.com/LinAung1997/Youtube-Player
