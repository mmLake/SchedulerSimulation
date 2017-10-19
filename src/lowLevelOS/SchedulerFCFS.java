package lowLevelOS;

import highLevelOS.ProcessObj;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by mayalake on 10/17/17.
 */
public class SchedulerFCFS extends Scheduler{
    private final static String textOne = "testdataOne.txt";
    private final static boolean procComplete = true;
    private final static int stopBurstVal = 0;
    private Queue<ProcessObj> processQueue = new ArrayBlockingQueue<ProcessObj>(30);

    public SchedulerFCFS(){
        readValues(textOne, processQueue);
        populateCSV(textOne, processQueue);
    }

    public void calculateCPUTimes() {

        for (ProcessObj proc : processQueue){
            proc.setStartBurstVal(proc.getBurst_time());

            proc.setStopBurstVal(stopBurstVal);
            proc.setProcComplete(procComplete);
        }
    }
}
