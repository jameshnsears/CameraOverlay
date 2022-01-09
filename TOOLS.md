# Tools

## 1. Applications used for development

* Debian 11
* exiftool

--

## 2. adb

* adb uninstall cameraoverlay
* adb shell dumpsys package com.github.jameshnsears.cameraoverlay
* adb kill-server 
* adb start-server


--

## 3. npm tools

```bash
npm install jscpd markdownlint markdownlint-cli
./node_modules/.bin/jscpd .github
./node_modules/.bin/markdownlint .
```

--

## 4. Android studio plugins

* ADB Idea - Philippe Breault
* SonarLint - SonarSource
* detekt - detekt
* CPU Usage Indicator - Vojtech Krasa
* CodeGlance - Vektah
* ADB Wi-Fi - Yury Polek

--

## 5. find

```bash
find . -name .editorconfig -exec rm -f {} \;
```

--

## 6. GitHub Secrets

```bash
base64 -w 0 <filename>
```

--

## 7. headless emulator

```bash
./Android/Sdk/emulator/emulator -avd "27_Nexus_5_-_8.1" -no-window
```
