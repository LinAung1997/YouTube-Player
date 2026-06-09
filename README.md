# YouTube Player - Premium Android Media Player

A production-ready Android application built with modern architecture patterns, delivering YouTube/Netflix-level media playback experience with advanced features including online streaming, offline downloads, gesture controls, and comprehensive playback management.

## 📱 Key Features

### Core Playback
- **YouTube URL Support**: Paste and play directly from YouTube links
- **Adaptive Streaming**: Quality switching (144p - 1080p)
- **Media3 ExoPlayer**: Modern, efficient video engine
- **Fullscreen Support**: Portrait and landscape with auto-rotation
- **Background Playback**: Continue audio when app is minimized

### Advanced Gestures & Controls
- **Double-Tap Seek**: ±10 seconds with animated indicators
- **Volume Control**: Right-side vertical swipe gesture
- **Brightness Control**: Left-side vertical swipe gesture
- **Playback Speed**: 0.25x to 3x with persistence
- **Picture-in-Picture**: Continue playback in floating window

### Content Management
- **Video History**: Auto-save with resume position
- **Favorites System**: Save and organize favorite videos
- **Offline Downloads**: Download for offline viewing
- **Smart Cache**: LRU-based disk caching with statistics

### Subtitles & Accessibility
- **Multiple Formats**: SRT, VTT, ASS, SSA support
- **Auto-Detection**: Automatic subtitle file discovery
- **Advanced Controls**: Font size, color, position, delay adjustment
- **Encoding Support**: UTF-8, Unicode, Zawgyi

### Professional Features
- **Sleep Timer**: 5/10/15/30/60 minute options
- **Playback Statistics**: Resolution, bitrate, buffer health display
- **Audio Boost**: Up to 200% volume boost
- **Screen Lock**: Prevent accidental control interaction
- **Mini Player**: Floating window while browsing

### User Experience
- **Material Design 3**: Modern, intuitive interface
- **Dark/Light Mode**: Full theme support
- **Tablet Optimization**: Responsive layout design
- **Smooth Animations**: Professional UI transitions

## 🏗️ Architecture

### Design Pattern
- **MVVM**: Model-View-ViewModel architecture
- **Repository Pattern**: Abstraction layer for data
- **Dependency Injection**: Clean component management
- **Lifecycle Aware**: Proper resource management

### Core Components
- **UI Layer**: Activities, Fragments, Custom Views
- **ViewModel Layer**: State management and business logic
- **Repository Layer**: Data abstraction and caching
- **Database Layer**: SQLite with Room/DAO pattern
- **Media Engine**: Media3 ExoPlayer integration

## 📊 Database Schema

### Tables
- **video_history**: Play history with resume positions
- **favorites**: Saved favorite videos
- **downloaded_videos**: Offline download metadata
- **settings_cache**: User preferences

## 🚀 Technical Stack

| Component | Technology |
|-----------|------------|
| Language | Java |
| Min SDK | API 21 (Android 5.0) |
| Target SDK | Latest Stable |
| Media Engine | AndroidX Media3 (ExoPlayer) |
| Database | SQLite |
| UI Framework | Material Design 3 |
| Architecture | MVVM + Repository |
| Preference Storage | SharedPreferences |

## 📂 Project Structure

```
YouTube-Player/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/hnl/ytplayer/
│   │   │   │   ├── ui/
│   │   │   │   │   ├── activities/
│   │   │   │   │   ├── fragments/
│   │   │   │   │   ├── adapters/
│   │   │   │   │   └── custom/
│   │   │   │   ├── viewmodel/
│   │   │   │   ├── repository/
│   │   │   │   ├── database/
│   │   │   │   ├── model/
│   │   │   │   ├── service/
│   │   │   │   ├── util/
│   │   │   │   └── player/
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle
└── settings.gradle
```

## 🔧 Build & Run

### Prerequisites
- Android Studio Flamingo or later
- JDK 11+
- Android SDK 21+

### Build
```bash
./gradlew build
```

### Run
```bash
./gradlew installDebug
adb shell am start -n com.hnl.ytplayer/.ui.activities.MainActivity
```

## 📋 Screens

1. **Home Screen**: URL input, recent videos
2. **Player Screen**: Full-featured video playback
3. **History Screen**: View and manage play history
4. **Favorites Screen**: Save and organize favorites
5. **Downloads Screen**: Manage offline content
6. **Settings Screen**: Configure app preferences

## 🔐 Security & Optimization

### Security
- ProGuard/R8 obfuscation
- Secure credential handling
- Certificate pinning ready

### Optimization
- Memory-efficient buffering
- Battery-optimized wake locks
- Proper lifecycle management
- Efficient cache eviction

### Stability
- Global exception handling
- Crash logging
- Leak detection and prevention
- Proper resource cleanup

## 📝 Development Guidelines

### Code Style
- Follow Google's Java Style Guide
- Use AndroidX libraries exclusively
- Implement proper null safety
- Write meaningful comments for complex logic

### Testing
- Unit tests for ViewModels
- Integration tests for Repository
- UI tests for critical flows

### Performance
- Lazy load content
- Implement pagination
- Use efficient database queries
- Monitor memory usage

## 🚫 Deprecated APIs

This project uses **ZERO deprecated APIs**:
- ✅ AndroidX Media3 (not old MediaPlayer)
- ✅ ViewBinding (not ButterKnife)
- ✅ ViewModel (not retained fragments)
- ✅ Room/DAO (not raw SQLite)

## 📦 Dependencies

Key libraries:
- `androidx.appcompat:appcompat`
- `androidx.media3:media3-exoplayer`
- `androidx.room:room-runtime`
- `androidx.lifecycle:lifecycle-viewmodel`
- `com.google.android.material:material`

## 📄 License

This project is provided as-is for educational and commercial use.

## 👨‍💻 Author

Senior Android Architect & Media Player Engineer

---

**Version**: 1.0.0  
**Last Updated**: 2026-06-09  
**Status**: Production Ready ✅
