package lpx2hue.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

/**
 * recordinglight.on.hue=6
 * recordinglight.on.bri=2
 * recordinglight.on.sat=2
 * recordinglight.off.return2orig
 * recordinglight.off.hue=
 * recordinglight.off.bri=
 * recordinglight.off.sat=
 * <p>
 * Created by nijhora1 on 15/06/16.
 */
@Configuration
@PropertySource("classpath:application.properties")
@Component
public class RecordingLightSettings {
    @Value("${recordinglight.on.hue}")
    private String onHue;
    @Value("${recordinglight.on.bri}")
    private String onBri;
    @Value("${recordinglight.on.sat}")
    private String onSat;

    @Value("${recordinglight.off.return2orig}")
    private boolean off2Orig;
    @Value("${recordinglight.off.hue}")
    private String offHue;
    @Value("${recordinglight.off.bri}")
    private String offBri;
    @Value("${recordinglight.off.sat}")
    private String offSat;


    public String getOnHue() {
        return onHue;
    }

    public void setOnHue(String onHue) {
        this.onHue = onHue;
    }

    public String getOnBri() {
        return onBri;
    }

    public void setOnBri(String onBri) {
        this.onBri = onBri;
    }

    public String getOnSat() {
        return onSat;
    }

    public void setOnSat(String onSat) {
        this.onSat = onSat;
    }

    public boolean isOff2Orig() {
        return off2Orig;
    }

    public void setOff2Orig(boolean off2Orig) {
        this.off2Orig = off2Orig;
    }

    public String getOffHue() {
        return offHue;
    }

    public void setOffHue(String offHue) {
        this.offHue = offHue;
    }

    public String getOffBri() {
        return offBri;
    }

    public void setOffBri(String offBri) {
        this.offBri = offBri;
    }

    public String getOffSat() {
        return offSat;
    }

    public void setOffSat(String offSat) {
        this.offSat = offSat;
    }
}
