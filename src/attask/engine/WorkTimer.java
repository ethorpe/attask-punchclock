/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package attask.engine;

import attask.beans.HourBean;
import attask.beans.UserBean;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Eric
 */
public class WorkTimer implements Runnable{
    
    public long startWorkTime;
    public long workingTime;
    private JLabel outputLabel;
    private HourBean incomingHr;
    private static WorkTimer instance = null;
    private UserBean userInfo;
    private int minsToSubtract;
    private boolean stopLoggingTime;
    JDialog otAlertDlg;
    private int queuedTime;
    private JLabel otLabel;
    private JFrame parentFrame;
    
    private WorkTimer() {
      // Exists only to defeat instantiation.
      stopLoggingTime=false;
    }
    
     public static WorkTimer getInstance() {
      if(instance == null) {
         instance = new WorkTimer();
      }
      return instance;
   }
    
    public void run() {
        startTimer();
    }
    
    public void startTimer()
    {
        startWorkTime = System.currentTimeMillis();
        workingTime = System.currentTimeMillis() + (60000*5);     //gives 5 minute start
        
        do{
            System.out.println(new Date());
            try {
                if(!stopLoggingTime){
                    calculateAnDisplay(startWorkTime, workingTime);
                    resetQueuedTime();
                }
                if(stopLoggingTime){
                    addQueuedTime();
                }
                
                Thread.sleep(15 * 60000);   //should be every 15 minutes which represents a quarter of a hour
                workingTime = System.currentTimeMillis();  
                //pause for 15 minutes
                //calculate time
            } catch (InterruptedException ex) {
                Logger.getLogger(WorkTimer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }while(true);
    }

    /**
     * @return the outputLabel
     */
    public JLabel getOutputLabel() {
        return outputLabel;
    }

    /**
     * @param outputLabel the outputLabel to set
     */
    public void setOutputLabel(JLabel outputLabel) {
        this.outputLabel = outputLabel;
    }

    private void calculateAnDisplay(long startWorkTime, long workingTime) {
        System.out.println("CalculateAnDisplay");
        int addTime = 0;
        if((!incomingHr.getHour().equals("0") || !incomingHr.getMin().equals("0")))   //convert current timesheet time to minutes
        {
            addTime = incomingHr.getHr() * 60;
            if(incomingHr.getMn() <= 25)
                addTime += 15;
            else if(incomingHr.getMn() <= 50)
                addTime += 30;
            else if (incomingHr.getMn() <= 75)
                addTime += 45;
            else
                addTime += 100;
        }

        long elaspedTime  =  workingTime - startWorkTime;
        double elaspedTimeMins = (elaspedTime/60000)+addTime;
        double calculatedTime = (elaspedTimeMins%60);
        String minutes="";
        int hours = (int)elaspedTimeMins/60;
        
        if(calculatedTime <= 15)
            minutes = "25";
        else if(calculatedTime <= 30)
            minutes = "50";
        else if (calculatedTime <= 45)
            minutes = "75";
        
        HourBean outgoingHours = incomingHr;
        String timesheetHours = hours+"."+minutes; 
        outgoingHours.setReportedTime(timesheetHours);
        incomingHr = ATTaskInterface.submitTimeToATTask(userInfo, outgoingHours, minsToSubtract, queuedTime);
        otAlert(Float.parseFloat(incomingHr.getHour()+"."+incomingHr.getMin()));
        outputLabel.setText(incomingHr.getHour()+"."+incomingHr.getMin()+" Hours Logged.");
        //outputLabel.setText(25-minsToSubtract+" Hours Logged.");
        minsToSubtract=0;
            
        
//        String minutes = "" + TimeUnit.MILLISECONDS.toMinutes(startWorkTime) - TimeUnit.MILLISECONDS.toMinutes(workingTime);
    }

    /**
     * @return the incomingHrf
     */
    public HourBean getIncomingHr() {
        return incomingHr;
    }

    /**
     * @param incomingHr the incomingHr to set
     */
    public void setIncomingHr(HourBean incomingHr) {
        this.incomingHr = incomingHr;
    }

    /**
     * @return the userInfo
     */
    public UserBean getUserInfo() {
        return userInfo;
    }

    /**
     * @param userInfo the userInfo to set
     */
    public void setUserInfo(UserBean userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * @return the minsToSubtract
     */
    public int getMinsToSubtract() {
        return minsToSubtract;
    }

    /**
     * @param minsToSubtract the minsToSubtract to set
     */
    public void setMinsToSubtract(int minsToSubtract) {
        this.minsToSubtract += minsToSubtract;
    }

    public void stopLoggingTime() {
        stopLoggingTime=true;
    }
 
    public void startLoggingTime() {
        stopLoggingTime=false;
    }

    private void otAlert(float clockedHours) {
        clockedHours = clockedHours - 12;
        if(clockedHours >= 0)   //this user has worked over 12 hours. Alert them to remind them they are logging time against system
        {
            if((clockedHours%2)==0){
               stopLoggingTime();
                addQueuedTime();
               otAlertDlg.setLocationRelativeTo(parentFrame);
               otAlertDlg.setSize(435, 250);
               otAlertDlg.setVisible(true);
               otLabel.setText(incomingHr.getHour()+"."+incomingHr.getMin());
            }
        }
    }

    public void setOtAlertDialog(JDialog otAlertDlg) {
        this.otAlertDlg = otAlertDlg;
    }

    /**
     * @param queuedTime the queuedTime to set
     */
    public void addQueuedTime() {
        this.queuedTime++;
    }

    public void resetQueuedTime() {
        this.queuedTime=0;
    }


    /**
     * @param otLabel the otLabel to set
     */
    public void setOtLabel(JLabel otLabel) {
        this.otLabel = otLabel;
    }

    /**
     * @param parentFrame the parentFrame to set
     */
    public void setParentFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }
    
}
