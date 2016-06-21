package lpx2hue.events;

import lpx2hue.iodevice.MidiInput;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

/**
 * Simple wrapper around MIDI messages, used to abstract from the actual bytes and provide a
 * more symbolic representation of the MIDI data. This class is used as a superclass
 */
public class MidiEvent extends ShortMessage {
    public static final int SYSEX_START = 0xF0;
    public static final int SYSEX_END = 0xF7;
    public static final int NOTE_OFF = 0x80;
    public static final int NOTE_ON = 0x90;
    public static final int CONTROL_CHANGE = 0xB0;
    public static final int PROGRAM_CHANGE = 0xC0;
    private int midiChannel = 0;

    MidiInput input = null;

    public MidiEvent(byte[] data) {
        super(data);
    }

    public MidiEvent(final MidiMessage _midiMessage) {
        this(_midiMessage.getMessage());
    }

    public MidiEvent(int command, int number, int value) {
        this(new byte[]{(byte) NOTE_ON, 0, 0});
        try {
            setMessage(command | midiChannel, number, value);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the input on which this message was received.
     */
    public MidiInput getInput() {
        return input;
    }

    public void setInput(MidiInput _input) {
        input = _input;
    }

    /**
     * @return the first data byte of this message
     */
    public int getData1() {
        if (length > 1) {
            return (data[1] & 0xFF);
        }
        return 0;
    }

    void setData1(final int _data1) {
        data[1] = (byte) (_data1 & 0xFF);
    }

    /**
     * @return the second data byte of this message
     */
    public int getData2() {
        if (length > 2) {
            return (data[2] & 0xFF);
        }
        return 0;
    }

    void setData2(final int _data2) {
        data[2] = (byte) (_data2 & 0xFF);
    }

    public static MidiEvent create(MidiMessage msg) {
        if (msg instanceof javax.sound.midi.SysexMessage)
            return new SysexMessage((javax.sound.midi.SysexMessage) msg);
        else if (msg instanceof ShortMessage) {
            ShortMessage smsg = (ShortMessage) msg;
            final int midiCommand = smsg.getCommand();
            final int midiChannel = smsg.getChannel();
            final int midiData1 = smsg.getData1();
            final int midiData2 = smsg.getData2();

            if (midiCommand == MidiEvent.NOTE_ON && midiData2 > 0) {
                return new Note(midiCommand, midiChannel, midiData1, midiData2);
            } else if (midiCommand == MidiEvent.NOTE_OFF || ((midiCommand == NOTE_ON) && (midiData2 == 0))) {
                return new Note(midiCommand, midiChannel, midiData1, midiData2);
            } else if (midiCommand == MidiEvent.CONTROL_CHANGE) {
                return new ControlChange(midiChannel, midiData1, midiData2);
            } else if (midiCommand == MidiEvent.PROGRAM_CHANGE) {
                return new ProgramChange(midiData1);
            }
        }
        return null;
    }
}
