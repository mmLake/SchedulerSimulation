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
    private Queue<ProcessObj> processQueue;

    public SchedulerRoundRobin(int timeQuantum){
        this.timeQuantum = timeQuantum;
        processQueue = new ArrayBlockingQueue<ProcessObj>(timeQuantum * 30);

        readValues(textOne, processQueue);
        initialQueueSize = processQueue.size();
        populateCSV(textOne, processQueue);
    }

    public void addRepeatingProc(ProcessObj proc){
        ProcessObj repeatingProc = new ProcessObj(proc.getPid(), proc.getBurst_time(), proc.getPriority());
        repeatingProc.setStartBurstVal(proc.getStartBurstVal());
        repeatingProc.setStopBurstVal(proc.getStopBurstVal());
        repeatingProc.setProcComplete(proc.getProcComplete());
        processQueue.add(repeatingProc);
    }

    public void calculateBurstValues(){
        int counter = 0;
        for (ProcessObj proc : processQueue){
            counter++;
            int burstTime = proc.getBurst_time();
            int stopVal = proc.getStopBurstVal();
            int startVal = proc.getStartBurstVal();

            //first iteration
            if (counter <= initialQueueSize) {
                if (burstTime < timeQuantum) {
                    proc.setStartBurstVal(burstTime);
                }
                else if (burstTime > timeQuantum) {
                    proc.setStartBurstVal(burstTime);
                    proc.setStopBurstVal(burstTime - timeQuantum);
                    proc.setProcComplete(false);

                    addRepeatingProc(proc);
                } 
            }
            //second iteration and on
            else {
                if (startVal > timeQuantum && stopVal > timeQuantum) {
                    proc.setStartBurstVal(stopVal);
                    proc.setStopBurstVal(stopVal - timeQuantum);

                    addRepeatingProc(proc);
                }
                else if (startVal > timeQuantum) {
                    proc.setStartBurstVal(stopVal);
                    proc.setStopBurstVal(0);
                    proc.setProcComplete(true);

                } else{
                    System.out.println("Edge Case ERROR");
                }
            }
        }
    }
}
