package lowLevelOS;

import highLevelOS.ProcessObj;

import java.io.*;
import java.util.Queue;
import java.util.Random;

/**
 * Created by mayalake on 10/17/17.
 */
public abstract class Scheduler {
    private static final String textFilePath = "/textfiles/";
    private static final String newTextFile = "results.txt";
    private static final String[] textFiles = {"testdataOne.txt", "testdataTwo.txt","testdataThree.txt","testdataFour.txt"};
    private static final int cpuSwitchTime = 3;
    private Queue<ProcessObj> processQueue;
    private int priorityTotal = 0;//only used for lottery

    abstract void calculateBurstValues(ProcessObj proc);
    abstract String getTimeQuantum();

    //only used for lottery
    public ProcessObj getRandomProcess(Queue<ProcessObj> processQueue){
        Random random = new Random();
        int randomVal = random.nextInt(priorityTotal) + 1;
        int priorityVal =0;

        for (ProcessObj process : processQueue){
            priorityVal += process.getPriority();

            if (priorityVal >= randomVal){
                processQueue.remove(process);
                priorityTotal -= process.getPriority();
                return process;
            }
        }
        return null;
    }

    public void populateCSV(String textFile, Queue<ProcessObj> processQueue){
        try {
            FileWriter writer = new FileWriter(newTextFile, true);
            BufferedWriter bw = new BufferedWriter(writer);

            bw.write("#############" + getClass().getSimpleName() + getTimeQuantum() + " : " + textFile + "#############");
            bw.newLine();

            //initialize
            int cpuTime = -cpuSwitchTime;
            int totalCpuTime = 0;
            int processSize = processQueue.size();
            for (ProcessObj proc : processQueue) {
                proc.setStartBurstVal(proc.getBurst_time());
                calculateBurstValues(proc);
                priorityTotal += proc.getPriority();
            }

            while (processQueue.size() > 0) {
                ProcessObj proc;

                //if lottery, process is random, else poll from top
                if (this instanceof SchedulerLottery) {
                    proc = getRandomProcess(processQueue);
                } else {
                    proc = processQueue.poll();
                }

                //write PID, start time, stop time
                bw.write("PID: " + proc.getPid());
                bw.write(" Start Burst Val: " + proc.getStartBurstVal());
                bw.write(" Stop Burst Val: " + proc.getStopBurstVal());

                //set current cpuTime and total cpuTime
                cpuTime += cpuSwitchTime + (proc.getStartBurstVal() - proc.getStopBurstVal());
                if (proc.getProcComplete()) {
                    totalCpuTime += cpuTime;
                }

                //write cpuTime
                bw.write(" CpuTime: " + cpuTime);

                //write if process is complete
                String completed = (proc.getProcComplete() ? "COMPLETE" : "INCOMPLETE");
                bw.write(" " + completed);

                //if process is incomplete, add process to back of the queue & recalculate start & stop burst vals
                if (!proc.getProcComplete()) {
                    processQueue.add(proc);
                    calculateBurstValues(proc);
                    priorityTotal += proc.getPriority();
                }

                bw.newLine();
            }

            //write average turnaround time
            int avgTurnaroundTime = totalCpuTime / processSize;
            bw.write("Average Turnaround Time: " + avgTurnaroundTime);
            bw.newLine();

            bw.close();

        } catch (Exception ioe){
            ioe.printStackTrace();
        }
    }

    public void readValues(String textFile, Queue<ProcessObj> processQueue){
        try{
            InputStream is = Scheduler.class.getResourceAsStream(textFilePath + textFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String pidString;

            while ((pidString = br.readLine()) != null) {
                int pid = Integer.parseInt(pidString);
                int burst_time = Integer.parseInt(br.readLine());
                int priority = Integer.parseInt(br.readLine());

                ProcessObj process = new ProcessObj(pid, burst_time, priority);
                processQueue.add(process);
            }

            br.close();

        } catch (Exception ioe){
            ioe.printStackTrace();
        }
    }

    public static String[] getTextFiles() {
        return textFiles;
    }

}
