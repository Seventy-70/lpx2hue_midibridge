import lpx2hue.events.ControlChange;
import lpx2hue.events.MidiEvent;
import lpx2hue.events.Note;
import lpx2hue.events.ProgramChange;
import org.junit.Test;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

import static org.junit.Assert.assertTrue;

/**
 * Created by nijhora1 on 15/06/16.
 */
public class MidiEventTests {
    @Test
    public void testCreateNote(){
        ShortMessage sm = new ShortMessage();
        try {
            sm.setMessage(ShortMessage.NOTE_ON, 2, 3, 4);
            assertTrue (MidiEvent.create(sm) instanceof Note);
        } catch (InvalidMidiDataException imde){
            imde.printStackTrace();
        }
    }

    @Test
    public void testCreateControlChange(){
        ShortMessage sm = new ShortMessage();
        try {
            sm.setMessage(ShortMessage.CONTROL_CHANGE, 2, 3, 4);
            assertTrue (MidiEvent.create(sm) instanceof ControlChange);
        } catch (InvalidMidiDataException imde){
            imde.printStackTrace();
        }
    }

    @Test
    public void testCreateProgramChange(){
        ShortMessage sm = new ShortMessage();
        try {
            sm.setMessage(ShortMessage.PROGRAM_CHANGE, 2, 3, 4);
            assertTrue (MidiEvent.create(sm) instanceof ProgramChange);
        } catch (InvalidMidiDataException imde){
            imde.printStackTrace();
        }
    }

    @Test
    public void testGetDataMethodsNoteOn(){
        ShortMessage sm = new ShortMessage();
        int data1 = 1;
        int data2 = 2;
        try {
            sm.setMessage(ShortMessage.NOTE_ON, 1, data1, data2);
            MidiEvent me = MidiEvent.create(sm);

            assertTrue ( me.getData1() == (data1 & 0xFF) );
            assertTrue ( me.getData2() == (data2 & 0xFF) );

        } catch (InvalidMidiDataException imde){
            imde.printStackTrace();
        }
    }

}
