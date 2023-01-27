#!/bin/sh
set -e

./gradlew -q \
  :app:detekt \
  :app:ktlintCheck \
  :app:lintFdroidDebug \
  :app:lintGoogleplayDebug
