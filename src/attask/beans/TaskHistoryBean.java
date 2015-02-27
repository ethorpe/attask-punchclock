/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package attask.beans;

import java.util.ArrayList;

/**
 *
 * @author Eric
 */
public class TaskHistoryBean {
    private String taskId;
    private String taskName;
    private double totalHours;
    
    private ArrayList<HistoryItem> historyItems;

    public TaskHistoryBean()
    {
        historyItems = new ArrayList<HistoryItem>();
    }
    /**
     * @return the taskId
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * @param taskId the taskId to set
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * @return the taskName
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * @param taskName the taskName to set
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * @return the totalHours
     */
    public double getTotalHours() {
        return totalHours;
    }

    /**
     * @param totalHours the totalHours to set
     */
    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }

    /**
     * @return the HistoryItems
     */
    public ArrayList<HistoryItem> getHistoryItems() {
        return historyItems;
    }

    /**
     * @param HistoryItems the HistoryItems to set
     */
    public void setHistoryItems(HistoryItem item) {
        this.historyItems.add(item);
    }
}
