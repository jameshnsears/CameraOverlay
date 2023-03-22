#!/bin/bash

# ./scriptname.sh [apk|aab] <release-channel> <release-note-file>


# Set environment variables for authentication with Google Play Console
export GOOGLE_APPLICATION_CREDENTIALS="/path/to/your/credentials.json"
export GCLOUD_PROJECT="your-project-id"
export ANDROID_HOME="/path/to/your/android/sdk"

# Parse command line arguments
if [ "$1" == "apk" ]; then
  BUILD_COMMAND="assembleVariantName"
  OUTPUT_FILE="app/build/outputs/apk/release/app-release.apk"
  FILE_TYPE="APK"
elif [ "$1" == "aab" ]; then
  BUILD_COMMAND="bundleVariantName"
  OUTPUT_FILE="app/build/outputs/bundle/release/app-release.aab"
  FILE_TYPE="AAB"
else
  echo "Invalid build type. Usage: $0 [apk|aab] <release-channel> <release-note>"
  exit 1
fi

if [ -z "$2" ]; then
  echo "Missing release channel. Usage: $0 [apk|aab] <release-channel> <release-note>"
  exit 1
fi

RELEASE_CHANNEL="$2"

if [ -z "$3" ]; then
  echo "Missing release note. Usage: $0 [apk|aab] <release-channel> <release-note>"
  exit 1
fi

RELEASE_NOTE="$3"

# Build the desired variant
./gradlew clean
./gradlew "$BUILD_COMMAND"

# Generate a signed APK or AAB file
if [ "$FILE_TYPE" == "APK" ]; then
  bundletool build-apks \
  --bundle=app/build/outputs/apk/release/app-release.apk \
  --output=app/build/outputs/apk/release/app-release.apks \
  --ks=keystore.jks \
  --ks-pass=file:/path/to/keystore-password \
  --ks-key-alias=alias-name \
  --key-pass=file:/path/to/key-password \
  --mode=universal
else
  bundletool build-apks \
  --bundle=app/build/outputs/bundle/release/app-release.aab \
  --output=app/build/outputs/bundle/release/app-release.apks \
  --ks=keystore.jks \
  --ks-pass=file:/path/to/keystore-password \
  --ks-key-alias=alias-name \
  --key-pass=file:/path/to/key-password \
  --mode=universal
fi

# Upload the generated APK or AAB file to Google Play Console
bundletool install-apks \
--apks=app/build/outputs/apk/release/app-release.apks \
--device-id=<device-id> \
--track="$RELEASE_CHANNEL" \
--release-notes-file="$RELEASE_NOTE" \
--adb=<path/to/adb>

echo "Uploaded $FILE_TYPE file to Google Play Console release channel $RELEASE_CHANNEL with release note:"
cat "$RELEASE_NOTE"
