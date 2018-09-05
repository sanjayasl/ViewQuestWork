package com.sanjaya.mobileassignment.util;

import io.reactivex.Scheduler;

public interface BaseSchedulerProvider {
    Scheduler getIOScheduler();

    Scheduler getComputerScheduler();

    Scheduler getUiScheduler();
}
