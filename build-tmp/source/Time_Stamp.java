import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import themidibus.*; 
import javax.sound.midi.MidiMessage; 
import javax.sound.midi.SysexMessage; 
import javax.sound.midi.ShortMessage; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Time_Stamp extends PApplet {

//\u30e6\u30fc\u30b6\u306e\u5165\u529b\u3057\u305f\u97f3\u306e\u5024\u3068\u3001\u6642\u9593\u3092\u8a18\u9332\u3059\u308b
 //Import the library
 //Import the MidiMessage classes http://java.sun.com/j2se/1.5.0/docs/api/javax/sound/midi/MidiMessage.html



//\u4e3b\u306b\u697d\u8b5c\u306e\u97f3\u3092\u7ba1\u7406\u3059\u308b\u7528
ScoreNote[][]note = new ScoreNote[4][8];//note[y\u8ef8\u5411\u304d\u306b\u6bb5\u6570][x\u8ef8\u5411\u304d\u306b\u97f3\u6570
int note_y, note_x = 0;

//midi\u7528
MidiBus myBus; //The MidiBus
int pitchbend, notebus_different = 0;//note_y\u306f\u6bb5\u843d\u6570\u3001note_x\u3067\u6bb5\u843d\u5185\u306e\u4f55\u756a\u76ee\u3092\u5f3e\u3044\u3066\u3044\u308b\u304b\u7ba1\u7406

int channel = 0;
int pitch = 64;
int velocity = 127;
int status_byte = 0xA0; // For instance let us send aftertouch
int channel_byte = 0; // On channel 0 again
int first_byte = 64; // The same note;
int second_byte = 80; // But with less velocity

ArrayList<ScoreNote> played_note;//pitchbend\u3067\u5f97\u305f\u3069\u306e\u7a0b\u5ea6\u305a\u308c\u3066\u3044\u308b\u304b\u3092\u5165\u308c\u308b\u305f\u3081\u306e\u914d\u5217\u3092\u7528\u610f

//\u6642\u523b
boolean flag = false;

//txt\u30d5\u30a1\u30a4\u30eb\u51fa\u529b\u306b\u5fc5\u8981\u306a\u914d\u5217
ArrayList<String> note_number = new ArrayList<String>();
ArrayList<String> now_number = new ArrayList<String>();
ArrayList<String> count = new ArrayList<String>();
ArrayList<String> note_velocity = new ArrayList<String>();
ArrayList<String> result = new ArrayList<String>();
ArrayList<String> pitche_bend = new ArrayList<String>();

float mill;
int note_num;
int now_num;
int note_vel;
public void setup() {
//\u753b\u9762
  // \u753b\u9762\u30b5\u30a4\u30ba\u3092\u6c7a\u5b9a

//midibus\u7528
  MidiBus.list(); // List all available Midi devices on STDOUT. This will show each device's index and name.
  myBus = new MidiBus(this, 0, 0); // Create a new MidiBus object

  //Pointer NoteName = new Pointer (NoteNumber, PointerX, PointerY);
 Pointer A4 = new Pointer(69, -200, -200);
 Pointer B4 = new Pointer(71, 411, 459);
 Pointer C5 = new Pointer(73, 417, 554);
 Pointer D5 = new Pointer(74, 419, 594);
 Pointer E5 = new Pointer(76, -200, -200);
 Pointer F5 = new Pointer(78, 431, 463);
 Pointer G5 = new Pointer(79, 432, 513);
 Pointer A5 = new Pointer(81, 436, 604);

//note[note_y][note_x] = new Note(all_score_PositionX, \u00d7\u306e\u521d\u671f\u8a2d\u5b9a, NoteName);
  note[0][0] = new ScoreNote(919, 0, A4);
  note[0][1] = new ScoreNote(1044, 0, B4);
  note[0][2] = new ScoreNote(1172, 0, C5);
  note[0][3] = new ScoreNote(1299, 0, D5);
  note[0][4] = new ScoreNote(1443, 0, E5);
  note[0][5] = new ScoreNote(1577, 0, F5);
  note[0][6] = new ScoreNote(1712, 0, G5);
  note[0][7] = new ScoreNote(1846, 0, A5);

  note[1][0] = new ScoreNote(919, 0, A4);
  note[1][1] = new ScoreNote(1044, 0, B4);
  note[1][2] = new ScoreNote(1172, 0, C5);
  note[1][3] = new ScoreNote(1299, 0, D5);
  note[1][4] = new ScoreNote(1443, 0, E5);
  note[1][5] = new ScoreNote(1577, 0, F5);
  note[1][6] = new ScoreNote(1712, 0, G5);
  note[1][7] = new ScoreNote(1846, 0, A5);

  note[2][0] = new ScoreNote(919, 0, A4);
  note[2][1] = new ScoreNote(1044, 0, B4);
  note[2][2] = new ScoreNote(1172, 0, C5);
  note[2][3] = new ScoreNote(1299, 0, D5);
  note[2][4] = new ScoreNote(1443, 0, E5);
  note[2][5] = new ScoreNote(1577, 0, F5);
  note[2][6] = new ScoreNote(1712, 0, G5);
  note[2][7] = new ScoreNote(1846, 0, A5);

  note[3][0] = new ScoreNote(919, 0, A4);
  note[3][1] = new ScoreNote(1044, 0, B4);
  note[3][2] = new ScoreNote(1172, 0, C5);
  note[3][3] = new ScoreNote(1299, 0, D5);
  note[3][4] = new ScoreNote(1443, 0, E5);
  note[3][5] = new ScoreNote(1577, 0, F5);
  note[3][6] = new ScoreNote(1712, 0, G5);
  note[3][7] = new ScoreNote(1846, 0, A5);

  myBus.sendNoteOn(channel, pitch, velocity); // Send a Midi noteOn
  myBus.sendNoteOff(channel, pitch, velocity); // Send a Midi nodeOff
  myBus.sendMessage(status_byte, channel_byte, first_byte, second_byte);
  myBus.sendMessage(
    new byte[] {
    (byte)0xF0, (byte)0x1, (byte)0x2, (byte)0x3, (byte)0x4, (byte)0xF7
    }
    );
  try { 
    SysexMessage message = new SysexMessage();
    message.setMessage(
      0xF0, 
      new byte[] {
      (byte)0x5, (byte)0x6, (byte)0x7, (byte)0x8, (byte)0xF7
      }, 
      5
      );
    myBus.sendMessage(message);
  } catch(Exception e) {
  }
 
}

public void draw(){
 background(0);
 mill = millis(); 
}

//midibus\u3092\u7ba1\u7406\u3057\u3066\u3044\u308b
public void rawMidi(byte[] data) { // You can also use rawMidi(byte[] data, String bus_name) 
  println();
  print("Status Byte/MIDI Command:"+(int)(data[0] & 0xFF));
  if (((int)(data[0] & 0xFF) >= 224)&&((int)(data[0] & 0xFF) <= 227)) {
    pitchbend = (int)(data[2] & 0xFF) * 128 + (int)(data[1] & 0xFF);
  } 
 for (int i = 1; i < data.length; i++) {
    print(": "+(i+1)+": "+(int)(data[i] & 0xFF));
 }
 for (int i = 1; i < data.length; i++) {
 print(": "+(i+1)+": "+(int)(data[i] & 0xFF));
  }
if (((int)(data[0] & 0xFF) >= 144)&&((int)(data[0] & 0xFF) <= 171)) {
    notebus_different=((data[1] & 0xFF)-(note[note_y][note_x].pointer()).MidiValue())*333+pitchbend-8192;
    note[note_y][note_x].addNote(notebus_different);
  }
if(((int)(data[0] & 0xFF) >= 143)&&((int)(data[0] & 0xFF) <= 150)) {
  //println("velocity:" +(int)(data[2] & 0xFF));
  note_vel = (int)(data[2] & 0xFF);
}
if (((int)(data[0] & 0xFF) >= 128)&&((int)(data[0] & 0xFF) <= 131)) {
    println();
    
    note_num = (note[note_y][note_x].pointer()).MidiValue();
    now_num = (int)(data[1] & 0xFF);
    
 if ((int)(data[1] & 0xFF)!=(note[note_y][note_x].pointer()).MidiValue()) {
    note[note_y][note_x].judge = 1;    
    }
    if ((int)(data[1] & 0xFF)==(note[note_y][note_x].pointer()).MidiValue()) {
      note_x++;
      
      if (note_x!=0&&note_x==8) {
        note_y++;
        note_x=0;
        if (note_y>3) {
         
          note_y=0;
        }
      }
    }
  }
  if((int)(data[0] & 0xFF) >= 0){
    flag = true;
    if(flag == true){
    note_number.add(Integer.toString(note_num));
    now_number.add(Integer.toString(now_num));
    count.add(""+mill);
    note_velocity.add(Integer.toString(note_vel));
    pitche_bend.add(Integer.toString(notebus_different));
    
  }
    flag = false;
   
  }
}

public void keyPressed() {
	if (key == 's' || key=='S') {	
	//txt\u30d5\u30a1\u30a4\u30eb\u7528
  //\u305d\u308c\u305e\u308c\u306e\u884c\u306b\u6587\u5b57\u5217\u3092\u30d5\u30a1\u30a4\u30eb\u3078\u66f8\u304d\u8fbc\u3080\u3002
  for(int i = 0; i < count.size() ; i++){
	result.add(note_number.get(i) + "," + now_number.get(i) + "," + pitche_bend.get(i) + "," + note_velocity.get(i) + "," +count.get(i));
}
  saveStrings("processing_data.txt", (String[])result.toArray(new String[result.size()-1])); 
}
}

class Pointer{
  private int midi_value;
  private int pos_x;
  private int pos_y;
  float random_value = 60*random(0,3);

  Pointer(int midi_value, int pos_x, int pos_y){
    this.midi_value = midi_value;
    this.pos_x = pos_x;
    this.pos_y = pos_y;
  }

  public int MidiValue(){
  	return this.midi_value;
  }
  public int PosX(){
  	return this.pos_x;
  }
  public int PosY(){
  	return this.pos_y;
  }
}
class ScoreNote {
  private int x;
  private int judge;
  private Pointer pointer;
  private ArrayList<Integer> played_note = new ArrayList();


 ScoreNote(int x, int judge, Pointer pointer){
 	this.x = x;
 	this.judge = judge;
 	this.pointer = pointer;
 }

 public int getX() {
    return this.x;
  }
  public int Judge() {
    return this.judge;
  }
  public Pointer pointer() {
    return this.pointer;
  }

  public void addNote(int n)
  {
    if (n<-300) {
      n=0;
    } else if ((-300<=n)&&(n<-270)) {
      n=1;
    } else if ((-270<=n)&&(n<-240)) {
      n=2;
    } else if ((-240<=n)&&(n<-210)) {
      n=3;
    } else if ((-210<=n)&&(n<-180)) {
      n=4;
    } else if ((-180<=n)&&(n<-150)) {
      n=5;
    } else if ((-150<=n)&&(n<-120)) {
      n=6;
    } else if ((-120<=n)&&(n<-90)) {
      n=7;
    } else if ((-90<=n)&&(n<-60)) {
      n=8;
    } else if ((-60<=n)&&(n<-30)) {
      n=9;
    } else if ((-30<=n)&&(n<0)) {
      n=10;
    } else if ((0<=n)&&(n<30)) {
      n=11;
    } else if ((30<=n)&&(n<60)) {
      n=12;
    } else if ((60<=n)&&(n<90)) {
      n=13;
    } else if ((90<=n)&&(n<120)) {
      n=14;
    } else if ((120<=n)&&(n<150)) {
      n=15;
    } else if ((150<=n)&&(n<180)) {
      n=16;
    } else if ((180<=n)&&(n<210)) {
      n=17;
    } else if ((210<=n)&&(n<240)) {
      n=18;
    } else if ((240<=n)&&(n<270)) {
      n=19;
    } else if ((270<=n)&&(n<300)) {
      n=20;
    } else if (300<=n) {
      n=21;
    }
    played_note.add(n);
  }

  public int getNote(int m) {
    return this.played_note.get(m);
  }

}
  public void settings() {  fullScreen(P2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Time_Stamp" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
