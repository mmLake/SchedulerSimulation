package com.main;

import lowLevelOS.SchedulerFCFS;
import lowLevelOS.SchedulerRoundRobin;
import lowLevelOS.SchedulerSJF;

public class Main {

    public static void main(String[] args) {
//        SchedulerFCFS fcfs = new SchedulerFCFS();
//        SchedulerSJF sjf = new SchedulerSJF();
        SchedulerRoundRobin rr = new SchedulerRoundRobin(60);
        SchedulerRoundRobin rrTwo = new SchedulerRoundRobin(30);
    }
}
