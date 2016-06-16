package lpx2hue.beans;

/**
 * Created by nijhora1 on 15/06/16.
 */
public class RecordingLightSettings {
    private int onHue;
    private int onBri;
    private int onSat;

    private int offHue;
    private int offBri;
    private int offSat;

    public int getOnHue() {
        return onHue;
    }

    public void setOnHue(int onHue) {
        this.onHue = onHue;
    }

    public int getOnBri() {
        return onBri;
    }

    public void setOnBri(int onBri) {
        this.onBri = onBri;
    }

    public int getOnSat() {
        return onSat;
    }

    public void setOnSat(int onSat) {
        this.onSat = onSat;
    }

    public int getOffHue() {
        return offHue;
    }

    public void setOffHue(int offHue) {
        this.offHue = offHue;
    }

    public int getOffBri() {
        return offBri;
    }

    public void setOffBri(int offBri) {
        this.offBri = offBri;
    }

    public int getOffSat() {
        return offSat;
    }

    public void setOffSat(int offSat) {
        this.offSat = offSat;
    }
}
