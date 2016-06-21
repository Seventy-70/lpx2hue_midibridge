package lpx2hue.events;

import javax.sound.midi.ShortMessage;

/**
 * Wrapper around Program Change messages. You can access the Program Change number by using the
 * ProgramChange-getNumber method.
 *
 */
public class ProgramChange extends MidiEvent {
	/**
	 * Create a Program Change message
	 * @param number the program change number
	 */
	public ProgramChange(final int number){
		super(ShortMessage.PROGRAM_CHANGE, number,-1);
	}
	
	/**
	 * 
	 * @return the program change number of this message
	 */
	public int getNumber(){
		return getData1();
	}
	
	public String toString() {
		return "ProgramChange " + getNumber();
	}
}
