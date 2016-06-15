package lpx2hue;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * Created by nijhora1 on 15/06/16.
 */
public class HueBridgeController {

    public static void switchOnHueLight() throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPut request = new HttpPut("http://localhost:4772/api/newdeveloper/lights/1/state");
        CloseableHttpResponse response;
        System.out.println("Send light on request to Hue");
        try {
            StringEntity input = new StringEntity("{\"on\": true,\"hue\": 65000,\"sat\": 254}");
            input.setContentType("application/json");

            request.setEntity(input);

            response = client.execute(request);


        } catch (Exception e){
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    public static void switchOffHueLight() throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPut request = new HttpPut("http://localhost:4772/api/newdeveloper/lights/1/state");
        CloseableHttpResponse response;
        System.out.println("Send light off request to Hue");
        try {
            StringEntity input = new StringEntity("{\"on\": true,\"hue\": 30000,\"sat\": 254}");
            input.setContentType("application/json");

            request.setEntity(input);

            response = client.execute(request);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            client.close();
        }
    }
}
