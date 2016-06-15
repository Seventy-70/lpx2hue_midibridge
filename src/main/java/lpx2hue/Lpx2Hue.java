package lpx2hue;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JFrame;

/**
 * Lpx2Hue is a class containing static methods to get a list of the available MIDI devices.
 * You can ask Lpx2Hue about available input and output devices. You can then use the methods
 * on the returned objects to open an input or output midi port.
 *
 */
public class Lpx2Hue {
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
		class Foobar {

            boolean recordLightOn = false;

			Foobar() {

			}

			void controller(Controller ctrlr) { System.out.println("control change: " + ctrlr);}

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
                        Lpx2Hue.switchOnHueLight();
                    } else if (recordLightTrigger && !recordLightOn) {
                        System.out.println("Received RECORDING LIGHT OFF from LPX ...");
                        Lpx2Hue.switchOffHueLight();
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

		int i = 0;
		for (MidiDevice device : getInputDevices()) {
			System.out.println(i + " input device " + device);
			i++;
		}

		i = 0;
		for (MidiDevice device : getOutputDevices()) {
			System.out.println(i + " output device " + device);
			i++;
		}

		Foobar foo = new Foobar();
		MidiInput input = Lpx2Hue.getInputDevices()[0].createInput(foo);

		JFrame frame = new JFrame();


		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

    private static void switchOnHueLight() throws IOException {
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

    private static void switchOffHueLight() throws IOException {
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
