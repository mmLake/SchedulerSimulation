package com.main;

import lowLevelOS.SchedulerFCFS;
import lowLevelOS.SchedulerSJF;

public class Main {

    public static void main(String[] args) {
        SchedulerFCFS fcfs = new SchedulerFCFS();
        SchedulerSJF sjf = new SchedulerSJF();
    }
}
