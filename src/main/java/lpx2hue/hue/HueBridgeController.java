package lpx2hue.hue;

import lpx2hue.beans.ApplicationContextProvider;
import lpx2hue.beans.HueSettings;
import lpx2hue.beans.RecordingLightSettings;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by nijhora1 on 15/06/16.
 */
@Component
public class HueBridgeController {

    private HueSettings hs;
    private RecordingLightSettings rls;
    private String lightResource;

    private ApplicationContext context;

    @Autowired
    public HueBridgeController(HueSettings hueSettings, RecordingLightSettings recordingLightSettings){
        hs = hueSettings;
        rls = recordingLightSettings;
        lightResource = hs.getBridgeAddress()+hs.getUser()+hs.getLight();
    }

    public void switchOnHueLight() throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPut request = new HttpPut(lightResource);
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
        HttpPut request = new HttpPut(lightResource);
        CloseableHttpResponse response = null;
        System.out.println("Send light off request to Hue");
        try {
            StringEntity input = new StringEntity("{\"on\": true,\"hue\": "+rls.getOffHue()+",\"sat\": "+rls.getOffSat()+"}");
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
}
