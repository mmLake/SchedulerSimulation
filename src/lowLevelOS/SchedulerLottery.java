package lowLevelOS;

import highLevelOS.ProcessObj;

import java.util.Random;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by mayalake on 10/19/17.
 */
public class SchedulerLottery extends Scheduler {
    private final static String textOne = "testdataOne.txt";
    private Queue<ProcessObj> processQueue = new ArrayBlockingQueue<ProcessObj>( 30);
    private int timeQuantum;
    private int priorityTotal;
    private int counter = 0;
    private int initialQueueSize;

    public SchedulerLottery(int timeQuantum){
        this.timeQuantum = timeQuantum;
        readValues(textOne, processQueue);
        initialQueueSize = processQueue.size();
        for (ProcessObj proc : processQueue){
            priorityTotal += proc.getPriority();
        }
        populateCSV(textOne, processQueue);
    }

    public ProcessObj getRandomProcess(){
        Random random = new Random();
        int randomVal = random.nextInt(priorityTotal) + 1;
        int priorityVal =0;

        for (ProcessObj proc : processQueue){
            priorityVal += proc.getPriority();
            if (priorityVal > randomVal){
                return proc;
            }
        }
        return null;
    }

    public void calculateBurstValues(ProcessObj proc){
        int stopVal = proc.getStopBurstVal();
        int startVal = proc.getStartBurstVal();

        //first iteration
        if (counter < initialQueueSize) {
            if (startVal < timeQuantum) {
                proc.setStopBurstVal(0);
            }
            else if (startVal > (timeQuantum * 2)) {
                proc.setStartBurstVal(startVal - timeQuantum);
                proc.setStopBurstVal(proc.getStartBurstVal() - timeQuantum);
                proc.setProcComplete(false);
            }
            else if (startVal > timeQuantum){
                proc.setStartBurstVal(startVal - timeQuantum);
                proc.setStopBurstVal(0);
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
