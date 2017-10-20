package lowLevelOS;

import highLevelOS.ProcessObj;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by mayalake on 10/19/17.
 */
public class SchedulerRoundRobin extends Scheduler{
    private final static String textOne = "testdataOne.txt";

    private int timeQuantum;
    private int initialQueueSize;
    private int counter = 0;
    private Queue<ProcessObj> processQueue;

    public SchedulerRoundRobin(int timeQuantum){
        this.timeQuantum = timeQuantum;
        processQueue = new ArrayBlockingQueue<ProcessObj>(30);

        readValues(textOne, processQueue);
        initialQueueSize = processQueue.size();
        populateCSV(textOne, processQueue);
    }

    public void calculateBurstValues(ProcessObj proc){
        int stopVal = proc.getStopBurstVal();
        int startVal = proc.getStartBurstVal();

        //first iteration
        if (counter < initialQueueSize) {
            if (startVal < timeQuantum) {
                proc.setStopBurstVal(0);
            }
            else if (startVal > timeQuantum){
                proc.setStopBurstVal(startVal - timeQuantum);
                proc.setProcComplete(false);
            }
            else{
                System.out.println("Edge Case ERROR");
            }
        }
        //second iteration and on
        else {
            if (startVal > timeQuantum && stopVal > timeQuantum) {
                proc.setStartBurstVal(stopVal);
                proc.setStopBurstVal(stopVal - timeQuantum);
            }
            else if (startVal > timeQuantum) {
                proc.setStartBurstVal(stopVal);
                proc.setStopBurstVal(0);
                proc.setProcComplete(true);

            } else{
                System.out.println("Edge Case ERROR");
                proc.setProcComplete(true);
            }
        }

        counter++;
    }
}
