/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package attask.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Eric
 */
public class TaskBean {

    private HashMap<String, String> taskIDs;
    private HashSet<String> allTaskIDs;
    
    public TaskBean(){
        taskIDs = new HashMap<String, String>();
        allTaskIDs = new HashSet<String>();
    }

    /**
     * @return the taskIDs
     */
    public HashMap<String, String> getTaskIDs() {
        return taskIDs;
    }

    /**
     * @param taskIDs the taskIDs to set
     */
    public void setTaskIDs(String taskId, String taskName) {
        this.taskIDs.put(taskId, taskName);
    }
    
    public void addAnyTaskIds(String taskIds){
        this.allTaskIDs.add(taskIds);
    }
    
    public HashSet<String> getAllTaskIds(){
        return this.allTaskIDs;
    }
    
    public void clearAllTasks()
    {
        this.allTaskIDs.clear();
    }
}
