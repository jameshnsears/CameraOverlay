package com.github.jameshnsears.cameraoverlay.model.utils;

import androidx.annotation.NonNull;

import timber.log.Timber.DebugTree;

public final class MethodLineLoggingTree extends DebugTree {
    @Override
    protected @NonNull
    String createStackElementTag(@NonNull final StackTraceElement element) {
        return String.format(
                "TIMBER: %s.%s, %s",
                super.createStackElementTag(element),
                element.getMethodName(),
                element.getLineNumber());
    }
}
