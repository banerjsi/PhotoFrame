# Photo Slideshow App for Android Tablets (API 16+)

A lightweight Android photo slideshow app designed for older tablets (Android 4.1 / API 16 and above). The app allows users to browse and select a folder of images, and then plays a fullscreen slideshow of the photos with configurable intervals.

---

## ✨ Features

- ✅ Supports devices running **Android 4.1 (Jelly Bean)** and above
- 📁 **Browse and select any folder** on the device/SD card for slideshow
- 🖼️ Displays photos in **fullscreen mode**, scaled to fit
- ⏱️ Configurable **interval timer** between images
- ⏸️ **Tap to pause/resume** slideshow at any time
- 🔄 Manual **Next / Previous** navigation when paused
- 📛 Displays **file name** of the current image when paused
- ⚡ Keeps screen on during slideshow
- 🚫 Graceful handling when no folder or images are selected

---

## 📦 APK Installation

To install the app manually on an Android device:

1. **Build the APK**:
   - In Android Studio:  
     `Build > Build Bundle(s) / APK(s) > Build APK(s)`
   - Locate the generated `.apk` (usually at `app/build/outputs/apk/debug/app-debug.apk`)

2. **Enable Unknown Sources** on your tablet:
   - Settings > Security > Enable "Unknown sources"

3. **Transfer the APK**:
   - Via USB, email, cloud storage, or ADB:
     ```bash
     adb install app-debug.apk
     ```

4. **Install and Run**:
   - Open the APK file and tap **Install**
   - Launch the app from the app drawer

---

## 🛠️ Development Notes

- **Minimum SDK**: 16 (Android 4.1 Jelly Bean)
- **Target SDK**: 16 (can be updated to meet Play Store requirements)
- No use of modern dependencies or Jetpack libraries
- Built with a **NoActivity** starter template for minimal overhead
- Uses native `File` API and `Handler` for slideshow logic

---

## 📁 Folder Selection

The folder selection uses a custom `Intent` for picking directories. On older Android versions (pre-Storage Access Framework), this is handled using basic file browsing and custom folder-picking logic.

---

## ⏱️ Slideshow Timing

The interval between image changes can be entered in seconds in the main screen before starting the slideshow. Default value is **5 seconds**.

---

## 💡 How to Use

1. Launch the app
2. Tap **"Browse Folder"** and select the folder with your images
3. Set slideshow interval (in seconds)
4. Tap **"Play Slideshow"**
5. Tap the image to **pause** and reveal:
   - Current image filename
   - "Next" and "Previous" buttons for manual navigation
6. Tap again to **resume** automatic slideshow

---

## 📷 Supported File Types

- `.jpg`, `.jpeg`, `.png`, `.gif`  
- Filters non-image files automatically

---

## 🚀 Future Improvements (Optional)

- Swipe gestures for next/previous
- Persisting interval setting with `SharedPreferences`
- Improved folder selection UI for newer Android versions
- Option to shuffle or loop photos

---

## 📄 License

This project is open source and provided as-is, optimized for legacy Android devices and tablets.

---


