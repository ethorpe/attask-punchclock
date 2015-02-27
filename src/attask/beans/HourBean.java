/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package attask.beans;

/**
 *
 * @author Eric
 */
public class HourBean {
    
    private String taskID;   
    private String hour;
    private String min;
    private int hr;
    private int mn;
    private String reportedTime;
    
    public HourBean(){
        hour = "0";
        min = "0";
    }
    public void convertData(String timesheetHour)
    {
        String data[] = timesheetHour.split("\\.");
        hour = data[0];
        min = data[1];
        hr = Integer.parseInt(hour);
        mn = Integer.parseInt(min);
    }
    /**
     * @return the taskID
     */
    public String getTaskID() {
        return taskID;
    }

    /**
     * @param taskID the taskID to set
     */
    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    /**
     * @return the hour
     */
    public String getHour() {
        return hour;
    }

    /**
     * @param hour the hour to set
     */
    public void setHour(String hour) {
        this.hour = hour;
    }

    /**
     * @return the min
     */
    public String getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(String min) {
        this.min = min;
    }

    /**
     * @return the hr
     */
    public int getHr() {
        return hr;
    }

    /**
     * @param hr the hr to set
     */
    public void setHr(int hr) {
        this.hr = hr;
    }

    /**
     * @return the mn
     */
    public int getMn() {
        return mn;
    }

    /**
     * @param mn the mn to set
     */
    public void setMn(int mn) {
        this.mn = mn;
    }

    public String getReportedTime() {
        return reportedTime;
    }

    public void setReportedTime(String reportedTime) {
        this.reportedTime = reportedTime;
    }
    
    
}
