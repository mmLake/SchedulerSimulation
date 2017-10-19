package lowLevelOS;

import highLevelOS.ProcessObj;

import java.io.*;
import java.util.Queue;
/**
 * Created by mayalake on 10/17/17.
 */
public abstract class Scheduler {
    private static final String textFilePath = "/textfiles/";
    private static final String newTextFile = "results.txt";
    private static final int cpuSwitchTime = 3;
    protected Queue<ProcessObj> processQueue;

    abstract void calculateCPUTimes();

    public void populateCSV(String textFile, Queue<ProcessObj> processQueue){

        try {
            FileWriter writer = new FileWriter(newTextFile);
            BufferedWriter bw = new BufferedWriter(writer);

            bw.write("#############" + getClass().getSimpleName() + " : " + textFile + "#############");
            bw.newLine();

            calculateCPUTimes();

            int avgTurnaroundTime = 0;
            int cpuTime = 0;
            for (ProcessObj proc : processQueue){
                //write start time
                bw.write("PID: " + proc.getPid());
                bw.write(" Start Burst Val: " + proc.getStartBurstVal());

                //write stop time
                bw.write(" Stop Burst Val: " + proc.getStopBurstVal());

                //write if process is complete
                String completed = (proc.getProcComplete() ? "COMPLETE" : "INCOMPLETE");
                bw.write(" " + completed);

                //last cpuTime
                cpuTime += proc.getBurst_time() + getCpuSwitchTime();

                bw.newLine();
            }

            avgTurnaroundTime = cpuTime/processQueue.size();
            System.out.println("avg turnaround time: " + avgTurnaroundTime);

            bw.write("Average Turnaround Time: " + avgTurnaroundTime);
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

            while((pidString = br.readLine())!= null){
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

    public static int getCpuSwitchTime() {
        return cpuSwitchTime;
    }

}
