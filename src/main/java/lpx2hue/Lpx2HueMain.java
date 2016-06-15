package lpx2hue;


import java.util.ArrayList;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JFrame;

import static java.lang.System.exit;

/**
 * Lpx2HueMain is a class containing static methods to get a list of the available MIDI devices.
 * You can ask Lpx2HueMain about available input and output devices. You can then use the methods
 * on the returned objects to open an input or output midi port.
 *
 */
public class Lpx2HueMain {
	/**
	 *
	 * @return the list of the available input devices.
	 */
	public static MidiInputDevice[] getInputDevices() {
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
	public static MidiOutputDevice[] getOutputDevices() {
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
	public static String[] getOutputDeviceNames() {
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
	public static MidiOutputDevice getOutputDevice(String name) {
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
	public static String[] getInputDeviceNames() {
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
	public static MidiInputDevice getInputDevice(String name) {
		MidiInputDevice[] devices = getInputDevices();

		for (MidiInputDevice device : devices) {
			if (name.equals(device.getName()))
				return device;
		}

		return null;
	}

	public static void main(String args[]) {

        System.out.println("LPX2HUE Midi Bridge startup...");
        System.out.println("List of all MidiSystem Input & Output Devices:");


		int i = 0;
        MidiInputDevice selectedInputDevice = null;

		for (MidiInputDevice device : getInputDevices()) {
			System.out.println("- input device " + i + ": "  + device);
			if (device.getName().contains("LPX2HUE_MB"))
				 selectedInputDevice = device;
			i++;
		}

		i = 0;
		for (MidiDevice device : getOutputDevices()) {
			System.out.println("- output device " + i + ": " + device);
			i++;
		}

        System.out.println("Setting up midi event handler for LPX2HUE Midi Bridge...");
        if (selectedInputDevice!=null){
            Lpx2HueEventHandler handler = new Lpx2HueEventHandler();
            MidiInput input = selectedInputDevice.createInput(handler);

            // do something to hold the program
            // TODO: make program deamon
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            System.out.println("LPX2HUE Midi Bridge startup completed. Listening to events...");
        } else {
            System.out.println("Failed to connect to midi input device with name: 'LPX2HUE_MB'. Please check your Midi Studio IAC driver setup." );
            exit(0);
        }
	}


}
