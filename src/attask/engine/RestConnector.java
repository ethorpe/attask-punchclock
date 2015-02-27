/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attask.engine;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 *
 * @author Eric
 */
public class RestConnector {
    
    public static ClientResponse getData(String restUri)
    {
      try {
            Client client = Client.create();
            WebResource webResource = client.resource(restUri);
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
            return response;
           } catch (Exception e) {
            e.printStackTrace();
           }
      return null;
    }
    
    public static String putData(String restUri)
    {
      try {
            Client client = Client.create();
            WebResource webResource = client.resource(restUri);
            ClientResponse response = webResource.accept("application/json").post(ClientResponse.class);
            return getResponse(response);
           } catch (Exception e) {
            e.printStackTrace();
           }
      return "Error occured";
    }
    
    private static String getResponse(ClientResponse response)
    {
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        return response.getEntity(String.class);
    }
}
