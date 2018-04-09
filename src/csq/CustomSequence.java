package csq;

import java.util.*;
import java.util.logging.*;
import javax.sound.midi.*;

/**
 * @author Cam
 */
public class CustomSequence {
    public static final String[] NOTE_NAMES = {"C", "CS", "D", "DS", "E", "F", "FS", "G", "GS", "A", "AS", "B"};
    public static final ArrayList<String> NOTE_NAMES_ARRAY = new ArrayList<String>(Arrays.asList(NOTE_NAMES));
    public Sequence s;
    public Track t;
    public Sequencer sr;
    public CustomSequence(){
        try {
            s = new Sequence(Sequence.PPQ,480);
            t = s.createTrack();
            sr = MidiSystem.getSequencer();
            sr.open();
            MidiMessage mm = new MetaMessage(81,new byte[]{0x07,-34,0x20},3);
            MidiEvent ev = new MidiEvent(mm,0);
            t.add(ev);
        } catch (InvalidMidiDataException | MidiUnavailableException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void addNote(String note, int channel, int octave, int start, int duration, int velocity){
        try {
            MidiMessage m = new ShortMessage(ShortMessage.NOTE_ON, channel, NOTE_NAMES_ARRAY.indexOf(note) + ((octave)+1)*12, velocity);
            MidiEvent ev = new MidiEvent(m,start);
            t.add(ev);
            MidiMessage m2 = new ShortMessage(ShortMessage.NOTE_OFF, channel, NOTE_NAMES_ARRAY.indexOf(note) + ((octave)+1)*12, 0);
            MidiEvent ev2 = new MidiEvent(m2,start+duration);
            t.add(ev2);
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(CustomSequence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }public void addNote(int note, int channel, int start, int duration, int velocity){
        try {
            MidiMessage m = new ShortMessage(ShortMessage.NOTE_ON, channel, note, velocity);
            MidiEvent ev = new MidiEvent(m,start);
            t.add(ev);
            MidiMessage m2 = new ShortMessage(ShortMessage.NOTE_OFF, channel, note, 0);
            MidiEvent ev2 = new MidiEvent(m2,start+duration);
            t.add(ev2);
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(CustomSequence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void addTrack(){
        t = s.createTrack();
    }
    public void setTrack(int n){
        t = s.getTracks()[n];
    }
    public Track[] getTracks(){
        return s.getTracks();
    }
    public void replaceTrack(int n, Track t){
        s.getTracks()[n] = t;
    }
    public void changeInstrument(int channel, int inst){
        try {
            ShortMessage sm = new ShortMessage();
            sm.setMessage(ShortMessage.PROGRAM_CHANGE, channel, inst, 0);
            t.add(new MidiEvent(sm, 0));
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(CustomSequence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void playSequence(){
        Sequence se = s;
        try {
            sr.open();
            sr.setSequence(se);
            sr.start();
        } catch (InvalidMidiDataException | MidiUnavailableException ex) {
            Logger.getLogger(CustomSequence.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(sr.isRunning()){
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(CustomSequence.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        sr.stop();
        sr.close();
    }
    public Sequence getSequence(){
        return s;
    }
}
