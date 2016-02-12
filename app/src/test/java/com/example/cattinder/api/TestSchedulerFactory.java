package com.example.cattinder.api;

import com.example.cattinder.rx.SchedulerFactory;

import rx.Scheduler;
import rx.schedulers.Schedulers;
public class TestSchedulerFactory extends SchedulerFactory {



    @Override
    public Scheduler newThread() {
        return mainThread() ;
    }
}
