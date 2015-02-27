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
public class TimesheetBean {
    private String timesheetId;
    private String status;
    private String timesheetBeginDate;

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the timesheetBeginDate
     */
    public String getTimesheetBeginDate() {
        return timesheetBeginDate;
    }

    /**
     * @param timesheetBeginDate the timesheetBeginDate to set
     */
    public void setTimesheetBeginDate(String timesheetBeginDate) {
        this.timesheetBeginDate = timesheetBeginDate;
    }

    /**
     * @return the timesheetId
     */
    public String getTimesheetId() {
        return timesheetId;
    }

    /**
     * @param timesheetId the timesheetId to set
     */
    public void setTimesheetId(String timesheetId) {
        this.timesheetId = timesheetId;
    }
    
    
}
