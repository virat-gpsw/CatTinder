package com.example.cattinder.api;

import com.example.cattinder.rx.SchedulerFactory;

import rx.Scheduler;
import rx.schedulers.Schedulers;
public class TestSchedulerFactory extends SchedulerFactory {

    /**
     * Run tests on the main thread.
     */
    @Override
    public Scheduler newThread() {
        return mainThread() ;
    }
}
