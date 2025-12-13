# RearDisplayFix

A Magisk module that automatically re-enables the rear display when the main screen turns off.  
This is a workaround for instances like when Game Turbo optimized apps disable the rear display and never restore it.

Made as a personal side project with help of AI and lots of trial-and-error.

## Notes
- Made for **Mi 11 Ultra**.
- Tested on [Xiaomi.eu ROM – HyperOS 2.0.7.0](https://xiaomi.eu/community/threads/hyperos-2-0-stable-release.74372/) and **Magisk 29.0**.

## Requirements
- Root + Magisk  
- **LSPosed** (to prevent MIUI/HyperOS from killing the background service)

## Installation
1. [Download module](https://github.com/DjomlaR/RearDisplayFix/releases/latest)
2. Flash ZIP in Magisk
3. Reboot
4. Grant root access when script first fires

## How it Works

The module installs a small system-level APK into `/system/priv-app/`.  
This APK listens for the Android `SCREEN_OFF` broadcast, and when detected, checks the status of the rear display (subscreen). When it detects that its disabled it triggers two system settings:

- Re-enable the rear display  
- Re-enable rear display gesture wake-up  

On MIUI/HyperOS, background services from user apps are often killed instantly due to aggressive battery optimization.  
Placing the APK in `priv-app` supposedly helps, but HyperOS still kills it instantly, which is why its **LSPosed dependent** to keep it alive.

I could have made it stand-alone by making a persistent notification for the apk, or even without apk, but that would require frequent polling to check for the screen state. I made it this way so that it has as small of impact on battery and resources as possible and to be as invisible as possible. Maybe it wouldn't have made much of difference but idk, since I'm not an android/java dev, this is just a side project with help of ai and lots of trial and error.

## Changelog
### **v1.0.1**
- Added missing subscreen state check, no more spammy superuser access messages.
### **v1.0.0**
- Initial release

---

## License

Licensed under the **GNU GPL** (v3).
