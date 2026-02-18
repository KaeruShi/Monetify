<div align="center">

# Monetify

An Xposed module that brings Material You (Monet) dynamic theming to third-party apps by modifying icons and color resources at runtime, allowing apps to seamlessly match your system theme.

[![License](https://img.shields.io/badge/License-GPL--3.0-blue.svg)](LICENSE)
[![Android](https://img.shields.io/badge/Android-12%2B-green.svg)](https://developer.android.com)
[![Xposed](https://img.shields.io/badge/Xposed-Required-orange.svg)](https://github.com/LSPosed/LSPosed)
</div>

## ✨ Features

- 🧩 **MVVM architecture** with clear separation between UI, ViewModel, and data layers  
- 💉 **Dagger Hilt** for dependency injection across application, ViewModel, and system components  
- 🎨 **Jetpack Compose** for building declarative, state-driven UI components  
- 🌈 **Material Expressive (Material You)** with dynamic color support and adaptive theming  
- 🎭 **Xposed-based icon replacement** to modify third-party app drawable resources at runtime  
- 🎨 **Runtime color hooking** to override third-party app color resources using the system Monet palette  

## 📱 Currently Supported Apps

- Pinterest  
- Reddit
- Spotify
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

## 📸 Screenshots

*Coming soon!*

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

## 📜 License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [YukiHookAPI](https://github.com/HighCapable/YukiHookAPI) - Powerful Xposed hooking framework
- [LSPosed](https://github.com/LSPosed/LSPosed) - Modern Xposed framework implementation  
- [Material You](https://m3.material.io/) - Google's Material Design 3
- [DexKit](https://github.com/LuckyPray/DexKit) - Dex deobfuscation library.

## ⚠️ Disclaimer

Monetify is an Xposed module that modifies the behavior of other applications and should be used at your own risk. Always back up your data before using Xposed modules, as the developer is not responsible for any damage or data loss caused by this module, and some applications may have anti-modification or detection mechanisms.

## 📞 Support

- 💬 [GitHub Discussions](https://github.com/KaeruShi/Monetify/discussions) - Ask questions and chat with the community
- 🐛 [GitHub Issues](https://github.com/KaeruShi/Monetify/issues) - Report bugs and request features
- ➤  [Telegram](https://weeabooify.t.me) - Support group

## 🌟 Star History

If you find this project useful, please consider giving it a star! ⭐

It helps the project grow and lets others discover it too.

---

<p align="center">
  <b>Made with ❤️ for the Android community</b>
</p>

<p align="center">
  <a href="https://github.com/KaeruShi/Monetify/stargazers">⭐ Star this repo</a> •
  <a href="https://github.com/KaeruShi/Monetify/issues">🐛 Report a bug</a> •
  <a href="https://github.com/KaeruShi/Monetify/discussions">💬 Join discussion</a>
</p>
