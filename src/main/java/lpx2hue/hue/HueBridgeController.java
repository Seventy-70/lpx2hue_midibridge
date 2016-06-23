package lpx2hue.hue;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lpx2hue.beans.HueSettings;
import lpx2hue.beans.RecordingLightSettings;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by nijhora1 on 15/06/16.
 */
@Component
public class HueBridgeController {

    private static HueBridgeController instance = null;

    private HueSettings hs;
    private RecordingLightSettings rls;
    private String lightResource;
    private String origLightState = null;


    private  HueBridgeController(){
        // defeat instantiation
    }

    public static HueBridgeController getInstance(){
        if(instance==null){
            instance = new HueBridgeController();
        }
        return instance;
    }

    @Autowired
    public void configure(HueSettings hueSettings, RecordingLightSettings recordingLightSettings){
        hs = hueSettings;
        rls = recordingLightSettings;
        lightResource = hs.getBridgeAddress()+hs.getUser()+"/lights/"+hs.getLight();
    }


    public void switchOnHueLight() throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        origLightState = getCurrentLightState();
        HttpPut request = new HttpPut(lightResource+"/state");
        CloseableHttpResponse response = null;
        System.out.println("Send light on request to Hue");
        try {
            StringEntity input = new StringEntity("{\"on\": true,\"hue\": "+rls.getOnHue()+",\"sat\": "+rls.getOnSat()+"}");
            input.setContentType("application/json");
            request.setEntity(input);
            response = client.execute(request);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (response!=null)
                response.close();
            client.close();
        }
    }

    public void switchOffHueLight() throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPut request = new HttpPut(lightResource+"/state");
        CloseableHttpResponse response = null;
        System.out.println("Send light off request to Hue");
        try {
            StringEntity input;

            if(rls.isOff2Orig() && origLightState!=null)
                input = new StringEntity(origLightState);
            else
                input = new StringEntity("{\"on\": true,\"hue\": "+rls.getOffHue()+",\"sat\": "+rls.getOffSat()+"}");

            input.setContentType("application/json");
            request.setEntity(input);

            response = client.execute(request);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (response!=null)
                response.close();
            client.close();
        }
    }

    private String getCurrentLightState() throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet getRequest = new HttpGet(lightResource);
        CloseableHttpResponse response = null;
        System.out.println("fetching current light state");
        try{
            response = client.execute(getRequest);
            InputStream stream = response.getEntity().getContent();

            String lightStateJson = IOUtils.toString(stream, "UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootnode = mapper.readTree(lightStateJson);

            JsonNode state = rootnode.get("state");
            String strState = mapper.writeValueAsString(state);
            System.out.println("light state: " + strState);
            response.close();
            client.close();
            return strState;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response!=null)
                response.close();
            client.close();
        }
        return null;
    }
}
