package highLevelOS;

/**
 * Created by mayalake on 10/17/17.
 */
public class ProcessObj {
    private int pid;
    private int burst_time;
    private int priority;
    private int startBurstVal = 0;
    private int stopBurstVal = 0;
    private boolean procComplete = true;

    public ProcessObj(int pid, int burst_time, int priority){
        this.pid = pid;
        this.burst_time = burst_time;
        this.priority = priority;
    }

    public int getPid() {
        return pid;
    }

    public int getBurst_time() {
        return burst_time;
    }

    public int getPriority() {
        return priority;
    }


    public int getStartBurstVal() {
        return startBurstVal;
    }

    public void setStartBurstVal(int startBurstVal) {
        this.startBurstVal = startBurstVal;
    }

    public int getStopBurstVal() {
        return stopBurstVal;
    }

    public void setStopBurstVal(int stopBurstVal) {
        this.stopBurstVal = stopBurstVal;
    }

    public boolean getProcComplete() {
        return procComplete;
    }

    public void setProcComplete(boolean procComplete) {
        this.procComplete = procComplete;
    }


    public String toString(){
        return "PID: " + pid + " Burst Time: " + burst_time + " Priority: " + priority;
    }
}
