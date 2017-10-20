package lowLevelOS;

import highLevelOS.ProcessObj;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by mayalake on 10/17/17.
 */
public class SchedulerFCFS extends Scheduler{
    private Queue<ProcessObj> processQueue = new ArrayBlockingQueue<ProcessObj>(30);

    public SchedulerFCFS(){
        for (String textfile : getTextFiles()) {
            readValues(textfile, processQueue);
            populateCSV(textfile, processQueue);
        }
    }

    public void calculateBurstValues(ProcessObj proc) {}
    public String getTimeQuantum(){return "";}
}
