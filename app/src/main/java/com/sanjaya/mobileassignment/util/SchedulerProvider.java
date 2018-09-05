package com.sanjaya.mobileassignment.util;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
/**
 * The ViewQuest created for demonstration purpose
 * don't copy/edit this code without author acknowledgement.
 *
 * @author  Sanjaya Ratnayake
 * @version 1.0
 * @since   2018-09-06
 */
public class SchedulerProvider implements BaseSchedulerProvider {
    @Override
    public Scheduler getIOScheduler() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler getComputerScheduler() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler getUiScheduler() {
        return Schedulers.trampoline();
    }
}
