package com.github.jameshnsears.cameraoverlay.common;

import androidx.annotation.NonNull;

import timber.log.Timber.DebugTree;

public final class MethodLineLoggingTree extends DebugTree {
    @Override
    protected @NonNull
    String createStackElementTag(@NonNull final StackTraceElement element) {
        return String.format(
                "%s, %s",
                element.getMethodName(),
                element.getLineNumber());
    }
}
