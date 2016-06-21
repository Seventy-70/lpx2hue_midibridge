package lpx2hue.events;

/**
 * Represents a MIDI ControlChange Change message. The values are parsed into the CC number and the value
 * 
 */
public class ControlChange extends MidiEvent {
	/**
	 * Create a ControlChange Change message.
	 * @param _channel ControlChange Change channel
	 * @param _number ControlChange Change number
	 * @param _value ControlChange Change value
	 */
	public ControlChange(final int _channel, final int _number, final int _value){
		super(CONTROL_CHANGE | _channel, _number, _value);
	}

	public ControlChange(final int _number, final int _value){
		super(CONTROL_CHANGE, _number, _value);
	}

	/**
	 * 
	 * @return the CC number of the message
	 */
	public int getCC(){
		return getData1();
	}

	/**
	 * 
	 * @return the value of the CC message
	 */
	public int getValue(){
		return getData2();
	}

	public String toString() {
		return "rwmidi.ControlChange cc: " + getCC() + " value: " + getValue();
	}
}
