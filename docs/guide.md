# Overview

Monetify is an Xposed module for Android 12+ that brings customization to installed apps by modifying icons and color resources at runtime. It is designed to match apps to your device style while keeping hooks and runtime logic separated from the core UI.

## Features

- **Dagger Hilt** for dependency injection across application, ViewModel, and system components.
- **Jetpack Compose** for declarative, state-driven UI.
- **Material Expressive** for adaptive theming and dynamic colors.
- **Import / Export** configuration for easy backup and sharing.

## Requirements

- Android 12+ (API 31+)
- Xposed Framework ([LSPosed](https://github.com/JingMatrix/LSPosed) recommended)
- Root access with Magisk, KernelSU, SukiSU, APatch, or similar

## Install from release

1. Download the latest APK from [Releases](https://github.com/KaeruShi/Monetify/releases)
2. Install the APK on your device
3. Activate the module in LSPosed/Xposed Framework
4. Open Monetify and configure your apps

## Build from source

**Prerequisites**

- Android Studio Hedgehog (2023.1.1) or newer
- JDK 18
- Android SDK with API 36

```bash
git clone https://github.com/KaeruShi/Monetify.git
cd Monetify
./gradlew assembleRelease
```

The APK will be generated at `app/build/outputs/apk/release/`.