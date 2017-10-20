package lowLevelOS;

import highLevelOS.ProcessObj;

import java.util.Comparator;
import java.util.Queue;
import java.util.PriorityQueue;


/**
 * Created by mayalake on 10/18/17.
 */
public class SchedulerSJF extends Scheduler {
    private Comparator<ProcessObj> comparator = new BurstTimeComparator();
    private Queue<ProcessObj> processQueue = new PriorityQueue<ProcessObj>(30, comparator);

    public SchedulerSJF(){
        for (String textfile : getTextFiles()) {
            readValues(textfile, processQueue);
            populateCSV(textfile, processQueue);
        }
    }

    public class BurstTimeComparator implements Comparator<ProcessObj>{
        public int compare(ProcessObj procA, ProcessObj procB){
            return procA.getBurst_time() - procB.getBurst_time();
        }
    }

    public void calculateBurstValues(ProcessObj proc){}
    public String getTimeQuantum(){return "";}

}
