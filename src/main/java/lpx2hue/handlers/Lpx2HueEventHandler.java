package lpx2hue.handlers;

import lpx2hue.events.ControlChange;
import lpx2hue.events.Note;
import lpx2hue.events.SysexMessage;
import lpx2hue.hue.HueBridgeController;

/**
 * Created by nijhora1 on 15/06/16.
 */
public class Lpx2HueEventHandler {
    boolean recordLightOn = false;

    public Lpx2HueEventHandler() {}

    public void noteOnReceived(Note note) {

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

    public void noteOffReceived(Note note) {
        System.out.println("note off " + note);
    }

    public void sysexReceived(SysexMessage msg) {
        System.out.println("sysex " + msg);
    }
}
