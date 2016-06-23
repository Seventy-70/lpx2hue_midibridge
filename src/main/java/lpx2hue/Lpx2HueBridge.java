package lpx2hue;


import lpx2hue.beans.MidiSettings;
import lpx2hue.handlers.Lpx2HueEventHandler;
import lpx2hue.iodevice.MidiDeviceProxy;
import lpx2hue.iodevice.MidiInput;
import lpx2hue.iodevice.MidiInputDevice;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import static java.lang.System.exit;
import static java.lang.Thread.sleep;

/**
 * Lpx2HueMain is a class containing static methods to get a list of the available MIDI devices.
 * You can ask Lpx2HueMain about available input and output devices. You can then use the methods
 * on the returned objects to open an input or output midi port.
 *
 * written by RJN
 */

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan
public class Lpx2HueBridge {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

	public static void main(String args[]) throws InterruptedException {
		System.setProperty("apple.awt.UIElement", "true");
        System.out.println("LPX2HUE Midi Bridge startup...");
        System.out.println("List of all MidiSystem input devices:");

		ApplicationContext context = new AnnotationConfigApplicationContext(Lpx2HueBridge.class);
		MidiSettings midiSettings = context.getBean(MidiSettings.class);

		MidiDeviceProxy proxy = new MidiDeviceProxy();
		proxy.printInputDevices();
		MidiInputDevice selectedInputDevice = proxy.getInputDevice(midiSettings.getInput());

        System.out.println("Setting up midi event handler for LPX2HUE Midi Bridge...");
        if (selectedInputDevice!=null){
            Lpx2HueEventHandler handler = new Lpx2HueEventHandler(context);
            MidiInput input = selectedInputDevice.createInput(handler);
            System.out.println("LPX2HUE Midi Bridge startup completed. Listening to events...");

            while(true){
                sleep(10000);
            }
        } else {
            System.out.println("Failed to connect to midi input device with name: "+ midiSettings.getInput()+". Please check your Midi Studio IAC driver setup." );
            exit(0);
        }
	}
}
