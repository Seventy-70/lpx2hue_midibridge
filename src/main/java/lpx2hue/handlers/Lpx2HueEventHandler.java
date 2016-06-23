package lpx2hue.handlers;

import lpx2hue.beans.HueSettings;
import lpx2hue.beans.RecordingLightSettings;
import lpx2hue.events.ControlChange;
import lpx2hue.events.Note;
import lpx2hue.events.SysexMessage;
import lpx2hue.hue.HueBridgeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Created by nijhora1 on 15/06/16.
 */
public class Lpx2HueEventHandler {
    boolean recordLightOn = false;

    private ApplicationContext context;

    @Autowired
    public Lpx2HueEventHandler(ApplicationContext ctx) {
        context = ctx;
    }

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
                HueBridgeController controller = HueBridgeController.getInstance();
                controller.configure(context.getBean(HueSettings.class),context.getBean(RecordingLightSettings.class));

                controller.switchOnHueLight();
            } else if (recordLightTrigger && !recordLightOn) {
                System.out.println("Received RECORDING LIGHT OFF from LPX ...");
                HueBridgeController controller = HueBridgeController.getInstance();
                controller.configure(context.getBean(HueSettings.class),context.getBean(RecordingLightSettings.class));

                controller.switchOffHueLight();
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
