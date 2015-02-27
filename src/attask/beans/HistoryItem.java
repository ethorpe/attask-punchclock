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
public class HistoryItem {
    private String hourId;
    private String taskDate;
    private Double loggedTime;
    private String comments;

    /**
     * @return the hourId
     */
    public String getHourId() {
        return hourId;
    }

    /**
     * @param hourId the hourId to set
     */
    public void setHourId(String hourId) {
        this.hourId = hourId;
    }

    /**
     * @return the loggedTime
     */
    public Double getLoggedTime() {
        return loggedTime;
    }

    /**
     * @param loggedTime the loggedTime to set
     */
    public void setLoggedTime(Double loggedTime) {
        this.loggedTime = loggedTime;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the taskDate
     */
    public String getTaskDate() {
        return taskDate;
    }

    /**
     * @param taskDate the taskDate to set
     */
    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }
   
        
    
}
