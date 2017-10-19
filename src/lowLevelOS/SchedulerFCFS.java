package lowLevelOS;

import highLevelOS.ProcessObj;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by mayalake on 10/17/17.
 */
public class SchedulerFCFS extends Scheduler{
    private final static String textOne = "testdataOne.txt";
    private Queue<ProcessObj> processQueue = new ArrayBlockingQueue<ProcessObj>(30);

    public SchedulerFCFS(){
        readValues(textOne, processQueue);
        populateCSV(textOne, processQueue);
    }

    public void calculateBurstValues() {
        for (ProcessObj proc : processQueue){
            proc.setStartBurstVal(proc.getBurst_time());
        }
    }
}
