/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package attask.engine;

import attask.beans.HourBean;
import attask.beans.TaskBean;
import attask.beans.TaskHistoryBean;
import attask.beans.TimesheetBean;
import attask.beans.UserBean;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Eric
 */
public class ATTaskInterface {
   
    private static UserBean userBean; 
    private static TaskBean hourTasks;

    public static String getCurrentFormattedDateTime() 
    {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    
    public static String getCurrentFormattedDate() 
    {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
    
    public static String getCurrentFormattedDateSetDate(int day) 
    {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        Date now = new Date();
        now.setDate(day);
        String strDate = sdfDate.format(now);
        
        return strDate;
    }
    
    public static String calculateFormattedTimesheetStartDate() 
    {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        Date now = new Date();
        int timesheetStart;
        Calendar cal = Calendar.getInstance();
        int currDay = cal.get(Calendar.DAY_OF_MONTH);
        
        if(currDay < 16){
            timesheetStart = 1;
        }else{
            timesheetStart=16;
        }
        now.setDate(timesheetStart);
        String strDate = sdfDate.format(now);
        
        return strDate;
    }
    
    
    public static UserBean login(UserBean attaskUserInfo)
    {
        String uri = "https://synchronoss.attask-ondemand.com/attask/api/login?username=%s&password=%s";
        uri = String.format(uri, attaskUserInfo.getUser(), attaskUserInfo.getPassword());
        attaskUserInfo = ResponseInterpreter.loginResponse(RestConnector.getData(uri), attaskUserInfo);

        return attaskUserInfo;
    }   
    
    public static TaskBean getTasksBasedOnUser(UserBean user)
    {
        String uri = "https://synchronoss.attask-ondemand.com/attask/api/v4.0/hour/search?fields=taskID,task:name&sessionID=%s&timesheet:userID=%s";
        uri = String.format(uri, user.getSessionId(), user.getUserId());
        return ResponseInterpreter.getTasksResponse(RestConnector.getData(uri));
        
    }
    
    public static void getTaskNames(UserBean user)
    {
    
    }
    
    public static HourBean submitTimeToATTask(UserBean user, HourBean hourData, int minsToSubstract, int queuedTime)
    {
        int quarterMin=25;
        queuedTime = (queuedTime==0)?1:queuedTime;
        String uri = "https://synchronoss.attask-ondemand.com/attask/api/v4.0/hour?sessionID=%s&taskID=%s&hours=0.%s&entryDate="+getCurrentFormattedDate()+"&method=post";
        uri = String.format(uri, user.getSessionId(), hourData.getTaskID(), (queuedTime*quarterMin) - minsToSubstract);
        return ResponseInterpreter.handleHourUpdateResponse(RestConnector.getData(uri), hourData);
      
    }

    public static void updateUserDiscriptionByTask(UserBean user, String taskId, String comments)
    {        
        String urlHeader = null;
        String urlUpdate = null;
        String urlSession = null;
        
        try {
            urlHeader = "https://synchronoss.attask-ondemand.com/attask/api/v4.0/hour?updates=";
            urlUpdate = "{\"taskID\":\"%s\",\"hours\":\"0.01\",\"description\":\"%s\",\"status\":\"SUB\",\"entryDate\":\"%s\"}"; 
            urlSession = "&sessionID=%s&method=post";
            urlUpdate = String.format(urlUpdate, taskId, comments, getCurrentFormattedDate());
            urlSession = String.format(urlSession, user.getSessionId());
            urlUpdate = URLEncoder.encode(urlUpdate, "utf-8");
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ATTaskInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        String uri = (urlHeader + urlUpdate+ urlSession);
        RestConnector.getData(uri).getEntity(String.class);
        
    }

    public static HourBean retrieveCurrentLoggedTime(UserBean user, String taskId){
        String uri="https://synchronoss.attask-ondemand.com/attask/api/v4.0/hour/search?fields=entryDate,hours,taskID,timesheetID&sessionID=%s&timesheet:userID=%s&taskID=%s&entryDate="+getCurrentFormattedDate();
        uri = String.format(uri, user.getSessionId(), user.getUserId(), taskId);
        HourBean incomingHr = ResponseInterpreter.getTimesheetTaskHours(RestConnector.getData(uri));
        
        if(incomingHr == null){
            return null;
        }else{
            return incomingHr;
        }
        

    }
    public static TaskHistoryBean getTaskTimeHistory(UserBean user, String taskName, String taskId)
    {
        String uri="https://synchronoss.attask-ondemand.com/attask/api/v4.0/hour/search?fields=description,hours,entryDate,timesheet:totalHours&entryDate=%s&entryDate_Range=%s&entryDate_Mod=between&taskID=%s&sessionID=%s";
        uri = String.format(uri, getCurrentFormattedDateSetDate(1), getCurrentFormattedDate(), taskId, user.getSessionId());
        
        TaskHistoryBean taskHistorys = ResponseInterpreter.tasksHistoryResponse(RestConnector.getData(uri));
        taskHistorys.setTaskId(taskId);
        taskHistorys.setTaskName(taskName);
        
        return taskHistorys;
    }

    public static TaskHistoryBean getTaskHistoryWithinTshet(UserBean user, String taskId)
    {
        String uri="https://synchronoss.attask-ondemand.com/attask/api/v4.0/hour/search?fields=description,hours,entryDate,timesheet:totalHours&entryDate=%s&entryDate_Range=%s&entryDate_Mod=between&taskID=%s&sessionID=%s";
        uri = String.format(uri, calculateFormattedTimesheetStartDate(), getCurrentFormattedDate(), taskId, user.getSessionId());
        
        TaskHistoryBean taskHistorys = ResponseInterpreter.tasksHistoryResponse(RestConnector.getData(uri));
        taskHistorys.setTaskId(taskId);
        
        return taskHistorys;
    }

    public static TimesheetBean isTimesheetSubmitted(UserBean user) {
        String uri="https://synchronoss.attask-ondemand.com/attask/api/v4.0/tshet/search?fields=ID,displayName,status,startDate&startDate=%s&userID=%s&sessionID=%s";
        uri = String.format(uri, calculateFormattedTimesheetStartDate(), user.getUserId(), user.getSessionId());
        
        TimesheetBean tshet = ResponseInterpreter.timesheetStateResponse(RestConnector.getData(uri));
        
        return tshet;
    }
   //maybe this should return something to confirm update
    public static void submitTimesheet(UserBean user, String tshetId, String tshetStatus) {

        String urlHeader = null;
        String urlUpdate = null;
        String urlSession = null;
        
        try {
            urlHeader = "https://synchronoss.attask-ondemand.com/attask/api/v4.0/tshet?updates=";
            urlUpdate = "{\"ID\":\"%s\",\"status\":\"%s\"}"; 
            urlSession = "&sessionID=%s&method=put";
            urlUpdate = String.format(urlUpdate, tshetId, tshetStatus);
            urlSession = String.format(urlSession, user.getSessionId());
            urlUpdate = URLEncoder.encode(urlUpdate, "utf-8");
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ATTaskInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        String uri = (urlHeader + urlUpdate+ urlSession);
        RestConnector.getData(uri).getEntity(String.class);
   
    
    }


}
