#!/usr/bin/env sh
##############################################################################
# Gradle start up script for UN*X
# Auto-generated minimal wrapper script
##############################################################################
set -e
DIRNAME=$(cd "$(dirname "$0")" && pwd)
GRADLE_HOME="$DIRNAME/.gradle-wrapper"
GRADLE_VERSION="8.0.2"
DIST_URL="https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip"

if [ ! -d "$GRADLE_HOME" ]; then
  mkdir -p "$GRADLE_HOME"
fi

if [ ! -d "$GRADLE_HOME/gradle-${GRADLE_VERSION}" ]; then
  echo "Downloading Gradle ${GRADLE_VERSION}..."
  ZIP="$GRADLE_HOME/gradle-${GRADLE_VERSION}.zip"
  if command -v curl >/dev/null 2>&1; then
    curl -sS -L -o "$ZIP" "$DIST_URL"
  elif command -v wget >/dev/null 2>&1; then
    wget -q -O "$ZIP" "$DIST_URL"
  else
    echo "Error: neither curl nor wget found. Please install one to bootstrap Gradle wrapper." >&2
    exit 1
  fi
  unzip -q -d "$GRADLE_HOME" "$ZIP"
  rm -f "$ZIP"
fi

"$GRADLE_HOME/gradle-${GRADLE_VERSION}/bin/gradle" "$@"
