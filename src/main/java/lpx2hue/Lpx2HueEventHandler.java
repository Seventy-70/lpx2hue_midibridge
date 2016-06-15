package lpx2hue;

/**
 * Created by nijhora1 on 15/06/16.
 */
public class Lpx2HueEventHandler {
    boolean recordLightOn = false;

    Lpx2HueEventHandler() {}

    void controller(ControlChange ctrlr) { System.out.println("control change: " + ctrlr);}

    void noteOnReceived(Note note) {

        boolean recordLightTrigger = false;

        if ((note.getPitch()==25) && note.getVelocity()==127) {
            recordLightTrigger = true;
            recordLightOn = true;
        } else if ((note.getPitch()==25) && note.getVelocity()==0) {
            recordLightTrigger = true;
            recordLightOn = false;
        }

        try {
            if (recordLightTrigger && recordLightOn) {
                System.out.println("Received RECORDING LIGHT ON from LPX ...");
                HueBridgeController.switchOnHueLight();
            } else if (recordLightTrigger && !recordLightOn) {
                System.out.println("Received RECORDING LIGHT OFF from LPX ...");
                HueBridgeController.switchOffHueLight();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void noteOffReceived(Note note) {
        System.out.println("note off " + note);
    }

    void sysexReceived(SysexMessage msg) {
        System.out.println("sysex " + msg);
    }
}
