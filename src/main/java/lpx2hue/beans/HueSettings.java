package lpx2hue.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * Created by nijhora1 on 15/06/16.
 */
public class HueSettings {
    private String bridgeAddress;
    private String user;
    private String light;

    public String getBridgeAddress() {
        return bridgeAddress;
    }

    public void setBridgeAddress(String bridgeAddress) {
        this.bridgeAddress = bridgeAddress;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }
}
