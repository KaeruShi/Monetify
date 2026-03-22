<div align="center">

# Monetify

An Xposed module that brings customization to user apps by modifying icons and color resources at runtime, allowing apps to seamlessly match your device style.

[![Platform](https://img.shields.io/badge/Android-12%2B-3DDC84?style=for-the-badge&logo=android)](https://www.android.com/)
[![Framework](https://img.shields.io/badge/Module-Xposed-red?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iODAwIiBoZWlnaHQ9IjgwMCIgdmlld0JveD0iMCAwIDgwMCA4MDAiIGZpbGw9Im5vbmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+PHBhdGggZD0iTTI1NS42NSA2Ni42N2E1OC43IDU4LjcgMCAwIDEgNDAuNzM5IDE2LjYwOGwuNTE2LjUwOC4wMDYuMDA2IDEwMy4yNSAxMDIuOTQxTDUwMy4wNjIgODMuODJsLjA2Ny0uMDY3YTU4Ljg2IDU4Ljg2IDAgMCAxIDQxLjgwNC0xNy4wODNoLS4wMDFjMTQuODc3LjAzNyAzMC4wNDMgNS42MzkgNDEuNTU5IDE3LjEyOGwuMDExLjAxLjAxMS4wMSAxMjkuNjY5IDEyOS42Ny4yNDguMjQ3LjI0LjI1NWMxMC45MDMgMTEuNTMyIDE2LjY2MSAyNi40MDQgMTYuNjYxIDQxLjIyMyAwIDE0LjkyNC01LjYwMiAzMC4xNDktMTcuMTI4IDQxLjcwM2wtLjAwNS4wMDYtLjAwNi4wMDUtMTAzLjAxOCAxMDMuMTQyIDEwMy4wMDcgMTAyLjk5My4wMS4wMTEuMDEyLjAxMWMxMS41MjYgMTEuNTU0IDE3LjEyOCAyNi43OCAxNy4xMjggNDEuNzA0cy01LjYwMiAzMC4xNS0xNy4xMjggNDEuNzAzTDU4Ni40NDYgNzE2LjI0OGE1OC44NiA1OC44NiAwIDAgMS00MS42NTggMTcuMDgydi4wMDFjLTE0LjkyNCAwLTMwLjE1LTUuNjAyLTQxLjcwNC0xNy4xMjhsLS4wMTEtLjAxMi0uMDExLS4wMUw0MDAuMTYgNjEzLjI2NSAyOTYuODk1IDcxNi4yMDlsLS4wNC4wMzlhNTguODQgNTguODQgMCAwIDEtNDEuMDQ5IDE3LjA4MmgtLjgyYTYwLjEyIDYwLjEyIDAgMCAxLTQxLjE2Ni0xNi44MjJsLS4xNjktLjE2Mi0uMTY0LS4xNjUtMTI5LjY3LTEyOS42ODUtLjAzMi0uMDMzLS4wMzItLjAzMmE1OC44NiA1OC44NiAwIDAgMS0xNy4wODMtNDEuODA0Yy4wMzctMTQuODc4IDUuNjQtMzAuMDQyIDE3LjEyOC00MS41NTlsLjAxLS4wMS4wMTEtLjAxMkwxODYuNzI1IDQwMC4xNSA4My43OTEgMjk2Ljg5NWwtLjAwNS0uMDA1YTU4LjcgNTguNyAwIDAgMS0xNy4xMTYtNDEuMjI4di0uNDQ5YzAtMTUuMzE4IDUuODUzLTI5Ljc5MiAxNi42Ni00MS4yMjNsLjI0MS0uMjU1TDIxMy43MzUgODMuNTcxbC4yNTUtLjI0YzExLjUzMi0xMC45MDQgMjYuNDA0LTE2LjY2MSA0MS4yMjMtMTYuNjYxem0xNzkuODgyIDUxMS4yNTggMTAyLjg2NCAxMDIuODc4LjMwNS4yODhjMS41NTkgMS40MDMgMy42OTEgMi4yMzcgNi4wODcgMi4yMzdoLjE0NWE4Ljg1IDguODUgMCAwIDAgNi4yNzItMi41NTRsMTI5LjYyMS0xMjkuNjJjMS41NjQtMS41NzkgMi41MDUtMy44MjQgMi41MDUtNi4zNjkgMC0yLjU1Ni0uOTQ5LTQuODExLTIuNTI1LTYuMzkyaC0uMDAxbC0xMDIuOTY0LTEwMi45NXptLTM1LjcxMy0xMzcuOTAxYTI1LjEgMjUuMSAwIDAgMC0xNy42OTMgNy4yOTYgMjUuMTA0IDI1LjEwNCAwIDAgMCAwIDM1LjQwMmMxMC4wMTYgOS43MzIgMjUuNjY5IDkuNzMyIDM1LjM4NiAwYTI1LjEwNyAyNS4xMDcgMCAwIDAgMC0zNS40MDIgMjUuMSAyNS4xIDAgMCAwLTE3LjY5My03LjI5Nm02NS4yMDQtNjQuODQyYTI1LjEgMjUuMSAwIDAgMC0xNy43IDcuMzAzIDI1LjEwMyAyNS4xMDMgMCAwIDAgMCAzNS4zODZjOS43MzMgOS43NDggMjUuNjY5IDkuNzQ4IDM1LjQwMiAwYTI1LjEwNyAyNS4xMDcgMCAwIDAgMC0zNS4zODYgMjUuMSAyNS4xIDAgMCAwLTE3LjcwMi03LjMwM20tMTMwLjA0Ni0uMzYyYTI1LjEgMjUuMSAwIDAgMC0xNy43MDIgNy4zMDMgMjUuMTA3IDI1LjEwNyAwIDAgMCAwIDM1LjM4NmM5Ljc0OCA5LjczMiAyNS42NjkgOS43MzIgMzUuNDAyIDBhMjUuMTAzIDI1LjEwMyAwIDAgMCAwLTM1LjM4NiAyNS4xIDI1LjEgMCAwIDAtMTcuNy03LjMwM00yNTUuMjEzIDExNi42N2MtMS44OTkgMC00LjQ0NC43NjUtNi43MzUgMi44NjlMMTE5LjUyNyAyNDguNDg4Yy0yLjE4NiAyLjM3My0yLjg1NyA0LjYwNi0yLjg1NyA2LjcyNXYuMzA4YTguNyA4LjcgMCAwIDAgMi41MzEgNi4wNzRsMTAyLjg4IDEwMy4yMDMgMTQyLjcyNS0xNDIuNzA5LTEwMy4xOTEtMTAyLjg4My0uMzExLS4yOTVhOC43IDguNyAwIDAgMC01Ljc3MS0yLjI0MXptMTQ0Ljk2OCAxOTMuMzFhMjUuMSAyNS4xIDAgMCAwLTE3LjY5MyA3LjI5NSAyNS4xMDcgMjUuMTA3IDAgMCAwIDAgMzUuNDAyYzkuNzMzIDkuNzMyIDI1LjY2OSA5LjczMiAzNS4zODYgMGEyNS4xMDQgMjUuMTA0IDAgMCAwIDAtMzUuNDAyIDI1LjEgMjUuMSAwIDAgMC0xNy42OTMtNy4yOTUiIGZpbGw9IiNmZmYiLz48L3N2Zz4=)](https://github.com/JingMatrix/LSPosed)
[![Telegram](https://img.shields.io/badge/Telegram-Join-26A5E4?style=for-the-badge&logo=telegram&logoColor=white)](https://t.me/weeabooify) <br>
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Material Expressive](https://img.shields.io/badge/Material%20Expressive-FF7BC4?style=for-the-badge&logo=materialdesign&logoColor=white)
</div>

## ✨ Features

- 🧩 **MVVM architecture** with clear separation between UI, ViewModel, and data layers  
- 💉 **Dagger Hilt** for dependency injection across application, ViewModel, and system components  
- 🎨 **Jetpack Compose** for building declarative, state-driven UI components  
- 🌈 **Material Expressive** with dynamic color support and adaptive theming  
- 🎭 **Xposed runtime hooking** for modifying third-party app icons and colors dynamically  
- 📦 **Import / Export configuration** for easy backup and sharing of your setup  

## 📱 Currently Supported Apps

List of applications that currently support Monet theming and icon pack.  
Some apps may have partial support or are still in development.

| App            | Support        |
|---------------|---------------|
| DocumentsUI    | Monet, Icon Pack |
| GitHub         | Monet, Icon Pack |
| Instagram      | Monet |
| Pinterest      | Icon Pack |
| Pixiv          | _Soon_ |
| Reddit         | Monet, Icon Pack |
| Substratum Lite| Monet, Icon Pack |
| WhatsApp       | _Soon_ |
| X / Twitter    | Monet, Icon Pack |
| Youtube        | Monet |

## 📋 Requirements

- **Android 12+** (API 31+)
- **Xposed Framework** ([LSPosed](https://github.com/JingMatrix/LSPosed) recommended)
- **Root Access** (Magisk, KernelSU, SukiSU, APatch, etc)

## 🚀 Installation

1. Download the latest APK from [Releases](https://github.com/KaeruShi/Monetify/releases)
2. Install the APK on your device
3. Activate the module in LSPosed/Xposed Framework
4. Open Monetify and configure your apps
5. Enjoy! 🎉

## 🛠️ Building from Source

**Prerequisites**
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 18
- Android SDK with API 36

**Build Steps**

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

**Planned Features**
- [ ] Shizuku support
- [ ] More apps support
- [ ] More icon packs
- [ ] More customization options
- [ ] Custom icons

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


