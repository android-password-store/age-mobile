#!/usr/bin/env bash

OUT_DIR="./dist"
BUILD_NAME="ageMobile"
ANDROID_JAVA_PKG="com.github.androidpasswordstore"
TARGET=android
OUT_EXTENSION=aar
TARGET_DIR=${OUT_DIR}/${TARGET}
TARGET_OUT_FILE=${TARGET_DIR}/${BUILD_NAME}.${OUT_EXTENSION}
ANDROID_HOME=${ANDROID_SDK_ROOT}
ANDROID_NDK_HOME="$(find ${ANDROID_SDK_ROOT}/ndk/ -maxdepth 1 -type d | tail -n1)"

go build golang.org/x/mobile/cmd/gomobile
go build golang.org/x/mobile/cmd/gobind
mkdir -p ${TARGET_DIR}
ANDROID_HOME=$ANDROID_HOME ANDROID_NDK_HOME=$ANDROID_NDK_HOME ./gomobile init
ANDROID_HOME=$ANDROID_HOME ANDROID_NDK_HOME=$ANDROID_NDK_HOME ./gomobile bind -tags mobile -target "${TARGET}" -javapkg=${ANDROID_JAVA_PKG} -x -ldflags="-s -w" -o ${TARGET_OUT_FILE}
