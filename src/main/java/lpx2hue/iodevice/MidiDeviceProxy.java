package lpx2hue.iodevice;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;

/**
 * Created by nijhora1 on 16/06/16.
 */
public class MidiDeviceProxy {

    /**
     *
     * @return the list of the available input devices.
     */
    public MidiInputDevice[] getInputDevices() {
        javax.sound.midi.MidiDevice.Info infos[] = MidiSystem.getMidiDeviceInfo();
        ArrayList<MidiInputDevice> result = new ArrayList<MidiInputDevice>();
        for (javax.sound.midi.MidiDevice.Info info : infos) {
            javax.sound.midi.MidiDevice device;
            try {
                device = MidiSystem.getMidiDevice(info);
                if (device.getMaxTransmitters() == 0)
                    continue;
                result.add(new MidiInputDevice(info));
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
                continue;
            }
        }
        return result.toArray(new MidiInputDevice[0]);
    }

    /**
     *
     * @return the list of the available output devices.
     */
    public MidiOutputDevice[] getOutputDevices() {
        javax.sound.midi.MidiDevice.Info infos[] = MidiSystem.getMidiDeviceInfo();
        ArrayList<MidiOutputDevice> result = new ArrayList<MidiOutputDevice>();
        for (javax.sound.midi.MidiDevice.Info info : infos) {
            javax.sound.midi.MidiDevice device;
            try {
                device = MidiSystem.getMidiDevice(info);
                if (device.getMaxReceivers() == 0)
                    continue;
                result.add(new MidiOutputDevice(info));
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
                continue;
            }
        }
        return result.toArray(new MidiOutputDevice[0]);
    }

    /**
     *
     * @return a list of the output devices names
     */
    public String[] getOutputDeviceNames() {
        MidiOutputDevice[] devices = getOutputDevices();
        ArrayList<String> result = new ArrayList<String>();
        for (MidiOutputDevice device : devices) {
            result.add(device.getName());
        }
        return result.toArray(new String[0]);
    }

    /**
     * Returns a specific output device
     * @param name the name of the output device
     * @return
     */
    public MidiOutputDevice getOutputDevice(String name) {
        MidiOutputDevice[] devices = getOutputDevices();
        for (MidiOutputDevice device : devices) {
            if (name.equals(device.getName()))
                return device;
        }
        return null;
    }

    /**
     *
     * @return a list of the output devices names
     */
    public String[] getInputDeviceNames() {
        MidiInputDevice[] devices = getInputDevices();
        ArrayList<String> result = new ArrayList<String>();
        for (MidiInputDevice device : devices) {
            result.add(device.getName());
        }
        return result.toArray(new String[0]);
    }

    /**
     * Returns a specific input device
     * @param name the name of the input device
     * @return
     */
    public MidiInputDevice getInputDevice(String name) {
        MidiInputDevice[] devices = getInputDevices();
        for (MidiInputDevice device : devices) {
            if (device.getName().contains(name))
                return device;
        }
        return null;
    }

    public void printInputDevices(){
        MidiInputDevice[] devices = getInputDevices();
        int index = 1;
        for (MidiInputDevice device : devices){
            System.out.println( index + ") input device: " + device);
            index++;
        }
    }

}
