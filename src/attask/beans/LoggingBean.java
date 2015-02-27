/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package attask.beans;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Eric
 */
public class LoggingBean {
    
     public void logActivity(String userActivity) throws IOException
     {
        Logger logger = Logger.getLogger("ATTaskClientLog");  
        FileHandler fh;
         try {  

            // This block configure the logger with handler and formatter  
            fh = new FileHandler("ATTaskClient.log");  
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  

            // the following statement is used to log any messages  
            logger.info(userActivity);  

    } catch (SecurityException e) {  
        e.printStackTrace();  
    } catch (IOException e) {  
        e.printStackTrace();  
    }  
   }
    
}
