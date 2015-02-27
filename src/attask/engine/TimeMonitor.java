/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package attask.engine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Eric
 */
public class TimeMonitor 
{
    private boolean keepClockingTime;
    private Thread workTimerReference;
    private boolean disableMonitoring;
    
    /**
     * @return the keepClockingTime
     */
    public boolean stopClockingTime() {
        return keepClockingTime;
    }

    /**
     * @param keepClockingTime the keepClockingTime to set
     */
    public void setKeepClockingTime(boolean keepClockingTime) {
        this.keepClockingTime = keepClockingTime;
    }

    /**
     * @return the workTimerReference
     */
    public Thread getWorkTimerReference() {
        return workTimerReference;
    }

    /**
     * @param workTimerReference the workTimerReference to set
     */
    public void setWorkTimerReference(Thread workTimerReference) {
        this.workTimerReference = workTimerReference;
    }
    
    public String checkCurrentTime(){
        
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String currentTime = dateFormat.format(cal.getTime()); //16:00:22
        return currentTime;
    }
  
    public void monitoring()
    {
        String hours[] = checkCurrentTime().split(":");
        if(Integer.parseInt(hours[0]) >= 16)
        {
            int currentHour = Integer.parseInt(hours[0]) - 16;
            if((currentHour % 4) == 0){    //alert every fourth hour
                
            }
      
        }
        
        
    }
}
