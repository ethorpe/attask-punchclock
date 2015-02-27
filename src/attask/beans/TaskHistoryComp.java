/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package attask.beans;

import java.util.Comparator;

/**
 *
 * @author Eric
 */
public class TaskHistoryComp implements Comparator<HistoryItem>{
    
    @Override
    public int compare(HistoryItem item1, HistoryItem item2) {
        double itemDate1 = Double.parseDouble(item1.getTaskDate().replaceAll("-", ""));
        double itemDate2 = Double.parseDouble(item2.getTaskDate().replaceAll("-", ""));
        if(itemDate1 > itemDate2){
            return 1;
        } else {
            return -1;
        }
    } 
}
