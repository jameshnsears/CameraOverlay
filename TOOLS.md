# Tools

## 1. Applications used for development

* Debian 11
* exiftool

--

## 2. adb

* adb uninstall cameraoverlay

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
