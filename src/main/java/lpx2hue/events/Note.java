package lpx2hue.events;

import lpx2hue.events.MidiEvent;

/**
 *
 */
public class Note extends MidiEvent {
	/**
	 * Create a Note object.
	 * @param i_pitch the pitch of the note
	 * @param i_velocity the velocity of the note
	 */
	public Note(final int i_pitch, final int i_velocity){
		super(NOTE_ON, i_pitch, i_velocity);
	}
	
	public Note(int midiChannel, int _pitch, int _velocity) {
		super(NOTE_ON | midiChannel, _pitch, _velocity);
	}

	public Note(int midiCommand, int midiChannel, int _pitch, int _velocity) {
		super(midiCommand | midiChannel, _pitch, _velocity);
	}

	/**
	 * 
	 * @return Pitch of the note
	 */
	public int getPitch(){
		return getData1();
	}
	
    void setPitch(final int i_pitch){
		setData1(i_pitch);
    }
    
    /**
     * 
     * @return Velocity of the note
     */
	public int getVelocity(){
		return getData2();
	}

	void setVelocity(final int i_velocity){
		setData2(i_velocity);
    }
	
	public String toString() {
		return "rwmidi.Note pitch: " + getPitch() + " velocity: " + getVelocity(); 
	}
}
