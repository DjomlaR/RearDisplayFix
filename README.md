![GitHub Downloads (all assets, all releases)](https://img.shields.io/github/downloads/DjomlaR/RearDisplayFix/total)

# RearDisplayFix

A LSPosed module that automatically checks and re-enables the rear display when the main screen turns off.  
This is a workaround for instances like when Game Turbo optimized apps disable the rear display and never restore it.

Made as a personal side project with help of AI and lots of trial-and-error.

## Notes
- Made for **Mi 11 Ultra**.
- Tested on [Xiaomi.eu ROM – HyperOS 2.0.7.0](https://xiaomi.eu/community/threads/hyperos-2-0-stable-release.74372/) and **LSPosed 1.9.2**.

## Requirements
- Root
- **LSPosed** (to reliably detect when phone goes to sleep, and prevent MIUI/HyperOS from killing the app when its needed)

## Installation
1. [Download APK](https://github.com/DjomlaR/RearDisplayFix/releases/latest)
2. Install it and enable in LSPosed (make sure System Framework hook is selected)
3. Reboot
4. Grant root access when script first fires and enjoy!

## How it Works

The APK hooks into the Android System Framework, from where it monitors when phone screen turns off using `startedGoingToSleep` event. Once that event happens, it launches the app which then executes commands to check if rear display (subscreen) is disabled, and if it is:

- Re-enables the rear display  
- Re-enables rear display gesture wake-up

On MIUI/HyperOS, background services from user apps are often killed instantly due to aggressive battery optimization.  
My previous attempts of placing the APK in `priv-app` by making Magisk module were also unreliable, because HyperOS still kills it almost always, which is why its **LSPosed dependent** to wake it when needed.

I could have made it stand-alone by making a persistent notification for the apk, or even as Magisk module without apk, but that would require frequent polling to check for the screen state. I made it this way so that it has as small of impact on battery and resources as possible and to be as invisible as possible. Maybe it wouldn't have made much of difference but idk, since I'm not an android/java dev, this is just a side project with help of ai and lots of trial and error.

## Changelog
### **v1.1.6**
- Made sure to execute the command only when main display is turned off
### **v1.1.4**
- Complete apk rewrite into lsposed module
### **v1.0.1**
- Added missing subscreen state check, no more spammy superuser access messages
### **v1.0.0**
- Initial release as magisk module

---

## License

Licensed under the **GNU GPL** (v3).
