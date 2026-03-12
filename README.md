<div align="center">

# Monetify

An Xposed module that brings customization to user apps by modifying icons and color resources at runtime, allowing apps to seamlessly match your system theme.

[![Platform](https://img.shields.io/badge/Android-12%2B-green?style=for-the-badge&logo=android)](https://www.android.com/)
[![Framework](https://img.shields.io/badge/Module-Xposed-red?style=for-the-badge)](https://github.com/JingMatrix/LSPosed)
[![Telegram](https://img.shields.io/badge/Telegram-Join-26A5E4?style=for-the-badge&logo=telegram&logoColor=white)](https://t.me/weeabooify)
</div>

## ✨ Features

- 🧩 **MVVM architecture** with clear separation between UI, ViewModel, and data layers  
- 💉 **Dagger Hilt** for dependency injection across application, ViewModel, and system components  
- 🎨 **Jetpack Compose** for building declarative, state-driven UI components  
- 🌈 **Material Expressive** with dynamic color support and adaptive theming  
- 🎭 **Xposed runtime resource hooking** for modifying third-party app icons and colors dynamically  
- 📦 **Import / Export configuration** for easy backup and sharing of your setup  

## 📱 Currently Supported Apps

- DocumentsUI
- Pinterest  
- Reddit
- X
- Substratum Lite

## 📋 Requirements

- **Android 12+** (API 31+)
- **Xposed Framework** ([LSPosed](https://github.com/LSPosed/LSPosed) recommended)
- **Root Access** (Magisk, KernelSU, APatch, etc)

## 🚀 Installation

1. Download the latest APK from [Releases](https://github.com/KaeruShi/Monetify/releases)
2. Install the APK on your device
3. Activate the module in LSPosed/Xposed Framework
4. Open Monetify and configure your apps
5. Enjoy! 🎉

## 🛠️ Building from Source

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 18
- Android SDK with API 36

### Build Steps

```bash
# Clone the repository
git clone https://github.com/KaeruShi/Monetify.git
cd Monetify

# Build the APK
./gradlew assembleRelease

# APK will be in app/build/outputs/apk/release/
```

## 🤝 Contributing

Contributions are always welcome and greatly appreciated!
Whether you’re fixing a small typo, improving documentation, refactoring code, or adding a brand-new feature — every contribution matters ❤️

## 🗺️ Roadmap

### Planned Features
- [ ] Shizuku support
- [ ] More apps support
- [ ] More icon packs
- [ ] More customization options

See all the progress [here](https://github.com/KaeruShi/Monetify/commits/master)

## 🐛 Bug Reports & Feature Requests

Found a bug or have a feature request? Please use [GitHub Issues](https://github.com/KaeruShi/Monetify/issues).

Before creating a new issue, please:
- Check if the issue already exists
- Include as much detail as possible
- Provide logcat output if reporting a crash

## 🙏 Acknowledgments

- [YukiHookAPI](https://github.com/HighCapable/YukiHookAPI) - Powerful Xposed hooking framework
- [DexKit](https://github.com/LuckyPray/DexKit) - Dex deobfuscation library
- [LSPosed](https://github.com/LSPosed/LSPosed) - Modern Xposed framework implementation  
- [Material You](https://m3.material.io/) - Google's Material Design 3

## ⚠️ Disclaimer
> [!WARNING]
> - Monetify is an Xposed module that modifies the behavior of other applications.Use it at your own risk and always back up your data. The developer is not responsible for any damage or data loss.
> - This project is still under active development. Some features may be incomplete, experimental, or may not work as expected.

## 🌟 Star History

If you find this project useful, please consider giving it a star! ⭐
<a href="https://gitdata.xuanhun520.com/?repos=kaerushi/monetify&type=Date">
<picture >
  <source media="(prefers-color-scheme: dark) and (max-width: 800px)" srcset="https://gitdata.xuanhun520.com/api/starimg?repos=kaerushi/monetify&type=Date&theme=dark" />
  <source  media="(prefers-color-scheme: light) and (max-width: 800px)" srcset="https://gitdata.xuanhun520.com/api/starimg?repos=kaerushi/monetify&type=Date&theme=light" />
  <img style="width: 800px; height: 533px;" alt="Star History Chart" src="https://gitdata.xuanhun520.com/api/starimg?repos=kaerushi/monetify&type=Date&theme=dark" />
</picture>
</a>

