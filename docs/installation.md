# Installation

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

## Local docs development

Run the local VitePress server to preview the documentation:

```bash
npm run docs:dev
```
