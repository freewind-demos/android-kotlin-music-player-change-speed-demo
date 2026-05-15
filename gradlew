#!/bin/sh

APP_HOME=$(cd "$(dirname "$0")" && pwd -P)
GRADLE_VERSION=8.7
GRADLE_HOME="$APP_HOME/.gradle/gradle-$GRADLE_VERSION"
GRADLE_BIN="$GRADLE_HOME/bin/gradle"
GRADLE_ZIP="/tmp/gradle-$GRADLE_VERSION-bin.zip"
GRADLE_URL="https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip"

if [ ! -x "$GRADLE_BIN" ]; then
  mkdir -p "$APP_HOME/.gradle"
  if [ ! -f "$GRADLE_ZIP" ]; then
    curl -fsSL "$GRADLE_URL" -o "$GRADLE_ZIP"
  fi
  unzip -q "$GRADLE_ZIP" -d "$APP_HOME/.gradle"
fi

exec "$GRADLE_BIN" "$@"

