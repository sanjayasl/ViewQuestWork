package com.sanjaya.mobileassignment.util;

import io.reactivex.Scheduler;
/**
 * The ViewQuest created for demonstration purpose
 * don't copy/edit this code without author acknowledgement.
 *
 * @author  Sanjaya Ratnayake
 * @version 1.0
 * @since   2018-09-06
 */
public interface BaseSchedulerProvider {
    Scheduler getIOScheduler();

    Scheduler getComputerScheduler();

    Scheduler getUiScheduler();
}
