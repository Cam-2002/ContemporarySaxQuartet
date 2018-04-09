package csq;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import javax.sound.midi.*;

public class Main {
    public static JFrame frame = new JFrame();
    public static JPanel panel = new JPanel();
    public static JLabel rangeL = new JLabel("Range: ");
    public static JLabel rhythmsL = new JLabel("Rhythms: ");
    public static JLabel lengthL = new JLabel("Quarter Notes: ");
    public static JLabel saxophonesL = new JLabel("Saxophones: ");
    public static JLabel keysL = new JLabel("Follow Scale:");
    public static JComboBox<String> rangeSP = new JComboBox<String>();
    public static JComboBox<String> rhythmsSP = new JComboBox<String>();
    public static JComboBox<String> keysSP = new JComboBox<String>();
    public static JCheckBox sopCH = new JCheckBox("Soprano");
    public static JCheckBox altoCH = new JCheckBox("Alto");
    public static JCheckBox tenorCH = new JCheckBox("Tenor");
    public static JCheckBox bariCH = new JCheckBox("Baritone");
    public static JTextField lengthTF = new JTextField("16");
    public static JButton generate = new JButton("Generate");
    public static int length = 3840*2;
    public static int tempo = 120;
    public static double HR = 1, WR = 1;
    public static String title = "Untitled";
    public static String[] titles = {"Modern","Postmodern","Minimalist","Polystylistic","Contemporary","Progressive","Avant-Garde","Urban"};
    public static String[] titleNouns = {"Chant","Jazz","Funk","Folk Songs","Dances","Poetry","Ballad"};
    public static int[][] keySigsMajor = {
        {0,2,4,5,7,9,11},
        {1,3,5,6,8,10,0},
        {2,4,6,7,9,11,1},
        {3,5,7,8,10,0,2},
        {4,6,8,9,11,1,3},
        {5,7,9,10,0,2,4},
        {6,8,10,11,1,3,5},
        {7,9,11,0,2,4,6},
        {8,10,0,1,3,5,7},
        {9,11,1,2,4,6,8},
        {10,0,2,3,5,7,9},
        {11,1,3,4,6,8,10}
    };
    public static int[] keySig;
    public static void main(String[] args) {
        sopCH.setSelected(true);
        altoCH.setSelected(true);
        tenorCH.setSelected(true);
        bariCH.setSelected(true);
        panel.setPreferredSize(new Dimension(200,300));
        panel.setLayout(null);
        panel.add(rangeL);
        panel.add(rhythmsL);
        panel.add(lengthL);
        panel.add(saxophonesL);
        panel.add(rangeSP);
        panel.add(rhythmsSP);
        panel.add(lengthTF);
        panel.add(sopCH);
        panel.add(altoCH);
        panel.add(tenorCH);
        panel.add(bariCH);
        panel.add(generate);
        panel.add(keysL);
        panel.add(keysSP);
        
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                HR = (double)panel.getHeight()/300;
                WR = (double)panel.getWidth()/200;
                rangeL.setBounds((int)(4*WR),(int)(4*HR),(int)(92*WR),(int)(22*HR));
                rangeSP.setBounds((int)(104*WR),(int)(4*HR),(int)(92*WR),(int)(22*HR));
                rhythmsL.setBounds((int)(4*WR),(int)(34*HR),(int)(92*WR),(int)(22*HR));
                rhythmsSP.setBounds((int)(104*WR),(int)(34*HR),(int)(92*WR),(int)(22*HR));
                keysL.setBounds((int)(4*WR),(int)(64*HR),(int)(92*WR),(int)(22*HR));
                keysSP.setBounds((int)(104*WR),(int)(64*HR),(int)(92*WR),(int)(22*HR));
                lengthL.setBounds((int)(4*WR),(int)(94*HR),(int)(92*WR),(int)(22*HR));
                lengthTF.setBounds((int)(104*WR),(int)(94*HR),(int)(92*WR),(int)(22*HR));
                saxophonesL.setBounds((int)(4*WR),(int)(124*HR),(int)(192*WR),(int)(22*HR));
                sopCH.setBounds((int)(24*WR),(int)(154*HR),(int)(162*WR),(int)(22*HR));
                altoCH.setBounds((int)(24*WR),(int)(184*HR),(int)(162*WR),(int)(22*HR));
                tenorCH.setBounds((int)(24*WR),(int)(214*HR),(int)(162*WR),(int)(22*HR));
                bariCH.setBounds((int)(24*WR),(int)(244*HR),(int)(162*WR),(int)(22*HR));
                generate.setBounds((int)(4*WR),(int)(274*HR),(int)(192*WR),(int)(22*HR));
            }
        });
        
        rangeSP.addItem("Normal");
        rangeSP.addItem("Altissimo");
        rangeSP.addItem("Insane");
        
        rhythmsSP.addItem("Normal");
        rhythmsSP.addItem("Faster");
        rhythmsSP.addItem("Insane");
        
        keysSP.addItem("Yes");
        keysSP.addItem("No");
        
        generate.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    length = Integer.parseInt(lengthTF.getText())*480;
                    Random r = new Random();
                    if(r.nextInt(3) == 1){
                        title = titles[r.nextInt(titles.length)] + " " + titles[r.nextInt(titles.length)] + " " + titles[r.nextInt(titles.length)] + " " + titleNouns[r.nextInt(titleNouns.length)] + " for Sax Quartet";
                    }else{
                        title = titles[r.nextInt(titles.length)] + " " + titles[r.nextInt(titles.length)] + " " + titleNouns[r.nextInt(titleNouns.length)] + " for Sax Quartet";
                    }
                    if(keysSP.getSelectedIndex() == 0) keySig = keySigsMajor[r.nextInt(keySigsMajor.length)];
                    //else if(keysSP.getSelectedIndex() == 1) keySig = keySigsMinor[r.nextInt(keySigsMinor.length)];
                    else keySig = new int[]{0,1,2,3,4,5,6,7,8,9,10,11};
                    generateContemporaryMidi();
                }catch(NumberFormatException ex){
                    lengthTF.setForeground(Color.red);
                }
            }
        });
        
        lengthTF.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                UIDefaults defaults = javax.swing.UIManager.getDefaults();
                lengthTF.setForeground(defaults.getColor("List.selectionForeground"));
            }
        });
        
        frame.add(panel);
        frame.setTitle("Contemporary Sax Quartet Generator");
        frame.setDefaultCloseOperation(3);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        generate.requestFocusInWindow();
    }
    public static void generateContemporaryMidi(){
        int range = rangeSP.getSelectedIndex();
        Random r = new Random();
        CustomSequence cs = new CustomSequence();
        int[] rhythms = {1920,960,480,480*3,240,240*3,120,120*3}; //Normal
        if(rhythmsSP.getSelectedIndex() == 1){ //Faster
            rhythms = new int[]{1920,960,960*3,480,480*3,240,240*3,120,120*3,60,60*3};
        }
        if(rhythmsSP.getSelectedIndex() == 2){ //Insane
            rhythms = new int[]{3840,1920,960,960*3,480,480*3,240,240*3,120,120*3,60,60*3,30,30*3,15,15*3};
        }
        if(sopCH.isSelected()){
            cs.addTrack();
            cs.changeInstrument(0, 64);
            if(range == 2){//insane
                int cl = 0;
                while(cl < length){
                    int note = r.nextInt(88+24-56)+56-12;
                    if(!inKeySig(note,keySig)) note++;
                    if(note > 127) note = 126;
                    int rhythm = rhythms[r.nextInt(rhythms.length)];
                    if(rhythm > length-cl) rhythm = length-cl;
                    cs.addNote(note, 0, cl, rhythm, 64);
                    cl+=rhythm;
                }
            }
            if(range == 1){//altissimo
                int cl = 0;
                while(cl < length){
                    int note = r.nextInt(88+12-56)+56;
                    if(!inKeySig(note,keySig)) note++;
                    if(note > 127) note = 126;
                    int rhythm = rhythms[r.nextInt(rhythms.length)];
                    if(rhythm > length-cl) rhythm = length-cl;
                    cs.addNote(note, 0, cl, rhythm, 64);
                    cl+=rhythm;
                }
            }
            if(range == 0){//normal
                int cl = 0;
                while(cl < length){
                    int note = r.nextInt(88-56)+56;
                    if(!inKeySig(note,keySig)) note++;
                    if(note > 127) note = 126;
                    int rhythm = rhythms[r.nextInt(rhythms.length)];
                    if(rhythm > length-cl) rhythm = length-cl;
                    cs.addNote(note, 0, cl, rhythm, 64);
                    cl+=rhythm;
                }
            }
        }
        if(altoCH.isSelected()){
            cs.addTrack();
            cs.changeInstrument(1, 65);
            if(range == 2){//insane
                int cl = 0;
                while(cl < length){
                    int note = r.nextInt(88+24-56)+56-7-12;
                    if(!inKeySig(note,keySig)) note++;
                    if(note > 127) note = 126;
                    int rhythm = rhythms[r.nextInt(rhythms.length)];
                    if(rhythm > length-cl) rhythm = length-cl;
                    cs.addNote(note, 1, cl, rhythm, 64);
                    cl+=rhythm;
                }
            }
            if(range == 1){//altissimo
                int cl = 0;
                while(cl < length){
                    int note = r.nextInt(88+12-56)+56-7;
                    if(!inKeySig(note,keySig)) note++;
                    if(note > 127) note = 126;
                    int rhythm = rhythms[r.nextInt(rhythms.length)];
                    if(rhythm > length-cl) rhythm = length-cl;
                    cs.addNote(note, 1, cl, rhythm, 64);
                    cl+=rhythm;
                }
            }
            if(range == 0){//normal
                int cl = 0;
                while(cl < length){
                    int note = r.nextInt(88-56)+56-7;
                    if(!inKeySig(note,keySig)) note++;
                    if(note > 127) note = 126;
                    int rhythm = rhythms[r.nextInt(rhythms.length)];
                    if(rhythm > length-cl) rhythm = length-cl;
                    cs.addNote(note, 1, cl, rhythm, 64);
                    cl+=rhythm;
                }
            }
        }
        if(tenorCH.isSelected()){
            cs.addTrack();
            cs.changeInstrument(2, 66);
            if(range == 2){//insane
                int cl = 0;
                while(cl < length){
                    int note = r.nextInt(88-56+24)+56-12-12;
                    if(!inKeySig(note,keySig)) note++;
                    if(note > 127) note = 126;
                    int rhythm = rhythms[r.nextInt(rhythms.length)];
                    if(rhythm > length-cl) rhythm = length-cl;
                    cs.addNote(note, 2, cl, rhythm, 64);
                    cl+=rhythm;
                }
            }
            if(range == 1){//altissimo
                int cl = 0;
                while(cl < length){
                    int note = r.nextInt(88-56+12)+56-12;
                    if(!inKeySig(note,keySig)) note++;
                    if(note > 127) note = 126;
                    int rhythm = rhythms[r.nextInt(rhythms.length)];
                    if(rhythm > length-cl) rhythm = length-cl;
                    cs.addNote(note, 2, cl, rhythm, 64);
                    cl+=rhythm;
                }
            }
            if(range == 0){//normal
                int cl = 0;
                while(cl < length){
                    int note = r.nextInt(88-56)+56-12;
                    if(!inKeySig(note,keySig)) note++;
                    if(note > 127) note = 126;
                    int rhythm = rhythms[r.nextInt(rhythms.length)];
                    if(rhythm > length-cl) rhythm = length-cl;
                    cs.addNote(note, 2, cl, rhythm, 64);
                    cl+=rhythm;
                }
            }
        }
        if(bariCH.isSelected()){
            cs.addTrack();
            cs.changeInstrument(3, 67);
            if(range == 2){//insane
                int cl = 0;
                while(cl < length){
                    int note = r.nextInt(88+24-56)+56-19-12;
                    if(!inKeySig(note,keySig)) note++;
                    if(note > 127) note = 126;
                    int rhythm = rhythms[r.nextInt(rhythms.length)];
                    if(rhythm > length-cl) rhythm = length-cl;
                    cs.addNote(note, 3, cl, rhythm, 64);
                    cl+=rhythm;
                }
            }
            if(range == 1){//altissimo
                int cl = 0;
                while(cl < length){
                    int note = r.nextInt(88-56+12)+56-19;
                    if(!inKeySig(note,keySig)) note++;
                    if(note > 127) note = 126;
                    int rhythm = rhythms[r.nextInt(rhythms.length)];
                    if(rhythm > length-cl) rhythm = length-cl;
                    cs.addNote(note, 3, cl, rhythm, 64);
                    cl+=rhythm;
                }
            }
            if(range == 0){//normal
                int cl = 0;
                while(cl < length){
                    int note = r.nextInt(88-56)+56-19;
                    if(!inKeySig(note,keySig)) note++;
                    if(note > 127) note = 126;
                    int rhythm = rhythms[r.nextInt(rhythms.length)];
                    if(rhythm > length-cl) rhythm = length-cl;
                    cs.addNote(note, 3, cl, rhythm, 64);
                    cl+=rhythm;
                }
            }
        }
        //cs.playSequence();
        try {
            String file = title.replaceAll(" ", "_") + ".mid";
            File f = new File(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath() + File.separator + file);
            MidiSystem.write(cs.getSequence(), 1, f);
            System.out.println("Saved to " + f.getPath() + ".");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static boolean inKeySig(int note, int[] keySig){
        for(int i:keySig){
            if(i==note%12){
                return true;
            }
        }
        return false;
    }
}
