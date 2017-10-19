package lowLevelOS;

import highLevelOS.ProcessObj;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by mayalake on 10/18/17.
 */
public class SchedulerSJF extends Scheduler {

    private final static String textOne = "testdataOne.txt";
    private final static boolean procComplete = true;
    private final static int stopBurstVal = 0;
    private Queue<ProcessObj> processQueue = new ArrayBlockingQueue<ProcessObj>(30);

    public SchedulerSJF(){
        readValues(textOne, processQueue);
        populateCSV(textOne, processQueue);
    }

    public void calculateCPUTimes() {
        int cpuTime = 0;

        sort();

        for (ProcessObj proc : processQueue){
            proc.setStartBurstVal(proc.getBurst_time());

            proc.setStopBurstVal(stopBurstVal);
            proc.setProcComplete(procComplete);
        }

    }

    public void sort(){
        for (ProcessObj proc : processQueue){
             
        }
    }
}
