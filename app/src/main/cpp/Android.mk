LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := hihi
LOCAL_SRC_FILES := native-lib.cpp
LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)
