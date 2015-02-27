/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package attask.engine;

/**
 *
 * @author Eric
 */

import attask.beans.HistoryItem;
import attask.beans.HourBean;
import attask.beans.TaskBean;
import attask.beans.TaskHistoryBean;
import attask.beans.TimesheetBean;
import attask.beans.UserBean;
import com.sun.jersey.api.client.ClientResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ResponseInterpreter 
{
    public static UserBean loginResponse(ClientResponse response, UserBean user)
    {
        JSONObject obj = new JSONObject(response.getEntity(String.class));
        JSONObject dataSet = obj.getJSONObject("data");
        String sessionId = dataSet.getString("sessionID");
        String userId = dataSet.getString("userID");
        user.setSessionId(sessionId);
        user.setUserId(userId);
        return user;
    }
    
    public static TaskBean getTasksResponse(ClientResponse response)
    {
        TaskBean tasks = new TaskBean();
        JSONObject obj = new JSONObject(response.getEntity(String.class));
        for(int i=0; i < obj.getJSONArray("data").length(); i++){
            String name = obj.getJSONArray("data").getJSONObject(i).getJSONObject("task").getString("name");
            String taskID = obj.getJSONArray("data").getJSONObject(i).getJSONObject("task").getString("ID");
            tasks.addAnyTaskIds(taskID);
            if((name.toUpperCase().indexOf("PTO") < 0 && name.toUpperCase().indexOf("HOLIDAY") < 0)) //Dont periodically add time towards PTO or Holidays
                tasks.setTaskIDs(name.trim(), taskID);
        }
        
        return tasks;
    }
    
    public static HourBean getTimesheetTaskHours(ClientResponse response)
    {
        HourBean hour = new HourBean();
        JSONObject obj = new JSONObject(response.getEntity(String.class));
        try{
            String hours = Double.toString((Double)obj.getJSONArray("data").getJSONObject(0).get("hours"));
            HourBean incomingHr = new HourBean();
            incomingHr.convertData(hours);
            return incomingHr;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
        
        
    }
    
    public static HourBean handleHourUpdateResponse(ClientResponse response, HourBean incomingHr)
    {
        HourBean hour = new HourBean();
        JSONObject obj = new JSONObject(response.getEntity(String.class));
        try{
            String hours = Double.toString(obj.getJSONObject("data").getDouble("hours"));
            incomingHr.convertData(hours);
            return incomingHr;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
        
        
    }
    public static void submitHoursResponse(String jsonString)
    {
        
    }

    public static TaskHistoryBean tasksHistoryResponse(ClientResponse response)
    {
        TaskHistoryBean historyData = new TaskHistoryBean();
        
        JSONObject obj = new JSONObject(response.getEntity(String.class));
        for(int i=0; i<obj.getJSONArray("data").length();i++)
        {
            HistoryItem historyPiece = new HistoryItem();
            JSONObject hourData = (JSONObject)obj.getJSONArray("data").get(i);
            if(!hourData.isNull("description")){
                 historyPiece.setComments(hourData.getString("description"));
            }
            
            historyPiece.setHourId(hourData.getString("ID"));
            historyPiece.setLoggedTime(hourData.getDouble("hours"));
            historyPiece.setTaskDate(hourData.getString("entryDate"));
            if(!hourData.isNull("timesheet")){
                JSONObject timesheetData = hourData.getJSONObject("timesheet");
                historyData.setTotalHours(timesheetData.getDouble("totalHours"));
            }
            
            historyData.setHistoryItems(historyPiece);
        }
        return historyData;
    }

    static TimesheetBean timesheetStateResponse(ClientResponse response) {
      
        TimesheetBean tshet = new TimesheetBean();
        
        JSONObject obj = new JSONObject(response.getEntity(String.class));
        JSONObject timesheetData = (JSONObject)obj.getJSONArray("data").getJSONObject(0);
        if(!timesheetData.isNull("status")){
            tshet.setStatus(timesheetData.getString("status"));
        }

        if(!timesheetData.isNull("startDate")){
            tshet.setTimesheetBeginDate(timesheetData.getString("startDate"));
        }
        tshet.setTimesheetId(timesheetData.getString("ID"));
        
        return tshet;
    }

    
}
